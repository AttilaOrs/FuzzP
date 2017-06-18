package UnifiedGp.Tree.Visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.ScaleProvider;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.Operator;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import core.common.recoder.fullrecorderevents.IFullRecorderEvent;
import core.common.recoder.fullrecorderevents.TickFinsihed;

public class ToPetriNetTest {

  private ToPetriNet toNet;

  @Before
  public void before() {
    toNet = new ToPetriNet(new ScaleProvider());
  }

  IInnerNode<NodeType> simpleSeq() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    Operator seq = new Operator(NodeType.Seq, d1, d2);
    return seq;
  }

  IInnerNode<NodeType> simpleSelec() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    Operator seq = new Operator(NodeType.Selc, d1, d2);
    return seq;
  }

  IInnerNode<NodeType> simpleConc() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    Operator seq = new Operator(NodeType.Conc, d1, d2);
    return seq;
  }

  IInnerNode<NodeType> simpleLoop() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    Operator seq = new Operator(NodeType.Loop, d1, d2);
    return seq;
  }

  IInnerNode<NodeType> complex() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    DelayLeaf d3 = new DelayLeaf(3);
    DelayLeaf d4 = new DelayLeaf(4);
    DelayLeaf d5 = new DelayLeaf(4);
    Operator seq = new Operator(NodeType.Seq, d1, d2);
    Operator select = new Operator(NodeType.Selc, d3, d4);
    Operator conc = new Operator(NodeType.Conc, seq, select);
    Operator loop = new Operator(NodeType.Loop, conc, d5);
    return loop;
  }

  @Test
  public void seq_test() {
    UnifiedPetriNet ll = toNet.toNet(simpleSeq());
    assertTrue(ll.getNrOfPlaces() == 3);
    assertTrue(ll.getNrOfTransition() == 2);
    assertTrue(ll.getTransitinsBeforePlace(0).size() == 0);
    assertTrue(ll.getTransitinsBeforePlace(1).size() == 1);
    assertTrue(ll.getTransitinsBeforePlace(2).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(0).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(1).size() == 1);
  }

  @Test
  public void selct_tes() {
    UnifiedPetriNet ll = toNet.toNet(simpleSelec());
    assertTrue(ll.getNrOfPlaces() == 2);
    assertTrue(ll.getNrOfTransition() == 2);
    assertTrue(ll.getTransitinsBeforePlace(0).size() == 0);
    assertTrue(ll.getTransitinsBeforePlace(1).size() == 2);
    assertTrue(ll.getOutputPlacesForTransition(0).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(1).size() == 1);
  }

  @Test
  public void conc_test() {
    UnifiedPetriNet ll = toNet.toNet(simpleConc());
    assertTrue(ll.getNrOfPlaces() == 6);
    assertTrue(ll.getNrOfTransition() == 4);
    assertTrue(ll.getTransitinsBeforePlace(0).size() == 0);
    assertTrue(ll.getTransitinsBeforePlace(1).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(0).size() == 2);
    assertTrue(ll.getPlacesNeededForTransition(1).size() == 2);
  }

  @Test
  public void loop_test() {
    UnifiedPetriNet ll = toNet.toNet(simpleLoop());
    assertTrue(ll.getNrOfPlaces() == 2);
    assertTrue(ll.getNrOfTransition() == 2);
    assertTrue(ll.getTransitinsBeforePlace(0).size() == 1);
    assertTrue(ll.getTransitinsBeforePlace(1).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(0).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(1).size() == 1);
  }

  @Test
  public void complex_test() {
    UnifiedPetriNet ll = toNet.toNet(complex());
    assertTrue(ll.getNrOfPlaces() == 7);
    assertTrue(ll.getNrOfTransition() == 7);
  }

  private IInnerNode<NodeType> simpleInput() {
    InputLeaf inp = new InputLeaf(InputType.ReaderBlocking, 0);
    DelayLeaf d = new DelayLeaf(0);
    return new Operator(NodeType.Seq, inp, d);
  }

  private IInnerNode<NodeType> sameInpuTwoTimes() {
    InputLeaf inp1 = new InputLeaf(InputType.ReaderBlocking, 0);
    InputLeaf inp2 = new InputLeaf(InputType.ReaderBlocking, 0);
    return new Operator(NodeType.Seq, inp1, inp2);
  }

  @Test
  public void simpleInput_structureTest() {
    UnifiedPetriNet ll = toNet.toNet(simpleInput());
    assertTrue(ll.getNrOfPlaces() == 5);
    assertTrue(ll.getNrOfTransition() == 3);
    assertEquals(ll.getPlacesNeededForTransition(0), Arrays.asList(3, 0));
    assertTrue(IntStream.range(0, ll.getNrOfPlaces()).filter(i -> ll.isInputPlace(i)).count() == 1);
  }

  @Test
  public void sameInputTwoTimes_structureTest() {
    UnifiedPetriNet ll = toNet.toNet(sameInpuTwoTimes());
    assertTrue(ll.getNrOfPlaces() == 7);
    assertTrue(ll.getNrOfTransition() == 4);
  }

  @Test
  public void simpleInput_behavourTest() {
    UnifiedPetriNet ll = toNet.toNet(simpleInput());
    Map<Integer, Integer> nameMap = toNet.getInpNrToINpPlace();
    ll.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(ll);
    exec.setRecorder(fullRec);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(nameMap.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    List<IFullRecorderEvent> e = fullRec.eventGroupedByTicks().get(0);
    TickFinsihed<UnifiedToken> tickFinished = (TickFinsihed<UnifiedToken>) e.get(e.size() - 1);
    assertTrue(tickFinished.getPlaceState().get(1).equals(new UnifiedToken(1.0)));
  }

  @Test
  public void sameInputTwoTimes_behaviourTest() {
    UnifiedPetriNet ll = toNet.toNet(sameInpuTwoTimes());
    Map<Integer, Integer> nameMap = toNet.getInpNrToINpPlace();
    ll.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(ll);
    exec.setRecorder(fullRec);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(nameMap.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    List<IFullRecorderEvent> e = fullRec.eventGroupedByTicks().get(0);
    TickFinsihed<UnifiedToken> tickFinished = (TickFinsihed<UnifiedToken>) e.get(e.size() - 1);
    assertTrue(tickFinished.getPlaceState().get(1).equals(new UnifiedToken(1.0)));
  }

}
