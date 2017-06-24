package UnifiedGp.Tree;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class VisitorCostumizer<TType> {

  Map<TType, Function<INode<TType>, Boolean>> operatorConsumers;
  Map<TType, Function<INode<TType>, Boolean>> leafConsumers;
  Map<Predicate<INode<TType>>, Consumer<INode<TType>>> maybeConsumers;

  public VisitorCostumizer() {
    operatorConsumers = new HashMap<>();
    leafConsumers = new HashMap<>();
    maybeConsumers = new HashMap<>();
  }

  public void registerPredicatedConsumer(Predicate<INode<TType>> pred, Consumer<INode<TType>> cons) {
    maybeConsumers.put(pred, cons);
  }


  public void registerOperatorConsumer(TType type, Function<INode<TType>, Boolean> cons) {
    operatorConsumers.put(type, cons);
  }

  public void registerLeafConsumer(TType type, Function<INode<TType>, Boolean> cons) {
    leafConsumers.put(type, cons);
  }

  
  public Optional<Function<INode<TType>, Boolean>> getOperatorConsumer(TType nodeType) {
    return Optional.ofNullable(operatorConsumers.get(nodeType));
  }

  public Optional<Function<INode<TType>, Boolean>> getLeafConsumer(TType nodeType) {
    return Optional.ofNullable(leafConsumers.get(nodeType));
  }

  public List<Consumer<INode<TType>>> getPredicatedConsumers(INode<TType> node) {
    return maybeConsumers.keySet().stream().filter(pr -> pr.test(node)).map(maybeConsumers::get).collect(toList());
  }






}
