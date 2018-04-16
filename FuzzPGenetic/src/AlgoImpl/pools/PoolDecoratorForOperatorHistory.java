package AlgoImpl.pools;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AlgoImpl.IterationLogger;
import structure.ICreaturePool;
import structure.IFitnesTransformer;
import structure.IGPGreature;

public class PoolDecoratorForOperatorHistory<TCreatue extends IGPGreature> implements ICreaturePool<TCreatue> {

  public static final String logPrefix = "OpLog_";
  public static final String fitnesPrefix = "opFit_";
  public static final String survPrefix = "sruv_";
  public static final String mutPrefix = "mut_";
  public static final String crossPrefix = "cross_";
  public static final String generatorPrefix = "gen_";

  private ICreaturePool<TCreatue> realPool;
  private IterationLogger logger;
  private IFitnesTransformer transformer;

  private ArrayList<ArrayList<int[]>> mutIds;
  private ArrayList<ArrayList<int[]>> crossIds;
  private ArrayList<ArrayList<int[]>> generatedIds;
  private ArrayList<int[]> survIds;
  private double[] survAvg;
  private ArrayList<double[]> generatedAvgs;
  private ArrayList<double[]> mutationAvgs;
  private ArrayList<double[]> crossoverAvgs;

  private HashMap<Integer, String> mutNames = new HashMap<>();
  private HashMap<Integer, String> breedNames = new HashMap<>();
  private HashMap<Integer, String> generatorNames = new HashMap<>();
  private HashMap<Integer, String> fitnessNames = new HashMap<>();

  public PoolDecoratorForOperatorHistory(ICreaturePool<TCreatue> realPool, IterationLogger logger,
      IFitnesTransformer transformer) {
    this.realPool = realPool;
    this.logger = logger;
    this.transformer = transformer;
    resetSavedIds();
    resolveNames();
  }

  private void resolveNames() {
    for (int i = 0; i < getBreedersNames().size(); i++) {
      breedNames.put(0, getBreedersNames().get(i));
    }

    for (int i = 0; i < getMutatorsNames().size(); i++) {
      mutNames.put(0, getMutatorsNames().get(i));
    }

    for (int i = 0; i < getFitnesCalsNames().size(); i++) {
      fitnessNames.put(0, getFitnesCalsNames().get(i));
    }

    for (int i = 0; i < getGeneratorsNames().size(); i++) {
      generatorNames.put(0, getGeneratorsNames().get(i));
    }

  }

  @Override
  public Map<Integer, Double[]> calculateFitnessAndDeleteOldGeneration() {
    Map<Integer, Double[]> res = realPool.calculateFitnessAndDeleteOldGeneration();
    if (transformer != null) {
      res = transformer.transform(res);
    }
    calculateAvarges(res);
    logAvarages();
    resetSavedIds();

    return res;
  }

  @Override
  public Map<Integer, Double[]> calculateFitnessAndMergeOldGenWithNew() {
    Map<Integer, Double[]> res = realPool.calculateFitnessAndMergeOldGenWithNew();
    if (transformer != null) {
      res = transformer.transform(res);
    }
    calculateAvarges(res);
    logAvarages();
    resetSavedIds();

    return res;
  }

  private void resetSavedIds() {
    mutIds = new ArrayList<>();
    crossIds = new ArrayList<>();
    generatedIds = new ArrayList<>();
    survIds = new ArrayList<>();
  }

  private void calculateAvarges(Map<Integer, Double[]> res) {
    int length = res.entrySet().iterator().next().getValue().length;
    survAvg = initializeVecor(length);
    int cntr = 0;
    for (int[] saved : survIds) {
      Double[] resOfSurvvied = res.get(saved[0]);
      addVector(survAvg, resOfSurvvied);
      cntr++;
    }
    divideVector(survAvg, cntr);

    generatedAvgs = new ArrayList<double[]>();
    for (int i = 0; i < generatedIds.size(); i++) {
      ArrayList<int[]> idsetToGen = generatedIds.get(i);
      int[] theOnly = idsetToGen.get(0);
      double[] curentAvg = initializeVecor(length);
      cntr = 0;
      for (int id = theOnly[0]; id < theOnly[0] + theOnly[1]; id++) {
        addVector(curentAvg, res.get(id));
        cntr++;
      }
      divideVector(curentAvg, cntr);
      generatedAvgs.add(curentAvg);
    }

    mutationAvgs = new ArrayList<double[]>();
    for (int i = 0; i < mutIds.size(); i++) {
      ArrayList<int[]> muList = mutIds.get(i);
      double[] curentAvg = initializeVecor(length);
      cntr = 0;
      for (int[] call : muList) {
        addVector(curentAvg, res.get(call[1]));
        cntr++;
      }
      divideVector(curentAvg, cntr);
      mutationAvgs.add(curentAvg);
    }

    crossoverAvgs = new ArrayList<double[]>();
    for (int i = 0; i < crossIds.size(); i++) {
      ArrayList<int[]> calls = crossIds.get(i);
      double[] curentAvg = initializeVecor(length);
      cntr = 0;
      for (int[] call : calls) {
        addVector(curentAvg, res.get(call[2]));
        addVector(curentAvg, res.get(call[3]));
        cntr += 2;
      }
      divideVector(curentAvg, cntr);
      crossoverAvgs.add(curentAvg);
    }

  }

  private void divideVector(double[] what, int cntr) {
    for (int i = 0; i < what.length; i++) {
      what[i] = what[i] / cntr;
      if (Double.isNaN(what[i])) {
        System.out.println("divizon by zero");
        what[i] = 0.0;
      }
    }
  }

  private void addVector(double[] toWhat, Double[] what) {
    for (int i = 0; i < toWhat.length; i++) {
      toWhat[i] += what[i];
    }
  }

  private double[] initializeVecor(int length) {
    double[] toRet = new double[length];
    for (int i = 0; i < length; i++) {
      toRet[i] = 0.0;
    }
    return toRet;

  }

  private void logAvarages() {
    for (int i = 0; i < survAvg.length; i++) {
      logger.addLogToTopic(logPrefix + survPrefix + fitnessNames.get(i), survAvg[i]);
    }
    for (int mutatorId = 0; mutatorId < mutationAvgs.size(); mutatorId++) {
      for (int finesId = 0; finesId < mutationAvgs.get(mutatorId).length; finesId++) {
        logger.addLogToTopic(logPrefix + mutNames.get(mutatorId) + "_" + fitnessNames.get(finesId),
            mutationAvgs.get(mutatorId)[finesId]);
      }
    }
    for (int genId = 0; genId < generatedAvgs.size(); genId++) {
      for (int finesId = 0; finesId < generatedAvgs.get(genId).length; finesId++) {
        logger.addLogToTopic(logPrefix + generatorNames.get(genId) + "_" + fitnessNames.get(finesId),
            generatedAvgs.get(genId)[finesId]);
      }
    }
    for (int crossId = 0; crossId < crossoverAvgs.size(); crossId++) {
      for (int finesId = 0; finesId < crossoverAvgs.get(crossId).length; finesId++) {
        logger.addLogToTopic(logPrefix + breedNames.get(crossId) + "_" + fitnessNames.get(finesId),
            crossoverAvgs.get(crossId)[finesId]);
      }
    }
  }

  @Override
  public void survive(List<int[]> idsToSurvive) {
    survIds.addAll(idsToSurvive);
    realPool.survive(idsToSurvive);
  }

  @Override
  public void mutate(int whichMutator, List<int[]> idsToMutate) {
    save(mutIds, whichMutator, idsToMutate);
    realPool.mutate(whichMutator, idsToMutate);
  }

  private void save(ArrayList<ArrayList<int[]>> where, int which, List<int[]> what) {
    while (where.size() <= which) {
      where.add(new ArrayList<>());
    }
    where.get(which).addAll(what);

  }

  @Override
  public void crossover(int whichbreeder, List<int[]> idsToCross) {
    save(crossIds, whichbreeder, idsToCross);
    realPool.crossover(whichbreeder, idsToCross);
  }

  @Override
  public void generate(int whichGenerator, int firstId, int howMany) {
    save(generatedIds, whichGenerator, asList(new int[] { firstId, howMany }));
    realPool.generate(whichGenerator, firstId, howMany);
  }

  @Override
  public TCreatue get(int id) {
    return realPool.get(id);
  }

  @Override
  public void terminatePool() {
    realPool.terminatePool();
  }

  @Override
  public List<String> getGeneratorsNames() {
    return realPool.getGeneratorsNames();
  }

  @Override
  public List<String> getMutatorsNames() {
    return realPool.getMutatorsNames();
  }

  @Override
  public List<String> getBreedersNames() {
    return realPool.getBreedersNames();
  }

  @Override
  public List<String> getFitnesCalsNames() {
    return realPool.getFitnesCalsNames();
  }

  @Override
  public GenerationSizeStats getSizeStats() {
    return realPool.getSizeStats();
  }

}
