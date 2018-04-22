package UnifiedGpProblmes.RoomHeatControl.Behavour;

import static java.lang.Math.sqrt;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class OverallWithSizeFitness implements IBehaviourBasedFitness<UnifiedGpIndi, FullHeatControllSimpleDescription> {

  private IBehaviourDescriponDataStore<FullHeatControllSimpleDescription> store;

  public static int acceptableSize = 20;
  public static int maxSize = 600;
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
    double d2 = calcualte(i.size);
    return tank * room * d2;
  }

  private double calcualte(int sum) {
    if (sum <= acceptableSize) {
      return 1.0;
    }
    if (sum >= maxSize) {
      return 0.0;
    }
    return sqrt(sqrt((1.0 - ((sum - acceptableSize) / ((double) (maxSize - acceptableSize))))));
  }

}
