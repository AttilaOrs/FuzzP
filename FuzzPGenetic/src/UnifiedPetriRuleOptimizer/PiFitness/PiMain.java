package UnifiedPetriRuleOptimizer.PiFitness;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.IntStream;

import com.google.gson.Gson;

import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreaturePoolWithStreams;
import UnifiedGp.Tree.Visitors.RuleOptimizationData;
import UnifiedPetriRuleOptimizer.BitIndi;
import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import UnifiedPetriRuleOptimizer.Generator;
import UnifiedPetriRuleOptimizer.Mutator;
import UnifiedPetriRuleOptimizer.UnifromCrossoverBitIndi;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FullRecorder;
import main.ScenarioSaverLoader;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class PiMain {

  public static void main(String[] args) {
    for (int tryning = 0; tryning < 100; tryning++) {

      PiUnifiedPetriMaker maker = new PiUnifiedPetriMaker();
      BitIndiDecoder decoder = new BitIndiDecoder(maker.net,
          IntStream.range(0, maker.net.getNrOfTransition()).mapToObj(t -> t).collect(toList()));
      System.out.println("nr of rules:: " + decoder.getNrOfRules());

      ArrayList<IOperatorFactory<ICreatureGenerator<BitIndi>>> gens = new ArrayList<>();
      gens.add(() -> new Generator(decoder.getNrOfRules()));

      ArrayList<IOperatorFactory<ICreatureMutator<BitIndi>>> mutators = new ArrayList<>();
      mutators.add(Mutator::new);

      ArrayList<IOperatorFactory<ICreatureBreeder<BitIndi>>> breeders = new ArrayList<>();
      breeders.add(UnifromCrossoverBitIndi::new);

      ArrayList<IOperatorFactory<ICreatureFitnes<BitIndi>>> fitnesses = new ArrayList<>();
      fitnesses.add(PiMain::createFitnes);

      ICreaturePool<BitIndi> pool = new CreaturePoolWithStreams<>(gens, mutators, breeders, fitnesses);

      SimpleGA<BitIndi> algo = new SimpleGA<>(pool, new LinearRankSelection());
      SimpleGA.iteration = 100;
      SimpleGA.population = 2000;
      algo.theAlgo();
      Integer best = algo.getMaxId();
      BitIndi i = pool.get(best);
      UnifiedPetriNet newNEt = decoder.modified(i);
      PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
      String jsonStr = saver.makeString(newNEt);
      PlotUtils.writeToFile("piRez_n" + tryning + ".json", jsonStr);
      // FirstOrderFitnes fitnes = new FirstOrderFitnes();
      double rez = createFitnes().evaluate(i);
      PlotUtils.writeToFile("rez" + tryning + ".txt", Double.toString(rez));
    }

  }

  private static BitIndiDecoder masterDecoder = null;

  public static FirstOrderFitnes createFitnes() {
    if (masterDecoder == null) {
      ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> loader = new ScenarioSaverLoader<>(UnifiedPetriNet.class);
      loader.load(new File("pi_secenario.json"), UnifiedToken::buildFromString);
      FullRecorder<UnifiedToken> rec = loader.getFullRec();
      UnifiedPetriNet net = loader.getPetriNet();

      String all = "";

      try (BufferedReader br = new BufferedReader(new FileReader("pi_scenario_data.json"))) {
        StringBuilder sb = new StringBuilder();
        all = br.lines().collect(joining());
      } catch (Exception e) {
        System.err.println(e);
      }
      Gson gg = new Gson();
      RuleOptimizationData f = gg.fromJson(all, RuleOptimizationData.class);
      ArrayList<Integer> trs = new ArrayList<>();
      trs.addAll(f.getType(RuleOptimizationData.OptType.Inp));
      trs.addAll(f.getType(RuleOptimizationData.OptType.Out));
      trs.addAll(f.getType(RuleOptimizationData.OptType.SplitExit));
      masterDecoder = new BitIndiDecoder(net, trs);
      System.out.println(" nr of rules:: " + masterDecoder.getNrOfRules());
    }

    return new FirstOrderFitnes(masterDecoder.myClone(), 26, 35, 36);
  }

}
