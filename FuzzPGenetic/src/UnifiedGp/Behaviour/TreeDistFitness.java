package UnifiedGp.Behaviour;

import java.util.Set;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.TreeDist.TreeDistUtil;
import structure.ICreaturePool;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class TreeDistFitness<TCreature extends UnifiedGpIndi, TBehaviourDescription> implements IBehaviourBasedFitness<TCreature, TBehaviourDescription>{
  
  
  private ICreaturePool<TCreature> pool;
  private IBehaviourDescriponDataStore<TBehaviourDescription> store;


  @Override
  public void setPool(ICreaturePool<TCreature> cr) {
    this.pool = cr;
  }

  @Override
   public void setStore(IBehaviourDescriponDataStore<TBehaviourDescription> store) {
    this.store = store;
  }

  @Override
  public double evaluate(Integer id) {
    Set<Integer> alive = store.getBest(10);
    TCreature myIndi = pool.get(id);
    int sum = alive.stream().filter(other -> other!= id).mapToInt(other -> {
      TCreature otherIndi = pool.get(other);
      return TreeDistUtil.treeEditDistance(myIndi.getRoot(), otherIndi.getRoot());
    }).sum();
    return ((double) sum )/(alive.size() *1000.0);
  }

}
