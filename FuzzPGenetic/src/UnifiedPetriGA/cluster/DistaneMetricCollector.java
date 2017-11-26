package UnifiedPetriGA.cluster;

import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.Map;

import org.ejml.data.DMatrixRMaj;

public class DistaneMetricCollector {
  private final HashMap<Integer, Map<Integer, Integer>> rez;

  public DistaneMetricCollector() {
    rez = new HashMap<>();
  }

  public void put(Integer fiTrId, Integer seTrId) {
    rez.putIfAbsent(fiTrId, new HashMap<>());
    Integer newCount = rez.get(fiTrId).getOrDefault(seTrId, 0) + 1;
    rez.get(fiTrId).put(seTrId, newCount);
    rez.putIfAbsent(seTrId, new HashMap<>());
    rez.get(seTrId).put(fiTrId, newCount);
  }

  @Override
  public String toString() {
    return rez.entrySet().stream()
        .map(e -> e.getKey() + " " + e.getValue())
        .collect(joining("\n"));
  }

  Integer currentTr;
  DMatrixRMaj currentGraphMatrix;

  public DMatrixRMaj createGraphMatrix(int limit, int nrOfTransitions) {

    currentGraphMatrix = new DMatrixRMaj(nrOfTransitions, nrOfTransitions);
    for (currentTr = 0; currentTr < nrOfTransitions; currentTr++) {
      if (rez.containsKey(currentTr)) {
        int sum = rez.get(currentTr).entrySet().stream().mapToInt(e -> e.getValue()).filter(i -> i > limit).sum();
        double probPerHit = 0.95 / sum;
        rez.get(currentTr).entrySet().stream()
            .filter(e -> e.getValue() > limit)
            .forEach(e -> {
              setValueForMatrix(e.getKey(), e.getValue() * probPerHit);
            });
        currentGraphMatrix.set(currentTr, currentTr, 0.05); // self loop
      } else {
        // unconnected transition
        currentGraphMatrix.set(currentTr, currentTr, 1.00); // self loop
      }

    }
    return currentGraphMatrix;
  }

  private void setValueForMatrix(Integer seTr, Double val) {
    currentGraphMatrix.set(seTr, currentTr, val);
  }

}
