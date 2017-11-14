package UnifiedGpProblmes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import AlgoImpl.IterationLogger;
import AlgoImpl.MultiobjectiveMulioperatorGA;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.Selectors.RouletteWheelSelection;
import AlgoImpl.Selectors.SelectOnlyOneWrapper;
import AlgoImpl.Selectors.TournamentSelection;
import AlgoImpl.pools.CreaturePoolWithStreams;
import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.Permutation;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifiedGpSuplier;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.GpIndi.UsageStats.CrossOverWrapper;
import UnifiedGp.GpIndi.UsageStats.FrequentUsageCutterMutator;
import UnifiedGp.GpIndi.UsageStats.GeneratorWrapperForUsage;
import UnifiedGp.GpIndi.UsageStats.MutationWrapper;
import UnifiedGp.GpIndi.UsageStats.RareUsageCuttingMutator;
import UnifiedGp.GpIndi.UsageStats.UnifiedGpIndiWithUsageStats;
import UnifiedGp.GpIndi.UsageStats.UsageBasedCrossOver;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import UnifiedGpProblmes.ArtificalAnt.AntFitnes;
import UnifiedGpProblmes.ArtificalAnt.ArtificalAntFitnesForUsage;
import UnifiedGpProblmes.CartCentering.CarFitnesForUsage;
import UnifiedGpProblmes.CartCentering.CartFitnes;
import UnifiedGpProblmes.CartCentering.UnifiedPetriController;
import UnifiedGpProblmes.FirstOrderSystem.FirstOrderFitnes;
import UnifiedGpProblmes.FirstOrderSystem.FirstOrderFitnesForUsage;
import UnifiedGpProblmes.Multiplexer.MultiplexerFitness;
import UnifiedGpProblmes.Multiplexer.MultiplexerFitnessForUsage;
import UnifiedGpProblmes.SymbolicRegression.SymbolicRegressionFitness;
import commonUtil.PlotUtils;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.ISelector;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class MeasreMain {

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
  public static class MeasureConfig {
    final int pop, iter;
    final String name;
    final Supplier<ProblemSpecification> specificationSuplier;
    final Supplier<ICreatureFitnes<UnifiedGpIndiWithUsageStats>> fitnessSuplier;
    final String path;
    final double[] crossWeight;
    final double[] mutWeight;

    public MeasureConfig(int pop, int iter, String name, Supplier<ProblemSpecification> specificationSuplier,
        Supplier<ICreatureFitnes<UnifiedGpIndiWithUsageStats>> fitnessSuplier, String path, double[] crossWeight, double[] mutWeight ) {
      this.pop = pop;
      this.iter = iter;
      this.name = name;
      this.specificationSuplier = specificationSuplier;
      this.fitnessSuplier = fitnessSuplier;
      this.path = path;
      this.crossWeight = crossWeight;
      this.mutWeight = mutWeight;
    }
  }

  public static final List<MeasureConfig> confs = new ArrayList<>();

  static {
    int repeate = 10;
    List<Supplier<ProblemSpecification>> probSpec = Arrays.asList(MultiplexerFitness::problemSpecification,
        SymbolicRegressionFitness::problemsSpecification, UnifiedPetriController::create,
        FirstOrderFitnes::createProblemSpecification, AntFitnes::problemSpecification);
    List<Supplier<ICreatureFitnes<UnifiedGpIndiWithUsageStats>>> fitness = Arrays.asList(MultiplexerFitnessForUsage::new, CarFitnesForUsage::new, FirstOrderFitnesForUsage::new, ArtificalAntFitnesForUsage::new);
    List<String> nameList = Arrays.asList("Multiplexer_",  "CartCenter_", "FirstOrder_", "Ant_");
    List<double[]> mutConf = Arrays.asList(new double[]{0.9, 0.1, 0.0, 0.0}, new double[]{0.3, 0.1, 0.3, 0.3}, new double[]{0.0, 0.0, 0.5, 0.5}, new double[]{0.2, 0.05, 0.375, 0.375  }, new double[]{0.70, 0.05, 0.125, 0.125 } );
    List<double[]> crossConf = Arrays.asList(new double[]{0.5, 0.5, 0.0 }, new double[]{0.3, 0.3, 0.3 }, new double[]{0.0, 0.0, 1.0}, new double[]{0.25, 0.25, 0.5}, new double[]{0.375, 0.375, 0.25}) ;
    List<String> confName = Arrays.asList("ClassicOnly_",  "Equal_", "UsageOnly_", "MosltyUsage_", "MosltyClassic_");
    
    
    

    for (int i = 0; i < repeate; i++) {
      for (int j = 0; j < nameList.size(); j++) {
        for(int m =0; m < confName.size(); m++ ){
          String name = nameList.get(j) +   confName.get(m) + i;
          confs.add(new MeasureConfig(2000, 100, name, probSpec.get(j), fitness.get(j), name+"/", crossConf.get(m), mutConf.get(m)));
        }
      }
    }
  }

  public static void main(String[] args) {
    for (MeasureConfig conf : confs) {
    SimpleGA.REMOVE_ELITE_FROM_POP = false;
    AbstactFitness.APPLY_SIZE_LIMIT = true;
    AbstactFitness.FIRED_TR_LIMIT = true;
    AbstactFitness.HARD_LIMIT = false;
    AbstactFitness.SIZE_LIMIT_START = 300;
    AbstactFitness.SIZE_LIMIT = 500;
      ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndiWithUsageStats>>> gens = new ArrayList<>();
      gens.add(() -> GeneratorWrapperForUsage.wrap(new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(conf.specificationSuplier.get()), 7)));

      ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndiWithUsageStats>>> mutators = new ArrayList<>();
      mutators.add( 
        () -> MutationWrapper
            .wrap(new UnifiedGpIndiTreeMutator(new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(conf.specificationSuplier.get())))));
      mutators.add( 
        () -> MutationWrapper.wrap(new  Permutation()));
      mutators.add( 
        () -> new RareUsageCuttingMutator());
      
      mutators.add( 
        () -> new FrequentUsageCutterMutator());


      ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndiWithUsageStats>>> fitnesses = new ArrayList<>();
      fitnesses.add(conf.fitnessSuplier::get);
      
      
    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndiWithUsageStats>>> breeders = new ArrayList<>();
    breeders.add(() -> CrossOverWrapper.wrap(new UnifiedGpIndiBreeder()));
    breeders.add(() -> CrossOverWrapper.wrap(new UnifromCrossOver(0.5)));
    breeders.add(() -> new UsageBasedCrossOver());

      ICreaturePool<UnifiedGpIndiWithUsageStats> pool = new CreaturePoolWithStreams<>(gens, mutators, breeders,
          fitnesses);

      MultiobjectiveMulioperatorGA<UnifiedGpIndiWithUsageStats> algo = new MultiobjectiveMulioperatorGA<>(pool, new LinearRankSelection(), new LinearRankSelection(), null, new double[]{1.0}, new double[]{1.0},conf.crossWeight, conf.mutWeight );
      MultiobjectiveMulioperatorGA.iteration = conf.iter;
      MultiobjectiveMulioperatorGA.population = conf.pop;
      
    long start = System.currentTimeMillis();
      algo.theAlgo();
    long stop = System.currentTimeMillis();
    
      IterationLogger logger = algo.getLogger();
      Integer maxId = algo.getMaxId();
      Double rez = pool.getOldPopulationFitnes().get(maxId)[0];
      String config = conf.name +"\n";
      
    config += "population " + MultiobjectiveMulioperatorGA.population + "\n";
    config += "iteration " + MultiobjectiveMulioperatorGA.iteration + "\n";
    config += "size limit " + AntFitnes.SIZE_LIMIT + "\n";
     config +="cross config "+ Arrays.stream(conf.crossWeight).mapToObj(Double::toString).collect(Collectors.joining(",")) + "\n";
     config +="mut config "+ Arrays.stream(conf.mutWeight).mapToObj(Double::toString).collect(Collectors.joining(",")) + "\n";
     config += "fitnes" + rez + "\n";
    config += "duration: " + (stop - start) + " milliseconds " + "(" + TimeUnit.MILLISECONDS.toMinutes(stop - start)
        + " minutes)\n";
    
    PlotUtils.writeToFile(conf.path + "rez.txt", config);
     
     
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree depth"),
        conf.path + DEPTH);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree ops"),
        conf.path + OPS);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree leafs"),
        conf.path + LEAFS);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), conf.path + TIME);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("Fit"), conf.path + FITNESS);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree size"), conf.path + SIZE);
    PlotUtils.hist(algo.getSizeHistLog(), conf.path + SIZE_HIST);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(IterationLogger.MEM_USE), conf.path + MEM_USE);
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(IterationLogger.GC_SEC), conf.path + GC_FILE);
      
      

    }

  }

  public static TreeBuilder<NodeType> createTreeBuilder(ProblemSpecification sp) {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(sp));
  }


}
