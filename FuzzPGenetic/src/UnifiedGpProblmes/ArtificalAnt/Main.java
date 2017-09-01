package UnifiedGpProblmes.ArtificalAnt;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreatureParallelPool;
import AlgoImpl.pools.PoolWrapperForTheorteticalDistance;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiOnePointCrossover;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifiedGpSuplier;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import commonUtil.PlotUtils;
import structure.ICreatureFitnes;
import structure.IOperatorFactory;
import structure.ISelector;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;
public class Main {

  private static final String CONFIG_REZ = "ConfRez.txt";
  private static final String DIVERSITY = "diversity.svg";
  private static final String SIZE_HIST = "sizeHistLog.svg";
  private static final String SIMILARTY_CAT = "similarityCategories.svg";
  private static final String SIZE = "tree_size.svg";
  private static final String FITNESS = "fitness.svg";
  private static final String TIME = "time_per_sol.svg";
  private static final String LEAFS = "tree_leaf.svg";
  private static final String OPS = "tree_ops.svg";
  private static final String DEPTH = "tree_depth.svg";

  public static void main(String[] args) {
    for (int i = 0; i < 30; i++) {
      doStuff("ant" + i + "/", i);
    }
  }

  public static void doStuff(String path, int runNr) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
    gens.add(() -> new UnifiedGpSuplier(createTreeBuilder()));


    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
    mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder()));

    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
    IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>> fact = () -> {
      if (runNr %3 ==0){
        return new UnifiedGpIndiBreeder();
      } else if(runNr % 3 == 1){
        return new UnifiedGpIndiOnePointCrossover();
      } else {
        return new UnifromCrossOver();
      }
    };
    breeders.add(fact);

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> new AntFitnes());
    ISelector otherSelector = new LinearRankSelection();
    ISelector survSelector = new LinearRankSelection();

    String selectorStr = "";
    selectorStr = "Both LinerRankSelector";
    SimpleGA.REMOVE_ELITE_FROM_POP = false;
    
    

    PoolWrapperForTheorteticalDistance<UnifiedGpIndi> pool = new PoolWrapperForTheorteticalDistance<>(
        new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders, fitnesses), otherSelector, runNr % 3);

    SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, otherSelector, survSelector);
    SimpleGA.iteration = 50;
    SimpleGA.population = 500;
    long start = System.currentTimeMillis();
    algo.theAlgo();
    long stop = System.currentTimeMillis();


    Integer i = algo.getMaxId();

    UnifiedGpIndi rez = pool.get(i);
    int rezFitnes = finalize(rez, runNr);
    String config = "population " + SimpleGA.population + "\n";
    config += "iteration " + SimpleGA.iteration + "\n";
    config += "size limit " + AntFitnes.SIZE_LIMIT + "\n";
    config += "family false \n";
    config += "ops " + SimpleGA.CROSSOVER + " " + SimpleGA.ELIT + " " + SimpleGA.MUTATION + " " + SimpleGA.SELECTION
        + " " + SimpleGA.NEW + "\n";
    config += selectorStr + "\n";
    config += "cross" + fact.generate().getClass().getName() + "\n";
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
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("fit"), path + FITNESS);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree size"), path + SIZE);
    PlotUtils.hist(algo.getSizeHistLog(), path + SIZE_HIST);
    IterationLogger poolLogger = pool.getLogger();
    PlotUtils.plot(poolLogger.getLogsForPlottingContatinigStrings("dist"), path + DIVERSITY);
    PlotUtils.plot(poolLogger.getLogsForPlottingContatinigStrings("cat"), path + SIMILARTY_CAT);

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

