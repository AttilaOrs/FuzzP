package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

public class TransitionFireStarted extends AbstractTransitionEvent {

  public TransitionFireStarted(int trNr) {
    super(trNr);
  }

  public TransitionFireStarted() {
    super();
  }

  @Override
  public String starterStr() {
    return ">>>";
  }

}
