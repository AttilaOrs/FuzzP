package UnifiedGpProblmes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.google.gson.Gson;

import AlgoImpl.IterationLogger;
import AlgoImpl.MultiobjectiveMulioperatorGA;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreaturePoolWithStreams;
import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import UnifiedGpProblmes.FirstOrderSystem.FirstOrderFitnes;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.ISelector;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class MeasreMain {

  public static class MeasureConfig {
    final int pop, iter;
    final String name;
    final Supplier<ProblemSpecification> specificationSuplier;
    final Supplier<ICreatureFitnes<UnifiedGpIndi>> fitnessSuplier;
    final ISelector selector;

    public MeasureConfig(int pop, int iter, String name, Supplier<ProblemSpecification> specificationSuplier,
        Supplier<ICreatureFitnes<UnifiedGpIndi>> fitnessSuplier, ISelector selector) {
      this.pop = pop;
      this.iter = iter;
      this.name = name;
      this.specificationSuplier = specificationSuplier;
      this.fitnessSuplier = fitnessSuplier;
      this.selector = selector;
    }
  }

  public static final List<MeasureConfig> confs = new ArrayList<>();
  static {
    int repeate = 50;
    List<Supplier<ProblemSpecification>> probSpec = Arrays.asList(FirstOrderFitnes::createProblemSpecification);
    List<Supplier<ICreatureFitnes<UnifiedGpIndi>>> fitnes = Arrays.asList(FirstOrderFitnes::new);
    List<String> nameList = Arrays.asList("FirstOrder_");
    List<ISelector> selectors = Arrays.asList(new LinearRankSelection());
    for (int i = 0; i < 50; i++) {
      confs.add(new MeasureConfig(2000, 100, "FirstOdrerClassic_" + i, FirstOrderFitnes::createProblemSpecification,
          FirstOrderFitnes::new, new LinearRankSelection()));

    }

  }

  /*
   * static { int repeate = 5; List<Supplier<ProblemSpecification>> probSpec =
   * Arrays.asList(MultiplexerFitness::problemSpecification,
   * SymbolicRegressionFitness::problemsSpecification,
   * UnifiedPetriController::create,
   * FirstOrderFitnes::createProblemSpecification,
   * AntFitnes::problemSpecification);
   * List<Supplier<ICreatureFitnes<UnifiedGpIndi>>> fitnes =
   * Arrays.asList(MultiplexerFitness::new, SymbolicRegressionFitness::new,
   * CartFitnes::new, FirstOrderFitnes::new, AntFitnes::new); List<String>
   * nameList = Arrays.asList("Multiplexer_", "SymbolicReg_", "CartCenter_",
   * "FirstOrder_", "Ant_");
   * 
   * List<String> selectorNames = Arrays.asList("Roulette", "Rank", "Tournamet",
   * "RouletteOne", "RankOne", "TournamentOne");
   * 
   * List<ISelector> selectors = Arrays.asList(new RouletteWheelSelection(), new
   * LinearRankSelection(), new TournamentSelection(), new
   * SelectOnlyOneWrapper(new RouletteWheelSelection()), new
   * SelectOnlyOneWrapper(new LinearRankSelection()), new
   * SelectOnlyOneWrapper(new TournamentSelection()));
   * 
   * 
   * for (int i = 0; i < repeate; i++) { for (int j = 0; j < nameList.size();
   * j++) { for (int s = 0; s < selectorNames.size(); s++) { confs.add(new
   * MeasureConfig(500, 100, nameList.get(j) + selectorNames.get(s) + "_" + i,
   * probSpec.get(j), fitnes.get(j), selectors.get(s))); } }
   * 
   * } }
   */

  private static final int HALD_RANK_MAX_SIZE = 7;
  public static void main(String[] args) {
    for (MeasureConfig conf : confs) {
      ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
      gens.add(() -> new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(conf.specificationSuplier.get()),
          HALD_RANK_MAX_SIZE));

      ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
      mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder(conf.specificationSuplier.get())));

      ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
      breeders.add(() -> new UnifiedGpIndiBreeder());
      breeders.add(() -> new UnifromCrossOver(0.5));

      ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
      fitnesses.add(conf.fitnessSuplier::get);

      ICreaturePool<UnifiedGpIndi> pool = new CreaturePoolWithStreams<UnifiedGpIndi>(gens, mutators, breeders,
          fitnesses);
      SimpleGA.REMOVE_ELITE_FROM_POP = false;
      AbstactFitness.APPLY_SIZE_LIMIT = true;
      AbstactFitness.FIRED_TR_LIMIT = true;
      AbstactFitness.HARD_LIMIT = false;
      AbstactFitness.SIZE_LIMIT_START = 300;
      AbstactFitness.SIZE_LIMIT = 500;

      double[] crossWeigth = new double[] { 0.5, 0.5 };
      MultiobjectiveMulioperatorGA<UnifiedGpIndi> algo = new MultiobjectiveMulioperatorGA<>(pool, conf.selector,
          conf.selector, null, new double[] { 1.0 }, new double[] { 1.0 }, crossWeigth, new double[] { 1.0 });
      SimpleGA.iteration = conf.iter;
      SimpleGA.population = conf.pop;
      long start = System.currentTimeMillis();
      algo.theAlgo();
      long stop = System.currentTimeMillis();

      String name = conf.name + "_i" + SimpleGA.iteration + "_p" + SimpleGA.population + "_";
      IterationLogger logger = algo.getLogger();
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree"), name + "bloat_tree.svg");
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), name + "bloat_time.svg");
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("Fit"), name + "fitnes.svg");
      PlotUtils.writeToFile(name + "bloat_tree.txt", logger.getLogsForPlottingContatinigStrings("tree").toString());
      PlotUtils.writeToFile(name + "bloat_time.txt", logger.getLogsForPlottingContatinigStrings("time").toString());
      PlotUtils.writeToFile(name + "fitnes.txt", logger.getLogsForPlottingContatinigStrings("fit").toString());
      Integer maxId = algo.getMaxId();
      UnifiedGpIndi rez = pool.get(maxId);
      finalizeFirstOrder(rez, name, stop - start);

    }

  }

  public static TreeBuilder<NodeType> createTreeBuilder(ProblemSpecification sp) {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(sp));
  }

  private static double finalizeFirstOrder(UnifiedGpIndi rez, String path, long m) {
    FirstOrderFitnes mm = new FirstOrderFitnes(true);
    mm.setRecordForOptimize(true);
    double rr = mm.evaluate(rez);

    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<>();
    String l = saver.makeString(mm.getRez().net);
    PlotUtils.writeToFile(path + "_Petri.json", l);
    String ss = mm.getRez().inpNrToInpPlace.toString() + "\n" + mm.getRez().outNrToOutTr.toString();
    PlotUtils.writeToFile(path + "_Mapping.txt", ss);
    String finalRez = rr + "\n";
    finalRez += TimeUnit.MILLISECONDS.toMinutes(m);
    finalRez += m;
    finalRez += rez.getRoot().toString();
    PlotUtils.writeToFile(path + "_rez.txt", finalRez);

    RuleOptimizationData data = mm.getOptimizationData();
    Gson gg = new Gson();
    String dataJson = gg.toJson(data);
    PlotUtils.writeToFile(path + "_OptData.json", dataJson);
    return rr;

  }


}
