package UnifiedGp.Behaviour;


import java.util.BitSet;

import structure.IGPGreature;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class BehaviourDiversityHammingFitness<TCreature extends IGPGreature, TBehaviourDescription extends BinaryDescrition>
    implements
      IBehaviourBasedFitness<TCreature, TBehaviourDescription> {

  private IBehaviourDescriponDataStore<TBehaviourDescription> store;

  @Override
  public void setStore(IBehaviourDescriponDataStore<TBehaviourDescription> store) {
    this.store = store;
  }

  @Override
  public double evaluate(Integer id) {
    TBehaviourDescription original = store.get(id);
    int allDistance = 0;
    for (Integer otherId : store.getIdsAlive()) {
      if (otherId != id) {
        TBehaviourDescription other = store.get(otherId);
        BitSet temp = (BitSet) other.getBits().clone();
        temp.xor(original.getBits());
        allDistance += temp.cardinality();
      }
    }
    int theoreticallyAll = original.getLength() * store.getIdsAlive().size();

    return ((double) allDistance) / ((double) theoreticallyAll);
  }

}
