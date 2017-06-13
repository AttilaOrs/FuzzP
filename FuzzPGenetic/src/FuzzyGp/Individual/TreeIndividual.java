package FuzzyGp.Individual;

import java.util.ArrayDeque;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Visitors.DepthFirstVisitorRunner;
import FuzzyGp.Tree.Visitors.VisitorCustomizer;
import structure.GPIndividSize;
import structure.IGPGreature;

public class TreeIndividual implements IGPGreature {

  private INode root;
  private GPIndividSize mySize;

  public TreeIndividual(INode myNode) {
    this.root = myNode;
    mySize = (new Sizer()).calcSize(root);
  }

  @Override
  public GPIndividSize getSizes() {
    return mySize;
  }

  public INode getRoot() {
    return this.root;
  }

  public void setRoot(INode n) {
    this.root = n;
    mySize = (new Sizer()).calcSize(root);
  }

  private static class Sizer implements VisitorCustomizer {
    ArrayDeque<GPIndividSize> stack = new ArrayDeque<>();

    public GPIndividSize calcSize(INode node) {
      DepthFirstVisitorRunner runner = new DepthFirstVisitorRunner(this);
      runner.visit(node);
      return stack.pop();

    }

    public void visitLeaf(ILeaf node) {
      stack.push(new GPIndividSize(0, 1, 1));
    };

    public void visitOperator(Operator oo) {
      GPIndividSize ss = stack.pop();
      GPIndividSize ff = stack.pop();
      GPIndividSize rez = GPIndividSize.uniteAtOperator(ff, ss);
      stack.push(rez);
    };

  }

}
