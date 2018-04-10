package AlgoImpl.Selectors;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;


public class NSGAIISelector {
	
	private ForkJoinPool myPool;

	public NSGAIISelector(ForkJoinPool myPool) {
		this.myPool = myPool;
	}


  private static final double EPSILON = 0.0001;

  protected List<Set<Integer>> fronts;

  private Map<Integer, RankAndDistance> ranks;

  private List<Integer> population;

  private Random rnd = new Random();

  public void initialize(Map<Integer, Double[]> originalRes) {
    assert (originalRes.values().iterator().next().length >= 2);
    Map<Integer, Double[]>  res = new ConcurrentHashMap<>();
    res.putAll(originalRes);
    fronts = new ArrayList<>();
    ranks = new ConcurrentHashMap<>();
    population = new ArrayList<>(res.keySet());
    Map<Integer, IntermediateEntry> inter = createIntermediateRes(res);

    int frontIndex = 0;
    while (fronts.get(frontIndex).size() != 0) {
      HashSet<Integer> newFront = new HashSet<>();
      for (Integer previousFronMeber : fronts.get(frontIndex)) {
        for (Integer dominated : inter.get(previousFronMeber).domunatedByMe) {
          inter.get(dominated).domintaionCount -= 1;
          if(inter.get(dominated).domintaionCount == 0) {
            ranks.put(dominated, new RankAndDistance(frontIndex + 1));
            newFront.add(dominated);
          }

        }
      }
      fronts.add(newFront);
      frontIndex++;
    }
    fronts.remove(frontIndex);
    calculateCroudingDistance(res);
  }

  private void calculateCroudingDistance(Map<Integer, Double[]> res) {
    int lenght = res.values().stream().mapToInt(fit -> fit.length).findAny().getAsInt();
    try {
		myPool.submit(() ->{
			fronts.parallelStream().forEach(front ->{
				for(int fi = 0; fi< lenght; fi++) {
				  final int fitnes = fi;
				  List<Integer> sortedFirst = front.stream().sorted((i1, i2) -> 
				     Double.compare(res.get(i1)[fitnes], res.get(i2)[fitnes]))
					  .collect(toList());
				  DoubleSummaryStatistics stat = front.stream().mapToDouble(i -> res.get(i)[0]).summaryStatistics();
				  final double firsMin = stat.getMin();
				  final double firsMax = stat.getMax();
				  ranks.get(sortedFirst.get(0)).distance = 42424242424242.0;
				  ranks.get(sortedFirst.get(sortedFirst.size() - 1)).distance = 42424242424242.0;
				  for (int i = 1; i < sortedFirst.size() - 1; i++) {
					ranks.get(
						sortedFirst.get(i)).distance += (res.get(sortedFirst.get(i + 1))[fi] - res.get(sortedFirst.get(i - 1))[fi])
							/ (firsMax - firsMin);
				  }
				}
			});
		}).get();
	} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	}

  }

  
  protected boolean dominated(Double[] one, Double[] two) {
	  boolean hasOneBigger = false;
	  for(int i =0; i < one.length; i++) {
		  if(one[i]-two[i]>EPSILON) {
			  hasOneBigger = true;
		  } else if(Math.abs(one[i] - two[i]) > EPSILON) {
			  return false;
		  }
	  }
	  return hasOneBigger;
  }

  protected Map<Integer, IntermediateEntry> createIntermediateRes(Map<Integer, Double[]> res) {
    Map<Integer, IntermediateEntry> intermediateResult = new ConcurrentHashMap<>();
    Set<Integer> firstFront = new ConcurrentSkipListSet<>();
    try {
		myPool.submit(() ->{
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
				  }

				}
			  }
			  if (principalEntry.domintaionCount == 0) {
				firstFront.add(keyPrincipal);
				ranks.put(keyPrincipal, new RankAndDistance(0));
			  }
			  intermediateResult.put(keyPrincipal, principalEntry);
			});
		}).get();
	} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	}

    fronts.add(firstFront);
    return intermediateResult;
  }

  public List<int[]> selectNondeterministicly(int howMany, int arraysSize) {
    List<int[]> toRet = new ArrayList<>();
    int frontCntr = 0;
    while(toRet.size() < howMany) {
      Set<Integer> currentFront = fronts.get(frontCntr);
      if (currentFront.size() <= howMany - toRet.size()) {
        for (Integer indi : currentFront) {
          int[] i = new int[arraysSize];
          i[0] = indi;
          toRet.add(i);
        }
      } else {
        int hasToBeSelected = howMany - toRet.size();
        List<Integer> sorted = currentFront.stream()
            .sorted((c1, c2) -> -Double.compare(ranks.get(c1).distance, ranks.get(c2).distance))
            .collect(Collectors.toList());
        for (int i = 0; i < hasToBeSelected; i++) {
          int[] t = new int[arraysSize];
          t[0] = sorted.get(i);
          toRet.add(t);
        }

      }
      frontCntr++;
    }
    return toRet;

  }

  public List<int[]> selectOne(int howMany, int arraySize) {
    List<int[]> toRet = new ArrayList<>();
    for (int i = 0; i < howMany; i++) {
      int firstIndi = population.get(rnd.nextInt(population.size()));
      int secondIndi = population.get(rnd.nextInt(population.size()));
      int selected = compare(firstIndi, secondIndi);
      int arr[] = new int[arraySize];
      arr[0] = selected;
      toRet.add(arr);
    }
    return toRet;
  }

  private int compare(int firstIndi, int secondIndi) {
    RankAndDistance fiRank = ranks.get(firstIndi);
    RankAndDistance seRank = ranks.get(secondIndi);
    if (fiRank.rank < seRank.rank) {
      return firstIndi;
    }
    if (fiRank.rank > seRank.rank) {
      return secondIndi;
    }
    if (fiRank.distance > seRank.distance) {
      return firstIndi;
    }
    return secondIndi;
  }

  public List<int[]> selectPairs(int howMany, int arraySize) {
    List<int[]> toRet = new ArrayList<>();
    for (int i = 0; i < howMany; i++) {
      int firstIndi = population.get(rnd.nextInt(population.size()));
      int secondIndi = population.get(rnd.nextInt(population.size()));
      int selected = compare(firstIndi, secondIndi);
      int selected2 = selected;
      while (selected2 == selected) {
        firstIndi = population.get(rnd.nextInt(population.size()));
        secondIndi = population.get(rnd.nextInt(population.size()));
        selected2 = compare(firstIndi, secondIndi);
      }

      int arr[] = new int[arraySize];
      arr[0] = selected;
      arr[1] = selected2;
      toRet.add(arr);
    }
    return toRet;
  }

  protected static class IntermediateEntry {
    int domintaionCount = 0;
    HashSet<Integer> domunatedByMe = new HashSet<>();

    @Override
    public String toString() {
      return "Entry [domintaionCount=" + domintaionCount + ", domunatedByMe=" + domunatedByMe + "]";
    }
  }

  protected static class RankAndDistance {
    int rank;
    double distance;

    public RankAndDistance(int rank) {
      this.rank = rank;
      distance = 0.0;
    }

    @Override
    public String toString() {
      return "RankAndDistance [rank=" + rank + ", distance=" + distance + "]";
    }

  }

  public Set<Integer> getFirstFront() {
    return fronts.get(0);
  }

}
