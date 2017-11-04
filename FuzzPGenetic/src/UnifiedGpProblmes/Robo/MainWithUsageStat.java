package UnifiedGpProblmes.Robo;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import AlgoImpl.IterationLogger;
import AlgoImpl.MultiobjectiveMulioperatorGA;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreatureParallelPool;
import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.GpIndi.UsageStats.AbstactFitnessWithUsage;
import UnifiedGp.GpIndi.UsageStats.CrossOverWrapper;
import UnifiedGp.GpIndi.UsageStats.GeneratorWrapperForUsage;
import UnifiedGp.GpIndi.UsageStats.MutationWrapper;
import UnifiedGp.GpIndi.UsageStats.UnifiedGpIndiWithUsageStats;
import UnifiedGp.GpIndi.UsageStats.UsageBasedCrossOver;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import UnifiedGpProblmes.ArtificalAnt.AntFitnes;
import UnifiedGpProblmes.Robo.Fitnesses.MazeWithUsage;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.ISelector;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class MainWithUsageStat {

  private static final String CONFIG_REZ = "ConfRez.txt";
  private static final String DIVERSITY = "diversity";
  private static final String SIZE_HIST = "sizeHistLog";
  private static final String SIMILARTY_CAT = "similarityCategories";
  private static final String MEM_USE = "mem_use";
  private static final String GC_FILE = "gc";
  private static final String SIZE = "tree_size";
  private static final String FITNESS = "fitness";
  private static final String TIME = "time_per_sol";
  private static final String LEAFS = "tree_leaf";
  private static final String OPS = "tree_ops";
  private static final String DEPTH = "tree_depth";

  public static void main(String[] args) {
    for (int i = 0; i < 60; i++) {
      doStuff("lineRobo" + i + "/", i, MainWithUsageStat::createProblemSpec, MainWithUsageStat::generateFitnes);
    }
  }

  static double prob = 50.0;
  private static final int HALD_RANK_MAX_SIZE = 7;

  public static void doStuff(String path, int runNr, Supplier<ProblemSpecification> spec,
      Supplier<ICreatureFitnes<UnifiedGpIndiWithUsageStats>> firntsSup) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndiWithUsageStats>>> gens = new ArrayList<>();
    gens.add(() -> GeneratorWrapperForUsage.wrap(new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(spec.get()),
        HALD_RANK_MAX_SIZE)));

    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndiWithUsageStats>>> mutators = new ArrayList<>();
    mutators.add(
        () -> MutationWrapper
            .wrap(new UnifiedGpIndiTreeMutator(new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(spec.get())))));

    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndiWithUsageStats>>> breeders = new ArrayList<>();
    breeders.add(() -> CrossOverWrapper.wrap(new UnifiedGpIndiBreeder()));
    breeders.add(() -> CrossOverWrapper.wrap(new UnifromCrossOver(0.5)));
    breeders.add(() -> new UsageBasedCrossOver());

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndiWithUsageStats>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> firntsSup.get());
    ISelector otherSelector = new LinearRankSelection();
    ISelector survSelector = new LinearRankSelection();
    SimpleGA.REMOVE_ELITE_FROM_POP = false;
    AbstactFitness.APPLY_SIZE_LIMIT = true;
    AbstactFitness.FIRED_TR_LIMIT = true;
    AbstactFitness.HARD_LIMIT = false;
    AbstactFitness.SIZE_LIMIT_START = 300;
    AbstactFitness.SIZE_LIMIT = 500;

    ICreaturePool<UnifiedGpIndiWithUsageStats> pool = new CreatureParallelPool<>(gens, mutators, breeders, fitnesses);
    // otherSelector = (runNr % 2 == 0) ? otherSelector : pool;

    double[] crossWeigth = new double[] { 0.1, 0.45, 0.45 };
    MultiobjectiveMulioperatorGA<UnifiedGpIndiWithUsageStats> algo = new MultiobjectiveMulioperatorGA<>(pool,
        otherSelector,
        survSelector, null, new double[] { 1.0 }, new double[] { 1.0 }, crossWeigth, new double[] { 1.0 });
    SimpleGA.iteration = 150;
    SimpleGA.population = 4000;
    long start = System.currentTimeMillis();
    algo.theAlgo();
    long stop = System.currentTimeMillis();

    Integer i = algo.getMaxId();

    UnifiedGpIndiWithUsageStats rez = pool.get(i);
    double rezFitnes = finalize(rez, path);
    String config = "population " + SimpleGA.population + "\n";
    config += "iteration " + SimpleGA.iteration + "\n";
    config += "size limit " + AntFitnes.SIZE_LIMIT + "\n";
    config += "family  " + " " + otherSelector.getClass().getSimpleName() + "\n";
    config += "ops " + SimpleGA.CROSSOVER + " " + SimpleGA.ELIT + " " + SimpleGA.MUTATION + " " + SimpleGA.SELECTION
        + " " + SimpleGA.NEW + "\n";
    config += "cross " + breeders.get(0).generate().getClass().getSimpleName() + "\n";

    config += "half ranked half full gen max " + HALD_RANK_MAX_SIZE + " \n";
    config += "Size config: HardLimit " + AntFitnes.APPLY_SIZE_LIMIT + " " + AntFitnes.HARD_LIMIT + " limit "
        + AntFitnes.SIZE_LIMIT + " start limit "
        + AntFitnes.SIZE_LIMIT_START + "FiredTransitionLmit " + AntFitnes.FIRED_TR_LIMIT + "\n";
    config += "result fitnes " + rezFitnes + "\n";
    config += "rez>> " + rez.getRoot().toString() + "\n";
    config += "duration: " + (stop - start) + " milliseconds " + "(" + TimeUnit.MILLISECONDS.toMinutes(stop - start)
        + " minutes)\n";
    config += "New family island";

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
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(IterationLogger.MEM_USE), path + MEM_USE);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(IterationLogger.GC_SEC), path + GC_FILE);

  }

  private static double finalize(UnifiedGpIndiWithUsageStats rez, String path) {
    AbstactFitnessWithUsage mm = generateFitnes();
    double rr = mm.evaluate(rez);
    // ((MutableStateLogged) f.table).writeToFileWithXs(new File("antMoove" +
    // runNr + ".txt"));
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<>();
    String l = saver.makeString(mm.getRez().net);
    PlotUtils.writeToFile(path + "Petri.json", l);
    String ss = mm.getRez().inpNrToInpPlace.toString() + "\n" + mm.getRez().outNrToOutTr.toString();
    PlotUtils.writeToFile(path + "Mapping.txt", ss);
    return rr;

  }


  public static ProblemSpecification createProblemSpec() {
    return MazeWithUsage.getProblemSpecification();

  }


  public static AbstactFitnessWithUsage generateFitnes() {
    return new MazeWithUsage(Court.getMaze().myClone());
  }

}

