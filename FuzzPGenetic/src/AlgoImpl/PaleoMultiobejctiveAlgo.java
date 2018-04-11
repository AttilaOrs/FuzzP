package AlgoImpl;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import AlgoImpl.Selectors.PaleoSelectors.NSGAIISelector;
import AlgoImpl.Selectors.PaleoSelectors.PaleoSelector;

import java.util.Map.Entry;

import structure.ICreaturePool;
import structure.IFitnesTransformer;
import structure.IGPGreature;

public class PaleoMultiobejctiveAlgo<TCreature extends IGPGreature> extends MultiobjectiveGA<TCreature> {
  
  public static int PALEO_CROSS = 75;
  public static int PALEO_MUT = 25;
  public static int PALEO_SURV_POP = 1000;
  public static int PALEO_NEW_POP = 1000;
  public static int PALEO_ITER = 100;

  private double[] generatorNormalWeigths;
  private double[] crossoverNormalWeigths;
  private double[] mutatationNormalWeigths;
  private PaleoSelector paleoSelector;

  public PaleoMultiobejctiveAlgo(ICreaturePool<TCreature> pool, IFitnesTransformer transformer, double[] generatorWeigths,
      double[] crossoverWeights, double[] mutationWeights, PaleoSelector paleoSelector) {
    super(pool, null, null, new double[]{1.0}, transformer);
    this.generatorNormalWeigths = normalise(generatorWeigths);
    this.crossoverNormalWeigths = normalise(crossoverWeights);
    this.mutatationNormalWeigths = normalise(mutationWeights);
    this.paleoSelector = paleoSelector;
    logger = new IterationLogger(
        Arrays.asList(getTopicNameMax(0), getTopicNameAvg(0), getTopicNameMax(1), IterationLogger.POP_SIZE));
  }

  @Override
  public void theAlgo() {
    double mutNr = PALEO_NEW_POP * PALEO_MUT / 100.0;
    double crossNr = PALEO_NEW_POP * CROSSOVER / 200.0;
    int curentIndex = 0;
    int indexMulti = (PALEO_NEW_POP > PALEO_SURV_POP) ? PALEO_NEW_POP : PALEO_SURV_POP;
    indexMulti *= 2;
    
    /* generating zeroth pop */
    long timeStart = System.nanoTime();
    for (int generatirIndex = 0; generatirIndex < generatorNormalWeigths.length; generatirIndex++) {
      int howMaany = (int) (PALEO_SURV_POP * generatorNormalWeigths[generatirIndex]);
      pool.generate(generatirIndex, curentIndex, howMaany);
      curentIndex += howMaany;
    }
    res = transform(pool.calculateFitnessAndDeleteOldGeneration());
    long timeStop = System.nanoTime();

    logMemoryAndGc();
    logIterationResults(iter, res);
    super.logNonFitnessRelated(iter, pool.getSizeStats(), (timeStop - timeStart) / res.size());

    for (int iter = 1; iter < PALEO_ITER; iter++) {
      timeStart = System.nanoTime();
      curentIndex = 0;
      paleoSelector.initialize(res);
      System.out.println("iter::: " + iter + "current first front: " + paleoSelector.getFirstFront());

      for (int mutIndex = 0; mutIndex < mutatationNormalWeigths.length; mutIndex++) {
        int howMany = (int) (mutNr * mutatationNormalWeigths[mutIndex]);
        if (howMany == 0) {
          break;
        }
        List<int[]> toMut = paleoSelector.selectOne(howMany, 2);
        for (int i = 0; i < toMut.size(); i++) {
          toMut.get(i)[1] = indexMulti * iter + curentIndex;
          curentIndex++;
        }
        pool.mutate(mutIndex, toMut);
      }

      for (int crossIndex = 0; crossIndex < crossoverNormalWeigths.length; crossIndex++) {
        int howMany = (int) (crossNr * crossoverNormalWeigths[crossIndex]);
        if (howMany == 0) {
          break;
        }
        List<int[]> toCross = paleoSelector.selectPairs(howMany, 4);
        for (int i = 0; i < toCross.size(); i++) {
          toCross.get(i)[2] = indexMulti * iter + curentIndex;
          curentIndex++;
          toCross.get(i)[3] = indexMulti * iter + curentIndex;
          curentIndex++;
        }
        pool.crossover(crossIndex, toCross);
      }
      res = transform(pool.calculateFitnessAndMergeOldGenWithNew());
      paleoSelector.initialize(res);
      List<int[]> toSurv = paleoSelector.selectNondeterministicly(PALEO_SURV_POP, 2);
      for (int j = 0; j < toSurv.size(); j++) {
        toSurv.get(j)[1] = indexMulti * iter + curentIndex;
        curentIndex++;
      }
      pool.survive(toSurv);
      res = transform(pool.calculateFitnessAndDeleteOldGeneration());
      timeStop = System.nanoTime();
      logMemoryAndGc();
      logIterationResults(iter, res);
      super.logNonFitnessRelated(iter, pool.getSizeStats(), (timeStop - timeStart) / res.size());
    }

    pool.terminatePool();
  }
  public Integer getBestBasedOn(int fitnessNr) {

    Optional<Entry<Integer, Double[]>> max = res.entrySet().stream()
        .max((entry1, entry2) -> entry1.getValue()[fitnessNr].compareTo(entry2.getValue()[fitnessNr]));
    return maxId = max.get().getKey();
  }

  public Set<Integer> getFirstFront() {
    paleoSelector.initialize(res);
    return paleoSelector.getFirstFront();
  }


}
