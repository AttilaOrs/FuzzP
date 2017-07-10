package AlgoImpl.pools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import structure.GPIndividSize;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IGPGreature;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class CreaturePoolWithStreams<TCreatue extends IGPGreature>
		implements
			ICreaturePool<TCreatue> {

	private static final int THREAD_NR = 16;

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
		return fitnesses;
	}

	ArrayList<IOperatorFactory<ICreatureGenerator<TCreatue>>> generators;
	ArrayList<IOperatorFactory<ICreatureMutator<TCreatue>>> mutators;
	ArrayList<IOperatorFactory<ICreatureBreeder<TCreatue>>> breeders;
	ArrayList<IOperatorFactory<ICreatureFitnes<TCreatue>>> fitnesses;

	ArrayList<OpManager<ICreatureGenerator<TCreatue>>> generatorManagers;
	ArrayList<OpManager<ICreatureMutator<TCreatue>>> mutatorManagers;
	ArrayList<OpManager<ICreatureBreeder<TCreatue>>> breederManagers;
	ArrayList<OpManager<ICreatureFitnes<TCreatue>>> fitnesManagers;

	ConcurrentHashMap<Integer, TCreatue> curentPool;
	ConcurrentHashMap<Integer, TCreatue> oldPool;
	ConcurrentHashMap<Integer, Double[]> curentResult;
	ConcurrentHashMap<Integer, Double[]> oldResult;

	Random rnd;
	private ForkJoinPool pool;

	public CreaturePoolWithStreams(
			ArrayList<IOperatorFactory<ICreatureGenerator<TCreatue>>> generators,
			ArrayList<IOperatorFactory<ICreatureMutator<TCreatue>>> mutators,
			ArrayList<IOperatorFactory<ICreatureBreeder<TCreatue>>> breeders,
			ArrayList<IOperatorFactory<ICreatureFitnes<TCreatue>>> fitnesCals) {

		this.generators = generators;
		generatorManagers = makeManagers(generators);
		this.mutators = mutators;
		mutatorManagers = makeManagers(mutators);
		this.breeders = breeders;
		breederManagers = makeManagers(breeders);
		this.fitnesses = fitnesCals;
		fitnesManagers = makeManagers(fitnesCals);
		curentPool = new ConcurrentHashMap<>();
		oldPool = new ConcurrentHashMap<>();
		curentResult = new ConcurrentHashMap<>();
		oldResult = new ConcurrentHashMap<>();
		rnd = new Random();
		pool = new ForkJoinPool(THREAD_NR);
	}

	private <MagicOp> ArrayList<OpManager<MagicOp>> makeManagers(
			ArrayList<IOperatorFactory<MagicOp>> factories) {
		ArrayList<OpManager<MagicOp>> manageres = new ArrayList<>();
		for (int q = 0; q < factories.size(); q++) {
			manageres.add(new OpManager<>(factories.get(q)));
		}
		return manageres;
	}

	@Override
	public void generate(int whichGenerator, int firstId, int howMany) {
		try {
			pool.submit(
					() -> {
						IntStream
								.range(firstId, firstId + howMany)
								.parallel()
								.forEach(
										i -> {
											TCreatue newCr = getGenerator(
													whichGenerator)
													.genearteRandomCreature(rnd);
											curentPool.put(i, newCr);
										});
					}).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crossover(int whichbreeder, List<int[]> idsToCross) {
		try {
			pool.submit(
					() -> {
						idsToCross.parallelStream().forEach(
								idAr -> {
									TCreatue[] res = getBreader(whichbreeder)
											.breed(oldPool.get(idAr[0]),
													oldPool.get(idAr[1]), rnd);
									curentPool.put(idAr[2], res[0]);
									curentPool.put(idAr[3], res[1]);
								});
					}).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mutate(int whichMutator, List<int[]> idsToMutate) {
		try {
			pool.submit(
					() -> {
						idsToMutate.parallelStream().forEach(
								idAr -> {
									TCreatue res = getMutator(whichMutator)
											.mutate(oldPool.get(idAr[0]), rnd);
									curentPool.put(idAr[1], res);
								});
					}).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void survive(List<int[]> idsToSurvive) {
		try {
			pool.submit(() -> {
				idsToSurvive.parallelStream().forEach(i -> {
          IGPGreature newInid = oldPool.get(i[0]).myClone();
          curentPool.put(i[1], (TCreatue) newInid);
          curentResult.put(i[1], oldResult.get(i[0]));
				});
			}).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<Integer, Double[]> calculateFitness() {
		try {
			pool.submit(
					() -> {
						curentPool.keySet().parallelStream()
								.filter(id -> !curentResult.containsKey(id))
								.forEach(this::calcFitnes);
					}).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		freeUpManagers();
		oldResult = curentResult;
		curentResult = new ConcurrentHashMap<>();
		oldPool = curentPool;
		curentPool = new ConcurrentHashMap<>();
		HashMap<Integer, Double[]> toRet = new HashMap<>();
		toRet.putAll(oldResult);
		return toRet;
	}

	private void freeUpManagers() {
		breederManagers.forEach(m -> m.allOpsAreFree());
		generatorManagers.forEach(m -> m.allOpsAreFree());
		mutatorManagers.forEach(m -> m.allOpsAreFree());
		fitnesManagers.forEach(m -> m.allOpsAreFree());
	}

	private void calcFitnes(Integer id) {
		Double[] res = new Double[fitnesses.size()];
		TCreatue toEval = curentPool.get(id);

		for (int i = 0; i < fitnesses.size(); i++) {
			double d = getFitnesCalculator(i).evaluate(toEval);
			res[i] = d;
		}

		curentResult.put(id, res);
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

	@Override
	public void terminatePool() {
		// TODO Auto-generated method stub

	}

	private ICreatureGenerator<TCreatue> getGenerator(int whichGenerator) {
		return generatorManagers.get(whichGenerator).getOperator();
	}

	private ICreatureBreeder<TCreatue> getBreader(int whichbreeder) {
		return breederManagers.get(whichbreeder).getOperator();
	}

	private ICreatureMutator<TCreatue> getMutator(int whichMutator) {
		return mutatorManagers.get(whichMutator).getOperator();
	}

	private ICreatureFitnes<TCreatue> getFitnesCalculator(int whichFitnes) {
		return fitnesManagers.get(whichFitnes).getOperator();
	}

	static class OpManager<Op> {
		ArrayList<Op> generatedOps;
		int currentIndex;
		IOperatorFactory<Op> myFactory;

		public OpManager(IOperatorFactory<Op> myFactory) {
			this.myFactory = myFactory;
			currentIndex = 0;
			generatedOps = new ArrayList<>();
		}

		synchronized public Op getOperator() {
			if (!(currentIndex < generatedOps.size())) {
				Op temp = myFactory.generate();
				generatedOps.add(temp);
				currentIndex++;
				return temp;
			} else {
				return generatedOps.get(currentIndex++);
			}
		}

		public void allOpsAreFree() {
			currentIndex = 0;
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
