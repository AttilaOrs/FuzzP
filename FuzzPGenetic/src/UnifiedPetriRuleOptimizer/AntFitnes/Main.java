package UnifiedPetriRuleOptimizer.AntFitnes;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

  private static final String PI_NET_JSON = "_Petri.json";
  private static final String PI_NET_OPTIMIZATION_DATA = "_OptData.json";
  private static final Integer outForwad = 240;
  private static final Integer outLeft = 249;
  private static final Integer outRight = 255;
  private static final Integer inp = 259;

  public static void main(String[] args) {
    generateFitnes();
    for (int tryning = 0; tryning < 100; tryning++) {

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
      PlotUtils.writeToFile("piRez_n" + tryning + ".json", jsonStr);
      // FirstOrderFitnes fitnes = new FirstOrderFitnes();
      double rez = generateFitnes().evaluate(i);
      String g = Double.toString(rez) + "\n";
      g += TimeUnit.MILLISECONDS.toMinutes(stop - start) + "\n";
      g += (stop - start) + "\n";

      PlotUtils.writeToFile("rez" + tryning + ".txt", g);
    }

  }

  private static BitIndiDecoder masterDecoder = null;

  private static AntFitnes generateFitnes() {
    if (masterDecoder == null) {
      masterDecoder = BitIndiDecoder.initDecoderBasedOnFiles(PI_NET_JSON, PI_NET_OPTIMIZATION_DATA,
          RuleOptimizationData.OptType.Inp, RuleOptimizationData.OptType.Out, RuleOptimizationData.OptType.SplitExit,
          RuleOptimizationData.OptType.SplitEnter, RuleOptimizationData.OptType.Modif,
          RuleOptimizationData.OptType.NoModif);
      System.out.println("nr of rules :: " + masterDecoder.getNrOfRules());
    }

    return new AntFitnes(masterDecoder.myClone(), outForwad, outLeft, outRight, inp);

  }

}
