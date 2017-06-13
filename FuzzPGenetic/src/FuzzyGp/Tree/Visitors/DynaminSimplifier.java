package FuzzyGp.Tree.Visitors;

import java.util.ArrayDeque;
import java.util.Set;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OperatorType;
import FuzzyGp.Tree.Nodes.ZeroEventInput;

public class DynaminSimplifier implements VisitorCustomizer {

  private Set<Integer> firedTransition;
  private NodeToTransitionMapping mapping;
  private DepthFirstVisitorRunner runner = new DepthFirstVisitorRunner(this);
  ArrayDeque<INode> que;

  public INode simplify(INode root, Set<Integer> firedTrasnition, NodeToTransitionMapping mapping) {
    que = new ArrayDeque<>();
    this.firedTransition = firedTrasnition;
    this.mapping = mapping;
    runner.visit(root);
    return que.pop();
  }

  public void visitLeaf(ILeaf node) {
    int[] rez = mapping.getMyTransitions(node);
    if (firedTransition.contains(rez[0])) {
      que.push(node);
    } else {
      que.push(new ZeroEventInput());
    }
  };

  public void visitConc(Operator oo) {
    INode second = que.pop();
    INode first = que.pop();
    if (isZeroEventInputLeaf(second) && isZeroEventInputLeaf(first)) {
      que.push(new ZeroEventInput());
      return;
    }
    que.push(new Operator(OperatorType.CONC, first, second));

  };

  private boolean isZeroEventInputLeaf(INode node) {
    return node instanceof ZeroEventInput;
  }

  public void visitSelect(Operator oo) {
    INode second = que.pop();
    INode first = que.pop();
    if (isZeroEventInputLeaf(second) && isZeroEventInputLeaf(first)) {
      que.push(new ZeroEventInput());
      return;
    }
    if (isZeroEventInputLeaf(second)) {
      que.push(first);
      return;
    }
    if (isZeroEventInputLeaf(first)) {
      que.push(second);
      return;
    }
    que.push(new Operator(OperatorType.SELCT, first, second));

  };

  public void visitSeq(Operator oo) {
    INode second = que.pop();
    INode first = que.pop();
    if (isZeroEventInputLeaf(second) && isZeroEventInputLeaf(first)) {
      que.push(new ZeroEventInput());
      return;
    }
    que.push(new Operator(OperatorType.SEQ, first, second));

  };

  public void visitLoop(Operator oo) {
    INode second = que.pop();
    INode first = que.pop();
    if (isZeroEventInputLeaf(second) && isZeroEventInputLeaf(first)) {
      que.push(new ZeroEventInput());
      return;
    }
    if (isZeroEventInputLeaf(second)) {
      que.push(first);
      return;
    }
    que.push(new Operator(OperatorType.LOOP, first, second));

  };

}
