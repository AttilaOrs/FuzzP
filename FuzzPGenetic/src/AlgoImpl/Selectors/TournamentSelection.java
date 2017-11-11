package AlgoImpl.Selectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import structure.ISelector;

public class TournamentSelection implements ISelector {
  private static final int DEFAULT_TOURNAMENT_SIZE = 7;

  private Random rnd;
  private final int tournamentSize;

  public TournamentSelection() {
    this(DEFAULT_TOURNAMENT_SIZE);
  }

  public TournamentSelection(int tournamentSize) {
    rnd = new Random();
    this.tournamentSize = tournamentSize;
  }

  @Override
  public List<int[]> selectOne(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize) {
    List<int[]> toRet = new ArrayList<>();
    ArrayList<Integer> keys = new ArrayList<>(res.keySet());

    while (toRet.size() < howMany) {
      Integer bestId = selectOne(res, basedOn, keys);
      int[] i = new int[arraySize];
      i[0] = bestId;
      toRet.add(i);
    }

    return toRet;
  }

  private Integer selectOne(Map<Integer, Double[]> res, int basedOn, ArrayList<Integer> keys) {
    Integer bestId = randomKey(keys);
    for (int i = 0; i < tournamentSize - 1; i++) {
      Integer rndId = randomKey(keys);
      if (res.get(rndId)[basedOn] > res.get(bestId)[basedOn]) {
        bestId = rndId;
      }
    }
    return bestId;
  }

  private Integer randomKey(List<Integer> l) {
    return l.get(rnd.nextInt(l.size()));
  }


  @Override
  public List<int[]> selectPairs(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize) {
    ArrayList<int[]> toRet = new ArrayList<>();
    ArrayList<Integer> keys = new ArrayList<>(res.keySet());
    while (toRet.size() < howMany) {
      Integer iOne = selectOne(res, basedOn, keys);
      Integer iTwo = selectOne(res, basedOn, keys);
      while (iOne.equals(iTwo)) {
        iTwo = selectOne(res, basedOn, keys);
      }
      int[] toPut = new int[arraySize];
      toPut[0] = iOne;
      toPut[1] = iTwo;
      toRet.add(toPut);
    }

    return toRet;
  }

}
