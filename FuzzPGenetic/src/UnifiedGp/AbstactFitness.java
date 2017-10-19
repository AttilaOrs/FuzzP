package UnifiedGp;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.DynamicallySimplifiedPetriNetBuilder;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FiredTranitionRecorder;
import structure.ICreatureFitnes;

public abstract class AbstactFitness implements ICreatureFitnes<UnifiedGpIndi> {

  public static boolean RESET_LOOP = true;

  public static boolean HARD_LIMIT = true;
  public static boolean FIRED_TR_LIMIT = true;
  public static boolean APPLY_SIZE_LIMIT = true;

  public static int SIZE_LIMIT = 300;
  public static int SIZE_LIMIT_START = 200;
  protected ToPetriNet tp;
  protected ProblemSpecification ps;
  protected DynamicallySimplifiedPetriNetBuilder dynamicSimplifier;
  protected StaticSimplifierPetriBuilder staticSimplifier;
  protected PetriConversationResult rez;


  public AbstactFitness(ProblemSpecification ps) {
    tp = new ToPetriNet(ps, true, RESET_LOOP);
    dynamicSimplifier = new DynamicallySimplifiedPetriNetBuilder();
    staticSimplifier = new StaticSimplifierPetriBuilder();
    this.ps = ps;
  }

  protected PetriConversationResult convert(UnifiedGpIndi creature) {
    rez = tp.toNet(creature.getRoot());
    return rez;
  }

  protected double sizeMulti(int size) {
    double multi = 1.0;
    if (APPLY_SIZE_LIMIT) {
      if (size > SIZE_LIMIT) {
        return 0.0;
      }

      if (!HARD_LIMIT && size > SIZE_LIMIT_START) {
        multi = 1.0 - ((size - SIZE_LIMIT_START) / ((SIZE_LIMIT - SIZE_LIMIT_START) * 1.0));
      }
    }
    return multi;
  }
  
  protected double fireCountMulti(FiredTranitionRecorder<UnifiedToken> rec, int mooves) {
    double multi2 = 1.0;
    int ss = mooves * 500;
    if (FIRED_TR_LIMIT && rec.getTransitionFiredCount() > ss) {
      multi2 = 1.0 - (1.0 * (rec.getTransitionFiredCount() - ss)) / (2.0 * ss);
    }
    return multi2;
  }
  protected void updateCreatureWithSimplification(UnifiedGpIndi creature, PetriConversationResult rez,
      FiredTranitionRecorder<UnifiedToken> tk) {
    IInnerNode<NodeType> dynamiclySimpliedRoot = dynamicSimplifier.createSimplifiedTree(creature.getRoot(),
        tk.getFiredTransition(), rez.nodeTransitionMapping.get());
    // System.out.println(dynamiclySimpliedRoot);
    IInnerNode<NodeType> newRoot = staticSimplifier.simplifyTree(dynamiclySimpliedRoot);
    creature.setRoot(newRoot);
  }

  public PetriConversationResult getRez() {
    return rez;
  }

}
