package structure.behaviour;

interface IBehaviourBasedFitness<TCreature, TBehaviourDescription> {

  boolean reeavluateAfterSurvival();

  void setStore(IBehaviourDescriponDataStore<TBehaviourDescription> store);

  double evaluate(Integer id);


}
