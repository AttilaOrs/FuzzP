package UnifiedGpProblmes.ArtificalAnt.Behaviour;

import static java.lang.Math.sqrt;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class AntSizeFitness implements IBehaviourBasedFitness<UnifiedGpIndi, AntBinaryDescription> {

  private IBehaviourDescriponDataStore<AntBinaryDescription> store;

  public static int acceptableSize = 20;
  public static int maxSize = 600;
  @Override
  public void setStore(IBehaviourDescriponDataStore<AntBinaryDescription> store) {
    this.store = store;

  }

  @Override
  public double evaluate(Integer id) {
    AntBinaryDescription w = store.get(id);
    return calcualte(w.getSize());
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
