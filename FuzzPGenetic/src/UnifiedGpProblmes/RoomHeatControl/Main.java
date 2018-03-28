package UnifiedGpProblmes.RoomHeatControl;

import static UnifiedGpProblmes.RoomHeatControl.RoomFitnes.getProblemSpecification;
import static commonUtil.PlotUtils.writeToFile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import AlgoImpl.IterationLogger;
import AlgoImpl.MultiobjectiveMulioperatorGA;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreatureParallelPool;
import UnifiedGp.AbstactFitness;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.ICreatureFitnes;
import structure.IOperatorFactory;
import structure.ISelector;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class Main {

  public static final String MORNING_SCENARIO_FILE = "MorningRoomScenario.json";
  public static final String EVENING_SCENARIO_FILE = "EveningRoomScenario.json";
  public static final String FITNESS_SCENARIO_FILE = "FitnessScenario.json";
  private static final int HALD_RANK_MAX_SIZE = 7;
  private static final String CONFIG_REZ = "ConfRez.txt";
  private static final String SIZE_HIST = "sizeHistLog";
  private static final String MEM_USE = "mem_use";
  private static final String GC_FILE = "gc";
  private static final String SIZE = "tree_size";
  private static final String FITNESS = "fitness";
  private static final String TIME = "time_per_sol";
  private static final String LEAFS = "tree_leaf";
  private static final String OPS = "tree_ops";
  private static final String DEPTH = "tree_depth";

  protected static RoomScenario moringScneario = null;
  protected static RoomScenario eveningScenario = null;
  private static RoomScenario fitnessScenario = null;

  public static void main(String args[]) {
    try {
      initScenarios();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.err.println("NO SCENARIO FILES");
      return;
    }

    for (int tryNr = 0; tryNr < 100; tryNr++) {
      String path = "RoomController" + tryNr + "/";

      ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
      gens.add(() -> new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(getProblemSpecification()),
          HALD_RANK_MAX_SIZE));

      ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
      mutators.add(() -> new UnifiedGpIndiTreeMutator(
          new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(getProblemSpecification()))));

      ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
      breeders.add(() -> new UnifiedGpIndiBreeder());
      breeders.add(() -> new UnifromCrossOver(0.5));

      ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
      fitnesses
          .add(() -> new RoomFitnessMultiScenario(Arrays.asList(fitnessScenario, moringScneario, eveningScenario)));
      ISelector otherSelector = new LinearRankSelection();
      ISelector survSelector = new LinearRankSelection();
      SimpleGA.REMOVE_ELITE_FROM_POP = false;
      AbstactFitness.APPLY_SIZE_LIMIT = true;
      AbstactFitness.FIRED_TR_LIMIT = true;
      AbstactFitness.HARD_LIMIT = false;
      AbstactFitness.SIZE_LIMIT_START = 250;
      AbstactFitness.SIZE_LIMIT = 500;

      SimpleGA.REMOVE_ELITE_FROM_POP = false;

      double[] crossWeigth = new double[] { 0.5, 0.5 };

      CreatureParallelPool<UnifiedGpIndi> pool = new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders,
          fitnesses);

      MultiobjectiveMulioperatorGA<UnifiedGpIndi> algo = new MultiobjectiveMulioperatorGA<>(pool, otherSelector,
          survSelector, null, new double[] { 1.0 }, new double[] { 1.0 }, crossWeigth, new double[] { 1.0 });
      SimpleGA.iteration = 100;
      SimpleGA.population = 5000;
      algo.setEralyStoppingCondition(d -> d >= 1.0);
      long start = System.currentTimeMillis();
      algo.theAlgo();
      long stop = System.currentTimeMillis();

      long startTime = System.currentTimeMillis();
      Integer i = algo.getMaxId();
      long stopTime = System.currentTimeMillis();

      UnifiedGpIndi rez = pool.get(i);
      double rezFitnes = finalize(rez, path, stopTime - startTime);
      String config = "population " + SimpleGA.population + "\n";
      config += "iteration " + SimpleGA.iteration + "\n";
      config += "size limit " + AbstactFitness.SIZE_LIMIT + "\n";
      config += "family  " + " " + otherSelector.getClass().getSimpleName() + "\n";
      config += "ops " + SimpleGA.CROSSOVER + " " + SimpleGA.ELIT + " " + SimpleGA.MUTATION + " " + SimpleGA.SELECTION
          + " " + SimpleGA.NEW + "\n";
      config += "cross " + breeders.get(0).generate().getClass().getSimpleName() + "\n";
      config += "cross " + breeders.get(1).generate().getClass().getSimpleName() + " " + 0.5 + "\n";
      config += "crossWeights " + crossWeigth[0] + " " + crossWeigth[1] + "\n";

      config += "half ranked half full gen max " + HALD_RANK_MAX_SIZE + " \n";
      config += "Size config: HardLimit " + AbstactFitness.APPLY_SIZE_LIMIT + " " + AbstactFitness.HARD_LIMIT
          + " limit "
          + AbstactFitness.SIZE_LIMIT + " start limit "
          + AbstactFitness.SIZE_LIMIT_START + "FiredTransitionLmit " + AbstactFitness.FIRED_TR_LIMIT + "\n";
      config += "result fitnes " + rezFitnes + "\n";
      config += "duration: " + (stop - start) + " milliseconds " + "(" + TimeUnit.MILLISECONDS.toMinutes(stop - start)
          + " minutes)";

      PlotUtils.writeToFile(path + CONFIG_REZ, config);

      // UnifiedVizualizer.visualize(convRez.net, rec,
      // TransitionPlaceNameStore.createOrdinarNames(convRez.net));
      IterationLogger logger = algo.getLogger();
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree depth"),
          path + DEPTH);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree ops"),
          path + OPS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree leafs"),
          path + LEAFS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), path + TIME);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("Fit"), path + FITNESS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree size"), path + SIZE);
      PlotUtils.hist(algo.getSizeHistLog(), path + SIZE_HIST);

    }
  }

  private static double finalize(UnifiedGpIndi rez, String path, long l) {

    RoomFitnes realFit = new RoomFitnes(fitnessScenario.myClone());
    IterationLogger fitnessLogger = new IterationLogger();
    realFit.setLogger(fitnessLogger);
    realFit.setRecordForOptimize(true);
    double fitnesReal = realFit.evaluate(rez);

    RoomFitnes fit = new RoomFitnes(moringScneario.myClone());
    IterationLogger moringLogger = new IterationLogger();
    fit.setLogger(moringLogger);
    fit.setRecordForOptimize(true);
    double fitnes = fit.evaluate(rez);

    RoomFitnes fitEvn = new RoomFitnes(eveningScenario.myClone());
    IterationLogger evnenLogger = new IterationLogger();
    fitEvn.setLogger(evnenLogger);
    double fitnesEvn = fitEvn.evaluate(rez);
    String rezStr = fitnesReal + "\n" + fitnes + "\n" + fitnesEvn + "\n" + rez.getRoot().toString();
    writeToFile(path + "rez.txt", rezStr);

    PlotUtils.plot(moringLogger.getLogsForPlottingContatinigStrings(""), path + "moring");
    PlotUtils.plot(fitnessLogger.getLogsForPlottingContatinigStrings(""), path + "real");
    PlotUtils.plot(evnenLogger.getLogsForPlottingContatinigStrings(""), path + "evening");

    Gson gs = new Gson();
    String optStr = gs.toJson(fit.getOptimizationData());
    writeToFile(path + "oprData.json", optStr);

    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
    String netStr = saver.makeString(fit.getLastRez().net);
    writeToFile(path + "Petri.json", netStr);
    String mapping = String.valueOf(fit.getLastRez().inpNrToInpPlace) + "\n";
    mapping += String.valueOf(fit.getLastRez().outNrToOutTr);

    writeToFile(path + "mapping.txt", mapping);

    return fitnesReal;
  }

  public static void initScenarios() throws FileNotFoundException {
    Gson gs = new Gson();
    JsonReader reader = new JsonReader(new FileReader(MORNING_SCENARIO_FILE));
    moringScneario = gs.fromJson(reader, RoomScenario.class);
    reader = new JsonReader(new FileReader(EVENING_SCENARIO_FILE));
    eveningScenario = gs.fromJson(reader, RoomScenario.class);
    reader = new JsonReader(new FileReader(FITNESS_SCENARIO_FILE));
    fitnessScenario = gs.fromJson(reader, RoomScenario.class);
  }


  private static void generateNewScenarios() {
    RoomScenario sc = RoomScenario.fitnessScenario();
    Gson gs = new Gson();
    String morningJson = gs.toJson(sc);

    /*
     * PlotUtils.writeToFile(MORNING_SCENARIO_FILE, morningJson); RoomScenario
     * evning = RoomScenario.extremeEvening(); String evString =
     * gs.toJson(evning); PlotUtils.writeToFile(EVENING_SCENARIO_FILE,
     * evString);
     */

    sc = RoomScenario.presentationScenario();
    String newScen = gs.toJson(sc);
    PlotUtils.writeToFile(FITNESS_SCENARIO_FILE, newScen);

  }

}
