package UnifiedGpProblmes.RoomHeatControl.Behavour.BinaryDesc;

import static java.lang.Math.sqrt;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGpProblmes.RoomHeatControl.Behavour.FullHeatControllSimpleDescription;
import structure.TriFunction;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class OverallFitness implements IBehaviourBasedFitness<UnifiedGpIndi, FullHeatBinaryDescripton> {

  public static int acceptableSize = 20;
  public static int maxSize = 600;
  private TriFunction<Double, Double, Double, Double> f;
  boolean sctrictSize;
  private IBehaviourDescriponDataStore<FullHeatBinaryDescripton> store;

  public OverallFitness() {
    this((d1, d2, d3) -> d1 * d2 * d3, false);
  }
  public OverallFitness(TriFunction<Double, Double, Double, Double> f, boolean strictSize) {
    this.f = f;
    this.sctrictSize = strictSize;
  }
  @Override
  public void setStore(IBehaviourDescriponDataStore<FullHeatBinaryDescripton> store) {
    this.store = store;

  }

  @Override
  public double evaluate(Integer id) {
    FullHeatBinaryDescripton fullBinary = store.get(id);
    FullHeatControllSimpleDescription i = fullBinary.getOriginalDesc();
    if (i.sizeMulti == 0.0) {
      return 0.0;
    }
    double tankHint = 1.0 / (1.0 + i.waterErrot);
    double tank = (((i.totalTick - i.tankOffLimit) + tankHint / 10.0)) / i.totalTick;
    double roomHint = 1.0 / (1.0 + i.roomTempError);
    double room = (((i.totalTick - i.roomInWrongState) * 0.70)) / i.totalTick + roomHint * 0.30;
    double sizeMulti = (sctrictSize) ? calcualte(i.size) : i.sizeMulti;
    return this.f.apply(tankHint, roomHint, sizeMulti);
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
