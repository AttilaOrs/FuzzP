package AlgoImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import structure.IFitnesTransformer;

public class MultiplierTransformer implements IFitnesTransformer {

  private ForkJoinPool pool;

  public MultiplierTransformer(ForkJoinPool pool) {
    this.pool = pool;
  }

  @Override
  public Map<Integer, Double[]> transform(Map<Integer, Double[]> fitnesVals) {

    ConcurrentHashMap<Integer, Double[]> toRet = new ConcurrentHashMap<>();
    try {
      pool.submit(() -> {
        fitnesVals.entrySet().parallelStream().forEach(entry -> {
          Double[] original = entry.getValue();
          Double[] transformed = new Double[original.length];
          transformed[0] = original[0];
          for (int i = 1; i < original.length; i++) {
            transformed[i] = original[i] * original[0];
          }
          toRet.put(entry.getKey(), transformed);
        });
      }).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return toRet;
  }

}
