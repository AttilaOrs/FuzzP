package UnifiedGpProblmes.Robo.Behaviour;

import static java.lang.Math.sqrt;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class MazeBehaviourFitness implements IBehaviourBasedFitness<UnifiedGpIndi, RoboBihaviourBinaryDescription>{

  private static final int acceptableSize = 30;
  private static final int maxSize = 600;
  private IBehaviourDescriponDataStore<RoboBihaviourBinaryDescription> store;
  private boolean applySize;
  
  public MazeBehaviourFitness(boolean applySize) {
    this.applySize = applySize;
  }
  
  @Override
  public void setStore(IBehaviourDescriponDataStore<RoboBihaviourBinaryDescription> store) {
    this.store = store;

  }
  @Override
  public double evaluate(Integer id) {
    RoboBihaviourBinaryDescription desc = store.get(id);
    double toRet = desc.getSegmentsTouchedInOrder() - desc.getWallsTouched()*10.0;
    if (toRet > 0) {
      toRet +=2.0;
    } else if (desc.getSegmentsTouchedInOrder() > 0){
      toRet = 2.0 - 1.0 / desc.getSegmentsTouchedInOrder();
    } else if (desc.getSegmentsTouched() > 0) {
      toRet= 1.0 - 1.0 / desc.getSegmentsTouched() ;
    }
    if(applySize) {
      return  toRet*calcualte(desc.getSize());
    } else {
      if(desc.getSize() > maxSize) {
        return 0.0;
      } else {
        return toRet;
      }
      
    }
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
