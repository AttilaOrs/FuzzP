import java.io.File;

import Main.UnifiedVizualizer;
import UETPNLisp.UETPNLisp;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGpProblmes.ArtificalAnt.AntFitnes;
import core.Drawable.TransitionPlaceNameStore;

public class AntRunner {

  public static void main(String[] args) {
    INode<NodeType> root = UETPNLisp.fromFile(new File("second.uls"));
    AntFitnes fitnes = new AntFitnes();
    double rez = fitnes.evaluate(new UnifiedGpIndi((IInnerNode<NodeType>) root));
    System.out.println(rez);
    PetriConversationResult convRez = fitnes.originalRez;
    System.out.println(fitnes.rec);
    convRez.nodeTransitionMapping.get().entrySet().forEach(
        e -> System.out.println(e.getKey() + " >> " + e.getValue()));
    UnifiedVizualizer.visualize(convRez.net, fitnes.recc, TransitionPlaceNameStore.createOrdinarNames(convRez.net));

  }

}
