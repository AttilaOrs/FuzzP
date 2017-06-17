package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;

public class Operator implements IInnerNode<NodeType> {

  NodeType myType;
  INode<NodeType> fi, se;

  public Operator(NodeType myType, INode<NodeType> fi, INode<NodeType> se) {
    this.myType = myType;
    this.fi = fi;
    this.se = se;
  }

  @Override
  public NodeType getType() {
    return myType;
  }

  @Override
  public INode<NodeType> getFirstChild() {
    return fi;
  }

  @Override
  public INode<NodeType> getSecondChild() {
    return se;
  }


}
