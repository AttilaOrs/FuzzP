package UnifiedGp.Tree;

import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractVisitor<TType, TSubnodeType> {

  final private VisitorCostumizer<TType, TSubnodeType> costumizer;

  public AbstractVisitor(VisitorCostumizer<TType, TSubnodeType> costumizer) {
    this.costumizer = costumizer;
  }

  abstract void visit(INode<TType> visit);

  protected boolean visitWithCostumizer(INode<TType> p) {
    if (!p.isLeaf()) {
      Optional<Function<INode<TType>, Boolean>> l = costumizer.getOperatorConsumer(p.getType());
      if (l.isPresent()) {
        return l.get().apply(p);
      }
    } else {
      Optional<Function<INode<TType>, Boolean>> l = costumizer.getLeafConsumer(p.getType());
      if (l.isPresent()) {
        l.get().apply(p);
      }
      ILeaf<TType, ? extends TSubnodeType> leaf = (ILeaf<TType, ? extends TSubnodeType>) p;
      l = costumizer.getSubLeafConsumer(leaf.getType(), leaf.getSubtype());
      if (l.isPresent()) {
        l.get().apply(p);
      }
    }
    return true;
  }

}
