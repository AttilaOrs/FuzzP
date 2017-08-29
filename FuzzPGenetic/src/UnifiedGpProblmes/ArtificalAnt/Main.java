package UnifiedGpProblmes.ArtificalAnt;

import java.util.ArrayList;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.Selectors.SelectOnlyOneWrapper;
import AlgoImpl.pools.CreaturePoolWithStreams;
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
import structure.ISelector;
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
    for (int i = 0; i < 1000; i++) {
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
    ISelector otherSelector = new LinearRankSelection();
    ISelector survSelector = new LinearRankSelection();

    String selectorStr = "";
    if (runNr % 3 == 0) {
      selectorStr = "Both LinerRankSelector";
      SimpleGA.REMOVE_ELITE_FROM_POP = false;
    } else if (runNr % 3 == 1) {
      SimpleGA.REMOVE_ELITE_FROM_POP = true;
      survSelector = new SelectOnlyOneWrapper(new LinearRankSelection());
      selectorStr = "Surv selected only once";
    } else {
      selectorStr = "Evrything selected only once";
      survSelector = new SelectOnlyOneWrapper(new LinearRankSelection());
      otherSelector = new SelectOnlyOneWrapper(new LinearRankSelection());
    }
    
    

    PoolWrapperForTheorteticalDistance<UnifiedGpIndi> pool = new PoolWrapperForTheorteticalDistance<>(
        new CreaturePoolWithStreams<UnifiedGpIndi>(gens, mutators, breeders, fitnesses), otherSelector);

    SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, (runNr % 2 == 0) ? pool : otherSelector, survSelector);
    SimpleGA.iteration = 100;
    SimpleGA.population = 1000;
    if (runNr % 4 >= 2) {
      SimpleGA.ELIT = 1;
      SimpleGA.SELECTION = 17;
      SimpleGA.NEW = 5;
      SimpleGA.MUTATION = 17;
      SimpleGA.CROSSOVER = 60; // sum has to be 100
    } else {
      SimpleGA.ELIT = 2;
      SimpleGA.SELECTION = 19;
      SimpleGA.NEW = 0;
      SimpleGA.MUTATION = 19;
      SimpleGA.CROSSOVER = 60; // sum has to be 100
    }
    algo.theAlgo();


    Integer i = algo.getMaxId();

    UnifiedGpIndi rez = pool.get(i);
    int rezFitnes = finalize(rez, runNr);
    String config = "population " + SimpleGA.population + "\n";
    config += "iteration " + SimpleGA.iteration + "\n";
    config += "size limit " + AntFitnes.SIZE_LIMIT + "\n";
    config += "family " + (runNr % 2 == 0) + "\n";
    config += "ops " + SimpleGA.CROSSOVER + " " + SimpleGA.ELIT + " " + SimpleGA.MUTATION + " " + SimpleGA.SELECTION
        + " " + SimpleGA.NEW + "\n";
    config += "result fitnes " + rezFitnes + "\n";
    config += selectorStr;

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
    PlotUtils.hist(algo.getSizeHistLog(), path + SIZE_HIST);
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

