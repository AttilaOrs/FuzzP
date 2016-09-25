package core.FuzzyPetriLogic.PetriNet.Recorders;

import java.util.HashSet;
import java.util.Set;

import core.FuzzyPetriLogic.PetriNet.IFuzzyPetriBehaviourRecorder;

public class FiredTranitionRecorder implements IFuzzyPetriBehaviourRecorder {
  Set<Integer> fired = new HashSet<>();

  @Override
  public void transitionFiredStarted(int transition) {
    fired.add(transition);
  }

  public Set<Integer> getFiredTransition() {
    return fired;
  }
}
