package FuzzyGp.Tree.Nodes;

import FuzzyGp.Tree.INode;

public class Operator implements INode {

  public Operator(OperatorType type, INode firstChildern, INode secondChildren) {
    this.firstChildern = firstChildern;
    this.secondChildren = secondChildren;
    this.type = type;
  }

  public final INode firstChildern, secondChildren;
  public final OperatorType type;

}
