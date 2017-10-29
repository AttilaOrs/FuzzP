package UnifiedGpProblmes.ArtificalAnt;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import AlgoImpl.IterationLogger;
import AlgoImpl.MultiobjectiveMulioperatorGA;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreatureParallelPool;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.GpIndi.UsageStats.CrossOverWrapper;
import UnifiedGp.GpIndi.UsageStats.GeneratorWrapperForUsage;
import UnifiedGp.GpIndi.UsageStats.MutationWrapper;
import UnifiedGp.GpIndi.UsageStats.UnifiedGpIndiWithUsageStats;
import UnifiedGp.GpIndi.UsageStats.UsageBasedCrossOver;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import commonUtil.PlotUtils;
import structure.ICreatureFitnes;
import structure.IOperatorFactory;
import structure.ISelector;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class UsageMeasureMain {

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
      doStuff("ant" + i + "/", i);
    }
  }

  static double prob = 50.0;
  private static final int HALD_RANK_MAX_SIZE = 7;

  public static void doStuff(String path, int runNr) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndiWithUsageStats>>> gens = new ArrayList<>();
    gens.add(() -> GeneratorWrapperForUsage
        .wrap(new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(AntFitnes.problemSpecification()),
            HALD_RANK_MAX_SIZE)));

    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndiWithUsageStats>>> mutators = new ArrayList<>();
    mutators.add(() -> MutationWrapper.wrap(new UnifiedGpIndiTreeMutator(createTreeBuilder())));

    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndiWithUsageStats>>> breeders = new ArrayList<>();
    breeders.add(() -> CrossOverWrapper.wrap(new UnifiedGpIndiBreeder()));
    breeders.add(() -> CrossOverWrapper.wrap(new UnifromCrossOver(prob)));
    breeders.add(() -> new UsageBasedCrossOver());

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndiWithUsageStats>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> new ArtificalAntFitnesForUsage());
    ISelector otherSelector = new LinearRankSelection();
    ISelector survSelector = new LinearRankSelection();
    AntFitnes.HARD_LIMIT = false;
    AntFitnes.APPLY_SIZE_LIMIT = true;
    AntFitnes.SIZE_LIMIT = 400;
    AntFitnes.SIZE_LIMIT_START = 500;
    AntFitnes.FIRED_TR_LIMIT = true;

    SimpleGA.REMOVE_ELITE_FROM_POP = false;

    double[] crossWeigth = new double[] { 0.33, 0.33, 0.33 };
    if (runNr % 5 == 0) {
      crossWeigth = new double[] { 0.0, 0.0, 1.0 };
    }
    if (runNr % 5 == 1) {
      crossWeigth = new double[] { 0.5, 0.5, 0.0 };
    }
    if (runNr % 5 == 2) {
      crossWeigth = new double[] { 0.33, 0.33, 0.33 };
    }
    if (runNr % 5 == 3) {
      crossWeigth = new double[] { 0.0, 1.0, 0.0 };
    }
    if (runNr % 5 == 4) {
      crossWeigth = new double[] { 1.0, 0.0, 0.0 };
    }

    CreatureParallelPool<UnifiedGpIndiWithUsageStats> pool = new CreatureParallelPool<>(gens,
        mutators,
        breeders,
        fitnesses);

    MultiobjectiveMulioperatorGA<UnifiedGpIndiWithUsageStats> algo = new MultiobjectiveMulioperatorGA<>(pool,
        otherSelector,
        survSelector, null, new double[] { 1.0 }, new double[] { 1.0 }, crossWeigth, new double[] { 1.0 });
    SimpleGA.iteration = 100;
    SimpleGA.population = 2000;
    algo.setEralyStoppingCondition(d -> d >= 89.0);
    long start = System.currentTimeMillis();
    algo.theAlgo();
    long stop = System.currentTimeMillis();

    Integer i = algo.getMaxId();

    UnifiedGpIndi rez = pool.get(i);
    int rezFitnes = finalize(rez, runNr);
    String config = "population " + SimpleGA.population + "\n";
    config += "iteration " + SimpleGA.iteration + "\n";
    config += "size limit " + AntFitnes.SIZE_LIMIT + "\n";
    config += "family  " + " " + otherSelector.getClass().getSimpleName() + "\n";
    config += "ops " + SimpleGA.CROSSOVER + " " + SimpleGA.ELIT + " " + SimpleGA.MUTATION + " " + SimpleGA.SELECTION
        + " " + SimpleGA.NEW + "\n";
    config += "cross " + breeders.get(0).generate().getClass().getSimpleName() + "\n";
    config += "cross " + breeders.get(1).generate().getClass().getSimpleName() + " " + prob + "\n";
    config += "crossWeights " + crossWeigth[0] + " " + crossWeigth[1] + "\n";

    config += "half ranked half full gen max " + HALD_RANK_MAX_SIZE + " \n";
    config += "Size config: HardLimit " + AntFitnes.APPLY_SIZE_LIMIT + " " + AntFitnes.HARD_LIMIT + " limit "
        + AntFitnes.SIZE_LIMIT + " start limit "
        + AntFitnes.SIZE_LIMIT_START + "FiredTransitionLmit " + AntFitnes.FIRED_TR_LIMIT + "\n";
    config += "result fitnes " + rezFitnes + "\n";
    config += "duration: " + (stop - start) + " milliseconds " + "(" + TimeUnit.MILLISECONDS.toMinutes(stop - start)
        + " minutes)\n";
    config += crossWeigth[0] + " " + crossWeigth[1] + " " + crossWeigth[2];

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

  private static int finalize(UnifiedGpIndi rez, int runNr) {
    AntFitnes f = new AntFitnes();
    f.tableSup = () -> new MutableStateLogged(GridReader.copyGrid());
    f.evaluate(rez);
    System.out.println(f.table.getFoodEaten() + " out of " + GridReader.getNumberOfFoodCells());
    System.out.println(f.table.getMovesTaken());
    // ((MutableStateLogged) f.table).writeToFileWithXs(new File("antMoove" +
    // runNr + ".txt"));
    return f.table.getFoodEaten();

  }

  public static TreeBuilder<NodeType> createTreeBuilder() {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(AntFitnes.problemSpecification()));
  }

}

