package UnifiedGpProblmes;

import java.util.ArrayList;
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
    for (int i = 0; i < repeate; i++) {
      confs.add(new MeasureConfig(100, 50, "firstOrder" + i, FirstOrderFitnes::createProblemSpecification,
          () -> new FirstOrderFitnes()));
      confs.add(new MeasureConfig(100, 100, "firstOrder" + i, FirstOrderFitnes::createProblemSpecification,
          () -> new FirstOrderFitnes()));
      confs.add(new MeasureConfig(500, 50, "firstOrder" + i, FirstOrderFitnes::createProblemSpecification,
          () -> new FirstOrderFitnes()));
      confs.add(new MeasureConfig(500, 100, "firstOrder" + i, FirstOrderFitnes::createProblemSpecification,
          () -> new FirstOrderFitnes()));
      confs.add(new MeasureConfig(1000, 50, "firstOrder" + i, FirstOrderFitnes::createProblemSpecification,
          () -> new FirstOrderFitnes()));
      confs.add(new MeasureConfig(1000, 100, "firstOrder" + i, FirstOrderFitnes::createProblemSpecification,
          () -> new FirstOrderFitnes()));

      confs.add(new MeasureConfig(100, 50, "cartCentering" + i, UnifiedPetriController::create,
          () -> new CartFitnes()));
      confs.add(new MeasureConfig(100, 100, "cartCentering" + i, UnifiedPetriController::create,
          () -> new CartFitnes()));
      confs.add(new MeasureConfig(500, 50, "cartCentering" + i, UnifiedPetriController::create,
          () -> new CartFitnes()));
      confs.add(new MeasureConfig(500, 100, "cartCentering" + i, UnifiedPetriController::create,
          () -> new CartFitnes()));
      confs.add(new MeasureConfig(1000, 50, "cartCentering" + i, UnifiedPetriController::create,
          () -> new CartFitnes()));
      confs.add(new MeasureConfig(1000, 100, "cartCentering" + i, UnifiedPetriController::create,
          () -> new CartFitnes()));

      confs.add(new MeasureConfig(100, 50, "ant" + i, AntFitnes::problemSpecification,
          () -> new AntFitnes()));
      confs.add(new MeasureConfig(100, 100, "ant" + i, AntFitnes::problemSpecification,
          () -> new AntFitnes()));
      confs.add(new MeasureConfig(500, 50, "ant" + i, AntFitnes::problemSpecification,
          () -> new AntFitnes()));
      confs.add(new MeasureConfig(500, 100, "ant" + i, AntFitnes::problemSpecification,
          () -> new AntFitnes()));
      confs.add(new MeasureConfig(1000, 50, "ant" + i, AntFitnes::problemSpecification,
          () -> new AntFitnes()));
      confs.add(new MeasureConfig(1000, 100, "ant" + i, AntFitnes::problemSpecification,
          () -> new AntFitnes()));

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
