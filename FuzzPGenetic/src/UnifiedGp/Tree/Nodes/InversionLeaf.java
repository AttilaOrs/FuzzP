package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class InversionLeaf implements ILeaf<NodeType> {

  @Override
  public NodeType getType() {
    return NodeType.Inv;
  }


}
