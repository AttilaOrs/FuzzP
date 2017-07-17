package AlgoImpl.pools;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import structure.GPIndividSize;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IGPGreature;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

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

  public PoolWrapperForTheorteticalDistance(ICreaturePool<T> wraped) {
    this.wraped = wraped;
    firstIter = true;
    crossOver = new ArrayList<>();
    mutate = new ArrayList<>();
    generate = new ArrayList<>();
    survive = new ArrayList<>();

  }

  @Override
  public Map<Integer, Double[]> calculateFitness() {
    Map<Integer, Double[]> i = wraped.calculateFitness();
    calcMatrix();
    System.out.println(createMatrixStr());
    return i;
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
        float newRelationSum = 0.0f;
        for (int index = 0; index < newRelation.length; index++) {
          Integer withWhoId = newIndexToId.get(index);
          if (withWhoId == id) {
            newRelation[index] = 0;
            newRelationSum += 0;
          } else {
            float[] otherOldRelation = relationWithOld.get(withWhoId);
            float s = 0.0f;
            for(int ii = 0; ii < oldRelation.length; ii++){
              float m = oldRelation[ii] * otherOldRelation[ii];
              s += m;
            }

            newRelation[index] = s;
            newRelationSum += s;
          }

        }
        for (int i = 0; i < newRelation.length; i++) {
          newRelation[i] = newRelation[i] / newRelationSum;
        }
        currentThDistance.put(id, newRelation);
      }
    }
  }

  private static final NumberFormat formatter = new DecimalFormat("#0.00");

  void sysoStats() {
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
    System.out.println(min + " " + max + " " + sum / currentThDistance.size());

  }

  String createMatrixStr() {
    StringBuilder bld = new StringBuilder();
    for (int index = 0; index < newIndexToId.size(); index++) {
      float[] f = currentThDistance.get(newIndexToId.get(index));

      for (int i = 0; i < f.length; i++) {
        bld.append(formatter.format(f[i])).append(" ");
      }
      bld.append("\n");
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
        newRel[i] = 0.75f * originalRel[i];
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
    survive.addAll(idsToSurvive);
    wraped.survive(idsToSurvive);
  }

  @Override
  public void mutate(int whichMutator, List<int[]> idsToMutate) {
    mutate.addAll(idsToMutate);
    wraped.mutate(whichMutator, idsToMutate);
  }

  @Override
  public void crossover(int whichbreeder, List<int[]> idsToCross) {
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
  public GPIndividSize getAvarageSizeOfCurrentPool() {
    return wraped.getAvarageSizeOfCurrentPool();
  }

}
