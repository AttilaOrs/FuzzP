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

  public static final int INP0 = 225;
  public static final int INP1 = 229;
  public static final int OUT0 = 219;

  public static final String fromPath = "/home/ors/Desktop/bprez/2/FirstOdrerClassic_47_i100_p2000__Petri.json";
  public static final String toPath = "fi_test.json";

  public static void main(String[] args) {
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
    PlotUtils.writeToFile("inp1.dat", str);
    str = recorder.evolutionOfPlaceDatFormatOnceInTick(INP1, t -> ((UnifiedToken) t).getValue());
    PlotUtils.writeToFile("inp2.dat", str);
    str = recorder.evolutionOfPlaceDatFormatOnceInTick(OUT0, t -> ((UnifiedToken) t).getValue());
    PlotUtils.writeToFile("out.dat", str);

    saver.setFullRec(recorder);
    saver.setPetriNet(net);

    saver.save(new File(toPath));
  }

}
