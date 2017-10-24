package core.common.recoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FireCounterRecorder<TokenType> extends FiredTranitionRecorder<TokenType> {

  Map<Integer, Integer> fireCounter;
  int tickCntr;

  public FireCounterRecorder() {
    fireCounter = new HashMap<>();

  }

  public int nrOfAllicks() {
    return tickCntr;
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
    return fireCounter.getOrDefault(trId, 0);
  }

  @Override
  public void tickFinished(List<Integer> delayStateOfTransition,
      List<TokenType> placeState) {
    tickCntr++;
  };
}
