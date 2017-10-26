

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
import core.UnifiedPetriLogic.DrawableUnifiedPetriNetWithExternalNames;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import dotDrawer.PetriDotDrawerVertical;

public class UETPNLispVisualzer {

  public static void main(String[] args) {
    INode<NodeType> root = UETPNLisp.fromFile(new File("first.uls"));
    ToPetriNet tpn = new ToPetriNet(createProblemSpecification(), true, false);
    PetriConversationResult rez = tpn.toNet((IInnerNode<NodeType>) root);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    exec.setRecorder(rec);
    Random rnd = new Random();
    HashMap<Integer, UnifiedToken> inp = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      inp.clear();
      for (int inpId = 0; inpId < 100; inpId++) {
        if (rnd.nextBoolean()) {
          rez.addToInpIfPossible(inp, inpId, new UnifiedToken(rnd.nextDouble() - 0.5));
        }
      }
      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(rez.net, rec, TransitionPlaceNameStore.createOrdinarNames(rez.net));
    PetriDotDrawerVertical drawer = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNetWithExternalNames(rez.net, TransitionPlaceNameStore.createOrdinarNames(rez.net)));
    drawer.makeImage("secondExample");

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
