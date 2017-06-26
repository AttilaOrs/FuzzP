package UnifiedGp.Tree.Visitors;

import java.util.ArrayDeque;
import java.util.Deque;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.ILeaf;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;

public class CopyReplace<TType> {

  private Deque<INode<TType>> myStakc;

  public INode<TType> copyReplace(INode<TType> root, INode<TType> original, INode<TType> aim) {
    myStakc = new ArrayDeque<>();
    VisitorCostumizer<TType> costumizer = new VisitorCostumizer<>();
    costumizer.registerPredicatedConsumer(node -> node.isLeaf() && node.equals(original),
        node -> myStakc.push(aim));

    costumizer.registerPredicatedConsumer(node -> node.isLeaf() && !node.equals(original),
        node -> myStakc.push(((ILeaf<TType>) node).myClone()));

    costumizer.registerPredicatedConsumer(node -> (!node.isLeaf()) && !node.equals(original),
        node -> {
          INode<TType> se = myStakc.pop();
          INode<TType> fi = myStakc.pop();
          myStakc.push(((IInnerNode<TType>) node).myClone(fi, se));
        });

    costumizer.registerPredicatedConsumer(node -> (!node.isLeaf()) && node.equals(original),
        node -> {
          myStakc.pop();
          myStakc.pop();
          myStakc.push(aim);
        });

    DepthFirstPostOrderVisitor<TType> nn = new DepthFirstPostOrderVisitor<>(costumizer);
    nn.visit(root);
    return myStakc.pop();
  }


}
