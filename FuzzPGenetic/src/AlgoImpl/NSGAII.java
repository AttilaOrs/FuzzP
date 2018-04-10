package AlgoImpl;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.Map.Entry;

import AlgoImpl.Selectors.NSGAIISelector;
import structure.ICreaturePool;
import structure.IFitnesTransformer;
import structure.IGPGreature;

public class NSGAII<TCreature extends IGPGreature> extends MultiobjectiveGA<TCreature> {
  
  public static int NSGAII_CROSS = 75;
  public static int NSGAII_MUT = 25;
  public static int NSGAII_POP = 1000;
  public static int NSGAII_ITER = 100;

  private double[] generatorNormalWeigths;
  private double[] crossoverNormalWeigths;
  private double[] mutatationNormalWeigths;
  private NSGAIISelector nsgaSelector;

  public NSGAII(ICreaturePool<TCreature> pool, IFitnesTransformer transformer, double[] generatorWeigths,
      double[] crossoverWeights, double[] mutationWeights, ForkJoinPool threadPool) {
    super(pool, null, null, new double[]{1.0}, transformer);
    this.generatorNormalWeigths = normalise(generatorWeigths);
    this.crossoverNormalWeigths = normalise(crossoverWeights);
    this.mutatationNormalWeigths = normalise(mutationWeights);
    nsgaSelector = new NSGAIISelector(threadPool);
    logger = new IterationLogger(
        Arrays.asList(getTopicNameMax(0), getTopicNameAvg(0), getTopicNameMax(1), IterationLogger.POP_SIZE));
  }

  @Override
  public void theAlgo() {
    double mutNr = NSGAII_POP * NSGAII_MUT / 100.0;
    double crossNr = NSGAII_POP * CROSSOVER / 200.0;
    int curentIndex = 0;
    
    /* generating zeroth pop */
    long timeStart = System.nanoTime();
    for (int generatirIndex = 0; generatirIndex < generatorNormalWeigths.length; generatirIndex++) {
      int howMaany = (int) (NSGAII_POP * generatorNormalWeigths[generatirIndex]);
      pool.generate(generatirIndex, curentIndex, howMaany);
      curentIndex += howMaany;
    }
    res = transform(pool.calculateFitnessAndDeleteOldGeneration());
    long timeStop = System.nanoTime();

    logMemoryAndGc();
    logIterationResults(iter, res);
    super.logNonFitnessRelated(iter, pool.getSizeStats(), (timeStop - timeStart) / res.size());

    for (int iter = 1; iter < NSGAII_ITER; iter++) {
      timeStart = System.nanoTime();
      curentIndex = 0;
      nsgaSelector.initialize(res);
      System.out.println("iter::: " + iter + "current first front: " + nsgaSelector.getFirstFront());

      for (int mutIndex = 0; mutIndex < mutatationNormalWeigths.length; mutIndex++) {
        int howMany = (int) (mutNr * mutatationNormalWeigths[mutIndex]);
        if (howMany == 0) {
          break;
        }
        List<int[]> toMut = nsgaSelector.selectOne(howMany, 2);
        for (int i = 0; i < toMut.size(); i++) {
          toMut.get(i)[1] = NSGAII_POP * iter * 2 + curentIndex;
          curentIndex++;
        }
        pool.mutate(mutIndex, toMut);
      }

      for (int crossIndex = 0; crossIndex < crossoverNormalWeigths.length; crossIndex++) {
        int howMany = (int) (crossNr * crossoverNormalWeigths[crossIndex]);
        if (howMany == 0) {
          break;
        }
        List<int[]> toCross = nsgaSelector.selectPairs(howMany, 4);
        for (int i = 0; i < toCross.size(); i++) {
          toCross.get(i)[2] = NSGAII_POP * iter * 2 + curentIndex;
          curentIndex++;
          toCross.get(i)[3] = NSGAII_POP * iter * 2 + curentIndex;
          curentIndex++;
        }
        pool.crossover(crossIndex, toCross);
      }
      res = transform(pool.calculateFitnessAndMergeOldGenWithNew());
      nsgaSelector.initialize(res);
      List<int[]> toSurv = nsgaSelector.selectNondeterministicly(NSGAII_POP, 2);
      for (int j = 0; j < toSurv.size(); j++) {
        toSurv.get(j)[1] = NSGAII_POP * iter * 2 + curentIndex;
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
    nsgaSelector.initialize(res);
    return nsgaSelector.getFirstFront();
  }


}
