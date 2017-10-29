package UnifiedGp.GpIndi.UsageStats;


import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.NodeUsageAsociator;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import UnifiedGp.Tree.Visitors.UsageStats;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FireCounterRecorder;
import core.common.recoder.FiredTranitionRecorder;
import structure.ICreatureFitnes;

public abstract class AbstactFitnessWithUsage implements ICreatureFitnes<UnifiedGpIndiWithUsageStats> {

  protected ToPetriNet tp;
  protected ProblemSpecification ps;
  protected DynamicSymplifierBuilderWithStats dynamicSimplifier;
  protected StaticSimplifierWithStats staticSimplifier;
  protected PetriConversationResult rez;

  private NodeUsageAsociator assoc;

  public AbstactFitnessWithUsage(ProblemSpecification ps) {
    tp = new ToPetriNet(ps, true, AbstactFitness.RESET_LOOP);
    dynamicSimplifier = new DynamicSymplifierBuilderWithStats();
    staticSimplifier = new StaticSimplifierWithStats();
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
    UsageStats oldStats = assoc.calcUsage(creature.getRoot(), tk, rez.nodeTransitionMapping.get());
    IInnerNode<NodeType> dynamiclySimpliedRoot = dynamicSimplifier.createSimplifiedTree(creature.getRoot(),
        tk.getFiredTransition(), rez.nodeTransitionMapping.get(), oldStats);

    UsageStats statAfterDynamicSimpl = dynamicSimplifier.getNewUsageStats();


    IInnerNode<NodeType> newRoot = staticSimplifier.simplifyTree(dynamiclySimpliedRoot, statAfterDynamicSimpl);
    creature.setUsageStats(staticSimplifier.getNewStat());
    creature.setRoot(newRoot);
  }

  public PetriConversationResult getRez() {
    return rez;
  }

}
