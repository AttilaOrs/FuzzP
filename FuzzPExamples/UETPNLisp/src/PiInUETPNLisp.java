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
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class PiInUETPNLisp {
  static Double command = 0.0;

  public static void main(String[] args) {
    INode<NodeType> root = UETPNLisp.fromFile(new File("pi_controller.uls"));
    ToPetriNet tpn = new ToPetriNet(createProblemSpecification(), true, false);
    PetriConversationResult rez = tpn.toNet((IInnerNode<NodeType>) root);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    exec.setRecorder(rec);
    Random rnd = new Random();
    HashMap<Integer, UnifiedToken> inp = new HashMap<>();
    rez.addActionIfPossible(0, t -> command = t.getValue());
    double x = 0;
    for (int i = 0; i < 30; i++) {
      inp.clear();

      double xnew = x * 0.67 + command * 0.35;
      double out = x * 0.87 + command * 0.22;
      x = xnew;
      rez.addToInpIfPossible(inp, 0, new UnifiedToken(1.0));
      rez.addToInpIfPossible(inp, 1, new UnifiedToken(out));
      command = 0.0;
      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(rez.net, rec, TransitionPlaceNameStore.createOrdinarNames(rez.net));


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
