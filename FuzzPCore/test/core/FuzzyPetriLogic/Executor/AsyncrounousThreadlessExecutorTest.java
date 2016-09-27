package core.FuzzyPetriLogic.Executor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyToken;
import exampleNets.TwoLoopNet;

public class AsyncrounousThreadlessExecutorTest {

	@Test
	public void twoLoopNet_reactionHeappensInstatnteniusly() throws InterruptedException {
		TwoLoopNet builder = new TwoLoopNet();
		AsyncrounousThreadlessExecutor executor = new AsyncrounousThreadlessExecutor(builder.getNet());
		HashMap<Integer, FuzzyToken> inps = new HashMap<>();
		inps.put(builder.p4_inp, FuzzyToken.zeroToken());
		executor.startNewTick();
		executor.startNewTick();
		assertTrue(builder.getFiredOuputTransition().size() == 1);
		assertTrue(builder.getFiredOuputTransition().contains(builder.t2_out));
		assertFalse(builder.getFiredOuputTransition().contains(builder.t4_out));
		executor.putTokenInInputPlace(inps);
		assertTrue(builder.getFiredOuputTransition().size() == 2);
		assertTrue(builder.getFiredOuputTransition().contains(builder.t4_out));

	}
}
