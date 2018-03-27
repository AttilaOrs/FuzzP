package UnifiedGpProblmes.ArtificalAnt;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

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
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedGp.Tree.Visitors.TreeBuilder;
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
    ISelector otherSelector = new LinearRankSelection();
    ISelector survSelector = new LinearRankSelection();
    SimpleGA.REMOVE_ELITE_FROM_POP = false;
    AbstactFitness.APPLY_SIZE_LIMIT = true;
    AbstactFitness.FIRED_TR_LIMIT = true;
    AbstactFitness.HARD_LIMIT = false;
    AbstactFitness.SIZE_LIMIT_START = 300;
    AbstactFitness.SIZE_LIMIT = 500;

    SimpleGA.REMOVE_ELITE_FROM_POP = false;
    
    double[] crossWeigth = new double[]{0.5, 0.5};
    

    CreatureParallelPool<UnifiedGpIndi> pool = new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders,
        fitnesses);

    MultiobjectiveMulioperatorGA<UnifiedGpIndi> algo = new MultiobjectiveMulioperatorGA<>(pool, otherSelector,
        survSelector, null, new double[] { 1.0 }, new double[] { 1.0 }, crossWeigth, new double[] { 1.0 });
    SimpleGA.iteration = 100;
    SimpleGA.population = 5000;
    algo.setEralyStoppingCondition(d -> d >= 89.0);
    long start = System.currentTimeMillis();
    algo.theAlgo();
    long stop = System.currentTimeMillis();


    long startTime = System.currentTimeMillis();
    Integer i = algo.getMaxId();
    long stopTime = System.currentTimeMillis();

    UnifiedGpIndi rez = pool.get(i);
    double rezFitnes = finalize(rez, runNr, path, stopTime - startTime);
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

