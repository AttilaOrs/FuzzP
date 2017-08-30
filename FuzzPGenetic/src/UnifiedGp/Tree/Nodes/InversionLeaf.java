package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class InversionLeaf implements ILeaf<NodeType> {

  @Override
  public NodeType getType() {
    return NodeType.Inv;
  }

  @Override
  public ILeaf<NodeType> myClone() {
    return new InversionLeaf();
  }

  @Override
  public String toString() {
    return NodeType.Inv.symbol;
  }

}
