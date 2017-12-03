import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Main.UnifiedVizualizer;
import UETPNLisp.UETPNLisp;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class PiInUETPNLisp {
  static Double command = 0.0;

  public static void main(String[] args) {
    INode<NodeType> root = UETPNLisp.fromFile(new File("pi_controller.uls"));
    ToPetriNet tpn = new ToPetriNet(createProblemSpecification(), true, false);
    PetriConversationResult rez = tpn.toNet((IInnerNode<NodeType>) root);

    PetriNetJsonSaver<UnifiedPetriNet> loader = new PetriNetJsonSaver<>();
    UnifiedPetriNet newNet = loader.load("piRez3.json", UnifiedPetriNet.class);
    
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(newNet);
    exec.setRecorder(rec);
    Random rnd = new Random();
    HashMap<Integer, UnifiedToken> inp = new HashMap<>();
    // rez.addActionIfPossible(0, t -> command = t.getValue());
    newNet.addActionForOuputTransition(0, t -> command = t.getValue());
    double x = 0;
    ReferenceProvider p = new ReferenceProvider();
    
      for (int i = 0; i < p.getRefSize(); i++) { inp.clear();
      
      double xnew = x * 0.67 + command * 0.35;
      double out = x * 0.87 + command * 0.22;
      x = xnew;
      inp.put(0, new UnifiedToken(out));
      inp.put(1, new UnifiedToken(p.getReference(i)));
      
      command = 0.0; exec.runTick(inp); }
     
    UnifiedVizualizer.visualize(newNet, rec, TransitionPlaceNameStore.createOrdinarNames(rez.net));


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
