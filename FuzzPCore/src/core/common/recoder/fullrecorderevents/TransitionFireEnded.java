package core.common.recoder.fullrecorderevents;

public class TransitionFireEnded extends AbstractTransitionEvent {

  public TransitionFireEnded(int trNr) {
    super(trNr);
  }

  public TransitionFireEnded() {
    super();
  }

  @Override
  public String starterStr() {
    return "<<<";
  }

}
