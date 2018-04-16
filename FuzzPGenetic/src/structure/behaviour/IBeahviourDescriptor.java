package structure.behaviour;

public interface IBeahviourDescriptor<TBehaviourDescription, TCreature> {

  TBehaviourDescription evaluate(TCreature cr);
    

}
