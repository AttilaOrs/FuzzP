package UnifiedGp.Tree.Visitors;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

import UnifiedGp.Tree.BreadthFirstVisitor;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;

public class RandomNodeSelector<TNodeType> {
  
  ArrayList<INode<TNodeType>> arr;

  public Optional<INode<TNodeType>> selectRandomNode(INode<TNodeType> root, Predicate<INode<TNodeType>> allowd,
      Random rnd) {
    arr = new ArrayList<>();
    VisitorCostumizer<TNodeType> cos = new VisitorCostumizer<>();
    cos.registerPredicatedConsumer(allowd, node -> arr.add(node));
    BreadthFirstVisitor<TNodeType> brd = new BreadthFirstVisitor<>(cos);
    brd.visit(root);

    if (arr.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(arr.get(rnd.nextInt(arr.size())));

 }

}
