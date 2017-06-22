package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class InversionLeaf implements ILeaf<NodeType, InversionNodeType> {

  @Override
  public NodeType getType() {
    return NodeType.Inv;
  }

  @Override
  public InversionNodeType getSubtype() {
    return InversionNodeType.Inv;
  }

}
