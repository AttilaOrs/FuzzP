package UnifiedGpProblmes.ArtificalAnt;

import java.io.File;
import java.util.ArrayList;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.SelectOnlyOneWrapper;
import AlgoImpl.Selectors.TournamentSelection;
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

  public static void main(String args[]) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
    gens.add(() -> new UnifiedGpSuplier(createTreeBuilder()));


    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
    mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder()));

    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
    breeders.add(() -> new UnifiedGpIndiBreeder());

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> new AntFitnes());
    SelectOnlyOneWrapper selector = new SelectOnlyOneWrapper(new TournamentSelection());

    PoolWrapperForTheorteticalDistance<UnifiedGpIndi> pool = new PoolWrapperForTheorteticalDistance<>(
        new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders, fitnesses), selector);

    SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, pool);
    SimpleGA.iteration = 50;
    SimpleGA.population = 500;
    algo.theAlgo();

    IterationLogger logger = algo.getLogger();
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree depth"), "bloat_tree_depth_ant_tour_one.svg");
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree ops"), "bloat_tree_ops_ant_tour_one.svg");
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree leafs"), "bloat_tree_leafs_ant_tour_one.svg");
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), "bloat_time_ant_tour_one.svg");
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("fit"), "firtes_ant_tour_one.svg");
    PlotUtils.hist(algo.getSizeHistLog(), "sizeHistLog.svg");

    Integer i = algo.getMaxId();

    UnifiedGpIndi rez = pool.get(i);
    finalize(rez);

    // UnifiedVizualizer.visualize(convRez.net, rec,
    // TransitionPlaceNameStore.createOrdinarNames(convRez.net));

  }

  private static void finalize(UnifiedGpIndi rez) {
    AntFitnes f = new AntFitnes();
    f.tableSup = () -> new MutableStateLogged(GridReader.copyGrid());
    f.evaluate(rez);
    System.out.println(f.table.getFoodEaten() + " out of " + GridReader.getNumberOfFoodCells());
    System.out.println(f.table.getMovesTaken());
    ((MutableStateLogged) f.table).writeToFileWithXs(new File("antMoove.txt"));

  }

  public static TreeBuilder<NodeType> createTreeBuilder() {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(AntFitnes.problemSpecification()));
  }

}

