package AlgoImpl.Selectors.PaleoSelectors;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PaleoSelector {

  public void initialize(Map<Integer, Double[]> originalRes);

  public List<int[]> selectNondeterministicly(int howMany, int arraysSize);

  public List<int[]> selectOne(int howMany, int arraySize);

  public List<int[]> selectPairs(int howMany, int arraySize);

  public Set<Integer> getFirstFront();

}
