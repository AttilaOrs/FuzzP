package AlgoImpl.Selectors.PaleoSelectors;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;


public class NSGAIISelector extends AbstactMultiobejctiveSelector {
	
  public NSGAIISelector(ForkJoinPool myPool) {
    super(myPool);
  }

  protected List<Set<Integer>> fronts;

  private Map<Integer, RankAndDistance> ranks;


  @Override
  public void initialize(Map<Integer, Double[]> originalRes) {
    assert (originalRes.values().iterator().next().length >= 2);
    Map<Integer, Double[]>  res = new ConcurrentHashMap<>();
    res.putAll(originalRes);
    fronts = new ArrayList<>();
    ranks = new ConcurrentHashMap<>();
    population = new ArrayList<>(res.keySet());
    IntermedateResult inter = createIntermediateRes(res);
    fronts.add(inter.firstFront);
    inter.firstFront.stream().forEach(i -> ranks.put(i, new RankAndDistance(0)));
    Map<Integer, IntermediateEntry> intermediateResultMap = inter.intermediateResultMap;

    int frontIndex = 0;
    while (fronts.get(frontIndex).size() != 0) {
      HashSet<Integer> newFront = new HashSet<>();
      for (Integer previousFronMeber : fronts.get(frontIndex)) {
        for (Integer dominated : intermediateResultMap.get(previousFronMeber).domunatedByMe) {
          intermediateResultMap.get(dominated).domintaionCount -= 1;
          if (intermediateResultMap.get(dominated).domintaionCount == 0) {
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

  


  @Override
  public List<int[]> selectNondeterministicly(int howMany, int arraysSize) {
	  if(fronts == null || fronts.isEmpty()) {
		  return new ArrayList<>();
	  }
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


  @Override
  protected int compareIndi(int firstIndi, int secondIndi) {
    RankAndDistance fiRank = ranks.getOrDefault(firstIndi, new RankAndDistance(10000));
    RankAndDistance seRank = ranks.getOrDefault(secondIndi, new RankAndDistance(1000));
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

  @Override
  public Set<Integer> getFirstFront() {
    return fronts.get(0);
  }

}
