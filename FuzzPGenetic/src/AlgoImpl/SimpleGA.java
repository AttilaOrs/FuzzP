package AlgoImpl;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import structure.ICreaturePool;
import structure.IGPGreature;
import structure.ISelector;

public class SimpleGA<TCreature extends IGPGreature> {
	ICreaturePool<TCreature> pool;
	ISelector selector;

	public static int population = 1000;
	public static int iteration = 100;
	public static int ELIT = 5;
	public static int SELECTION = 15;
	public static int MUTATION = 15;
	public static int CROSSOVER = 65; // sum has to be 100

	protected Map<Integer, Double[]> res;
	protected Integer maxId;
	private long garbageCollectionLastTime;
	protected IterationLogger logger;

	public SimpleGA(ICreaturePool<TCreature> pool, ISelector selector) {
		this.pool = pool;
		this.selector = selector;
		this.maxId = null;
		garbageCollectionLastTime = -1;
		logger = new IterationLogger();
	}

	public void theAlgo() {
		int eliteNr = population * ELIT / 100;
		int survNr = population * SELECTION / 100;
		int mutNr = population * MUTATION / 100;
		int cross = population * CROSSOVER / 200;

		for (int iter = 0; iter < iteration; iter++) {
			if (iter == 0) {
				pool.generate(0, 0, population);
			} else {
				int curentIndex = 0;
				List<Entry<Integer, Double[]>> elite = res.entrySet().stream()
						.sorted((entry1,
								entry2) -> entry1.getValue()[0]
										.compareTo(entry2.getValue()[0]) * -1)
						.limit(eliteNr).collect(Collectors.toList());
				List<int[]> elitsSurv = elite.stream()
						.map(entry -> new int[]{entry.getKey()})
						.collect(Collectors.toList());

				pool.survive(elitsSurv);

				List<int[]> toSurv = selector.selectOne(res, 0,
						survNr + eliteNr, 1);

				pool.survive(toSurv);

				List<int[]> toMut = selector.selectOne(res, 0, mutNr, 2);
				for (int i = 0; i < toMut.size(); i++) {
					toMut.get(i)[1] = population * iter + curentIndex;
					curentIndex++;
				}

				pool.mutate(0, toMut);

				List<int[]> toCross = selector.selectPairs(res, 0, cross, 4);
				for (int i = 0; i < toCross.size(); i++) {
					toCross.get(i)[2] = population * iter + curentIndex;
					curentIndex++;
					toCross.get(i)[3] = population * iter + curentIndex;
					curentIndex++;
				}

				pool.crossover(0, toCross);

			}
			res = pool.calculateFitness();

			DoubleSummaryStatistics statistics = res.entrySet().stream()
					.collect(
							Collectors.summarizingDouble(e -> e.getValue()[0]));
			logMemoryAndGc();
			logIterationResults(iter, statistics.getAverage(),
					statistics.getMax(), res.size());

			System.gc();

		}
		Optional<Entry<Integer, Double[]>> max = res.entrySet().stream()
				.max((entry1, entry2) -> entry1.getValue()[0]
						.compareTo(entry2.getValue()[0]));
		this.maxId = max.get().getKey();
		pool.terminatePool();
	}

	protected void logIterationResults(int iter, double average, double max,
			int size) {
		logger.addLogToTopic(IterationLogger.AVG_FIT, average);
		logger.addLogToTopic(IterationLogger.MAX_FIT, max);
		logger.addLogToTopic(IterationLogger.POP_SIZE, size / 1.0);
		logger.iterFinished(iter);
	}

	protected void logMemoryAndGc() {
		Runtime runtime = Runtime.getRuntime();
		long usedMemory = (runtime.totalMemory() - runtime.freeMemory())
				/ (1024 * 1024);
		logger.addLogToTopic(IterationLogger.GC_SEC,
				getGarbageCollectionTimeINLastIteration() / 1000.0);
		logger.addLogToTopic(IterationLogger.MEM_USE, (double) usedMemory);
	}

	public Integer getMaxId() {
		return this.maxId;
	}

	private long getGarbageCollectionTimeINLastIteration() {
		long collectionTime = 0;
		for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory
				.getGarbageCollectorMXBeans()) {
			collectionTime += garbageCollectorMXBean.getCollectionTime();
		}
		long toRet = 0;
		if (this.garbageCollectionLastTime != -1) { // the first time shuold
													// return 0
			toRet = collectionTime - this.garbageCollectionLastTime;
		}
		this.garbageCollectionLastTime = collectionTime;
		return toRet;
	}

	public IterationLogger getLogger() {
		return logger;
	}

	public void setLogger(IterationLogger logger) {
		this.logger = logger;
	}
}
