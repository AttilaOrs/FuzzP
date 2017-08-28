package AlgoImpl;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import structure.ICreaturePool;
import structure.ICreaturePool.GenerationSizeStats;
import structure.IGPGreature;
import structure.ISelector;

public class SimpleGA<TCreature extends IGPGreature> {
	ICreaturePool<TCreature> pool;
	ISelector selector;

  public static final int SIZE_HIST_LOG = 10;

	public static int population = 1000;
	public static int iteration = 100;
  public static int ELIT = 2;
  public static int SELECTION = 18;
	public static int MUTATION = 15;
	public static int CROSSOVER = 65; // sum has to be 100

	protected Map<Integer, Double[]> res;
	protected Integer maxId;
	private long garbageCollectionLastTime;
	protected IterationLogger logger;
  protected Map<String, Map<Integer, Integer>> sizeHistLogs;
  private int curentIndex;
  protected int iter;

	public SimpleGA(ICreaturePool<TCreature> pool, ISelector selector) {
		this.pool = pool;
		this.selector = selector;
		this.maxId = null;
		garbageCollectionLastTime = -1;
		logger = new IterationLogger();
    sizeHistLogs = new HashMap<>();
	}

	public void theAlgo() {
		int eliteNr = population * ELIT / 100;
		int survNr = population * SELECTION / 100;
		int mutNr = population * MUTATION / 100;
    int cross = (population - eliteNr - survNr - mutNr) / 2;
    if (eliteNr + survNr + mutNr + cross != population) {
      mutNr += 1;
    }

    for (iter = 0; iter < iteration; iter++) {
      long timeStart = System.nanoTime();
			if (iter == 0) {
				pool.generate(0, 0, population);
			} else {
        curentIndex = 0;
				List<Entry<Integer, Double[]>> elite = res.entrySet().stream()
						.sorted((entry1,
								entry2) -> entry1.getValue()[0]
										.compareTo(entry2.getValue()[0]) * -1)
						.limit(eliteNr).collect(Collectors.toList());
				List<int[]> elitsSurv = elite.stream()
            .map(entry -> new int[] { entry.getKey(), nextIndex() })
						.collect(Collectors.toList());

				pool.survive(elitsSurv);

        Map<Integer, Double[]> withoutElite = res.entrySet().stream().filter(e -> !elite.contains(e))
            .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        List<int[]> toSurv = selector.selectOne(withoutElite, 0,
            survNr, 2);
        for (int i = 0; i < toSurv.size(); i++) {
          toSurv.get(i)[1] = nextIndex();
        }

				pool.survive(toSurv);

				List<int[]> toMut = selector.selectOne(res, 0, mutNr, 2);
				for (int i = 0; i < toMut.size(); i++) {
          toMut.get(i)[1] = nextIndex();
				}

				pool.mutate(0, toMut);

				List<int[]> toCross = selector.selectPairs(res, 0, cross, 4);
				for (int i = 0; i < toCross.size(); i++) {
          toCross.get(i)[2] = nextIndex();
          toCross.get(i)[3] = nextIndex();
				}

				pool.crossover(0, toCross);

			}
			res = pool.calculateFitness();
      GenerationSizeStats size = pool.getSizeStats();
      long timeStop = System.nanoTime();

			DoubleSummaryStatistics statistics = res.entrySet().stream()
					.collect(
							Collectors.summarizingDouble(e -> e.getValue()[0]));
			logMemoryAndGc();
			logIterationResults(iter, statistics.getAverage(),
          statistics.getMax(), res.size(), size, (timeStop - timeStart) / res.size());

			System.gc();

		}
		Optional<Entry<Integer, Double[]>> max = res.entrySet().stream()
				.max((entry1, entry2) -> entry1.getValue()[0]
						.compareTo(entry2.getValue()[0]));
		this.maxId = max.get().getKey();
		pool.terminatePool();
	}

  protected int nextIndex() {
    int temp = population * iter + curentIndex;
    curentIndex++;
    return temp;
  }


  protected void logIterationResults(int iter, double average, double max,
      int size, GenerationSizeStats sizeStats, long l) {
		logger.addLogToTopic(IterationLogger.AVG_FIT, average);
		logger.addLogToTopic(IterationLogger.MAX_FIT, max);
		logger.addLogToTopic(IterationLogger.POP_SIZE, size / 1.0);

    logger.addLogToTopic(IterationLogger.TREE_DEPTH_AVG, sizeStats.avg.depth / 1.0);
    logger.addLogToTopic(IterationLogger.TREE_LEAFS_AVG, sizeStats.avg.leafs / 1.0);
    logger.addLogToTopic(IterationLogger.TREE_OPS_AVG, sizeStats.avg.ops / 1.0);

    logger.addLogToTopic(IterationLogger.TREE_DEPTH_MIN, sizeStats.min.depth / 1.0);
    logger.addLogToTopic(IterationLogger.TREE_LEAFS_MIN, sizeStats.min.leafs / 1.0);
    logger.addLogToTopic(IterationLogger.TREE_OPS_MIN, sizeStats.min.ops / 1.0);

    logger.addLogToTopic(IterationLogger.TREE_DEPTH_MAX, sizeStats.max.depth / 1.0);
    logger.addLogToTopic(IterationLogger.TREE_LEAFS_MAX, sizeStats.max.leafs / 1.0);
    logger.addLogToTopic(IterationLogger.TREE_OPS_MAX, sizeStats.max.ops / 1.0);

    logger.addLogToTopic(IterationLogger.TREE_SIZE_AVG, sizeStats.avg.size() / 1.0);

    logger.addLogToTopic(IterationLogger.TIME, l / 1.0);
		logger.iterFinished(iter);
    if (iter % SIZE_HIST_LOG == 0) {
      sizeHistLogs.put("gen " + iter, sizeStats.sizeHist);
    }
	}

	protected void logMemoryAndGc() {
		Runtime runtime = Runtime.getRuntime();
		long usedMemory = (runtime.totalMemory() - runtime.freeMemory())
				/ (1024 * 1024);
		logger.addLogToTopic(IterationLogger.GC_SEC,
				getGarbageCollectionTimeINLastIteration() / 1000.0);
		logger.addLogToTopic(IterationLogger.MEM_USE, (double) usedMemory);
	}

  public Map<String, Map<Integer, Integer>> getSizeHistLog() {
    return sizeHistLogs;
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
