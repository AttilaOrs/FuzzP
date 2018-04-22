package UnifiedGpProblmes.RoomHeatControl.Behavour;

import static UnifiedGpProblmes.RoomHeatControl.FullControllMultiScenarioFitness.getProblemSpecification;
import static commonUtil.PlotUtils.plot;
import static commonUtil.PlotUtils.writeToFile;
import static java.util.Arrays.asList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import AlgoImpl.IterationLogger;
import AlgoImpl.MultiplierTransformer;
import AlgoImpl.PaleoMultiobejctiveAlgo;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.PaleoSelectors.SPEAIISelector;
import AlgoImpl.pools.CreatureParallelPool;
import AlgoImpl.pools.PoolWrapperForTheorteticalDistance;
import AlgoImpl.pools.behaviour.BehaviourParalellPool;
import UnifiedGp.AbstactFitness;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import UnifiedGpProblmes.RoomHeatControl.FullControllMultiScenarioFitness;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.IOperatorFactory;
import structure.behaviour.IBeahviourDescriptor;
import structure.behaviour.IBehaviourBasedFitness;
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

  private static RoomScenario moringScneario = null;
  private static RoomScenario eveningScenario = null;
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
      String path = "FullController" + tryNr + "/";

      ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
      gens.add(
          () -> new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(getProblemSpecification()), HALD_RANK_MAX_SIZE));

      ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
      mutators.add(() -> new UnifiedGpIndiTreeMutator(
          new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(getProblemSpecification()))));

      ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
      breeders.add(() -> new UnifiedGpIndiBreeder());
      breeders.add(() -> new UnifromCrossOver(0.3));


      ArrayList<IOperatorFactory<IBehaviourBasedFitness<UnifiedGpIndi, FullHeatControllSimpleDescription>>> bfitnesCals = new ArrayList<>();
      bfitnesCals.add(OverallFitness::new);
      bfitnesCals.add(() -> new OverallFitness((d1, d2) -> d1 + d2));

      IOperatorFactory<IBeahviourDescriptor<FullHeatControllSimpleDescription, UnifiedGpIndi>> descriptorFactory = () -> new FullHeastControllSimpleDescriptor(
          Arrays.asList(moringScneario, eveningScenario, fitnessScenario));

      AbstactFitness.APPLY_SIZE_LIMIT = true;
      AbstactFitness.FIRED_TR_LIMIT = true;
      AbstactFitness.HARD_LIMIT = false;
      AbstactFitness.SIZE_LIMIT_START = 30;
      AbstactFitness.SIZE_LIMIT = 300;

      SimpleGA.REMOVE_ELITE_FROM_POP = false;

      double[] crossWeigth = new double[]{0.5, 0.5};

      BehaviourParalellPool<UnifiedGpIndi, FullHeatControllSimpleDescription> pool = new BehaviourParalellPool<UnifiedGpIndi, FullHeatControllSimpleDescription>(
          gens, mutators, breeders, bfitnesCals, descriptorFactory);

      ForkJoinPool forkJoin = new ForkJoinPool(CreatureParallelPool.THREAD_NR);

      PoolWrapperForTheorteticalDistance<UnifiedGpIndi> distPool = new PoolWrapperForTheorteticalDistance<>(pool,
          forkJoin);
      SPEAIISelector paleoSelector = new SPEAIISelector(forkJoin);
      PaleoMultiobejctiveAlgo<UnifiedGpIndi> algo = new PaleoMultiobejctiveAlgo<>(pool,
          new MultiplierTransformer(forkJoin), new double[]{1.0}, crossWeigth, new double[]{1.0}, paleoSelector);

      PaleoMultiobejctiveAlgo.PALEO_ITER = 150;
      PaleoMultiobejctiveAlgo.PALEO_SURV_POP = 2400;
      PaleoMultiobejctiveAlgo.PALEO_NEW_POP = 2400;
      algo.setEralyStoppingCondition(d -> d >= 1.0);
      long start = System.currentTimeMillis();
      algo.theAlgo();
      long stop = System.currentTimeMillis();

      long startTime = System.currentTimeMillis();
      Integer i = algo.getBestBasedOn(0);
      long stopTime = System.currentTimeMillis();

      UnifiedGpIndi rez = pool.get(i);
      double rezFitnes = finalize(rez, path, stopTime - startTime);
      String config = "population surv" + PaleoMultiobejctiveAlgo.PALEO_SURV_POP + "\n";
      config += "population new " + PaleoMultiobejctiveAlgo.PALEO_NEW_POP + "\n";
      config += "iteration " + PaleoMultiobejctiveAlgo.PALEO_ITER + "\n";
      config += "size limit " + AbstactFitness.SIZE_LIMIT + "\n";
      config += "paleo selector " + paleoSelector.getClass().getSimpleName() + "\n";
      config += "ops " + PaleoMultiobejctiveAlgo.PALEO_CROSS + " " + PaleoMultiobejctiveAlgo.PALEO_MUT + "\n";
      config += "cross " + breeders.get(0).generate().getClass().getSimpleName() + "\n";
      config += "cross " + breeders.get(1).generate().getClass().getSimpleName() + " " + 0.3 + "\n";
      config += "crossWeights " + crossWeigth[0] + " " + crossWeigth[1] + "\n";

      config += "half ranked half full gen max " + HALD_RANK_MAX_SIZE + " \n";
      config += "Size config: HardLimit " + AbstactFitness.APPLY_SIZE_LIMIT + " " + AbstactFitness.HARD_LIMIT
          + " limit " + AbstactFitness.SIZE_LIMIT + " start limit " + AbstactFitness.SIZE_LIMIT_START
          + "FiredTransitionLmit " + AbstactFitness.FIRED_TR_LIMIT + "\n";
      config += "result fitnes " + rezFitnes + "\n";
      config += "duration: " + (stop - start) + " milliseconds " + "(" + TimeUnit.MILLISECONDS.toMinutes(stop - start)
          + " minutes)";

      PlotUtils.writeToFile(path + CONFIG_REZ, config);

      PlotUtils.writeToFile(path + CONFIG_REZ, config);

      // UnifiedVizualizer.visualize(convRez.net, rec,
      // TransitionPlaceNameStore.createOrdinarNames(convRez.net));
      IterationLogger logger = algo.getLogger();
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree depth"), path + DEPTH);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree ops"), path + OPS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree leafs"), path + LEAFS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), path + TIME);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("Fit"), path + FITNESS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree size"), path + SIZE);
      PlotUtils.hist(algo.getSizeHistLog(), path + SIZE_HIST);

    }
  }

  private static double finalize(UnifiedGpIndi rez, String path, long l) {

    FullControllMultiScenarioFitness realFit = new FullControllMultiScenarioFitness(
        asList(fitnessScenario, moringScneario, eveningScenario));
    realFit.setRecordForOptimize(true);
    realFit.setSaveBehaviour(true);
    double fitnesReal = realFit.evaluate(rez);
    int cntr = 0;
    for (IterationLogger logger : realFit.getLoggers()) {
      plot(logger.getLogsForPlottingContatinigStrings(""), path + "_scen" + (cntr++));
    }

    String rezStr = fitnesReal + rez.getRoot().toString();
    PlotUtils.writeToFile(path + "rez.txt", rezStr);

    Gson gs = new Gson();
    String optStr = gs.toJson(realFit.getOptimizationData());
    writeToFile(path + "oprData.json", optStr);

    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
    String netStr = saver.makeString(realFit.getLastRez().net);
    writeToFile(path + "Petri.json", netStr);
    String mapping = String.valueOf(realFit.getLastRez().inpNrToInpPlace) + "\n";
    mapping += String.valueOf(realFit.getLastRez().outNrToOutTr);

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

}
