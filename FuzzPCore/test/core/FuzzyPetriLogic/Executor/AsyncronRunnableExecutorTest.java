package core.FuzzyPetriLogic.Executor;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyToken;
import exampleNets.SimpleDelayPetriNetBuilder;
import exampleNets.TwoLoopNet;

public class AsyncronRunnableExecutorTest {
	private static long defaultDelay = 20;
	private static long precision = 2;

	@Test
	public void simpleDelayNet() throws InterruptedException {
		SimpleDelayPetriNetBuilder builder = new SimpleDelayPetriNetBuilder();
		AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(builder.getNet(), defaultDelay);
		HashMap<Integer, FuzzyToken> inps = new HashMap<>();
		inps.put(2, FuzzyToken.zeroToken());
		long startTime = currentTimeMillis();

		executor.putTokenInInputPlace(inps);
		(new Thread(executor)).start();
		Thread.sleep(defaultDelay * 2);
		executor.stop();
		assertTrue(builder.getTimeStamps().size() == 1);
		long diff = builder.getTimeStamps().get(0) - startTime;
		assertTrue(diff >= defaultDelay);
		assertTrue(diff < defaultDelay * 2);
	}

	@Test
	public void twoLoopNet_delayLoopRunsEveryThick() throws InterruptedException {
		TwoLoopNet builder = new TwoLoopNet();
		AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(builder.getNet(), defaultDelay);
		(new Thread(executor)).start();
		Thread.sleep(defaultDelay * 10);
		executor.stop();
		assertTrue(builder.getTimeStamps().size() >= 9);
		assertFalse(builder.getFiredOuputTransition().contains(builder.t4_out));
		for (int i = 0; i < builder.getTimeStamps().size() - 1; i++) {
			assertTrue(builder.getTimeStamps().get(i + 1) - builder.getTimeStamps().get(i) < defaultDelay + precision);
		}
	}

	@Test
	public void twoLoopNet_reactionHeappensInstatnteniusly() throws InterruptedException {
		TwoLoopNet builder = new TwoLoopNet();
		AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(builder.getNet(), defaultDelay);
		HashMap<Integer, FuzzyToken> inps = new HashMap<>();
		inps.put(builder.p4_inp, FuzzyToken.zeroToken());
		(new Thread(executor)).start();
		Thread.sleep(defaultDelay + precision);
		long putInTime = currentTimeMillis();
		executor.putTokenInInputPlace(inps);
		Thread.sleep(defaultDelay * 2);
		executor.stop();
		int cntr_tr2 = 0;
		int cntr_tr4 = 0;
		int index_Tr4 = -1;
		for (int i = 0; i < builder.getFiredOuputTransition().size(); i++) {
			if (builder.getFiredOuputTransition().get(i) == builder.t2_out) {
				cntr_tr2++;
			}
			if (builder.getFiredOuputTransition().get(i) == builder.t4_out) {
				cntr_tr4++;
				index_Tr4 = i;
			}
		}
		assertTrue(cntr_tr2 == 3);
		assertTrue(cntr_tr4 == 1);
		assertTrue(builder.getTimeStamps().get(index_Tr4) - putInTime < precision);
	}
}
