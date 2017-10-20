package UnifiedGp.GpIndi.UsageStats;


import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.DynamicallySimplifiedPetriNetBuilder;
import UnifiedGp.Tree.Visitors.NodeUsageAsociator;
import UnifiedGp.Tree.Visitors.NodeUsageMerger;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import UnifiedGp.Tree.Visitors.UsageStats;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FireCounterRecorder;
import core.common.recoder.FiredTranitionRecorder;
import structure.ICreatureFitnes;

public abstract class AbstactFitnessWithUsage implements ICreatureFitnes<UnifiedGpIndiWithUsageStats> {

  protected ToPetriNet tp;
  protected ProblemSpecification ps;
  protected DynamicallySimplifiedPetriNetBuilder dynamicSimplifier;
  protected StaticSimplifierPetriBuilder staticSimplifier;
  private PetriConversationResult rez;

  private NodeUsageAsociator assoc;

  public AbstactFitnessWithUsage(ProblemSpecification ps) {
    tp = new ToPetriNet(ps, true, AbstactFitness.RESET_LOOP);
    dynamicSimplifier = new DynamicallySimplifiedPetriNetBuilder();
    staticSimplifier = new StaticSimplifierPetriBuilder();
    assoc = new NodeUsageAsociator();
  }

  protected PetriConversationResult convert(UnifiedGpIndi creature) {
    rez = tp.toNet(creature.getRoot());
    return rez;
  }

  protected double sizeMulti(int size) {
    double multi = 1.0;
    if (AbstactFitness.APPLY_SIZE_LIMIT) {
      if (size > AbstactFitness.SIZE_LIMIT) {
        return 0.0;
      }

      if (!AbstactFitness.HARD_LIMIT && size > AbstactFitness.SIZE_LIMIT_START) {
        multi = 1.0 - ((size - AbstactFitness.SIZE_LIMIT_START)
            / ((AbstactFitness.SIZE_LIMIT - AbstactFitness.SIZE_LIMIT_START) * 1.0));
      }
    }
    return multi;
  }

  protected double fireCountMulti(FiredTranitionRecorder<UnifiedToken> rec, int mooves) {
    double multi2 = 1.0;
    int ss = mooves * 500;
    if (AbstactFitness.FIRED_TR_LIMIT && rec.getTransitionFiredCount() > ss) {
      multi2 = 1.0 - (1.0 * (rec.getTransitionFiredCount() - ss)) / (2.0 * ss);
    }
    return multi2;
  }

  protected void updateCreatureWithSimplification(UnifiedGpIndiWithUsageStats creature, PetriConversationResult rez,
      FireCounterRecorder<UnifiedToken> tk) {
    IInnerNode<NodeType> dynamiclySimpliedRoot = dynamicSimplifier.createSimplifiedTree(creature.getRoot(),
        tk.getFiredTransition(), rez.nodeTransitionMapping.get());
    UsageStats oldStats = assoc.calcUsage(creature.getRoot(), tk, rez.nodeTransitionMapping.get());


    IInnerNode<NodeType> newRoot = staticSimplifier.simplifyTree(dynamiclySimpliedRoot);
    UsageStats mm = NodeUsageMerger.merge(creature.getRoot(), oldStats, newRoot, new UsageStats());
      creature.setUsageStats(mm);
    creature.setRoot(newRoot);
  }

  public PetriConversationResult getRez() {
    return rez;
  }

}
