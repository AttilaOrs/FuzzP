package UnifiedGp.Tree;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstVisitor<TType, TSubnodeType> extends AbstractVisitor<TType, TSubnodeType> {

  private Queue<INode<TType>> queue;

  public BreadthFirstVisitor(VisitorCostumizer<TType, TSubnodeType> costumizer) {
    super(costumizer);
  }

  public void visit(INode<TType> root) {
    queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      INode<TType> p = queue.poll();
      if (visitWithCostumizer(p) && !p.isLeaf()) {
        IInnerNode<TType> inner = (IInnerNode<TType>) p;
        queue.add(inner.getFirstChild());
        queue.add(inner.getSecondChild());
      }

    }
    

  }



}
