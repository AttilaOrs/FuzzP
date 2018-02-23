package UnifiedPetriRuleOptimizer.PiFitness;

import java.io.File;

import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider.Result;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FullRecorder;
import main.ScenarioSaverLoader;

public class ConvertNetToScenarioMain {

  public static final String fromPath = "/home/ors/Desktop/bprez/newEra/piRez_n32.json";
  public static final String toPath = "fi_test.json";

  public static void main(String[] args) {
    PiSimulator sim = new PiSimulator(26, 35, 36, true);
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
    String str = recorder.evolutionOfPlaceDatFormatOnceInTick(35, t -> ((UnifiedToken) t).getValue());
    PlotUtils.writeToFile("inp1.dat", str);
    str = recorder.evolutionOfPlaceDatFormatOnceInTick(36, t -> ((UnifiedToken) t).getValue());
    PlotUtils.writeToFile("inp2.dat", str);
    str = recorder.evolutionOfPlaceDatFormatOnceInTick(7, t -> ((UnifiedToken) t).getValue());
    PlotUtils.writeToFile("out.dat", str);

    saver.setFullRec(recorder);
    saver.setPetriNet(net);

    saver.save(new File(toPath));
  }

}
