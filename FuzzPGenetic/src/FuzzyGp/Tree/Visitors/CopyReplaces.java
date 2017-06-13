package FuzzyGp.Tree.Visitors;

import java.util.ArrayDeque;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.Operator;

public class CopyReplaces implements VisitorCustomizer {
  public CopyReplaces() {
  }

  INode original, replaceWith;
  ArrayDeque<INode> queue = new ArrayDeque<>();

  public INode copy(INode toCopy) {
    queue = new ArrayDeque<INode>();
    this.original = null;
    this.replaceWith = null;
    DepthFirstVisitorRunner runner = new DepthFirstVisitorRunner(this);
    runner.visit(toCopy);
    return queue.pop();
  }

  public INode replace(INode toVisit, INode original, INode replaceWith) {
    queue = new ArrayDeque<INode>();
    this.original = original;
    this.replaceWith = replaceWith;
    DepthFirstVisitorRunner runner = new DepthFirstVisitorRunner(this);
    runner.visit(toVisit);
    return queue.pop();

  }

  public void visitOperator(Operator oo) {
    INode ss = queue.pop();
    INode ff = queue.pop();
    if (original != null && oo.equals(original)) {
      queue.push(replaceWith);
    } else {
      queue.push(new Operator(oo.type, ff, ss));
    }
  };

  public void visitLeaf(ILeaf node) {
    if (original != null && node.equals(original)) {
      queue.push(replaceWith);
    } else {
      queue.push(node.myClone());
    }
  };
}