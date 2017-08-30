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

  @Override
  public ILeaf<NodeType> myClone() {
    return new DelayLeaf(delay);
  }

  @Override
  public String toString() {
    return NodeType.Delay.symbol + ":" + delay;
  }
}
