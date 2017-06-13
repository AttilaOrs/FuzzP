package FuzzyGp.Tree.Visitors;

import java.util.ArrayList;
import java.util.Random;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.Operator;

public class RandomNodeSelector implements VisitorCustomizer {
  ArrayList<INode> ops;
  private boolean firstOperatorIsSelectable, leafsAreSelectable;

  public RandomNodeSelector() {
    firstOperatorIsSelectable = false;
    leafsAreSelectable = true;
  }

  public RandomNodeSelector(boolean firstOperatorIsSelectable, boolean leafsAreSelectable) {
    this.firstOperatorIsSelectable = firstOperatorIsSelectable;
    this.leafsAreSelectable = leafsAreSelectable;
  }

  public INode randomNode(INode node, Random rnd) {
    ops = new ArrayList<INode>();
    BreadthFirstVisitorRunner runner = new BreadthFirstVisitorRunner(this);
    runner.visit(node);
    int index = -1;
    if (firstOperatorIsSelectable) {
      index = rnd.nextInt(ops.size());
    } else {
      if (ops.size() == 1) {
        return null;
      }
      index = rnd.nextInt(ops.size() - 1) + 1;
    }
    return ops.get(index);
  }

  public void visitOperator(Operator oo) {
    ops.add(oo);
  };

  public void visitLeaf(ILeaf oo) {
    if (leafsAreSelectable) {
      ops.add(oo);
    }

  };
}
