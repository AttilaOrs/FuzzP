package UnifiedPetriGA.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.Selectors.RouletteWheelSelection;
import AlgoImpl.pools.CreatureParallelPool;
import UnifiedPetriGA.BendingBreeder;
import UnifiedPetriGA.BendingCreatorGenerator;
import UnifiedPetriGA.BendingCreature;
import UnifiedPetriGA.BendingCreatureMutator;
import UnifiedPetriGA.mapper.BendingCreatureUTPNMapper;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.ICreatureFitnes;
import structure.IOperatorFactory;
import structure.ISelector;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class ControllerMain {

  private static class Configuration {
    int population, iteration;
    ISelector ss;
    String name;
    List<Integer> trToOptimize;

    public Configuration(String name, ISelector ss, int pop, int iter, List<Integer> trsToOtimze) {
      this.iteration = iter;
      this.ss = ss;
      this.population = pop;
      this.name = name;
      this.trToOptimize = trsToOtimze;
    }

  }

  private static ArrayList<Configuration> confs = new ArrayList<>();
  static {
    ControllerUnifiedPetriMaker maker = new ControllerUnifiedPetriMaker();
    RouletteWheelSelection roulette = new RouletteWheelSelection();
    LinearRankSelection linear = new LinearRankSelection();
    List<int[]> ll = Arrays.asList(new int[] { 1000, 100 }, new int[] { 2000, 100 }, new int[] { 3000, 100 },
        new int[] { 4000, 100 }, new int[] { 5000, 100 }, new int[] { 1500, 150 }, new int[] { 2000, 150 },
        new int[] { 3000, 150 }, new int[] { 4000, 150 }, new int[] { 5000, 150 }, new int[] { 2000, 200 },
        new int[] { 2000, 200 }, new int[] { 3000, 200 }, new int[] { 4000, 200 }, new int[] { 5000, 200 },
        new int[] { 10000, 100 }, new int[] { 10000, 150 }, new int[] { 10000, 200 });
    for (int[] pp : ll) {
      confs.add(new Configuration("Controller_roulette_" + pp[0] + "_" + pp[1], roulette, pp[0], pp[1],
          Arrays.asList(maker.T5)));
      confs.add(new Configuration("Controller_f_roulette_" + pp[0] + "_" + pp[1], roulette, pp[0], pp[1],
          Arrays.asList(maker.T5, maker.T3, maker.T4)));
      confs.add(new Configuration("Controller_ff_roulette_" + pp[0] + "_" + pp[1], roulette, pp[0], pp[1],
          Arrays.asList(maker.T5, maker.T3, maker.T4, maker.T1, maker.T2)));

      confs.add(
          new Configuration("Controller_linear_" + pp[0] + "_" + pp[1], linear, pp[0], pp[1], Arrays.asList(maker.T5)));
      confs.add(new Configuration("Controller_f_linear_" + pp[0] + "_" + pp[1], linear, pp[0], pp[1],
          Arrays.asList(maker.T5, maker.T3, maker.T4)));
      confs.add(new Configuration("Controller_ff_linear_" + pp[0] + "_" + pp[1], linear, pp[0], pp[1],
          Arrays.asList(maker.T5, maker.T3, maker.T4, maker.T1, maker.T2)));

    }

  }

  public static void main(String args[]) {
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
    for (Configuration cc : confs) {

      ControllerUnifiedPetriMaker maker = new ControllerUnifiedPetriMaker();
      BendingCreatureUTPNMapper mapper = new BendingCreatureUTPNMapper(maker.net, cc.trToOptimize, true);
      Scenario sc = Scenario.readFromJson("Scenatio.json");
      ArrayList<IOperatorFactory<ICreatureGenerator<BendingCreature>>> generators = new ArrayList<>();
      generators.add(() -> new BendingCreatorGenerator(mapper.getNrOfGenes()));
      ArrayList<IOperatorFactory<ICreatureMutator<BendingCreature>>> mutators = new ArrayList<>();
      mutators.add(() -> new BendingCreatureMutator());
      ArrayList<IOperatorFactory<ICreatureBreeder<BendingCreature>>> breeders = new ArrayList<>();
      breeders.add(() -> new BendingBreeder());
      ArrayList<IOperatorFactory<ICreatureFitnes<BendingCreature>>> fitnesCals = new ArrayList<>();
      fitnesCals.add(() -> new ControllerFitness(sc.myClone(), mapper.myClone()));
      CreatureParallelPool<BendingCreature> pool = new CreatureParallelPool<>(generators, mutators, breeders,
          fitnesCals);
      SimpleGA.iteration = cc.iteration;
      SimpleGA.population = cc.population;
      SimpleGA<BendingCreature> algo = new SimpleGA<>(pool, cc.ss);
      IterationLogger log = new IterationLogger();
      algo.setLogger(log);
      algo.theAlgo();
      Integer id = algo.getMaxId();
      BendingCreature bb = pool.get(id);
      ControllerFitness ff = new ControllerFitness(sc, mapper);
      double rez = ff.evaluate(bb);
      PlotUtils.plot(log.getLogsForPlottingContatinigStrings(""), cc.name + "_Behave.svg");
      PlotUtils.writeToFile(cc.name + " rez.txt", Double.toString(rez) + "\n " + ff.getStatistics().toString());
      saver.save(mapper.decode(bb), cc.name + "_upn.json");
    }

  }
}
