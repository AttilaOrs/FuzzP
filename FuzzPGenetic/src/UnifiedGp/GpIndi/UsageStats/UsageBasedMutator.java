package UnifiedGp.GpIndi.UsageStats;

import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import structure.operators.ICreatureMutator;

public class UsageBasedMutator implements ICreatureMutator<UnifiedGpIndiWithUsageStats> {

  private TreeBuilder<NodeType> builder;
  private CopyReplace<NodeType> copyReplace;
  private boolean staticSimplificationEnabbled;
  private StaticSimplifierPetriBuilder simplifier;
  private UsageSelector selector;
  private int maxDepth;

  public UsageBasedMutator(TreeBuilder<NodeType> builder, boolean enableStaticSimplifiction, int maxDepth,
      UsageSelector selector) {
    this.builder = builder;
    copyReplace = new CopyReplace<>();
    staticSimplificationEnabbled = enableStaticSimplifiction;
    this.selector = selector;
    if (staticSimplificationEnabbled) {
      simplifier = new StaticSimplifierPetriBuilder();
    } else {
      simplifier = null;
    }

  }


  @Override
  public UnifiedGpIndiWithUsageStats mutate(UnifiedGpIndiWithUsageStats creature, Random random) {
    INode<NodeType> selected = selector.selectNode(creature.getUsageStats(), random, creature.getRoot());
    if (creature.getRoot().equals(selected) || selected == null) {
      selected = creature.getRoot().getFirstChild();
    }
    IInnerNode<NodeType> generated = builder.genearteRandomTree(random, maxDepth);
    INode<NodeType> newRoot = copyReplace.copyReplace(creature.getRoot(), selected, generated);
    if (staticSimplificationEnabbled) {
      return new UnifiedGpIndiWithUsageStats(simplifier.simplifyTree((IInnerNode<NodeType>) newRoot));
    }
    return new UnifiedGpIndiWithUsageStats((IInnerNode<NodeType>) newRoot);

  }

}
