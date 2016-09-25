package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

public interface IFullRecorderEvent {

  String makeString();

  void buildFromString(String str);

  boolean isMyString(String str);

}
