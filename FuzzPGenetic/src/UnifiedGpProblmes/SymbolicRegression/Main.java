package UnifiedGpProblmes.SymbolicRegression;

import static java.lang.System.currentTimeMillis;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import AlgoImpl.IterationLogger;
import AlgoImpl.MultiobjectiveMulioperatorGA;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreatureParallelPool;
import UnifiedGp.AbstactFitness;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifiedGpSuplier;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class Main {

  public static void main(String args[]) {
    for (int i = 0; i < 10; i++) {
      String path = "symReg" + i + "/";

      ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
      gens.add(() -> new UnifiedGpSuplier(createTreeBuilder()));
      new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(SymbolicRegressionFitness.problemsSpecification()),
          7);

      ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
      mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder()));

      ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
      breeders.add(() -> new UnifiedGpIndiBreeder());
      breeders.add(() -> new UnifromCrossOver(0.5));

      ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
      fitnesses.add(() -> new SymbolicRegressionFitness());

      ICreaturePool<UnifiedGpIndi> pool = new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders, fitnesses);

      double[] crossWeigth = new double[] { 0.5, 0.5 };
      MultiobjectiveMulioperatorGA<UnifiedGpIndi> algo = new MultiobjectiveMulioperatorGA<>(pool,
          new LinearRankSelection(),
          new LinearRankSelection(), null, new double[] { 1.0 }, new double[] { 1.0 }, crossWeigth,
          new double[] { 1.0 });
      SimpleGA.iteration = 100;
      SimpleGA.population = 1000;
      SimpleGA.REMOVE_ELITE_FROM_POP = false;
      AbstactFitness.APPLY_SIZE_LIMIT = true;
      AbstactFitness.FIRED_TR_LIMIT = true;
      AbstactFitness.HARD_LIMIT = false;
      AbstactFitness.SIZE_LIMIT_START = 300;
      AbstactFitness.SIZE_LIMIT = 500;
      long startTime = currentTimeMillis();
      algo.theAlgo();
      long stopTime = currentTimeMillis();

      IterationLogger logger = algo.getLogger();
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree"), path + "bloat_tree_sym.svg");
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), path + "bloat_time_sym.svg");
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("fit"), path + "firtes_sym.svg");

      Integer maxID = algo.getMaxId();

      UnifiedGpIndi rez = pool.get(maxID);
      finalize(rez, path, stopTime - startTime);
    }

    // UnifiedVizualizer.visualize(convRez.net, rec,
    // TransitionPlaceNameStore.createOrdinarNames(convRez.net));

  }

  private static void finalize(UnifiedGpIndi rez, String path, long m) {
    SymbolicRegressionFitness fit = new SymbolicRegressionFitness();
    fit.setRecordForOptimize(true);
    double rezFitnes = fit.evaluate(rez);
    Map<String, Map<Double, Double>> l = fit.makeLog(rez);
    PlotUtils.plot2(l, path + "comp_reg.svg");

    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<>();
    String petri = saver.makeString(fit.getRez().net);
    PlotUtils.writeToFile(path + "_Petri.json", petri);

    RuleOptimizationData data = fit.getOptimizationData();
    Gson gg = new Gson();
    String dataJson = gg.toJson(data);
    PlotUtils.writeToFile(path + "_OptData.json", dataJson);

    String finalRez = rezFitnes + "\n";
    finalRez += TimeUnit.MILLISECONDS.toMinutes(m) + " minutes (";
    finalRez += m + "milliseconds) \n";
    finalRez += rez.getRoot().toString();
    PlotUtils.writeToFile(path + "_rez.txt", finalRez);

  }

  public static TreeBuilder<NodeType> createTreeBuilder() {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(SymbolicRegressionFitness.problemsSpecification()));
  }

}
