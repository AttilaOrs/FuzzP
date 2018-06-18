package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;

public class InnerNode implements IInnerNode<NodeType> {

  final NodeType myType;
  final INode<NodeType> fi, se;

  public InnerNode(NodeType myType, INode<NodeType> fi, INode<NodeType> se) {
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

  @Override
  public IInnerNode<NodeType> myClone(INode<NodeType> fi, INode<NodeType> se) {
    return new InnerNode(myType, fi, se);
  }

  @Override
  public String toString() {
    return "(" + myType.symbol + " " + ((fi != null) ? fi.toString() : "XX") + " "
        + ((se != null) ? se.toString() : "XX") + ")";
  }

}
