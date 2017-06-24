package UnifiedGp.Tree;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractVisitor<TType> {

  final private VisitorCostumizer<TType> costumizer;

  public AbstractVisitor(VisitorCostumizer<TType> costumizer) {
    this.costumizer = costumizer;
  }

  abstract void visit(INode<TType> visit);

  protected boolean visitWithCostumizer(INode<TType> p) {
    for (Consumer<INode<TType>> c : costumizer.getPredicatedConsumers(p)) {
      c.accept(p);
    }
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
    }
    return true;
  }

}
