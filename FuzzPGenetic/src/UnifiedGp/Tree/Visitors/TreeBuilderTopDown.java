package UnifiedGp.Tree.Visitors;

import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;

public class TreeBuilderTopDown<NodeType> extends TreeBuilder<NodeType> {

  public TreeBuilderTopDown(TreeBuilderConfig<NodeType> config) {
    super(config);
  }

  @Override
  public IInnerNode<NodeType> genearteRandomTree(Random rnd) {
    return (IInnerNode<NodeType>) genearteRandomTreeRecusivly(rnd, config.getMaxDepth(), true, false);
  }

  @Override
  public IInnerNode<NodeType> genearteRandomTree(Random rnd, int maxDepth) {
    return (IInnerNode<NodeType>) genearteRandomTreeRecusivly(rnd, maxDepth, true, false);
  }

  public IInnerNode<NodeType> genearteFullRandomTree(Random rnd, int depth) {
    return (IInnerNode<NodeType>) genearteRandomTreeRecusivly(rnd, depth, true, true);
  }

  private INode<NodeType> genearteRandomTreeRecusivly(Random rnd, int remaningDepth, boolean first, boolean full) {
    if (!first && (remaningDepth <= 0 || ((!full) && config.getLeafOrInnerNode() < rnd.nextDouble()))) {
      return super.randomLeaf(rnd);
    } else {
      INode<NodeType> fi = genearteRandomTreeRecusivly(rnd, remaningDepth - 1, false, full);
      INode<NodeType> se = genearteRandomTreeRecusivly(rnd, remaningDepth - 1, false, full);
      return super.randomInnerNode(rnd, fi, se);
    }
  }

}
