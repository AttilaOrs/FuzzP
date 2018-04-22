package UnifiedGpProblmes.RoomHeatControl.Behavour;

import java.util.function.BiFunction;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class OverallFitness implements IBehaviourBasedFitness<UnifiedGpIndi, FullHeatControllSimpleDescription> {

  private BiFunction<Double, Double, Double> f;

  public OverallFitness() {
    this((d1, d2) -> d1 * d2);
  }
  public OverallFitness(BiFunction<Double, Double, Double> f) {
    this.f = f;
  }

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
    double hint = 1.0 / (1.0 + i.waterErrot);
    double tank = (((i.totalTick - i.tankOffLimit) + hint / 10.0)) / i.totalTick;
    hint = 1.0 / (1.0 + i.roomInGoodState);
    double room = ((i.roomInGoodState + hint / 10.0)) / i.totalTick;
    return this.f.apply(tank, room) * i.sizeMulti;

  }

}
