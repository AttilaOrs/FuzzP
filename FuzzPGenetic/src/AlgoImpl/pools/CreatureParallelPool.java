package AlgoImpl.pools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import structure.GPIndividSize;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IGPGreature;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class CreatureParallelPool<TCreatue extends IGPGreature>
		implements
			ICreaturePool<TCreatue> {

	public static final int THREAD_NR = 4;

	@Override
  public ArrayList<IOperatorFactory<ICreatureGenerator<TCreatue>>> getGenerators() {
		return generators;
	}

	@Override
  public ArrayList<IOperatorFactory<ICreatureMutator<TCreatue>>> getMutators() {
		return mutators;
	}

	@Override
  public ArrayList<IOperatorFactory<ICreatureBreeder<TCreatue>>> getBreeders() {
		return breeders;
	}

	@Override
  public ArrayList<IOperatorFactory<ICreatureFitnes<TCreatue>>> getFitnesCals() {
		return fitnesCals;
	}

	ArrayList<IOperatorFactory<ICreatureGenerator<TCreatue>>> generators;
	ArrayList<IOperatorFactory<ICreatureMutator<TCreatue>>> mutators;
	ArrayList<IOperatorFactory<ICreatureBreeder<TCreatue>>> breeders;
	ArrayList<IOperatorFactory<ICreatureFitnes<TCreatue>>> fitnesCals;

	ConcurrentHashMap<Integer, TCreatue> curentPool;
	ConcurrentHashMap<Integer, TCreatue> oldPool;
	ConcurrentHashMap<Integer, Double[]> curentResult;
	ConcurrentHashMap<Integer, Double[]> oldResult;

	Random rnd;

	ConcurrentLinkedQueue<Task> toDo;
	ArrayList<WorkerThread> workers;

	public CreatureParallelPool(
			ArrayList<IOperatorFactory<ICreatureGenerator<TCreatue>>> generators,
			ArrayList<IOperatorFactory<ICreatureMutator<TCreatue>>> mutators,
			ArrayList<IOperatorFactory<ICreatureBreeder<TCreatue>>> breeders,
			ArrayList<IOperatorFactory<ICreatureFitnes<TCreatue>>> fitnesCals) {
		this.generators = generators;
		this.mutators = mutators;
		this.breeders = breeders;
		this.fitnesCals = fitnesCals;
		curentPool = new ConcurrentHashMap<>();
		oldPool = new ConcurrentHashMap<>();
		curentResult = new ConcurrentHashMap<>();
		oldResult = new ConcurrentHashMap<>();
		rnd = new Random();
		toDo = new ConcurrentLinkedQueue<>();
		workers = new ArrayList<>();
		for (int q = 0; q < THREAD_NR; q++) {
			WorkerThread worker = new WorkerThread();
			workers.add(worker);
			worker.start();
		}

	}

	@Override
	public HashMap<Integer, Double[]> calculateFitness() {
		waitThreadsToFinish();
		oldResult = curentResult;
		curentResult = new ConcurrentHashMap<>();
		oldPool = curentPool;
		curentPool = new ConcurrentHashMap<>();
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
			toDo.add(new Task(TaskType.MUTATE, idsToMutate.get(ii),
					whichMutator));
		}
		wakeUpThreads();
	}

	@Override
	public void crossover(int whichbreeder, List<int[]> idsToCross) {
		for (int ii = 0; ii < idsToCross.size(); ii++) {
			toDo.add(new Task(TaskType.CROSSOVER, idsToCross.get(ii),
					whichbreeder));
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
		GENRATE, SURVIVE, MUTATE, CROSSOVER
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
		ArrayList<ICreatureFitnes<TCreatue>> workerFitnesCals;
		Random workerRandom;
		private boolean finish;

		public WorkerThread() {
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
				workerFitnesCals.add(fitnesCals.get(i).generate());
			}
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
					}
				}
			}
		}

		private void doCrossOver(Task work) {
			TCreatue mother = oldPool.get(work.params[0]);
			TCreatue father = oldPool.get(work.params[1]);
			TCreatue[] res = workerBreeders.get(work.selector).breed(mother,
					father, workerRandom);
			calcFitnes(res[0], work.params[2]);
			calcFitnes(res[1], work.params[3]);
		}

		private void doMutate(Task work) {
			TCreatue original = oldPool.get(work.params[0]);
			TCreatue mutated = workerMutators.get(work.selector).mutate(
					original, workerRandom);
			calcFitnes(mutated, work.params[1]);
		}

		private void doSurvive(Task work) {
		  TCreatue i = oldPool.get(work.params[0]);
      curentPool.put(work.params[1], (TCreatue) i.myClone());
      curentResult.put(work.params[1], oldResult.get(work.params[0]));
		}

		private void doInitialziation(Task work) {
			TCreatue newlyCreated = workerGenerators.get(work.selector)
					.genearteRandomCreature(workerRandom);
			calcFitnes(newlyCreated, work.params[0]);
		}

		private void calcFitnes(TCreatue tCreatue, int i) {
			Double[] fitness = new Double[workerFitnesCals.size()];
			for (int q = 0; q < workerFitnesCals.size(); q++) {
				double res = workerFitnesCals.get(q).evaluate(tCreatue);
				fitness[q] = res;
			}
			curentPool.put(i, tCreatue);
			curentResult.put(i, fitness);
		}
	}

  @Override
  public GPIndividSize getAvarageSizeOfCurrentPool() {
    GPIndividSize s = new GPIndividSize();
    for (TCreatue cr : oldPool.values()) {
      s.add(cr.getSizes());
    }
    return new GPIndividSize(s.ops / oldPool.size(), s.leafs / oldPool.size(), s.depth / oldPool.size());
  }
}
