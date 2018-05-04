package structure.behaviour;

import structure.ICreaturePool;
import structure.IGPGreature;

public interface IBehaviourBasedFitness<TCreature extends IGPGreature, TBehaviourDescription> {

  default void setStore(IBehaviourDescriponDataStore<TBehaviourDescription> store) {
    //do nothing
  }
  
  default void setPool(ICreaturePool<TCreature> cr) {
    //do nothing
  }

  double evaluate(Integer id);

}
