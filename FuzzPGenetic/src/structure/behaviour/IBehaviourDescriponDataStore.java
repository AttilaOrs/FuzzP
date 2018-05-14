package structure.behaviour;

import java.util.Set;

public interface IBehaviourDescriponDataStore<TBehaviourDescription> {

  public void store(Integer id, TBehaviourDescription descriton);

  public TBehaviourDescription get(Integer id);

  public void deleteAllButThese(Set<Integer> ids);

  public Set<Integer> getIdsAlive();
  
  public Set<Integer> getBest(int i);


}
