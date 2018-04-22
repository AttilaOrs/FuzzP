package UnifiedGpProblmes.RoomHeatControl.Behavour;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class RoomOnlyFitness implements IBehaviourBasedFitness<UnifiedGpIndi, FullHeatControllSimpleDescription> {

  private IBehaviourDescriponDataStore<FullHeatControllSimpleDescription> store;

  @Override
  public void setStore(IBehaviourDescriponDataStore<FullHeatControllSimpleDescription> store) {
    this.store = store;

  }

  @Override
  public double evaluate(Integer id) {
    FullHeatControllSimpleDescription i = store.get(id);
    if (i.sizeMulti == 0.0) {
      return 0.0;
    }
    double hint = 1.0 / (1.0 + i.roomInGoodState);
    double d = ((i.roomInGoodState + hint / 10.0) * i.sizeMulti) / i.totalTick;
    return d;
  }

}
