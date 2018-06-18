package UnifiedGpProblmes.Robo.Behaviour;

import static UnifiedGpProblmes.Robo.Fitnesses.MazeFitnes.getProblemSpecification;
import static commonUtil.PlotUtils.writeToFile;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import AlgoImpl.IterationLogger;
import AlgoImpl.PaleoMultiobejctiveAlgo;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.PaleoSelectors.NSGAIISelector;
import AlgoImpl.pools.CreatureParallelPool;
import AlgoImpl.pools.behaviour.BehaviourParalellPool;
import UnifiedGp.AbstactFitness;
import UnifiedGp.Behaviour.BehaviourDiversityHammingFitness;
import UnifiedGp.GpIndi.HalfRampHalfFull;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifromCrossOver;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import UnifiedGpProblmes.Robo.Fitnesses.MazeFitnes;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.IOperatorFactory;
import structure.behaviour.IBeahviourDescriptor;
import structure.behaviour.IBehaviourBasedFitness;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class RoboBeahveMain {
  
  private static final int HALD_RANK_MAX_SIZE = 7;
  private static final String CONFIG_REZ = "ConfRez.txt";
  private static final String SIZE_HIST = "sizeHistLog";
  private static final String MEM_USE = "mem_use";
  private static final String GC_FILE = "gc";
  private static final String SIZE = "tree_size";
  private static final String FITNESS = "fitness";
  private static final String TIME = "time_per_sol";
  private static final String LEAFS = "tree_leaf";
  private static final String OPS = "tree_ops";
  private static final String DEPTH = "tree_depth";
  public static void main(String args[]) {

    for (int tryNr = 0; tryNr < 100; tryNr++) {
      String path = "Robo_NEW__" + tryNr + "/";

      ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
      gens.add(
          () -> new HalfRampHalfFull(new TreeBuilderCongigGeneralImpl(getProblemSpecification()), HALD_RANK_MAX_SIZE));

      ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
      mutators.add(() -> new UnifiedGpIndiTreeMutator(
          new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(getProblemSpecification()))));

      ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
      breeders.add(() -> new UnifiedGpIndiBreeder());
      breeders.add(() -> new UnifromCrossOver(0.3));

      ArrayList<IOperatorFactory<IBehaviourBasedFitness<UnifiedGpIndi, RoboBihaviourBinaryDescription>>> bfitnesCals = new ArrayList<>();
      bfitnesCals.add(() -> new MazeBehaviourFitness(false));
      bfitnesCals.add(() -> new MazeBehaviourFitness(true));
      //bfitnesCals.add(() -> new BehaviourDiversityHammingFitness<>());
      

      IOperatorFactory<IBeahviourDescriptor<RoboBihaviourBinaryDescription, UnifiedGpIndi>> descriptorFactory = () -> new RoboBehaviourBinaryDescriptor(Court.getMaze().myClone());

      AbstactFitness.APPLY_SIZE_LIMIT = true;
      AbstactFitness.FIRED_TR_LIMIT = true;
      AbstactFitness.HARD_LIMIT = false;
      AbstactFitness.SIZE_LIMIT_START = 400;
      AbstactFitness.SIZE_LIMIT = 600;

      SimpleGA.REMOVE_ELITE_FROM_POP = false;

      double[] crossWeigth = new double[]{0.5, 0.5};

      ForkJoinPool forkJoin = new ForkJoinPool(CreatureParallelPool.THREAD_NR);

      NSGAIISelector paleoSelector = new NSGAIISelector(forkJoin);
      BehaviourParalellPool<UnifiedGpIndi, RoboBihaviourBinaryDescription> pool = new BehaviourParalellPool<>(
          gens, mutators, breeders, bfitnesCals, descriptorFactory, paleoSelector);

      PaleoMultiobejctiveAlgo<UnifiedGpIndi> algo = new PaleoMultiobejctiveAlgo<>(pool,
          null, new double[]{1.0}, crossWeigth, new double[]{1.0}, paleoSelector);

      PaleoMultiobejctiveAlgo.PALEO_ITER = 10;
      PaleoMultiobejctiveAlgo.PALEO_SURV_POP = 20;
      PaleoMultiobejctiveAlgo.PALEO_NEW_POP = 20;
      long start = System.currentTimeMillis();
      algo.theAlgo();
      long stop = System.currentTimeMillis();

      Set<Integer> firstFront = algo.getFirstFront();
      int maxId = -1;
      double maxFitnes = -1000.0;
      MazeBehaviourFitness f = new MazeBehaviourFitness(false);
      f.setStore(pool.getStore());

      for (Integer fi : firstFront) {
        double fitnes = f.evaluate(fi);
        if (fitnes > maxFitnes) {
          maxFitnes = fitnes;
          maxId = fi;
        }

      }


      UnifiedGpIndi rez = pool.get(maxId);
      System.out.println(rez);
      System.out.println(">>" + rez.getRoot());
      double rezFitnes = finalize(rez, path + "princ/" );
      String config = "population surv" + PaleoMultiobejctiveAlgo.PALEO_SURV_POP + "\n";
      config += "population new " + PaleoMultiobejctiveAlgo.PALEO_NEW_POP + "\n";
      config += "iteration " + PaleoMultiobejctiveAlgo.PALEO_ITER + "\n";
      config += "size limit " + AbstactFitness.SIZE_LIMIT + "\n";
      config += "paleo selector " + paleoSelector.getClass().getSimpleName() + "\n";
      config += "ops " + PaleoMultiobejctiveAlgo.PALEO_CROSS + " " + PaleoMultiobejctiveAlgo.PALEO_MUT + "\n";
      config += "cross " + breeders.get(0).generate().getClass().getSimpleName() + "\n";
      config += "cross " + breeders.get(1).generate().getClass().getSimpleName() + " " + 0.3 + "\n";
      config += "crossWeights " + crossWeigth[0] + " " + crossWeigth[1] + "\n";

      config += "half ranked half full gen max " + HALD_RANK_MAX_SIZE + " \n";
      config += "Size config: HardLimit " + AbstactFitness.APPLY_SIZE_LIMIT + " " + AbstactFitness.HARD_LIMIT
          + " limit " + AbstactFitness.SIZE_LIMIT + " start limit " + AbstactFitness.SIZE_LIMIT_START
          + "FiredTransitionLmit " + AbstactFitness.FIRED_TR_LIMIT + "\n";
      config += "result fitnes " + rezFitnes + "\n";
      config += "duration: " + (stop - start) + " milliseconds " + "(" + TimeUnit.MILLISECONDS.toMinutes(stop - start)
          + " minutes)";

      writeToFile(path + CONFIG_REZ, config);

      writeToFile(path + CONFIG_REZ, config);

      // UnifiedVizualizer.visualize(convRez.net, rec,
      // TransitionPlaceNameStore.createOrdinarNames(convRez.net));
      IterationLogger logger = algo.getLogger();
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree depth"), path + DEPTH);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree ops"), path + OPS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree leafs"), path + LEAFS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"), path + TIME);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("Fit"), path + FITNESS);
      PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree size"), path + SIZE);
      PlotUtils.hist(algo.getSizeHistLog(), path + SIZE_HIST);

      }
    }
  
  private static double finalize(UnifiedGpIndi rez, String path) {
    MazeFitnes mm =new MazeFitnes(Court.getMaze().myClone());
    AbstactFitness abs = (AbstactFitness) mm;
    abs.setRecordForOptimize(true);
    double rr = mm.evaluate(rez);

    // ((MutableStateLogged) f.table).writeToFileWithXs(new File("antMoove" +
    // runNr + ".txt"));
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<>();
    String l = saver.makeString(((AbstactFitness) mm).getRez().net);
    PlotUtils.writeToFile(path + "Petri.json", l);
    String ss = ((AbstactFitness) mm).getRez().inpNrToInpPlace.toString() + "\n"
        + ((AbstactFitness) mm).getRez().outNrToOutTr.toString();
    PlotUtils.writeToFile(path + "Mapping.txt", ss);
    RuleOptimizationData optData = abs.getOptimizationData();
    Gson gg = new Gson();
    String dataJson = gg.toJson(optData);
    PlotUtils.writeToFile(path + "_OptData.json", dataJson);
    return rr;

  }
}

