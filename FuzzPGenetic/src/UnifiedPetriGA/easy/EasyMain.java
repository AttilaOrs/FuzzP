package UnifiedPetriGA.easy;

import java.util.ArrayList;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
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

public class EasyMain {

  private static class Configuration {
    int population, iteration;
    ISelector ss;
    String name;

    public Configuration(String name, ISelector ss, int pop, int iter) {
      this.iteration = iter;
      this.ss = ss;
      this.population = pop;
      this.name = name;
    }

  }

  private static ArrayList<Configuration> confs = new ArrayList<>();
  static {
    confs.add(new Configuration("split_nou", new RouletteWheelSelection(), 1000, 100));
    /*
     * confs.add(new Configuration("first_roulettte_split", new
     * RouletteWheelSelection(), 10000, 100)); confs.add(new
     * Configuration("first_rank_split", new LinearRankSelection(), 10000,
     * 100)); confs.add(new Configuration("second_roulettte_split", new
     * RouletteWheelSelection(), 20000, 200)); confs.add(new
     * Configuration("second_rank_split", new LinearRankSelection(), 20000,
     * 200)); confs.add(new Configuration("third_roulettte_split", new
     * RouletteWheelSelection(), 30000, 300)); confs.add(new
     * Configuration("third_rank_split", new LinearRankSelection(), 30000,
     * 300)); confs.add(new Configuration("big_roulettte_split", new
     * RouletteWheelSelection(), 50000, 300)); confs.add(new
     * Configuration("big_rank_split", new LinearRankSelection(), 50000, 300));
     * confs.add(new Configuration("bigbig_roulettte_split", new
     * RouletteWheelSelection(), 70000, 300)); confs.add(new
     * Configuration("bigbig_rank_split", new LinearRankSelection(), 70000,
     * 300));
     */

  }

  public static void main(String args[]) {
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
    for (Configuration cc : confs) {

      testUnifiedPetriMaker maker = new testUnifiedPetriMaker();
      BendingCreatureUTPNMapper mapper = new BendingCreatureUTPNMapper(maker.net, true);
      System.out.println(mapper.getNrOfGenes());
      ArrayList<IOperatorFactory<ICreatureGenerator<BendingCreature>>> generators = new ArrayList<>();
      generators.add(() -> new BendingCreatorGenerator(mapper.getNrOfGenes()));
      ArrayList<IOperatorFactory<ICreatureMutator<BendingCreature>>> mutators = new ArrayList<>();
      mutators.add(() -> new BendingCreatureMutator());
      ArrayList<IOperatorFactory<ICreatureBreeder<BendingCreature>>> breeders = new ArrayList<>();
      breeders.add(() -> new BendingBreeder());
      ArrayList<IOperatorFactory<ICreatureFitnes<BendingCreature>>> fitnesCals = new ArrayList<>();
      fitnesCals.add(() -> new EasyFitnessSplit());
      CreatureParallelPool<BendingCreature> pool = new CreatureParallelPool<>(generators, mutators, breeders,
          fitnesCals);
      SimpleGA.iteration = cc.iteration;
      SimpleGA.population = cc.population;
      SimpleGA<BendingCreature> algo = new SimpleGA<>(pool, new RouletteWheelSelection());
      IterationLogger log = new IterationLogger();
      algo.setLogger(log);
      algo.theAlgo();
      Integer id = algo.getMaxId();
      BendingCreature bb = pool.get(id);
      EasyFitnessSplit ff = new EasyFitnessSplit();
      double rez = ff.evaluate(bb);
      PlotUtils.plot(log.getLogsForPlotting(""), cc.name + "_Behave.svg");
      PlotUtils.writeToFile(cc.name + " rez.txt", Double.toString(rez) + " " + Double.toString(ff.maxError));
      saver.save(mapper.decode(bb), cc.name + "_upn.json");
    }

  }

}
