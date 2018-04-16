package AlgoImpl.pools.behaviour;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import structure.behaviour.IBehaviourDescriponDataStore;

public class BehaviourStore<TBehaviourDescription> implements IBehaviourDescriponDataStore<TBehaviourDescription> {

  private ConcurrentHashMap<Integer, TBehaviourDescription> innerStrore;

  public BehaviourStore() {
    this.innerStrore = new ConcurrentHashMap<Integer, TBehaviourDescription>();
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

}
