package UnifiedGpProblmes.ArtificalAnt.Behaviour;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class AntBehaviourFitness implements IBehaviourBasedFitness<UnifiedGpIndi, AntBinaryDescription> {

  private IBehaviourDescriponDataStore<AntBinaryDescription> store;

  @Override
  public void setStore(IBehaviourDescriponDataStore<AntBinaryDescription> store) {
    this.store = store;
  }

  @Override
  public double evaluate(Integer id) {
    AntBinaryDescription w = store.get(id);
    return w.getMulti() * w.getNrOfFoodEaten();
  }

}
