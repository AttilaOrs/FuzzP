package UnifiedPetriRuleOptimizer.PiFitness;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

  private static final int OUT0_TR_NR = 154;
  private static final int INP0_PL_NR = 162;
  private static final int INP1_PL_NR = 166;
  private static final String PI_NET_OPTIMIZATION_DATA = "FirstOdrerClassic_5_i100_p2000__OptData.json";
  private static final String PI_NET_JSON = "fi_test.json";

  public static void main(String[] args) {
    initMasterDecoder();
    for (int tryning = 0; tryning < 100; tryning++) {


      ArrayList<IOperatorFactory<ICreatureGenerator<BitIndi>>> gens = new ArrayList<>();
      gens.add(() -> new Generator(masterDecoder.getNrOfRules()));

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
      long start = System.currentTimeMillis();
      algo.theAlgo();
      long stop = System.currentTimeMillis();

      Integer best = algo.getMaxId();
      BitIndi i = pool.get(best);
      UnifiedPetriNet newNEt = masterDecoder.modified(i);
      PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
      String jsonStr = saver.makeString(newNEt);
      PlotUtils.writeToFile("piRez_n" + tryning + ".json", jsonStr);
      // FirstOrderFitnes fitnes = new FirstOrderFitnes();
      double rez = createFitnes().evaluate(i);
      String g = Double.toString(rez) + "\n";
      g += TimeUnit.MILLISECONDS.toMinutes(stop- start) + "\n";
      g += (stop- start) + "\n";
      
      
      
      PlotUtils.writeToFile("rez" + tryning + ".txt", g);
    }

  }

  private static BitIndiDecoder masterDecoder = null;

  private static void initMasterDecoder() {

      ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> loader = new ScenarioSaverLoader<>(UnifiedPetriNet.class);
    loader.load(new File(PI_NET_JSON), UnifiedToken::buildFromString);
      FullRecorder<UnifiedToken> rec = loader.getFullRec();
      UnifiedPetriNet net = loader.getPetriNet();

      String all = "";

      try (BufferedReader br = new BufferedReader(new FileReader(PI_NET_OPTIMIZATION_DATA))) {
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

  public static FirstOrderFitnes createFitnes() {
    if (masterDecoder == null) {
      initMasterDecoder();
    }

    return new FirstOrderFitnes(masterDecoder.myClone(), OUT0_TR_NR, INP0_PL_NR, INP1_PL_NR);
  }

}
