package structure;

import java.util.Map;

public interface IFitnesTransformer {

  public Map<Integer, Double[]> transform(Map<Integer, Double[]> fitnesVals);

}
