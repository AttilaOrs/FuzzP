package UnifiedGp.Tree.Visitors;

import java.util.Map;

import core.UnifiedPetriLogic.UnifiedPetriNet;

public class PetriConversationResult {
  public final UnifiedPetriNet net;
  public final Map<Integer, Integer> inpNrToInpPlace;
  public final Map<Integer, Integer> outNrToOutTr;

  public PetriConversationResult(UnifiedPetriNet net, Map<Integer, Integer> inpNrToInpPlace,
      Map<Integer, Integer> outNrToOutTr) {
    this.net = net;
    this.inpNrToInpPlace = inpNrToInpPlace;
    this.outNrToOutTr = outNrToOutTr;
  }

}
