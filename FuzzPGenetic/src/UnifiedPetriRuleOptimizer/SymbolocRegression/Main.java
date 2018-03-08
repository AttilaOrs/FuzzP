package UnifiedPetriRuleOptimizer.SymbolocRegression;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreaturePoolWithStreams;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedPetriRuleOptimizer.BitIndi;
import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import UnifiedPetriRuleOptimizer.Generator;
import UnifiedPetriRuleOptimizer.Mutator;
import UnifiedPetriRuleOptimizer.UnifromCrossoverBitIndi;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class Main {
  private static final String PETRI_NET = "_Petri _sym_reg.json";
  private static final String PETRI_NET_OPTIMIZATION_DATA = "_OptData_sym_reg.json";
  private static final Integer INP_ID = 433;
  private static final Integer OUT_ID = 427;

  public static void main(String args[]) {
    generateFitnes();
    for (int trying = 0; trying < 100; trying++) {
      
      ArrayList<IOperatorFactory<ICreatureGenerator<BitIndi>>> gens = new ArrayList<>();
      gens.add(() -> new Generator(masterDecoder.getNrOfRules()));

      ArrayList<IOperatorFactory<ICreatureMutator<BitIndi>>> mutators = new ArrayList<>();
      mutators.add(Mutator::new);

      ArrayList<IOperatorFactory<ICreatureBreeder<BitIndi>>> breeders = new ArrayList<>();
      breeders.add(UnifromCrossoverBitIndi::new);

      ArrayList<IOperatorFactory<ICreatureFitnes<BitIndi>>> fitnesses = new ArrayList<>();
      fitnesses.add(Main::generateFitnes);

      ICreaturePool<BitIndi> pool = new CreaturePoolWithStreams<>(gens, mutators, breeders, fitnesses);

      SimpleGA<BitIndi> algo = new SimpleGA<>(pool, new LinearRankSelection());
      SimpleGA.iteration = 10;
      SimpleGA.population = 100;
      long start = System.currentTimeMillis();
      algo.theAlgo();
      long stop = System.currentTimeMillis();

      Integer best = algo.getMaxId();
      BitIndi i = pool.get(best);
      UnifiedPetriNet newNEt = masterDecoder.modified(i);
      PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
      String jsonStr = saver.makeString(newNEt);
      PlotUtils.writeToFile("piRez_n" + trying + ".json", jsonStr);
      // FirstOrderFitnes fitnes = new FirstOrderFitnes();
      double rez = generateFitnes().evaluate(i);
      String g = Double.toString(rez) + "\n";
      g += TimeUnit.MILLISECONDS.toMinutes(stop - start) + "\n";
      g += (stop - start) + "\n";

      PlotUtils.writeToFile("rez" + trying + ".txt", g);
    }

  }

  private static BitIndiDecoder masterDecoder = null;

  private static SymbloicRegressionFitnes generateFitnes() {
    if (masterDecoder == null) {
      masterDecoder = BitIndiDecoder.initDecoderBasedOnFiles(PETRI_NET, PETRI_NET_OPTIMIZATION_DATA,
          RuleOptimizationData.OptType.Inp, RuleOptimizationData.OptType.Out, RuleOptimizationData.OptType.SplitExit);
      System.out.println("nr of rules :: " + masterDecoder.getNrOfRules());
    }

    return new SymbloicRegressionFitnes(masterDecoder.myClone(), INP_ID, OUT_ID);

  }

  private static void findInpOut() {
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<>();
    UnifiedPetriNet net = saver.load(PETRI_NET, UnifiedPetriNet.class);
    OptionalInt inp = IntStream.range(0, net.getNrOfPlaces()).filter(net::isInputPlace).findFirst();
    System.out.println(inp);
    OptionalInt out = IntStream.range(0, net.getNrOfTransition()).filter(net::isOuputTransition).findFirst();
    System.out.println(out);
  }

}
