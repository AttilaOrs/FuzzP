package AlgoImpl.Selectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import structure.ISelector;

public class SelectOnlyOneWrapper implements ISelector {

  private ISelector originalSelector;

  public SelectOnlyOneWrapper(ISelector s) {
    this.originalSelector = s;
  }

  @Override
  public List<int[]> selectOne(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize) {
    Set<Integer> selected = originalSelector.selectOne(res, basedOn, howMany, arraySize).stream().map(i -> i[0])
        .collect(Collectors.toSet());
    Map<Integer, Double[]> resCopy = new HashMap<>(res);
    while (selected.size() != howMany) {
      resCopy = resCopy.entrySet().stream().filter(e -> !selected.contains(e.getKey()))
          .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
      originalSelector.selectOne(resCopy, basedOn, howMany - selected.size(), arraySize).stream().map(i -> i[0])
          .forEach(i -> selected.add(i));
    }
    return selected.stream().map(i -> {
      int[] ret = new int[arraySize];
      ret[0] = i;
      return ret;
    }).collect(Collectors.toList());
  }

  @Override
  public List<int[]> selectPairs(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize) {
    return originalSelector.selectPairs(res, basedOn, howMany, arraySize);
  }

}
