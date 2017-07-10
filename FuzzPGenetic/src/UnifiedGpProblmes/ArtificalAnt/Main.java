package UnifiedGpProblmes.ArtificalAnt;

import java.io.File;
import java.util.ArrayList;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreatureParallelPool;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifiedGpSuplier;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import commonUtil.PlotUtils;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;
public class Main {

  public static void main(String args[]) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
    gens.add(() -> new UnifiedGpSuplier(createTreeBuilder()));

    // gens.add(() -> ((rnd) -> new
    // UnifiedGpIndi(FirstOrderFitnesTest.createConstantNet(1.5))));

    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
    mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder()));

    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
    breeders.add(() -> new UnifiedGpIndiBreeder());

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> new AntFitnes());

    ICreaturePool<UnifiedGpIndi> pool = new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders, fitnesses);

    SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, new LinearRankSelection());
    SimpleGA.iteration = 20;
    SimpleGA.population = 1200;
    algo.theAlgo();

    IterationLogger logger = algo.getLogger();
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree"), "bloat_tree_ant.svg");
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), "bloat_time_ant.svg");
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("fit"), "firtes_ant.svg");

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
    System.out.println(f.table.getFoodEaten());
    System.out.println(f.table.getMovesTaken());
    System.out.println(GridReader.getNumberOfFoodCells());
    ((MutableStateLogged) f.table).writeToFileWithXs(new File("antMoove.txt"));

  }

  public static TreeBuilder<NodeType> createTreeBuilder() {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(AntFitnes.problemSpecification()));
  }

}

