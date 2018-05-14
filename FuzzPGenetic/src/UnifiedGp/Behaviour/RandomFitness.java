package UnifiedGp.Behaviour;

import java.util.Random;

import structure.IGPGreature;
import structure.behaviour.IBehaviourBasedFitness;

public class RandomFitness <TCreature extends IGPGreature, TBehaviourDescription> implements IBehaviourBasedFitness<TCreature, TBehaviourDescription>{
  
  private Random rnd;

  public RandomFitness() {
    this.rnd = new Random();
  }

  @Override
  public double evaluate(Integer id) {
    return rnd.nextDouble();
  }

}
