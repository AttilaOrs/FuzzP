package UnifiedGp.Tree.Visitors;

import java.util.Map;
import java.util.Optional;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import core.UnifiedPetriLogic.UnifiedPetriNet;

public class PetriConversationResult {
  public final UnifiedPetriNet net;
  public final Map<Integer, Integer> inpNrToInpPlace;
  public final Map<Integer, Integer> outNrToOutTr;
  public final Optional<Map<INode<NodeType>, Integer>> nodeTransitionMapping;

  public PetriConversationResult(UnifiedPetriNet net, Map<Integer, Integer> inpNrToInpPlace,
      Map<Integer, Integer> outNrToOutTr, Optional<Map<INode<NodeType>, Integer>> mapping) {
    this.net = net;
    this.inpNrToInpPlace = inpNrToInpPlace;
    this.outNrToOutTr = outNrToOutTr;
    this.nodeTransitionMapping = mapping;
    
  }

}
