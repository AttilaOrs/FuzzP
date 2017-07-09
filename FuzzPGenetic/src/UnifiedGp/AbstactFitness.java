package UnifiedGp;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.DynamicallySimplifiedPetriNetBuilder;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FiredTranitionRecorder;
import structure.ICreatureFitnes;

public abstract class AbstactFitness implements ICreatureFitnes<UnifiedGpIndi> {


  protected ToPetriNet tp;
  protected ProblemSpecification ps;
  protected DynamicallySimplifiedPetriNetBuilder simplifier;

  public AbstactFitness(ProblemSpecification ps) {
    tp = new ToPetriNet(ps, true);
    simplifier = new DynamicallySimplifiedPetriNetBuilder();

  }

  protected PetriConversationResult convert(UnifiedGpIndi creature) {
    PetriConversationResult rez = tp.toNet(creature.getRoot());
    return rez;
  }
  
  protected void updateCreatureWithSimplification(UnifiedGpIndi creature, PetriConversationResult rez,
      FiredTranitionRecorder<UnifiedToken> tk) {
    IInnerNode<NodeType> newRoot = simplifier.createSimplifiedTree(creature.getRoot(),
        tk.getFiredTransition(), rez.nodeTransitionMapping.get());
    creature.setRoot(newRoot);
  }

}
