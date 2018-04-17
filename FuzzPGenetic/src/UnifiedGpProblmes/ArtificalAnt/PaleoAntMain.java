package UnifiedGpProblmes.ArtificalAnt;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.google.gson.Gson;

import AlgoImpl.IterationLogger;
import AlgoImpl.MultiplierTransformer;
import AlgoImpl.PaleoMultiobejctiveAlgo;
import AlgoImpl.Selectors.PaleoSelectors.NSGAIISelector;
import AlgoImpl.Selectors.PaleoSelectors.PaleoSelector;
import AlgoImpl.pools.CreatureParallelPool;
import AlgoImpl.pools.PoolWrapperForTheorteticalDistance;
import UnifiedGp.AbstactFitness;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.ICreatureFitnes;
import structure.IOperatorFactory;
import structure.SizeFitnes;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class PaleoAntMain {

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
  private static final String DIST = "dist";
  private static final String DIST_CAT = "dist_cat";
  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      doStuff("NSGII/antPaleo" + i + "/", i, join -> new NSGAIISelector(join));
      /*
       * if (i % 2 == 0) { doStuff("NSGII/antPaleo" + i + "/", i, join -> new
       * NSGAIISelector(join)); } else { doStuff("SPEAII/antPaleo" + i + "/", i,
       * join -> new SPEAIISelector(join)); }
       */
    }
  }

  static double prob = 30.0;
  private static final int HALD_RANK_MAX_SIZE = 7;

  public static void doStuff(String path, int runNr, Function<ForkJoinPool, PaleoSelector> selectorFactory) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
    gens.add(() -> new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(AntFitnes.problemSpecification()),
        HALD_RANK_MAX_SIZE));


    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
    mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder()));

    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
    breeders.add(() -> new UnifiedGpIndiBreeder());
    breeders.add(() -> new UnifromCrossOver(prob));

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> new AntFitnes());
    fitnesses.add(() -> new SizeFitnes<>());
    AbstactFitness.APPLY_SIZE_LIMIT = true;
    AbstactFitness.FIRED_TR_LIMIT = true;
    AbstactFitness.HARD_LIMIT = false;
    AbstactFitness.SIZE_LIMIT_START = 500;
    AbstactFitness.SIZE_LIMIT = 700;


    double[] crossWeigth = new double[]{0.5, 0.5};

    CreatureParallelPool<UnifiedGpIndi> pool = new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders,
        fitnesses);
    ForkJoinPool forkJoin = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    PoolWrapperForTheorteticalDistance<UnifiedGpIndi> distancePool = new PoolWrapperForTheorteticalDistance<>(pool,
        forkJoin);

    PaleoSelector selector = selectorFactory.apply(forkJoin);
    

    PaleoMultiobejctiveAlgo<UnifiedGpIndi> algo = new PaleoMultiobejctiveAlgo<>(distancePool,
        new MultiplierTransformer(forkJoin), new double[]{1.0}, crossWeigth,
        new double[]{1.0}, selector);

    PaleoMultiobejctiveAlgo.PALEO_ITER = 150;
    PaleoMultiobejctiveAlgo.PALEO_SURV_POP = 2400;
    PaleoMultiobejctiveAlgo.PALEO_NEW_POP = 2400;


    long start = System.currentTimeMillis();
    algo.theAlgo();
    long stop = System.currentTimeMillis();


    Integer i = algo.getBestBasedOn(0);

    UnifiedGpIndi rez = pool.get(i);
    double rezFitnes = finalize(rez, runNr, path, start - stop);
    String config = "population surv" + PaleoMultiobejctiveAlgo.PALEO_SURV_POP + "\n";
    config += "population new " + PaleoMultiobejctiveAlgo.PALEO_NEW_POP + "\n";
    config += "iteration " + PaleoMultiobejctiveAlgo.PALEO_ITER + "\n";
    config += "size limit " + AntFitnes.SIZE_LIMIT + "\n";
    config += "paleo selector " + selector.getClass().getSimpleName() + "\n";
    config += "ops " + PaleoMultiobejctiveAlgo.PALEO_CROSS + " " + PaleoMultiobejctiveAlgo.PALEO_MUT + "\n";
    config += "cross " + breeders.get(0).generate().getClass().getSimpleName() + "\n";
    config += "cross " + breeders.get(1).generate().getClass().getSimpleName() + " " + prob + "\n";
    config += "crossWeights " + crossWeigth[0] + " " + crossWeigth[1] + "\n";

    config += "half ranked half full gen max " + HALD_RANK_MAX_SIZE + " \n";
    config += "Size config: HardLimit " + AntFitnes.APPLY_SIZE_LIMIT + " " + AntFitnes.HARD_LIMIT + " limit "
        + AntFitnes.SIZE_LIMIT + " start limit " + AntFitnes.SIZE_LIMIT_START + "FiredTransitionLmit "
        + AntFitnes.FIRED_TR_LIMIT + "\n";
    config += "result fitnes " + rezFitnes + "\n";
    config += "duration: " + (stop - start) + " milliseconds " + "(" + TimeUnit.MILLISECONDS.toMinutes(stop - start)
        + " minutes)";

    PlotUtils.writeToFile(path + CONFIG_REZ, config);


    IterationLogger logger = algo.getLogger();
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree depth"), path + DEPTH);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree ops"), path + OPS);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree leafs"), path + LEAFS);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), path + TIME);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("Fit"), path + FITNESS);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree size"), path + SIZE);
    PlotUtils.hist(algo.getSizeHistLog(), path + SIZE_HIST);
    PlotUtils.plot(distancePool.getLogger().getLogsForPlottingContatinigStrings("dist"), path + DIST);
    PlotUtils.plot(distancePool.getLogger().getLogsForPlottingContatinigStrings("cat"), path + DIST_CAT);

    /*
     * Set<Integer> firstFront = algo.getFirstFront(); for (Integer indi :
     * firstFront) { finalize(pool.get(indi), indi, path + "/FF/" + indi, 0);
     * 
     * }
     */
    forkJoin.shutdown();

  }

  private static double finalize(UnifiedGpIndi rez, int runNr, String path, long time) {
    AntFitnes f = new AntFitnes();
    f.setRecordForOptimize(true);
    f.tableSup = () -> new MutableStateLogged(GridReader.copyGrid());
    double rr = f.evaluate(rez);
    // System.out.println(f.table.getFoodEaten() + " out of " +
    // GridReader.getNumberOfFoodCells());
    // System.out.println(f.table.getMovesTaken());

    // ((MutableStateLogged) f.table).writeToFileWithXs(new File("antMoove" +
    // runNr + ".txt"));
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<>();
    String l = saver.makeString(f.getRez().net);
    PlotUtils.writeToFile(path + "_Petri.json", l);
    String ss = f.getRez().inpNrToInpPlace.toString() + "\n" + f.getRez().outNrToOutTr.toString();
    PlotUtils.writeToFile(path + "_Mapping.txt", ss);
    String finalRez = rr + "\n";
    finalRez += TimeUnit.MILLISECONDS.toMinutes(time);
    finalRez += time;
    finalRez += rez.getRoot().toString();
    PlotUtils.writeToFile(path + "_rez.txt", finalRez);
    RuleOptimizationData data = f.getOptimizationData();
    Gson gg = new Gson();
    String dataJson = gg.toJson(data);
    PlotUtils.writeToFile(path + "_OptData.json", dataJson);
    return rr;

  }

  public static TreeBuilder<NodeType> createTreeBuilder() {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(AntFitnes.problemSpecification()));
  }

}

