package FuzzyGp.Tree.Visitors;

import java.util.ArrayList;
import java.util.Random;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.Operator;

public class RandomNodeSelectorWithType implements VisitorCustomizer {

  private Class[] types;
  private ArrayList<INode> savedNode;

  public INode getRandomNodeWithtypes(INode root, Random rnd, Class... types) {
    this.types = types;
    this.savedNode = new ArrayList<INode>();
    BreadthFirstVisitorRunner rr = new BreadthFirstVisitorRunner(this);
    rr.visit(root);
    if (savedNode.size() == 1) {
      return savedNode.get(0);
    }
    if (savedNode.size() > 1) {
      return savedNode.get(rnd.nextInt(savedNode.size()));
    }
    return null; // the size is zero hence no such nodes exists
  }

  private boolean hasToBeSaved(INode node) {
    for (Class ss : this.types) {
      if (ss.isInstance(node)) {
        return true;
      }
    }
    return false;
  }

  public void visitLeaf(ILeaf node) {
    if (hasToBeSaved(node)) {
      savedNode.add(node);
    }
  };

  public void visitOperator(Operator oo) {
    if (hasToBeSaved(oo)) {
      savedNode.add(oo);
    }
  };

}
