package AlgoImpl.pools.behaviour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import structure.GPIndividSize;
import structure.ICreaturePool;
import structure.IGPGreature;
import structure.IOperatorFactory;
import structure.behaviour.IBeahviourDescriptor;
import structure.behaviour.IBehaviourBasedFitness;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class BehaviourParalellPool<TCreatue extends IGPGreature, TBehavourDesc> implements ICreaturePool<TCreatue> {

  public static final int THREAD_NR = Runtime.getRuntime().availableProcessors() * 2;

  ArrayList<IOperatorFactory<ICreatureGenerator<TCreatue>>> generators;
  ArrayList<IOperatorFactory<ICreatureMutator<TCreatue>>> mutators;
  ArrayList<IOperatorFactory<ICreatureBreeder<TCreatue>>> breeders;
  ArrayList<IOperatorFactory<IBehaviourBasedFitness<TCreatue, TBehavourDesc>>> fitnesCals;
  IOperatorFactory<IBeahviourDescriptor<TBehavourDesc, TCreatue>> descriptorFactory;

  ConcurrentHashMap<Integer, TCreatue> curentPool;
  ConcurrentHashMap<Integer, TCreatue> oldPool;
  ConcurrentHashMap<Integer, Double[]> curentResult;
  ConcurrentHashMap<Integer, Double[]> oldResult;

  Random rnd;
  ConcurrentLinkedQueue<Task> toDo;
  ConcurrentLinkedQueue<Task> secondPhaseTask;
  ArrayList<WorkerThread> workers;
  BehaviourStore<TBehavourDesc> store;

  public BehaviourParalellPool(
			ArrayList<IOperatorFactory<ICreatureGenerator<TCreatue>>> generators,
			ArrayList<IOperatorFactory<ICreatureMutator<TCreatue>>> mutators,
			ArrayList<IOperatorFactory<ICreatureBreeder<TCreatue>>> breeders,
      ArrayList<IOperatorFactory<IBehaviourBasedFitness<TCreatue, TBehavourDesc>>> fitnesCals,
      IOperatorFactory<IBeahviourDescriptor<TBehavourDesc, TCreatue>> descriptorFactory) {
		this.generators = generators;
		this.mutators = mutators;
		this.breeders = breeders;
		this.fitnesCals = fitnesCals;
    this.descriptorFactory = descriptorFactory;
		curentPool = new ConcurrentHashMap<>();
		oldPool = new ConcurrentHashMap<>();
		curentResult = new ConcurrentHashMap<>();
		oldResult = new ConcurrentHashMap<>();
		rnd = new Random();
		toDo = new ConcurrentLinkedQueue<>();
		workers = new ArrayList<>();
    secondPhaseTask = new ConcurrentLinkedQueue<>();
    store = new BehaviourStore<>();
		for (int q = 0; q < THREAD_NR; q++) {
			WorkerThread worker = new WorkerThread(this);
			workers.add(worker);
			worker.start();
		}

	}

  @Override
  public HashMap<Integer, Double[]> calculateFitnessAndDeleteOldGeneration() {
    waitThreadsToFinish();
    toDo.addAll(secondPhaseTask);
    secondPhaseTask.clear();
    waitThreadsToFinish();

    oldResult = curentResult;
    curentResult = new ConcurrentHashMap<>();
    oldPool = curentPool;
    curentPool = new ConcurrentHashMap<>();
    store.deleteAllButThese(oldPool.keySet());
    HashMap<Integer, Double[]> toRet = new HashMap<>();
    toRet.putAll(oldResult);
    return toRet;
  }

  @Override
  public Map<Integer, Double[]> calculateFitnessAndMergeOldGenWithNew() {
    waitThreadsToFinish();
    toDo.addAll(secondPhaseTask);
    secondPhaseTask.clear();
    waitThreadsToFinish();
    oldResult.putAll(curentResult);
    oldPool.putAll(curentPool);
    curentResult = new ConcurrentHashMap<>();
    curentPool = new ConcurrentHashMap<>();
    store.deleteAllButThese(oldPool.keySet());
    HashMap<Integer, Double[]> toRet = new HashMap<>();
    toRet.putAll(oldResult);
    return toRet;
  }


  @Override
  public void survive(List<int[]> idsToSurvive) {
    for (int ii = 0; ii < idsToSurvive.size(); ii++) {
      toDo.add(new Task(TaskType.SURVIVE, idsToSurvive.get(ii), -1));
    }
    wakeUpThreads();
  }

  @Override
  public void mutate(int whichMutator, List<int[]> idsToMutate) {
    for (int ii = 0; ii < idsToMutate.size(); ii++) {
      toDo.add(new Task(TaskType.MUTATE, idsToMutate.get(ii), whichMutator));
    }
    wakeUpThreads();
  }

  @Override
  public void crossover(int whichbreeder, List<int[]> idsToCross) {
    for (int ii = 0; ii < idsToCross.size(); ii++) {
      toDo.add(new Task(TaskType.CROSSOVER, idsToCross.get(ii), whichbreeder));
    }
    wakeUpThreads();
  }

  @Override
  public void generate(int whichGenerator, int firstId, int howMany) {
    for (int ii = firstId; ii < firstId + howMany; ii++) {
      toDo.add(new Task(TaskType.GENRATE, new int[]{ii}, whichGenerator));
    }
    wakeUpThreads();
  }

  @Override
  public TCreatue get(int id) {
    if (oldPool.containsKey(id)) {
      return oldPool.get(id);
    } else if (curentPool.containsKey(id)) {
      return curentPool.get(id);
    }
    return null;
  }

  private enum TaskType {
    GENRATE, SURVIVE, MUTATE, CROSSOVER, CALC_FITNESS
  }

  private static class Task {
    public Task(TaskType type, int[] params, int selector) {
      this.type = type;
      this.params = params;
      this.selector = selector;
    }

    TaskType type;
    int[] params;
    int selector;

    @Override
    public String toString() {
      String str = Arrays.stream(params).mapToObj(i -> Integer.toString(i)).collect(Collectors.joining(","));
      return "{t " + type + " " + str + "}";

    }
  }

  @Override
  public void terminatePool() {
    for (WorkerThread wr : workers) {
      wr.setFinish();
    }
    wakeUpThreads();

  }

  private void wakeUpThreads() {

    for (WorkerThread ll : workers) {
      if (ll.getState() != Thread.State.RUNNABLE) {
        synchronized (ll) {
          ll.notify();
        }
      }
    }
  }

  private void waitThreadsToFinish() {
    boolean workingThread = areWorkersFinsihed();
    while ((toDo.size() != 0) || (!workingThread)) {
      wakeUpThreads();
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      workingThread = areWorkersFinsihed();
    }

  }

  private boolean areWorkersFinsihed() {
    for (WorkerThread ll : this.workers) {
      if (ll.getState() == Thread.State.RUNNABLE) {
        return false;
      }
    }
    return true;
  }

  public class WorkerThread extends Thread {
    ArrayList<ICreatureGenerator<TCreatue>> workerGenerators;
    ArrayList<ICreatureMutator<TCreatue>> workerMutators;
    ArrayList<ICreatureBreeder<TCreatue>> workerBreeders;
    ArrayList<IBehaviourBasedFitness<TCreatue, TBehavourDesc>> workerFitnesCals;
    IBeahviourDescriptor<TBehavourDesc, TCreatue> behaviourDescriptor;

    Random workerRandom;
    private boolean finish;
    private BehaviourParalellPool<TCreatue, TBehavourDesc> poolToPass;

    public WorkerThread(BehaviourParalellPool<TCreatue, TBehavourDesc> poolToPass) {
      this.poolToPass = poolToPass;
      workerGenerators = new ArrayList<>();
      for (int i = 0; i < generators.size(); i++) {
        workerGenerators.add(generators.get(i).generate());
      }
      workerMutators = new ArrayList<>();
      for (int i = 0; i < mutators.size(); i++) {
        workerMutators.add(mutators.get(i).generate());
      }

      workerBreeders = new ArrayList<>();
      for (int i = 0; i < breeders.size(); i++) {
        workerBreeders.add(breeders.get(i).generate());
      }

      workerFitnesCals = new ArrayList<>();
      for (int i = 0; i < fitnesCals.size(); i++) {
        IBehaviourBasedFitness<TCreatue, TBehavourDesc> fi = fitnesCals.get(i).generate();
        fi.setStore(store);
        fi.setPool(poolToPass);
        workerFitnesCals.add(fi);
      }
      behaviourDescriptor = descriptorFactory.generate();
      finish = false;
      workerRandom = new Random();

    }

    public void setFinish() {
      finish = true;
    }

    @Override
    public synchronized void run() {
      while (!finish) {
        if (toDo.size() == 0) {
          try {
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        Task work = toDo.poll();
        if (work != null) {

          switch (work.type) {
            case GENRATE :
              doInitialziation(work);
              break;
            case SURVIVE :
              doSurvive(work);
              break;
            case MUTATE :
              doMutate(work);
              break;
            case CROSSOVER :
              doCrossOver(work);
              break;
            case CALC_FITNESS :
              calcFitness(work);
              break;
          }
        }
      }
    }

    private void doCrossOver(Task work) {
      TCreatue mother = oldPool.get(work.params[0]);
      TCreatue father = oldPool.get(work.params[1]);
      TCreatue[] res = workerBreeders.get(work.selector).breed(mother, father, workerRandom);
      calcBehaviour(res[0], work.params[2]);
      calcBehaviour(res[1], work.params[3]);
    }

    private void doMutate(Task work) {
      TCreatue original = oldPool.get(work.params[0]);
      TCreatue mutated = workerMutators.get(work.selector).mutate(original, workerRandom);
      calcBehaviour(mutated, work.params[1]);
    }

    private void doSurvive(Task work) {
      TCreatue i = oldPool.get(work.params[0]);
      curentPool.put(work.params[1], (TCreatue) i.myClone());
      TBehavourDesc behave = store.get(work.params[0]);
      store.store(work.params[1], behave);
      // curentResult.put(work.params[1], oldResult.get(work.params[0]));
      secondPhaseTask.add(new Task(TaskType.CALC_FITNESS, new int[]{work.params[1]}, -1));
    }

    private void doInitialziation(Task work) {
      TCreatue newlyCreated = workerGenerators.get(work.selector).genearteRandomCreature(workerRandom);
      calcBehaviour(newlyCreated, work.params[0]);
    }
    

    private void calcBehaviour(TCreatue tCreatue, int i) {
      TBehavourDesc behaviour = behaviourDescriptor.evaluate(tCreatue);
      store.store(i, behaviour);
      secondPhaseTask.add(new Task(TaskType.CALC_FITNESS, new int[]{i}, -1));
      curentPool.put(i, tCreatue);
    }

    private void calcFitness(Task work) {
      Double[] fitness = new Double[workerFitnesCals.size()];
      for (int q = 0; q < workerFitnesCals.size(); q++) {
        double res = workerFitnesCals.get(q).evaluate(work.params[0]);
        fitness[q] = res;
      }
      curentResult.put(work.params[0], fitness);
    }
  }


  @Override
  public ICreaturePool.GenerationSizeStats getSizeStats() {
    GPIndividSize sum = new GPIndividSize();
    GPIndividSize min = new GPIndividSize(2000, 2000, 2000);
    GPIndividSize max = new GPIndividSize();
    Map<Integer, Integer> hist = new HashMap<>();

    for (TCreatue cr : oldPool.values()) {
      GPIndividSize curentSize = cr.getSizes();
      sum.add(curentSize);
      min.depth = (min.depth > curentSize.depth) ? curentSize.depth : min.depth;
      min.leafs = (min.leafs > curentSize.leafs) ? curentSize.leafs : min.leafs;
      min.ops = (min.ops > curentSize.ops) ? curentSize.ops : min.ops;

      max.depth = (max.depth < curentSize.depth) ? curentSize.depth : max.depth;
      max.leafs = (max.leafs < curentSize.leafs) ? curentSize.leafs : max.leafs;
      max.ops = (max.ops < curentSize.ops) ? curentSize.ops : max.ops;
      Integer size = curentSize.leafs + curentSize.ops;
      Integer key = ((size / 10) * 10) + 5;
      if (hist.containsKey(key)) {
        hist.put(key, hist.get(key) + 1);
      } else {
        hist.put(key, 1);
      }

    }
    GPIndividSize avg = new GPIndividSize(sum.ops / oldPool.size(), sum.leafs / oldPool.size(),
        sum.depth / oldPool.size());
    return new GenerationSizeStats(min, max, avg, hist);
  }

  @Override
  public List<String> getGeneratorsNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getMutatorsNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getBreedersNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getFitnesCalsNames() {
    // TODO Auto-generated method stub
    return null;
  }

  public BehaviourStore<TBehavourDesc> getStore() {
    return this.store;
  }

}
