import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGpProblmes.FirstOrderSystem.FirtsOrderSystem;
import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class PiRezultView {
  static Double command = 0.0;

  public static void main(String[] args) {

    FirtsOrderSystem sys = new FirtsOrderSystem(0.67, 0.35, 0.87, 0.22);
    ReferenceProvider prov = new ReferenceProvider(3);
    PetriNetJsonSaver<UnifiedPetriNet> loader = new PetriNetJsonSaver<>();
    UnifiedPetriNet newNet = loader.load("piRez_n99.json", UnifiedPetriNet.class);

    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(newNet);
    exec.setRecorder(rec);
    HashMap<Integer, UnifiedToken> inp = new HashMap<>();
    // rez.addActionIfPossible(0, t -> command = t.getValue());
    newNet.addActionForOuputTransition(0, d -> {
      sys.setCommand(d.getValue());
    });
    double x = 0;
    for (int i = 0; i < prov.getRefSize(); i++) {
      inp.clear();

      inp.put(0, new UnifiedToken(sys.curentStatus()));
      inp.put(1, new UnifiedToken(prov.getReference(i)));
      exec.runTick(inp);
      sys.runTick();

    }
    System.out.println(100.0 / (1 + prov.calcError(sys.getEvolution())));

    UnifiedVizualizer.visualize(newNet, rec, TransitionPlaceNameStore.createOrdinarNames(newNet));


  }

  public static ProblemSpecificationImpl createProblemSpecification() {
    Map<Integer, Double> inpScale = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      inpScale.put(i, 2.0);
    }
    Map<Integer, Double> outScale = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      outScale.put(i, 2.0);
    }

    return new ProblemSpecificationImpl(2.0, 5, inpScale, outScale);
  }
}
