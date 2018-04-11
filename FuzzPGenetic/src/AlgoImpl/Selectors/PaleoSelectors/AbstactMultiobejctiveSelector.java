package AlgoImpl.Selectors.PaleoSelectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

abstract class AbstactMultiobejctiveSelector implements PaleoSelector {

  private static final double EPSILON = 0.0001;
  protected ForkJoinPool myPool;
  protected List<Integer> population;
  private Random rnd;


  public AbstactMultiobejctiveSelector(ForkJoinPool myPool) {
    this.myPool = myPool;
    this.rnd = new Random();
  }

  protected boolean dominated(Double[] one, Double[] two) {
    boolean hasOneBigger = false;
    for (int i = 0; i < one.length; i++) {
      if (one[i] - two[i] > EPSILON) {
        hasOneBigger = true;
      } else if (Math.abs(one[i] - two[i]) > EPSILON) {
        return false;
      }
    }
    return hasOneBigger;
  }

  protected IntermedateResult createIntermediateRes(Map<Integer, Double[]> res) {
    Map<Integer, IntermediateEntry> intermediateResultMap = new ConcurrentHashMap<>();
    Set<Integer> firstFront = new ConcurrentSkipListSet<>();
    try {
      myPool.submit(() -> {
        res.keySet().parallelStream().forEach(keyPrincipal -> {
          IntermediateEntry principalEntry = new IntermediateEntry();
          for (Integer keySecondary : res.keySet()) {
            if (keyPrincipal != keySecondary) {
              Double[] principalFitnes = res.get(keyPrincipal);
              Double[] secondaryFitnes = res.get(keySecondary);
              if (dominated(principalFitnes, secondaryFitnes)) {
                principalEntry.domunatedByMe.add(keySecondary);
              } else if (dominated(secondaryFitnes, principalFitnes)) {
                principalEntry.domintaionCount++;
                principalEntry.dominators.add(keySecondary);
              }

            }
          }
          if (principalEntry.domintaionCount == 0) {
            firstFront.add(keyPrincipal);
            // ranks.put(keyPrincipal, new RankAndDistance(0));
          }
          intermediateResultMap.put(keyPrincipal, principalEntry);
        });
      }).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return new IntermedateResult(intermediateResultMap, firstFront);
  }

  protected abstract int compareIndi(int firstIndi, int secondIndi);


  @Override
  public List<int[]> selectOne(int howMany, int arraySize) {
    List<int[]> toRet = new ArrayList<>();
    for (int i = 0; i < howMany; i++) {
      int firstIndi = population.get(rnd.nextInt(population.size()));
      int secondIndi = population.get(rnd.nextInt(population.size()));
      int selected = compareIndi(firstIndi, secondIndi);
      int arr[] = new int[arraySize];
      arr[0] = selected;
      toRet.add(arr);
    }
    return toRet;
  }


  @Override
  public List<int[]> selectPairs(int howMany, int arraySize) {
    List<int[]> toRet = new ArrayList<>();
    for (int i = 0; i < howMany; i++) {
      int firstIndi = population.get(rnd.nextInt(population.size()));
      int secondIndi = population.get(rnd.nextInt(population.size()));
      int selected = compareIndi(firstIndi, secondIndi);
      int selected2 = selected;
      while (selected2 == selected) {
        firstIndi = population.get(rnd.nextInt(population.size()));
        secondIndi = population.get(rnd.nextInt(population.size()));
        selected2 = compareIndi(firstIndi, secondIndi);
      }

      int arr[] = new int[arraySize];
      arr[0] = selected;
      arr[1] = selected2;
      toRet.add(arr);
    }
    return toRet;
  }


  protected static class IntermedateResult {
    final Map<Integer, IntermediateEntry> intermediateResultMap;
    final Set<Integer> firstFront;

    public IntermedateResult(Map<Integer, IntermediateEntry> intermediateResult, Set<Integer> firstFront) {
      this.intermediateResultMap = intermediateResult;
      this.firstFront = firstFront;

    }
  }

  protected static class IntermediateEntry {
    int domintaionCount = 0;
    HashSet<Integer> domunatedByMe = new HashSet<>();
    HashSet<Integer> dominators = new HashSet<>();

    @Override
    public String toString() {
      return "Entry [domintaionCount=" + domintaionCount + ", domunatedByMe=" + domunatedByMe + "]";
    }
  }
}
