package AlgoImpl.pools.behaviour;

import AlgoImpl.Selectors.PaleoSelectors.PaleoSelector;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import structure.behaviour.IBehaviourDescriponDataStore;

public class BehaviourStore<TBehaviourDescription> implements IBehaviourDescriponDataStore<TBehaviourDescription> {

  private ConcurrentHashMap<Integer, TBehaviourDescription> innerStrore;
  private PaleoSelector selector;

  public BehaviourStore(PaleoSelector s) {
    this.innerStrore = new ConcurrentHashMap<Integer, TBehaviourDescription>();
    this.selector = s;
  }

  @Override
  public void store(Integer id, TBehaviourDescription descriton) {
    innerStrore.put(id, descriton);
  }

  @Override
  public TBehaviourDescription get(Integer id) {
    return innerStrore.get(id);
  }

  @Override
  public void deleteAllButThese(Set<Integer> idsToSave) {
    innerStrore.keySet().stream().filter(id -> !idsToSave.contains(id)).forEach(id -> innerStrore.remove(id));;

  }

  @Override
  public Set<Integer> getIdsAlive() {
    return innerStrore.keySet();
  }
  
  
  @Override
  public Set<Integer> getBest(int i){
    return this.selector.selectNondeterministicly(i, 1).stream().map(arr -> arr[0]).collect(Collectors.toSet());
  }

}
