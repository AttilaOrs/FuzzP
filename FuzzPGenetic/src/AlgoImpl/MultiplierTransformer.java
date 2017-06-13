package AlgoImpl;

import java.util.Map;
import java.util.stream.Collectors;

import structure.IFitnesTransformer;

public class MultiplierTransformer implements IFitnesTransformer {

  @Override
  public Map<Integer, Double[]> transform(Map<Integer, Double[]> fitnesVals) {
    Map<Integer, Double[]> mm = fitnesVals.entrySet().parallelStream().collect(
        Collectors.toMap(e -> e.getKey(), e -> new Double[] { e.getValue()[0], e.getValue()[0] * e.getValue()[1] }));
    ;
    return mm;
  }

}
