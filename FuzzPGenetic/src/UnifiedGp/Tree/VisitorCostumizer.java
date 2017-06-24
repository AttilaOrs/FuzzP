package UnifiedGp.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class VisitorCostumizer<TType> {

  Map<TType, Function<INode<TType>, Boolean>> operatorConsumers;
  Map<TType, Function<INode<TType>, Boolean>> leafConsumers;

  public VisitorCostumizer() {
    operatorConsumers = new HashMap<>();
    leafConsumers = new HashMap<>();
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






}
