package UnifiedGp.Tree.Visitors;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;

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

  public boolean addActionIfPossible(Integer i, Consumer<UnifiedToken> cons) {
    
    if (outNrToOutTr.containsKey(i)) {
      net.addActionForOuputTransition(outNrToOutTr.get(i), cons);
      return true;
    }
    return false;

  }

  public boolean addToInpIfPossible(Map<Integer, UnifiedToken> inp, Integer inpNr, UnifiedToken tk) {
    if (inpNrToInpPlace.containsKey(inpNr)) {
      inp.put(inpNrToInpPlace.get(inpNr), tk);
      return true;
    }
    return false;
  }

  public boolean hasInp(int i) {
    return inpNrToInpPlace.containsKey(i);

  }

}
