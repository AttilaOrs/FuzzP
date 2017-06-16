package UnifiedGp.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class VisitorCostumizer<TType, TSubnodeType> {

  Map<TType, Function<INode<TType>, Boolean>> operatorConsumers;
  Map<TType, Function<INode<TType>, Boolean>> leafConsumers;
  Map<TType, Map<TSubnodeType, Function<INode<TType>, Boolean>>> subLeafConsumers;

  public VisitorCostumizer() {
    operatorConsumers = new HashMap<>();
    leafConsumers = new HashMap<>();
    subLeafConsumers = new HashMap<>();
  }

  public void registerOperatorConsumer(TType type, Function<INode<TType>, Boolean> cons) {
    operatorConsumers.put(type, cons);
  }

  public void registerLeafConsumer(TType type, Function<INode<TType>, Boolean> cons) {
    leafConsumers.put(type, cons);
  }

  public <TSub extends TSubnodeType> void registerLeafSubConsumer(TType nodeType, TSub subType,
      Function<INode<TType>, Boolean> cons) {
    if (!subLeafConsumers.containsKey(nodeType)) {
      subLeafConsumers.put(nodeType, new HashMap<>());
    }
    subLeafConsumers.get(nodeType).put(subType, cons);
  }
  
  public Optional<Function<INode<TType>, Boolean>> getOperatorConsumer(TType nodeType) {
    return Optional.ofNullable(operatorConsumers.get(nodeType));
  }

  public Optional<Function<INode<TType>, Boolean>> getLeafConsumer(TType nodeType) {
    return Optional.ofNullable(leafConsumers.get(nodeType));
  }

  public <TSub extends TSubnodeType> Optional<Function<INode<TType>, Boolean>> getSubLeafConsumer(
      TType nodeType, TSub sub) {
    if (subLeafConsumers.containsKey(nodeType)) {
      return Optional.ofNullable(subLeafConsumers.get(nodeType).get(sub));
    }
    return Optional.empty();
  }





}
