package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class DelayLeaf implements ILeaf<NodeType, DelaySubtype> {

  private final int delay;

  public DelayLeaf(int delay) {
    this.delay = delay;
  }

  @Override
  public NodeType getType() {
    return NodeType.Delay;
  }

  @Override
  public DelaySubtype getSubtype() {
    return DelaySubtype.SimpleDelay;
  }

  public int getDelay() {
    return delay;
  }
}
