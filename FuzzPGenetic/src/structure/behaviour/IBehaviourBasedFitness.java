package structure.behaviour;

public interface IBehaviourBasedFitness<TCreature, TBehaviourDescription> {

  void setStore(IBehaviourDescriponDataStore<TBehaviourDescription> store);

  double evaluate(Integer id);


}
