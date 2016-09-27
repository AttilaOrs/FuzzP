package core.FuzzyPetriLogic.Executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import exampleNets.ConcurentPetriNetBuilder;
import exampleNets.SelectionLikeTwoBranchExample;
import exampleNets.SimpleDelayPetriNetBuilder;

public class SynchronousFuzzyPetriExecutorTest {


  @Test
  public void orderIfTransitios() {
    SimpleDelayPetriNetBuilder exampleBuilder = new SimpleDelayPetriNetBuilder();
    SynchronousFuzzyPetriExecutor executor = new SynchronousFuzzyPetriExecutor(exampleBuilder.getNet());
    assertTrue(executor.orderOfTransition.toString().equals("[0, 2, 1]"));
  }

  @Test
  public void simpleDeplayNet_runtest() {
    SimpleDelayPetriNetBuilder exampleBuilder = new SimpleDelayPetriNetBuilder();
    SynchronousFuzzyPetriExecutor executor = new SynchronousFuzzyPetriExecutor(exampleBuilder.getNet());
    HashMap<Integer, FuzzyToken> inps = new HashMap<>();
    inps.put(2, new FuzzyToken(1.0, 0.0, 0.0, 0.0, 0.0));

    executor.runTick(inps);
    assertTrue(exampleBuilder.getFiredOuputTransition().size() == 0);

    executor.runTick(inps);
    assertTrue(exampleBuilder.getFiredOuputTransition().size() == 1);
    assertTrue(exampleBuilder.getTokens().get(0).shortString().equals("<0.00,1.00,0.00,0.00,0.00>"));

    executor.runTick(null);
    assertTrue(exampleBuilder.getFiredOuputTransition().size() == 2);
    assertTrue(exampleBuilder.getTokens().get(0).shortString().equals("<0.00,1.00,0.00,0.00,0.00>"));

    executor.runTick(null);
    assertTrue(exampleBuilder.getFiredOuputTransition().size() == 2);


  }

  @Test
  public void selectionPetriNet_runtest() {
    SelectionLikeTwoBranchExample builder = new SelectionLikeTwoBranchExample();
    SynchronousFuzzyPetriExecutor sim = new SynchronousFuzzyPetriExecutor(builder.getNet());
    HashMap<Integer, FuzzyToken> inp = new HashMap<>();
    inp.put(1, FuzzyToken.zeroToken());
    inp.put(2, new FuzzyToken());

    sim.runTick(inp);
    assertTrue(builder.getFiredOuputTransition().size() == 1);
    assertTrue(builder.getFiredOuputTransition().get(0) == 4); // t4 fired
    assertTrue(builder.getTokens().get(0).shortString().equals("<0.00,0.00,1.00,0.00,0.00>"));
    checkSimState(sim, "<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>", "[0, 0, 0, 0, 0, 0, 1, 0, 0]");

    sim.runTick(null);
    assertTrue(builder.getFiredOuputTransition().size() == 1);
    checkSimState(sim, "<0.00,0.00,1.00,0.00,0.00>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>",
        "[0, 0, 0, 0, 0, 0, 0, 0, 0]");

    inp.put(1, new FuzzyToken());
    inp.put(2, new FuzzyToken(0.0, 1.0, 0.0, 0.0, 0.0));
    sim.runTick(inp);
    assertTrue(builder.getFiredOuputTransition().size() == 2);
    assertTrue(builder.getFiredOuputTransition().get(1) == 5); // t5 fired
    assertTrue(builder.getTokens().get(1).shortString().equals("<0.00,1.00,0.00,0.00,0.00>"));
    checkSimState(sim, "<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>", "[0, 0, 0, 0, 0, 0, 0, 2, 0]");

    sim.runTick(null);
    checkSimState(sim, "<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>", "[0, 0, 0, 0, 0, 0, 0, 1, 0]");
    sim.runTick(null);
    checkSimState(sim, "<0.00,1.00,0.00,0.00,0.00>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>;<phi>",
        "[0, 0, 0, 0, 0, 0, 0, 0, 0]");
  }

  @Test
  public void concurentPetriTest_runtest() {
    ConcurentPetriNetBuilder builder = new ConcurentPetriNetBuilder();
    SynchronousFuzzyPetriExecutor sim = new SynchronousFuzzyPetriExecutor(builder.getNet());

    sim.runTick(null);
    checkSimState(sim, "<phi>;<0.00,0.00,1.00,0.00,0.00>;<0.00,0.00,1.00,0.00,0.00>;<phi>;<phi>;<phi>;<phi>",
        "[0, 0, 0, 0, 0]");

    HashMap<Integer, FuzzyToken> inp = new HashMap<>();
    inp.put(3, FuzzyToken.zeroToken());
    sim.runTick(inp);
    assertTrue(builder.getFiredOuputTransition().size() == 1);
    assertTrue(builder.getFiredOuputTransition().get(0) == 2); // t5 fired
    checkSimState(sim, "<phi>;<phi>;<0.00,0.00,1.00,0.00,0.00>;<phi>;<phi>;<phi>;<phi>", "[0, 0, 0, 1, 0]");

    sim.runTick(null);
    assertTrue(builder.getFiredOuputTransition().size() == 1);
    checkSimState(sim, "<phi>;<0.00,0.00,1.00,0.00,0.00>;<0.00,0.00,1.00,0.00,0.00>;<phi>;<phi>;<phi>;<phi>",
        "[0, 0, 0, 0, 0]");


  }

  public static void checkSimState(SynchronousFuzzyPetriExecutor sim, String state, String delayState) {
    assertEquals(state, makeString(sim.stateOfPlaces));
    assertEquals(delayState, String.valueOf(sim.delayStateOfTransitions));
  }

  public static String makeString(List<FuzzyToken> out) {
    return out.stream().map(ft -> ft.shortString()).collect(Collectors.joining(";"));
  }
}
