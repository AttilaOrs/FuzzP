package UnifiedGp.GpIndi;

import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.RandomNodeSelector;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import structure.operators.ICreatureMutator;

public class UnifiedGpIndiTreeMutator implements ICreatureMutator<UnifiedGpIndi> {


  private TreeBuilder<NodeType> builder;
  private RandomNodeSelector<NodeType> randomSelector;
  private CopyReplace<NodeType> copyReplace;

  public UnifiedGpIndiTreeMutator(TreeBuilder<NodeType> builder) {
    this.builder = builder;
    randomSelector = new RandomNodeSelector<>();
    copyReplace = new CopyReplace<>();
  }

  @Override
  public UnifiedGpIndi mutate(UnifiedGpIndi creature, Random random) {
    INode<NodeType> selected = randomSelector.selectRandomNode(creature.root,
        node -> !node.equals(creature.root), random).get();
    IInnerNode<NodeType> genrated = builder.genearteRandomCreature(random, 5);
    INode<NodeType> newRoot = copyReplace.copyReplace(creature.root, selected, genrated);
    return new UnifiedGpIndi((IInnerNode<NodeType>) newRoot);
  }

}
