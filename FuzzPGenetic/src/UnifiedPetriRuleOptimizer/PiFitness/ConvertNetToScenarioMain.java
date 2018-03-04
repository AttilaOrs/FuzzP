package UnifiedPetriRuleOptimizer.PiFitness;

import java.io.File;
import java.util.stream.Collectors;

import UnifiedGpProblmes.FirstOrderSystem.FirtsOrderSystem;
import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider.Result;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FullRecorder;
import main.ScenarioSaverLoader;

public class ConvertNetToScenarioMain {

  public static final int INP0 = 162;
  public static final int INP1 = 166;
  public static final int OUT0 = 154;

  public static final String fromPath = "/home/ors/Desktop/bprez/different_system/piRez_n22.json";

  public static final String toPath = "fi_test_diff_opt.json";

  public static void main(String[] args) {
    convert();
    // simulatePi();
  }

  private static void simulatePi() {
    // FirtsOrderSystem sys = new FirtsOrderSystem(0.9, 0.67, 0.2, 0.1);
    FirtsOrderSystem sys = new FirtsOrderSystem(0.67, 0.35, 0.87, 0.22);
    sys.setCommand(1.0);
    for (int i = 0; i <= 60; i++) {
      sys.setCommand(1.0);
      sys.runTick();
    }
    String w = sys.getEvolution().stream().map(d -> String.valueOf(d)).collect(Collectors.joining("\n"));
    PlotUtils.writeToFile("pi_evol1.dat", w);
  }

  private static void convert() {
    PiSimulator sim = new PiSimulator(OUT0, INP0, INP1, true);
    PetriNetJsonSaver<UnifiedPetriNet> loader = new PetriNetJsonSaver<UnifiedPetriNet>();
    UnifiedPetriNet net = loader.load(fromPath, UnifiedPetriNet.class);

    Result wamp = sim.simulate(net);
    System.out.println(">>> error:" + wamp.error);
    System.out.println(">>> steady:" + wamp.steadyStateError);
    System.out.println(">>> change:" + wamp.changeSum);
    double fi = 100.0 / (1.0 + 0.5 * wamp.error + 0.4 * wamp.changeSum + 0.1 * wamp.steadyStateError);
    System.out.println(">>> fi :  " + fi);

    ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> saver = new ScenarioSaverLoader<>(UnifiedPetriNet.class);

    FullRecorder<UnifiedToken> recorder = sim.getRecorder();
    String str = recorder.evolutionOfPlaceDatFormatOnceInTick(INP0, t -> ((UnifiedToken) t).getValue());
    PlotUtils.writeToFile("inp1_diff_opt.dat", str);
    str = recorder.evolutionOfPlaceDatFormatOnceInTick(INP1, t -> ((UnifiedToken) t).getValue());
    PlotUtils.writeToFile("inp2_diff_opt.dat", str);
    str = recorder.evolutionOfPlaceDatFormatOnceInTick(OUT0, t -> ((UnifiedToken) t).getValue());
    PlotUtils.writeToFile("out_diff_opt.dat", str);

    saver.setFullRec(recorder);
    saver.setPetriNet(net);

    saver.save(new File(toPath));
  }

}
