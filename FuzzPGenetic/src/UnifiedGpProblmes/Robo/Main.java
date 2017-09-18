package UnifiedGpProblmes.Robo;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreatureParallelPool;
import AlgoImpl.pools.PoolWrapperForTheorteticalDistance;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import UnifiedGpProblmes.ArtificalAnt.AntFitnes;
import UnifiedGpProblmes.Robo.Simulator.Lines;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Points;
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
      doStuff("lineRobo" + i + "/", i, Main::createProblemSpec, Main::generateFitnes);
    }
  }

  static double prob = 50.0;
  private static final int HALD_RANK_MAX_SIZE = 7;

  public static void doStuff(String path, int runNr, Supplier<ProblemSpecification> spec,
      Supplier<ICreatureFitnes<UnifiedGpIndi>> firntsSup) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
    gens.add(() -> new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(spec.get()),
        HALD_RANK_MAX_SIZE));


    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
    mutators.add(
        () -> new UnifiedGpIndiTreeMutator(new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(spec.get()))));

    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
    breeders.add(() -> new UnifiedGpIndiBreeder());

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> firntsSup.get());
    ISelector otherSelector = new LinearRankSelection();
    ISelector survSelector = new LinearRankSelection();
    SimpleGA.REMOVE_ELITE_FROM_POP = false;
    
    

    PoolWrapperForTheorteticalDistance<UnifiedGpIndi> pool = new PoolWrapperForTheorteticalDistance<>(
        new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders, fitnesses), otherSelector, 1);
    otherSelector = (runNr % 4 >= 2) ? pool : otherSelector;

    SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, otherSelector, survSelector);
    SimpleGA.iteration = 50;
    SimpleGA.population = 100;
    algo.setEralyStoppingCondition(d -> d >= 89.0);
    long start = System.currentTimeMillis();
    algo.theAlgo();
    long stop = System.currentTimeMillis();


    Integer i = algo.getMaxId();

    UnifiedGpIndi rez = pool.get(i);
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
    IterationLogger poolLogger = pool.getLogger();
    PlotUtils.plot(poolLogger.getLogsForPlottingContatinigStrings("dist"), path + DIVERSITY);
    PlotUtils.plot(poolLogger.getLogsForPlottingContatinigStrings("cat"), path + SIMILARTY_CAT);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(IterationLogger.MEM_USE), path + MEM_USE);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(IterationLogger.GC_SEC), path + GC_FILE);

  }

  private static double finalize(UnifiedGpIndi rez, String path) {
    LineFallowerFitnes mm = generateFitnes();
    double rr = mm.evaluate(rez);
    // ((MutableStateLogged) f.table).writeToFileWithXs(new File("antMoove" +
    // runNr + ".txt"));
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<>();
    saver.save(mm.getRez().net, path + "Petri.json");
    String ss = mm.getRez().inpNrToInpPlace.toString() + "\n" + mm.getRez().outNrToOutTr.toString();
    PlotUtils.writeToFile(path + "Mapping.txr", ss);
    return rr;

  }

  public static ProblemSpecification createProblemSpec() {

    // return new TreeBuilder<>(new
    // TreeBuilderCongigGeneralImpl(AntFitnes.problemSpecification()));
    ProblemSpecification d = LineFallowerFitnes.getProblemSpecification();
    return d;
  }


  static Points p = Lines.getPoint();
  public static LineFallowerFitnes generateFitnes() {
    return new LineFallowerFitnes(p.myClone());
  }

}

