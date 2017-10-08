package core.common.recoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FireCounterRecorder<TokenType> extends FiredTranitionRecorder<TokenType> {

  Map<Integer, Integer> fireCounter;

  public FireCounterRecorder() {
    fireCounter = new HashMap<>();
  }

  @Override
  public void transitionFiredStarted(int transition) {
    fireCounter.computeIfPresent(transition, (t, i) -> i + 1);
    fireCounter.computeIfAbsent(transition, t -> 1);
  }

  @Override
  public long getTransitionFiredCount() {
    return fireCounter.values().stream().mapToLong(i -> i).sum();
  }

  @Override
  public Set<Integer> getFiredTransition() {
    return fireCounter.keySet();
  }

  public Integer getFireCountForTransition(Integer trId) {
    return fireCounter.get(trId);
  }
}
