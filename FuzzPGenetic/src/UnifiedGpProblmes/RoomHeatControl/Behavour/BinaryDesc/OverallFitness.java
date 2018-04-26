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
  private double errorOrState;

  public OverallFitness() {
    this((d1, d2, d3) -> d1 * d2 * d3, false, 1.0);
  }
  public OverallFitness(TriFunction<Double, Double, Double, Double> f, boolean strictSize, double errorStateRatio) {
    this.f = f;
    this.sctrictSize = strictSize;
    this.errorOrState = errorOrState;
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
    double tankError = 1.0 / (1.0 + i.waterErrot);
    double tankState = (((i.totalTick - i.tankOffLimit) + 1.0)) / i.totalTick;
    double roomError = 1.0 / (1.0 + i.roomTempError);
    double roomSate = (((i.totalTick - i.roomInWrongState) + 1.0)) / i.totalTick;
    double sizeMulti = (sctrictSize) ? calcualte(i.size) : i.sizeMulti;
    double tank = errorOrState * tankError + (1.0 - errorOrState) * tankState;
    double room = errorOrState * roomError + (1.0 - errorOrState) * roomSate;
    return this.f.apply(tank, room, sizeMulti);
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
