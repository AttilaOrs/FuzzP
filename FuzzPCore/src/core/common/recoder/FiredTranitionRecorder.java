package core.common.recoder;

import java.util.HashSet;
import java.util.Set;

public class FiredTranitionRecorder<TokenType> implements IGeneralPetriBehavoiurRecorder<TokenType> {
  Set<Integer> fired = new HashSet<>();
  long fireCntr = 0;

  @Override
  public void transitionFiredStarted(int transition) {
    fired.add(transition);
    fireCntr++;
  }

  public long getTransitionFiredCount() {
    return fireCntr;
  }

  public Set<Integer> getFiredTransition() {
    return fired;
  }

  @Override
  public String toString() {
    return "FiredTransitionRecorder:" + fired.toString();
  }
}
