package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

public abstract class AbstractTransitionEvent implements IFullRecorderEvent {

  public int trNr;

  public AbstractTransitionEvent(int trNr) {
    this.trNr = trNr;
  }

  public AbstractTransitionEvent() {
    this.trNr = -1;
  }

  public String makeString() {
    return starterStr() + trNr;
  }

  public void buildFromString(String str) {
    trNr = Integer.parseInt(str.replace(starterStr(), ""));
  }

  public boolean isMyString(String str) {
    return str.startsWith(starterStr());
  }

  public abstract String starterStr();

}
