package UnifiedPetriRuleOptimizer.Robo;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreaturePoolWithStreams;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;
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

  private static final String PETRI_NET = "Petri_maze_robo.json";
  private static final String PETRI_NET_OPTIMIZATION_DATA = "_OptData_maze_robo.json";
  private static final int[] inps = new int[] { 241, 247, 253, 255, 256 };
  private static final int[] outs = new int[] { 245, 257 };

  private static BitIndiDecoder masterDecoder = null;
  private static Court maze;

  public static void main(String[] args) {

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
      SimpleGA.iteration = 100;
      SimpleGA.population = 2000;
      long start = System.currentTimeMillis();
      algo.theAlgo();
      long stop = System.currentTimeMillis();

      Integer best = algo.getMaxId();
      BitIndi i = pool.get(best);
      finalize(trying, start, stop, i);
    }

  }

  private static void finalize(int trying, long start, long stop, BitIndi i) {
    UnifiedPetriNet newNEt = masterDecoder.modified(i);
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
    String jsonStr = saver.makeString(newNEt);
    PlotUtils.writeToFile("roboRez_Petri" + trying + ".json", jsonStr);
    MazeFitnes fitnes = generateFitnes();
    
    double rez = fitnes.evaluate(i);


    String g = Double.toString(rez) + "\n";
    g += TimeUnit.MILLISECONDS.toMinutes(stop - start) + "\n";
    g += (stop - start) + "\n";
    PlotUtils.writeToFile("rez" + trying + ".txt", g);

  }

  private static MazeFitnes generateFitnes() {
    if (masterDecoder == null) {
      masterDecoder = BitIndiDecoder.initDecoderBasedOnFiles(PETRI_NET, PETRI_NET_OPTIMIZATION_DATA,
          RuleOptimizationData.OptType.Inp, RuleOptimizationData.OptType.Out, RuleOptimizationData.OptType.SplitExit);
      System.out.println("nr of rules :: " + masterDecoder.getNrOfRules());
      maze = Court.getMaze();

    }
    return new MazeFitnes(masterDecoder.myClone(), inps, outs, maze.myClone());

  }
}
