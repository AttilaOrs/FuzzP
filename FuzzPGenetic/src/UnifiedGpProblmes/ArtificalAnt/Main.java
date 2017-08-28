package UnifiedGpProblmes.ArtificalAnt;

import java.util.ArrayList;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.Selectors.SelectOnlyOneWrapper;
import AlgoImpl.pools.CreatureParallelPool;
import AlgoImpl.pools.PoolWrapperForTheorteticalDistance;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifiedGpSuplier;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import commonUtil.PlotUtils;
import structure.ICreatureFitnes;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;
public class Main {

  private static final String CONFIG_REZ = "ConfRez.txt";
  private static final String DIVERSITY = "diversity.svg";
  private static final String SIZE_HIST = "sizeHistLog.svg";
  private static final String SIZE = "tree_size.svg";
  private static final String FITNESS = "fitness.svg";
  private static final String TIME = "time_per_sol.svg";
  private static final String LEAFS = "tree_leaf.svg";
  private static final String OPS = "tree_ops.svg";
  private static final String DEPTH = "tree_depth.svg";

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      doStuff("ant" + i + "/", i);
    }
  }

  public static void doStuff(String path, int runNr) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
    gens.add(() -> new UnifiedGpSuplier(createTreeBuilder()));


    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
    mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder()));

    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
    breeders.add(() -> new UnifiedGpIndiBreeder());

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> new AntFitnes());
    SelectOnlyOneWrapper selector = new SelectOnlyOneWrapper(new LinearRankSelection());

    PoolWrapperForTheorteticalDistance<UnifiedGpIndi> pool = new PoolWrapperForTheorteticalDistance<>(
        new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders, fitnesses), selector);

    SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, (runNr % 2 == 0) ? pool : selector);
    SimpleGA.iteration = 50;
    SimpleGA.population = 100;
    algo.theAlgo();


    Integer i = algo.getMaxId();

    UnifiedGpIndi rez = pool.get(i);
    int rezFitnes = finalize(rez, runNr);
    String config = "population " + SimpleGA.population + "\n";
    config += "iteration " + SimpleGA.iteration + "\n";
    config += "size limit " + AntFitnes.SIZE_LIMIT + "\n";
    config += "family " + (runNr % 2 == 0) + "\n";
    config += "result fitnes " + rezFitnes + "\n";

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
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("size"), path + SIZE);
    PlotUtils.hist(algo.getSizeHistLog(), SIZE_HIST + runNr + ".svg");
    PlotUtils.plot(pool.getLogger().getLogsForPlottingContatinigStrings(" "), path + DIVERSITY);

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

