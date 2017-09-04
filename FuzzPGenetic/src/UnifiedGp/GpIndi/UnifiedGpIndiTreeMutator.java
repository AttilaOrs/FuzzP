package UnifiedGp.GpIndi;

import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.RandomNodeSelector;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import structure.operators.ICreatureMutator;

public class UnifiedGpIndiTreeMutator implements ICreatureMutator<UnifiedGpIndi> {

  private static final boolean defaultStaticSimplification = true;
  private TreeBuilder<NodeType> builder;
  private RandomNodeSelector<NodeType> randomSelector;
  private CopyReplace<NodeType> copyReplace;
  private final boolean staticSimplificationEnabbled;
  private final StaticSimplifierPetriBuilder simplifier;

  public UnifiedGpIndiTreeMutator(TreeBuilder<NodeType> builder) {
    this(builder, defaultStaticSimplification);
  }

  public UnifiedGpIndiTreeMutator(TreeBuilder<NodeType> builder, boolean enableStaticSimplifiction) {
    this.builder = builder;
    randomSelector = new RandomNodeSelector<>();
    copyReplace = new CopyReplace<>();
    staticSimplificationEnabbled = enableStaticSimplifiction;
    if (staticSimplificationEnabbled) {
      simplifier = new StaticSimplifierPetriBuilder();
    } else {
      simplifier = null;
    }
  }

  @Override
  public UnifiedGpIndi mutate(UnifiedGpIndi creature, Random random) {
    INode<NodeType> selected = randomSelector.selectRandomNode(creature.getRoot(),
        node -> !node.equals(creature.getRoot()), random).get();
    IInnerNode<NodeType> genrated = builder.genearteRandomTree(random, 5);
    INode<NodeType> newRoot = copyReplace.copyReplace(creature.getRoot(), selected, genrated);
    if (staticSimplificationEnabbled) {

      return new UnifiedGpIndi(simplifier.simplifyTree((IInnerNode<NodeType>) newRoot));
    }
    return new UnifiedGpIndi((IInnerNode<NodeType>) newRoot);
  }

}
