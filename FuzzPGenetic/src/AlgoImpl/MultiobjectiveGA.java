package AlgoImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import structure.ICreaturePool;
import structure.IFitnesTransformer;
import structure.IGPGreature;
import structure.ISelector;

public class MultiobjectiveGA<TCreature extends IGPGreature> extends SimpleGA<TCreature> {

  public static final String MAX_FIT_PATT = "Max of Fitnes_";
  public static final String AVG_FIT_PATT = "Avg of Fitnes_";

  protected double[] fitnesNormalWeigths;
  private IFitnesTransformer transformer;

  public MultiobjectiveGA(ICreaturePool<TCreature> pool, ISelector selector, double[] fitnessweights,
      IFitnesTransformer transformer) {
    super(pool, selector);
    this.fitnesNormalWeigths = normalise(fitnessweights);

    this.transformer = transformer;
    this.logger = new IterationLogger(Arrays.asList(getTopicNameMax(0), getTopicNameAvg(0)));

  }

  public static double[] normalise(double[] nonNormal) {
    double[] normal = new double[nonNormal.length];
    double sum = Arrays.stream(nonNormal).sum();
    for (int i = 0; i < nonNormal.length; i++) {
      normal[i] = nonNormal[i] / sum;
    }
    return normal;
  }

  private String getTopicNameMax(int i) {
    return MAX_FIT_PATT + i;
  }

  private String getTopicNameAvg(int i) {
    return AVG_FIT_PATT + i;
  }

  int hack;

  @Override
  public void theAlgo() {
    int eliteNr = population * ELIT / 100;
    int survNr = population * SELECTION / 100;
    int mutNr = population * MUTATION / 100;
    int cross = population * CROSSOVER / 200;

    for (iter = 0; iter < iteration; iter++) {
      if (iter == 0) {
        pool.generate(0, 0, population);
      } else {
        int curentIndex = 0;
        for (hack = 0; hack < fitnesNormalWeigths.length; hack++) {
          List<Entry<Integer, Double[]>> elite = res.entrySet().stream()
              .sorted((entry1, entry2) -> entry1.getValue()[hack].compareTo(entry2.getValue()[hack]) * -1)
              .limit((long) (eliteNr * fitnesNormalWeigths[hack])).collect(Collectors.toList());
          List<int[]> elitsSurv = elite.stream().map(entry -> new int[] { entry.getKey(), nextIndex() })
              .collect(Collectors.toList());
          pool.survive(elitsSurv);
        }

        for (int i = 0; i < fitnesNormalWeigths.length; i++) {
          List<int[]> toSurv = selector.selectOne(res, i, (int) (survNr * fitnesNormalWeigths[i]), 2);
          for (int j = 0; j < toSurv.size(); j++) {
            toSurv.get(i)[1] = nextIndex();
          }
          pool.survive(toSurv);
        }

        for (int ff = 0; ff < fitnesNormalWeigths.length; ff++) {
          List<int[]> toMut = selector.selectOne(res, ff, (int) (mutNr * fitnesNormalWeigths[ff]), 2);
          for (int i = 0; i < toMut.size(); i++) {
            toMut.get(i)[1] = nextIndex();
          }
          pool.mutate(0, toMut);
        }

        for (int ff = 0; ff < fitnesNormalWeigths.length; ff++) {
          List<int[]> toCross = selector.selectPairs(res, ff, (int) (cross * fitnesNormalWeigths[ff]), 4);
          for (int i = 0; i < toCross.size(); i++) {
            toCross.get(i)[2] = nextIndex();
            toCross.get(i)[3] = nextIndex();
          }

          pool.crossover(0, toCross);
        }

      }
      res = transform(pool.calculateFitness());

      logMemoryAndGc();
      logIterationResults(iter, res);

      System.gc();

    }
    Optional<Entry<Integer, Double[]>> max = res.entrySet().stream()
        .max((entry1, entry2) -> entry1.getValue()[0].compareTo(entry2.getValue()[0]));
    this.maxId = max.get().getKey();
    pool.terminatePool();
  }

  protected Map<Integer, Double[]> transform(Map<Integer, Double[]> calculateFitness) {
    if (this.transformer == null) {
      return calculateFitness;
    }
    return transformer.transform(calculateFitness);
  }

  protected void logIterationResults(int iter, Map<Integer, Double[]> res) {
    double[] maxs = new double[res.size()];
    double[] sums = new double[res.size()];
    for (Double[] oneRes : res.values()) {
      for (int q = 0; q < oneRes.length; q++) {
        sums[q] += oneRes[q];
        if (oneRes[q] > maxs[q]) {
          maxs[q] = oneRes[q];
        }
      }
    }

    for (int q = 0; q < fitnesNormalWeigths.length; q++) {
      logger.addLogToTopic(getTopicNameMax(q), maxs[q]);
      logger.addLogToTopic(getTopicNameAvg(q), sums[q] / res.size());
    }
    logger.iterFinished(iter);
  }

}
