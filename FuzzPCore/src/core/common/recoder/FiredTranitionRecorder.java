package core.common.recoder;

import java.util.HashSet;
import java.util.Set;

public class FiredTranitionRecorder<TokenType> implements IGeneralPetriBehavoiurRecorder<TokenType> {
  Set<Integer> fired = new HashSet<>();

  @Override
  public void transitionFiredStarted(int transition) {
    fired.add(transition);
  }

  public Set<Integer> getFiredTransition() {
    return fired;
  }
}
