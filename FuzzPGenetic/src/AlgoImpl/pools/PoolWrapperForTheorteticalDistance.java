package AlgoImpl.pools;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import AlgoImpl.IterationLogger;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IGPGreature;
import structure.IOperatorFactory;
import structure.ISelector;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class PoolWrapperForTheorteticalDistance<T extends IGPGreature> implements ICreaturePool<T>, ISelector {
  private ICreaturePool<T> wraped;

  Map<Integer, float[]> currentThDistance;
  List<int[]> crossOver;
  List<int[]> mutate;
  List<Integer> generate;
  List<int[]> survive;

  private boolean firstIter;

  private Map<Integer, Integer> newIndexToId;

  private Map<Integer, Integer> newIdToIndex;

  private ISelector originalSelector;

  private IterationLogger log;

  public PoolWrapperForTheorteticalDistance(ICreaturePool<T> wraped, ISelector originalSelector) {
    this.wraped = wraped;
    firstIter = true;
    crossOver = new ArrayList<>();
    mutate = new ArrayList<>();
    generate = new ArrayList<>();
    survive = new ArrayList<>();
    this.originalSelector = originalSelector;
    log = new IterationLogger();

  }

  @Override
  public Map<Integer, Double[]> calculateFitness() {
    Map<Integer, Double[]> i = wraped.calculateFitness();
    calcMatrix();
    // System.out.println(createMatrixStr());
    // sysoStats();
    logStats();
    return i;
  }

  public IterationLogger getLogger() {
    return log;
  }

  private void calcMatrix() {
    if (firstIter) {
      initializeFileds();
      firstIter = false;
    } else {
      Map<Integer, float[]> relationWithOld = calcRelationWithOldGen();
      newIndexToId = new HashMap<>();
      newIdToIndex = new HashMap<>();
      
      Integer cntr = 0;
      for (Integer id : relationWithOld.keySet()) {
        newIdToIndex.put(id, cntr);
        newIndexToId.put(cntr++, id);
      }
      currentThDistance.clear();

      for (Integer id : relationWithOld.keySet()) {
        float[] newRelation = new float[newIndexToId.size()];
        float[] oldRelation = relationWithOld.get(id);
        for (int index = 0; index < newRelation.length; index++) {
          Integer withWhoId = newIndexToId.get(index);
          if (withWhoId == id) {
            newRelation[index] = 1;
          } else {
            float[] otherOldRelation = relationWithOld.get(withWhoId);
            float common = 0.0f;
            float notCommon = 0.0f;
            for(int ii = 0; ii < oldRelation.length; ii++){
              float m = 0.0f;
              m = (oldRelation[ii] * otherOldRelation[ii]);
              common += m;
              notCommon += oldRelation[ii] + otherOldRelation[ii] - 2 * m;
            }
            float all = common / (common + notCommon);

            newRelation[index] = all;
          }
        }
        /*
         * for (int i = 0; i < newRelation.length; i++) { newRelation[i] =
         * newRelation[i] / newRelationSum; }
         */
        currentThDistance.put(id, newRelation);
      }
    }
  }

  private static final NumberFormat formatter = new DecimalFormat("#0.000");

  void logStats() {
    double min = 100.0;
    double max = 0.0;
    double sum = 0.0;

    for (Integer id : currentThDistance.keySet()) {
      float[] rand = currentThDistance.get(id);
      DoubleSummaryStatistics stats = IntStream.range(0, rand.length).mapToDouble(i -> rand[i]).summaryStatistics();
      double d = stats.getAverage();
      sum += d;
      if (min > d) {
        min = d;
      }
      if (max < d) {
        max = d;
      }

    }
    log.addLogToTopic("min avg dist", min);
    log.addLogToTopic("max avg dist", max);
    log.addLogToTopic("sum avg dist", sum / currentThDistance.size());

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

  private Map<Integer, float[]> calcRelationWithOldGen() {
    Map<Integer, float[]> relationWithOld = new HashMap<>();
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
    return relationWithOld;
  }

  private void initializeFileds() {
    currentThDistance = new HashMap<>();
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
    generate.addAll(IntStream.range(firstId, howMany).mapToObj(i -> i).collect(Collectors.toList()));
    wraped.generate(whichGenerator, firstId, howMany);
  }

  @Override
  public ArrayList<IOperatorFactory<ICreatureGenerator<T>>> getGenerators() {
    return wraped.getGenerators();
  }

  @Override
  public ArrayList<IOperatorFactory<ICreatureMutator<T>>> getMutators() {
    return wraped.getMutators();
  }

  @Override
  public ArrayList<IOperatorFactory<ICreatureBreeder<T>>> getBreeders() {
    return wraped.getBreeders();
  }

  @Override
  public ArrayList<IOperatorFactory<ICreatureFitnes<T>>> getFitnesCals() {
    return wraped.getFitnesCals();
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

  @Override
  public List<int[]> selectOne(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize) {
    return originalSelector.selectOne(res, basedOn, howMany, arraySize);
  }

  @Override
  public List<int[]> selectPairs(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize) {
    // System.out.println("original");
    // printFirness(res);
    List<int[]> selectedForMating = originalSelector.selectOne(res, basedOn, howMany, arraySize);
    for (int selectedForMatingIndex = 0; selectedForMatingIndex < selectedForMating.size(); selectedForMatingIndex++) {
      int slectedId = selectedForMating.get(selectedForMatingIndex)[0];
      // System.out.println(">>>" + slectedId);
      float[] dist = currentThDistance.get(slectedId);
      Map<Integer, Double[]> newFitnes = new HashMap<>();
      for (Integer i : res.keySet()) {
        double currentNewFitnes = res.get(i)[basedOn] * func(dist[newIdToIndex.get(i)]);
        newFitnes.put(i, new Double[] { currentNewFitnes });
      }
      // printFirness(newFitnes);

      List<int[]> l = originalSelector.selectOne(newFitnes, 0, 1, 1);
      // System.out.println("<<<" + l.get(0)[0]);

      selectedForMating.get(selectedForMatingIndex)[1] = l.get(0)[0];
    }
    return selectedForMating;

  }

  private void printFirness(Map<Integer, Double[]> res) {
    StringBuilder bld = new StringBuilder();
    for (Integer key : res.keySet()) {
      bld.append(key).append(">>").append(res.get(key)[0]).append(" ");
    }
    System.out.println(bld.toString());
  }

  private Double func(float f) {
    return 1.0 / (pow(100.0, abs(0.1 - f)));
  }

}
