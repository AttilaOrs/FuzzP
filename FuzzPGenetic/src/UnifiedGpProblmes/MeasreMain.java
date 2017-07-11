package UnifiedGpProblmes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreaturePoolWithStreams;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifiedGpSuplier;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import UnifiedGpProblmes.ArtificalAnt.AntFitnes;
import UnifiedGpProblmes.CartCentering.CartFitnes;
import UnifiedGpProblmes.CartCentering.UnifiedPetriController;
import UnifiedGpProblmes.FirstOrderSystem.FirstOrderFitnes;
import UnifiedGpProblmes.Multiplexer.MultiplexerFitness;
import UnifiedGpProblmes.SymbolicRegression.SymbolicRegressionFitness;
import commonUtil.PlotUtils;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class MeasreMain {

  public static class MeasureConfig {
    final int pop, iter;
    final String name;
    final Supplier<ProblemSpecification> specificationSuplier;
    final Supplier<ICreatureFitnes<UnifiedGpIndi>> fitnessSuplier;

    public MeasureConfig(int pop, int iter, String name, Supplier<ProblemSpecification> specificationSuplier,
        Supplier<ICreatureFitnes<UnifiedGpIndi>> fitnessSuplier) {
      this.pop = pop;
      this.iter = iter;
      this.name = name;
      this.specificationSuplier = specificationSuplier;
      this.fitnessSuplier = fitnessSuplier;
    }
  }

  public static final List<MeasureConfig> confs = new ArrayList<>();

  static {
    int repeate = 5;
    List<Supplier<ProblemSpecification>> probSpec = Arrays.asList(MultiplexerFitness::problemSpecification,
        SymbolicRegressionFitness::problemsSpecification, UnifiedPetriController::create,
        FirstOrderFitnes::createProblemSpecification, AntFitnes::problemSpecification);
    List<Supplier<ICreatureFitnes<UnifiedGpIndi>>> fitnes = Arrays.asList(MultiplexerFitness::new,
        SymbolicRegressionFitness::new, CartFitnes::new, FirstOrderFitnes::new, AntFitnes::new);
    List<String> nameList = Arrays.asList("Multiplexer_", "SymbolicReg_", "CartCenter_", "FirstOrder_", "Ant_");

    for (int i = 0; i < repeate; i++) {
      for (int j = 0; j < nameList.size(); j++) {
        confs.add(new MeasureConfig(500, 100, nameList.get(j) + i, probSpec.get(j), fitnes.get(j)));
      }

    }
  }

  public static void main(String[] args) {
    for (MeasureConfig conf : confs) {
      ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
      gens.add(() -> new UnifiedGpSuplier(createTreeBuilder(conf.specificationSuplier.get())));

      ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
      mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder(conf.specificationSuplier.get())));

      ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
      breeders.add(() -> new UnifiedGpIndiBreeder());

      ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
      fitnesses.add(conf.fitnessSuplier::get);

      ICreaturePool<UnifiedGpIndi> pool = new CreaturePoolWithStreams<UnifiedGpIndi>(gens, mutators, breeders,
          fitnesses);

      SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, new LinearRankSelection());
      SimpleGA.iteration = conf.iter;
      SimpleGA.population = conf.pop;
      algo.theAlgo();

      String name = conf.name + "_i" + SimpleGA.iteration + "_p" + SimpleGA.population + "_";

      IterationLogger logger = algo.getLogger();
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree"), name + "bloat_tree.svg");
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), name + "bloat_time.svg");
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("fit"), name + "fitnes.svg");
      PlotUtils.writeToFile(name + "bloat_tree.txt", logger.getLogsForPlottingContatinigStrings("tree").toString());
      PlotUtils.writeToFile(name + "bloat_time.txt", logger.getLogsForPlottingContatinigStrings("time").toString());
      PlotUtils.writeToFile(name + "fitnes.txt", logger.getLogsForPlottingContatinigStrings("fit").toString());

    }

  }

  public static TreeBuilder<NodeType> createTreeBuilder(ProblemSpecification sp) {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(sp));
  }


}
