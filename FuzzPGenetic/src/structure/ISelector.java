package structure;

import java.util.List;
import java.util.Map;

public interface ISelector {

  public List<int[]> selectOne(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize);

  public List<int[]> selectPairs(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize);

}
