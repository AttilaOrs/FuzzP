package UnifiedGp.Tree;

import java.util.Stack;

public class DepthFirstPostOrderVisitor<TType> extends AbstractVisitor<TType> {

  Stack<INode<TType>> myStack;

  public DepthFirstPostOrderVisitor(VisitorCostumizer<TType> costumizer) {
    super(costumizer);
  }

  @Override
  void visit(INode<TType> node) {
    myStack = new Stack<>();
    INode<TType> lastVisitedNode = null;
    while (node != null || (!myStack.isEmpty())) {
      if (node != null) {
        myStack.push(node);
        if (!node.isLeaf()) {
          IInnerNode<TType> same = (IInnerNode<TType>) node;
          node = same.getFirstChild();
        } else {
          node = null;
        }
      } else {
        INode<TType> peekNode = myStack.peek();
        if (!peekNode.isLeaf()) {
          IInnerNode<TType> samePeak = (IInnerNode<TType>) peekNode;
          if (!samePeak.getSecondChild().equals(lastVisitedNode)) {
            node = samePeak.getSecondChild();
            continue;
          }
        }
        visitWithCostumizer(peekNode);
        lastVisitedNode = myStack.pop();
      }

    }

  }

}
