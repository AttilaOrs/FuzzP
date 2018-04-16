package AlgoImpl.pools;

import static java.lang.Math.abs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import AlgoImpl.IterationLogger;
import structure.ICreaturePool;
import structure.IGPGreature;

public class PoolWrapperForTheorteticalDistance<T extends IGPGreature> implements ICreaturePool<T> {
  private ICreaturePool<T> wraped;

  Map<Integer, float[]> currentThDistance;
  List<int[]> crossOver;
  List<int[]> mutate;
  List<Integer> generate;
  List<int[]> survive;

  private boolean firstIter;

  private Map<Integer, Integer> newIndexToId;

  private Map<Integer, Integer> newIdToIndex;

  private IterationLogger log;

  private boolean addAsFitness;

  private ForkJoinPool threadPool;

  public PoolWrapperForTheorteticalDistance(ICreaturePool<T> wraped, ForkJoinPool threadPool) {
    this(wraped, threadPool, true);
  }
  public PoolWrapperForTheorteticalDistance(ICreaturePool<T> wraped, ForkJoinPool threadPool, boolean addAsFitnes) {
    this.wraped = wraped;
    firstIter = true;
    crossOver = new ArrayList<>();
    mutate = new ArrayList<>();
    generate = new ArrayList<>();
    survive = new ArrayList<>();
    log = new IterationLogger();
    this.addAsFitness = addAsFitnes;
    this.threadPool = threadPool;
  }

  @Override
  public Map<Integer, Double[]> calculateFitnessAndDeleteOldGeneration() {
    Map<Integer, Double[]> originalRes = wraped.calculateFitnessAndDeleteOldGeneration();
    calcMatrix(false);
    double[] sts = logStats();
    if (!addAsFitness) {
      return originalRes;
    }
    Map<Integer, Double[]> toRet = updateFitness(originalRes, sts);
    return toRet;
  }

  @Override
  public Map<Integer, Double[]> calculateFitnessAndMergeOldGenWithNew() {
    Map<Integer, Double[]> originalRes = wraped.calculateFitnessAndMergeOldGenWithNew();
    calcMatrix(true);
    double[] sts = logStats();
    if (!addAsFitness) {
      return originalRes;
    }
    Map<Integer, Double[]> toRet = updateFitness(originalRes, sts);
    return toRet;
  }

  private Map<Integer, Double[]> updateFitness(Map<Integer, Double[]> originalRes, double[] sts) {
    Map<Integer, Double[]> toRet = new HashMap<>();
    for (Integer id : originalRes.keySet()) {
      float[] f = currentThDistance.get(id);
      Double newFitnes = 0.0;
      for (int i = 0; i < f.length; i++) {
        newFitnes += f[i];
      }
      newFitnes = (newFitnes / f.length);
      int originalSize = originalRes.get(id).length;

      Double[] newFitnesses = Arrays.copyOf(originalRes.get(id), originalSize + 1);
      if (sts[1] - sts[0] < 0.0000001) {
        newFitnesses[originalSize] = 1.0;
      } else {
        newFitnesses[originalSize] = abs(1.0 - (newFitnes - sts[0]) / (sts[1] - sts[0]));
      }
      toRet.put(id, newFitnesses);

    }
    return toRet;
  }


  public IterationLogger getLogger() {
    return log;
  }

  private void calcMatrix(boolean mergeOld) {
    if (firstIter) {
      initializeFileds();
      firstIter = false;
      generate.clear();
    } else {
      Map<Integer, float[]> relationWithOld = calcRelationWithOldGen(mergeOld);
      newIndexToId = new HashMap<>();
      newIdToIndex = new HashMap<>();
      
      Integer cntr = 0;
      for (Integer id : relationWithOld.keySet()) {
        newIdToIndex.put(id, cntr);
        newIndexToId.put(cntr++, id);
      }
      currentThDistance.clear();
      try {
        threadPool.submit(() -> {
          relationWithOld.keySet().parallelStream().forEach(id -> {

            float[] newRelation = new float[newIndexToId.size()];
            float[] oldRelation = relationWithOld.get(id);
            for (int index = 0; index < newRelation.length; index++) {
              Integer withWhoId = newIndexToId.get(index);
              if (withWhoId.equals(id)) {
                newRelation[index] = 1;
              } else {
                float[] otherOldRelation = relationWithOld.get(withWhoId);
                float common = 0.0f;
                float notCommon = 0.0f;
                for (int ii = 0; ii < oldRelation.length; ii++) {
                  float m = 0.0f;
                  m = (oldRelation[ii] * otherOldRelation[ii]);
                  common += m;
                  notCommon += oldRelation[ii] + otherOldRelation[ii] - 2 * m;
                }
                float all = 0.0f;
                if (common != 0.0f) {
                  all = common / (common + notCommon);
                }

                newRelation[index] = all;
            }
          }
            /*
             * for (int i = 0; i < newRelation.length; i++) { newRelation[i] =
             * newRelation[i] / newRelationSum; }
             */
            currentThDistance.put(id, newRelation);
          });
        }).get();
      } catch (InterruptedException | ExecutionException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }

  private static final NumberFormat formatter = new DecimalFormat("#0.000");
  static final Map<String, Predicate<Float>> categoryMap = new HashMap<>();
  static {
    // categoryMap.put("<0.001", f -> f < 0.001f);
    // categoryMap.put("[0.001,0.005)", f -> f >= 0.001f && f < 0.005);
    categoryMap.put("[0.005,0.01)", f -> f >= 0.05 && f < 0.01);
    categoryMap.put("[0.01,0.015)", f -> f >= 0.01 && f < 0.015);
    categoryMap.put("[0.015,0.02)", f -> f >= 0.015 && f < 0.020);
    categoryMap.put("[0.02,0.025]", f -> f >= 0.02 && f <= 0.025);
    categoryMap.put("0.25<", f -> f > 0.025);
  }

  double[] logStats() {
    double min = 100.0;
    double max = 0.0;
    double sum = 0.0;
    Map<String, Integer> categoryCount = new HashMap<>();
    categoryMap.keySet().forEach(st -> categoryCount.put(st, 0));

    for (Integer id : currentThDistance.keySet()) {
      float[] rand = currentThDistance.get(id);
      float curentAvg = 0.0f;
      for (float f : rand) {
        curentAvg += f;
        if (f != 1.0f) {
          for (String categoryName : categoryMap.keySet()) {
            if (categoryMap.get(categoryName).test(f)) {
              categoryCount.put(categoryName, categoryCount.get(categoryName) + 1);
            }
          }
        }
      }
      curentAvg /= rand.length;
      sum += curentAvg;
      if (min > curentAvg) {
        min = curentAvg;
      }
      if (max < curentAvg) {
        max = curentAvg;
      }

    }
    log.addLogToTopic("min avg dist", min);
    log.addLogToTopic("max avg dist", max);
    log.addLogToTopic("sum avg dist", sum / currentThDistance.size());
    for (Entry<String, Integer> catCount : categoryCount.entrySet()) {
      log.addLogToTopic("cat" + catCount.getKey(), catCount.getValue().doubleValue() / 2.0);
    }
    System.out.println("th dist:  " + min + " " + max + " " + sum / currentThDistance.size());
    return new double[]{min, max};

  }

  String createMatrixStr() {
    StringBuilder bld = new StringBuilder();
    for (int index = 0; index < newIndexToId.size(); index++) {
      String i = newIndexToId.get(index).toString();
      while (i.length() < 5) {
        i += "-";
      }
      i += "|";
      bld.append(i);
    }
    bld.append("\n");
    for (int index = 0; index < newIndexToId.size(); index++) {
      float[] f = currentThDistance.get(newIndexToId.get(index));

      for (int i = 0; i < f.length; i++) {
        if (f[i] == 0.0f) {
          bld.append("_____ ");
        } else {
          bld.append(formatter.format(f[i])).append(" ");
        }
      }
      bld.append("<<").append(newIndexToId.get(index)).append("\n");
    }
    return bld.toString();
  }

  private Map<Integer, float[]> calcRelationWithOldGen(boolean mergeOld) {
    Map<Integer, float[]> relationWithOld = new HashMap<>();
    if (mergeOld) {
      relationWithOld.putAll(currentThDistance);
    }
    for (int[] surv : survive) {
      float[] rel = currentThDistance.get(surv[0]);
      relationWithOld.put(surv[1], rel);
    }
    survive.clear();
    for (int[] mut : mutate) {
      float[] originalRel = currentThDistance.get(mut[0]);
      float[] newRel = new float[originalRel.length];
      for (int i = 0; i < originalRel.length; i++) {
        newRel[i] = 0.9f * originalRel[i];
      }
      relationWithOld.put(mut[1], newRel);
    }
    mutate.clear();
    for (int[] breed : crossOver) {
      float[] one = currentThDistance.get(breed[0]);
      float[] two = currentThDistance.get(breed[1]);
      float[] newOne = new float[one.length];
      float[] newTwo = new float[one.length];
      for (int i = 0; i < one.length; i++) {
        newOne[i] = (one[i] + two[i]) / 2.0f;
        newTwo[i] = (one[i] + two[i]) / 2.0f;
        relationWithOld.put(breed[2], newOne);
        relationWithOld.put(breed[3], newTwo);
      }
    }
    crossOver.clear();
    for (Integer generated : generate) {
      float[] nothin = new float[currentThDistance.size()];
      relationWithOld.put(generated, nothin);
    }
    generate.clear();
    return relationWithOld;
  }

  private void initializeFileds() {
    currentThDistance = new ConcurrentHashMap<>();
    newIdToIndex = new HashMap<>();
    newIndexToId = new HashMap<>();
    for (Integer i = 0; i < generate.size(); i++) {
      float[] rel = new float[generate.size()];
      rel[i] = 1.0f;
      currentThDistance.put(generate.get(i), rel);
      newIdToIndex.put(generate.get(i), i);
      newIndexToId.put(i, generate.get(i));
    }
  }

  @Override
  public void survive(List<int[]> idsToSurvive) {
    // idsToSurvive.forEach(arr -> System.out.println("s " + arr[0] + " " + " "
    // + arr[1]));
    survive.addAll(idsToSurvive);
    wraped.survive(idsToSurvive);
  }

  @Override
  public void mutate(int whichMutator, List<int[]> idsToMutate) {
    // idsToMutate.forEach(arr -> System.out.println("m " + arr[0] + " " + " " +
    // arr[1]));
    mutate.addAll(idsToMutate);
    wraped.mutate(whichMutator, idsToMutate);
  }

  @Override
  public void crossover(int whichbreeder, List<int[]> idsToCross) {
    // idsToCross.forEach(arr -> System.out.println("c " + arr[0] + " " + " " +
    // arr[1] + " " + arr[2] + " " + arr[3]));
    crossOver.addAll(idsToCross);
    wraped.crossover(whichbreeder, idsToCross);
  }

  @Override
  public void generate(int whichGenerator, int firstId, int howMany) {
    generate.addAll(IntStream.range(firstId, firstId + howMany).mapToObj(i -> i).collect(Collectors.toList()));
    wraped.generate(whichGenerator, firstId, howMany);
  }

  @Override
  public List<String> getGeneratorsNames() {
    return wraped.getGeneratorsNames();
  }

  @Override
  public List<String> getMutatorsNames() {
    return wraped.getMutatorsNames();
  }

  @Override
  public List<String> getBreedersNames() {
    return wraped.getBreedersNames();
  }

  @Override
  public List<String> getFitnesCalsNames() {
    return wraped.getFitnesCalsNames();
  }

  @Override
  public T get(int id) {
    return wraped.get(id);
  }

  @Override
  public void terminatePool() {
    wraped.terminatePool();
  }

  @Override
  public structure.ICreaturePool.GenerationSizeStats getSizeStats() {
    return wraped.getSizeStats();
  }


  private void printFirness(Map<Integer, Double[]> res) {
    StringBuilder bld = new StringBuilder();
    for (Integer key : res.keySet()) {
      bld.append(key).append(">>");
      for (int i = 0; i < res.get(key).length; i++) {
        bld.append(formatter.format(res.get(key)[i])).append(" ");
      }
      bld.append("\n");
    }
    System.out.println(bld.toString());
  }



}
