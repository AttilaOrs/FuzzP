package UnifiedPetriRuleOptimizer.Room;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreaturePoolWithStreams;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
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

  private static final String PETRI_NET = "Petri_room.json";
  private static final String PETRI_NET_OPTIMIZATION_DATA = "oprData_room.json";
  private static final int[] inps = new int[] { 156, 166 };
  private static final int[] outs = new int[] { 145, 150 };

  private static BitIndiDecoder masterDecoder = null;
  private static RoomScenario moringScneario = null;
  private static RoomScenario eveningScenario = null;

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
    PlotUtils.writeToFile("piRez_n" + trying + ".json", jsonStr);
    // FirstOrderFitnes fitnes = new FirstOrderFitnes();
    RoomFitnes morningFit = generateFitnes();
    IterationLogger morningLogger = new IterationLogger();
    morningFit.setLogger(morningLogger);
    double rez = morningFit.evaluate(i);
    
    RoomFitnes evening = new RoomFitnes(masterDecoder, inps, outs, eveningScenario);
    IterationLogger evningLogger = new IterationLogger();
    evening.setLogger(evningLogger);
    double evevingFit = evening.evaluate(i);
    
    
    
    String g = Double.toString(rez) + "\n";
    g += evevingFit + "\n";
    g += TimeUnit.MILLISECONDS.toMinutes(stop - start) + "\n";
    g += (stop - start) + "\n";
    PlotUtils.writeToFile("rez" + trying + ".txt", g);

    PlotUtils.plot(morningLogger.getLogsForPlottingContatinigStrings(""), "moring" + trying + "Plot");
    PlotUtils.plot(evningLogger.getLogsForPlottingContatinigStrings(""), "evening" + trying + "Plot");
  }


  private static RoomFitnes generateFitnes() {
    if (masterDecoder == null) {
      masterDecoder = BitIndiDecoder.initDecoderBasedOnFiles(PETRI_NET, PETRI_NET_OPTIMIZATION_DATA,
          RuleOptimizationData.OptType.Inp, RuleOptimizationData.OptType.Out, RuleOptimizationData.OptType.SplitExit);
      System.out.println("nr of rules :: " + masterDecoder.getNrOfRules());
      Gson gs = new Gson();
      JsonReader reader;
      try {
        reader = new JsonReader(new FileReader(UnifiedGpProblmes.RoomHeatControl.Main.MORNING_SCENARIO_FILE));
        moringScneario = gs.fromJson(reader, RoomScenario.class);
        reader = new JsonReader(new FileReader(UnifiedGpProblmes.RoomHeatControl.Main.EVENING_SCENARIO_FILE));
        eveningScenario = gs.fromJson(reader, RoomScenario.class);
      } catch (FileNotFoundException e) {
        System.err.println("FILE NOT FOUND!!!");
        e.printStackTrace();
      }

    }

    return new RoomFitnes(masterDecoder.myClone(), inps, outs, moringScneario.myClone());

  }

}
