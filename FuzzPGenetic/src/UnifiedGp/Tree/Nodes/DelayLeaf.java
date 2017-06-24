package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class DelayLeaf implements ILeaf<NodeType> {

  private final int delay;

  public DelayLeaf(int delay) {
    this.delay = delay;
  }

  @Override
  public NodeType getType() {
    return NodeType.Delay;
  }

  public int getDelay() {
    return delay;
  }
}
