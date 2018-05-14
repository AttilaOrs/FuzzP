package AlgoImpl.Selectors.PaleoSelectors;

import static java.lang.Double.compare;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SPEAIISelector extends AbstactMultiobejctiveSelector {

  protected HashMap<Integer, Double> assignedFitnes;
  private Set<Integer> firstFront;
  private List<Integer> bestIds;

  public SPEAIISelector(ForkJoinPool myPool) {
    super(myPool);
  }

  @Override
  public void initialize(Map<Integer, Double[]> originalRes) {
    int nrOfFitnessValues = originalRes.values().iterator().next().length;
    assert (nrOfFitnessValues >= 2);
    ConcurrentHashMap<Integer, Double[]> res = new ConcurrentHashMap<>(originalRes);
    IntermedateResult intermadiate = super.createIntermediateRes(res);
    ConcurrentHashMap<Integer, Double> assignedFitnesConc = new ConcurrentHashMap<>();
    
    try {
      myPool.submit(() -> {
        res.keySet().parallelStream().forEach(primaryIndiId -> {
          double rawFitnes = 0;
          for (Integer wo : intermadiate.intermediateResultMap.get(primaryIndiId).dominators) {
            rawFitnes += intermadiate.intermediateResultMap.get(wo).domunatedByMe.size();
          }
          Double[] original = res.get(primaryIndiId);
          DoubleSummaryStatistics stat = res.keySet().stream().filter(key -> key != primaryIndiId).mapToDouble(key -> {
            double toRet = 0.0;
            Double[] comparetTo = res.get(key);
            for (int i = 0; i < original.length; i++) {
              toRet += pow(original[i] - comparetTo[i], 2);
            }
            return sqrt(toRet);
          }).summaryStatistics();
          double assignedFitnes = rawFitnes + (1.0 / (2.0 + stat.getMin() + (stat.getAverage() / 100.0)));
          assignedFitnesConc.put(primaryIndiId, assignedFitnes);
        });
      }).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    this.firstFront = intermadiate.firstFront;
    assignedFitnes = new HashMap<>(assignedFitnesConc);
    population = assignedFitnes.keySet().stream()
        .sorted((i1, i2) -> compare(assignedFitnes.get(i1), assignedFitnes.get(i2)))
        .collect(toList());
    bestIds = IntStream.generate(() -> -1).limit(nrOfFitnessValues).mapToObj(i -> i)
        .collect(Collectors.toList());
    double[] maxFitness = new double[nrOfFitnessValues];
    for (Integer indiId : originalRes.keySet()) {
      Double[] curentFitnesVector = originalRes.get(indiId);
      for (int fiIndex = 0; fiIndex < nrOfFitnessValues; fiIndex++) {
        if (maxFitness[fiIndex] < curentFitnesVector[fiIndex]) {
          maxFitness[fiIndex] = curentFitnesVector[fiIndex];
          bestIds.set(fiIndex, indiId);
        }
      }
    }

  }
  
  
  @Override
  public List<int[]> selectNondeterministicly(int howMany, int arraysSize) {
	if (bestIds == null || bestIds.isEmpty()) {
		return new ArrayList<>();
	}
    List<int[]> toRet = new ArrayList<>();
    for (int i = 0; i < bestIds.size(); i++) {
      if (toRet.size() == howMany) {
        break;
      }
      int[] arr = new int[arraysSize];
      arr[0] = bestIds.get(i);
      toRet.add(arr);
    }
    for (int i = 0; i < population.size(); i++) {
      if (toRet.size() == howMany) {
        break;
      }
      Integer currentIndi = population.get(i);
      if (!bestIds.contains(currentIndi)) {
        int[] arr = new int[arraysSize];
        arr[0] = population.get(i);
        toRet.add(arr);
      }
    }
    if (toRet.size() < howMany) {
      throw new RuntimeException("Not enaugh meber  " + toRet.size() + " " + howMany);
    }

    return toRet;
  }

  @Override
  public Set<Integer> getFirstFront() {
    return firstFront;
  }


  @Override
  protected int compareIndi(int firstIndi, int secondIndi) {
    if (assignedFitnes.get(firstIndi) < assignedFitnes.get(secondIndi)) {
      return firstIndi;
    }
    return secondIndi;
  }

}
