package UnifiedGp.Tree.Visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.InversionLeaf;
import UnifiedGp.Tree.Nodes.MemoryLeaf;
import UnifiedGp.Tree.Nodes.NegateLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
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
    toNet = new ToPetriNet(new ScaleProvider() {

      @Override
      public double defaultScale() {
        return 2.0;
      }

      @Override
      public Double getScaleForInp(int inpNr) {
        return 2.0;
      }

      @Override
      public Double getScaleForOut(int inpNr) {
        return 2.0;
      }

    });
  }

  IInnerNode<NodeType> simpleSeq() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    InnerNode seq = new InnerNode(NodeType.Seq, d1, d2);
    return seq;
  }

  IInnerNode<NodeType> simpleSelec() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    InnerNode seq = new InnerNode(NodeType.Selc, d1, d2);
    return seq;
  }

  IInnerNode<NodeType> simpleConc() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    InnerNode seq = new InnerNode(NodeType.Conc, d1, d2);
    return seq;
  }

  IInnerNode<NodeType> simpleLoop() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    InnerNode seq = new InnerNode(NodeType.Loop, d1, d2);
    return seq;
  }

  IInnerNode<NodeType> complex() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    DelayLeaf d3 = new DelayLeaf(3);
    DelayLeaf d4 = new DelayLeaf(4);
    DelayLeaf d5 = new DelayLeaf(4);
    InnerNode seq = new InnerNode(NodeType.Seq, d1, d2);
    InnerNode select = new InnerNode(NodeType.Selc, d3, d4);
    InnerNode conc = new InnerNode(NodeType.Conc, seq, select);
    InnerNode loop = new InnerNode(NodeType.Loop, conc, d5);
    return loop;
  }

  @Test
  public void seq_test() {
    UnifiedPetriNet ll = toNet.toNet(simpleSeq()).net;
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
    UnifiedPetriNet ll = toNet.toNet(simpleSelec()).net;
    assertTrue(ll.getNrOfPlaces() == 2);
    assertTrue(ll.getNrOfTransition() == 2);
    assertTrue(ll.getTransitinsBeforePlace(0).size() == 0);
    assertTrue(ll.getTransitinsBeforePlace(1).size() == 2);
    assertTrue(ll.getOutputPlacesForTransition(0).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(1).size() == 1);
  }

  @Test
  public void conc_test() {
    UnifiedPetriNet ll = toNet.toNet(simpleConc()).net;
    assertTrue(ll.getNrOfPlaces() == 6);
    assertTrue(ll.getNrOfTransition() == 4);
    assertTrue(ll.getTransitinsBeforePlace(0).size() == 0);
    assertTrue(ll.getTransitinsBeforePlace(1).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(0).size() == 2);
    assertTrue(ll.getPlacesNeededForTransition(1).size() == 2);
  }

  @Test
  public void loop_test() {
    UnifiedPetriNet ll = toNet.toNet(simpleLoop()).net;
    assertTrue(ll.getNrOfPlaces() == 2);
    assertTrue(ll.getNrOfTransition() == 2);
    assertTrue(ll.getTransitinsBeforePlace(0).size() == 1);
    assertTrue(ll.getTransitinsBeforePlace(1).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(0).size() == 1);
    assertTrue(ll.getOutputPlacesForTransition(1).size() == 1);
  }

  @Test
  public void complex_test() {
    UnifiedPetriNet ll = toNet.toNet(complex()).net;
    assertTrue(ll.getNrOfPlaces() == 7);
    assertTrue(ll.getNrOfTransition() == 7);
  }

  private IInnerNode<NodeType> simpleInput() {
    InputLeaf inp = new InputLeaf(InputType.ReaderBlocking, 0);
    DelayLeaf d = new DelayLeaf(0);
    return new InnerNode(NodeType.Seq, inp, d);
  }

  private IInnerNode<NodeType> sameInpuTwoTimes() {
    InputLeaf inp1 = new InputLeaf(InputType.ReaderBlocking, 0);
    InputLeaf inp2 = new InputLeaf(InputType.ReaderBlocking, 0);
    return new InnerNode(NodeType.Seq, inp1, inp2);
  }

  @Test
  public void simpleInput_structureTest() {
    UnifiedPetriNet ll = toNet.toNet(simpleInput()).net;
    assertTrue(ll.getNrOfPlaces() == 5);
    assertTrue(ll.getNrOfTransition() == 3);
    assertEquals(ll.getPlacesNeededForTransition(0), Arrays.asList(3, 0));
    assertTrue(IntStream.range(0, ll.getNrOfPlaces()).filter(i -> ll.isInputPlace(i)).count() == 1);
  }

  @Test
  public void sameInputTwoTimes_structureTest() {
    UnifiedPetriNet ll = toNet.toNet(sameInpuTwoTimes()).net;
    assertTrue(ll.getNrOfPlaces() == 7);
    assertTrue(ll.getNrOfTransition() == 4);
  }

  @Test
  public void simpleInput_behavourTest() {
    PetriConversationResult rez = toNet.toNet(simpleInput());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    exec.setRecorder(fullRec);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    List<IFullRecorderEvent> e = fullRec.eventGroupedByTicks().get(0);
    TickFinsihed<UnifiedToken> tickFinished = (TickFinsihed<UnifiedToken>) e.get(e.size() - 1);
    assertTrue(tickFinished.getPlaceState().get(1).equals(new UnifiedToken(1.0)));
  }

  @Test
  public void sameInputTwoTimes_behaviourTest() {
    PetriConversationResult rez = toNet.toNet(sameInpuTwoTimes());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    exec.setRecorder(fullRec);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    List<IFullRecorderEvent> e = fullRec.eventGroupedByTicks().get(0);
    TickFinsihed<UnifiedToken> tickFinished = (TickFinsihed<UnifiedToken>) e.get(e.size() - 1);
    assertTrue(tickFinished.getPlaceState().get(1).equals(new UnifiedToken(1.0)));
  }

  private IInnerNode<NodeType> simpleInputOuput() {
    InputLeaf inp = new InputLeaf(InputType.ReaderBlocking, 0);
    OutputLeaf d = new OutputLeaf(0, OutType.Copy);
    return new InnerNode(NodeType.Seq, inp, d);
  }

  @Test
  public void simpleInputOuput_structureTest() {
    PetriConversationResult rez = toNet.toNet(simpleInputOuput());
    assertTrue(rez.net.getNrOfPlaces() == 6);
    assertTrue(rez.net.getNrOfTransition() == 4);
    assertTrue(IntStream.range(0, rez.net.getNrOfPlaces()).filter(i -> rez.net.isInputPlace(i)).count() == 1);
    assertTrue(IntStream.range(0, rez.net.getNrOfTransition()).filter(i -> rez.net.isOuputTransition(i)).count() == 1);
  }

  Double sipleInputOuput_behavoirTest = null;
  @Test
  public void sipleInputOuput_behavoirTest() {
    PetriConversationResult rez = toNet.toNet(simpleInputOuput());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), t -> sipleInputOuput_behavoirTest = t.getValue());
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    assertEquals((Double) 1.0, sipleInputOuput_behavoirTest);
  }


  private IInnerNode<NodeType> complexInputOuptNet() {
    InputLeaf inp = new InputLeaf(InputType.ReaderBlocking, 0);
    OutputLeaf o1 = new OutputLeaf(0, OutType.Copy);
    OutputLeaf o2 = new OutputLeaf(0, OutType.Copy);
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    InnerNode seq1 = new InnerNode(NodeType.Seq, d1, o1);
    InnerNode seq2 = new InnerNode(NodeType.Seq, d2, o2);
    InnerNode conc = new InnerNode(NodeType.Conc, seq1, seq2);

    return new InnerNode(NodeType.Seq, inp, conc);
  }

  Double complexInputOuputnet_behavoirTest = null;

  @Test
  public void complexInputOuputnet_behavoirTest() {
    PetriConversationResult rez = toNet.toNet(complexInputOuptNet());

    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), t -> complexInputOuputnet_behavoirTest = t.getValue());
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    assertNull(complexInputOuputnet_behavoirTest);
    inp.clear();
    exec.runTick(inp);
    assertEquals((Double) 1.0, complexInputOuputnet_behavoirTest);
    complexInputOuputnet_behavoirTest = null;
    exec.runTick(inp);
    assertEquals((Double) 1.0, complexInputOuputnet_behavoirTest);
  }

  private IInnerNode<NodeType> blockedNet() {
    BlockLeaf bl = new BlockLeaf();
    OutputLeaf o = new OutputLeaf(0, OutType.Copy);
    return new InnerNode(NodeType.Seq, bl, o);
  }
  
  Double blockedNet_behavoirTest = Double.MIN_VALUE;
  
  @Test
  public void blockedNet_behavoirTest() {
    PetriConversationResult rez = toNet.toNet(blockedNet());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), t -> blockedNet_behavoirTest = t.getValue());
    
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    exec.runTick(inp);
    assertEquals((Double)Double.MIN_VALUE, blockedNet_behavoirTest);
  }
  
  private IInnerNode<NodeType> memoryNet() {
    InputLeaf inp = new InputLeaf(InputType.ReaderBlocking, 0);
    MemoryLeaf mem = new MemoryLeaf(2);
    OutputLeaf o = new OutputLeaf(0,OutType.Copy);
    InnerNode seq1 = new InnerNode(NodeType.Seq, inp, mem);
    InnerNode seq2 = new InnerNode(NodeType.Seq, seq1, o);
    DelayLeaf d = new DelayLeaf(0);
    return new InnerNode(NodeType.Loop, seq2, d);
  }
  
  Double memoryNet_behavoirTest = null;
  
  @Test
  public void memoryNet_behavoirTest() {
    PetriConversationResult rez = toNet.toNet(memoryNet());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), t -> memoryNet_behavoirTest = t.getValue());

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    assertEquals((Double) 0.0, memoryNet_behavoirTest);
    
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(0.5));
    exec.runTick(inp);
    assertEquals((Double) 0.0, memoryNet_behavoirTest);
    
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(0.25));
    exec.runTick(inp);
    assertEquals((Double) 1.0, memoryNet_behavoirTest);
    
    memoryNet_behavoirTest = null;
    
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    assertEquals((Double) 0.5, memoryNet_behavoirTest);
    
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(1.0));
    exec.runTick(inp);
    assertEquals((Double) 0.25, memoryNet_behavoirTest);
  }
  
  private IInnerNode<NodeType> constantNet() {
    ConstantLeaf constantLeaf  = new ConstantLeaf(0.13);
    OutputLeaf o = new OutputLeaf(0,OutType.Copy);
    InnerNode seq1 = new InnerNode(NodeType.Seq, constantLeaf, o);
    DelayLeaf d = new DelayLeaf(1);
    return new InnerNode(NodeType.Loop, seq1, d);
  }
  
  Double constantNet_behavourTest =  null;
  @Test
  public void constantNet_behavourTest(){
    
    PetriConversationResult rez = toNet.toNet(constantNet());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), t -> constantNet_behavourTest = t.getValue());
    
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    
    exec.runTick(inp);
    assertEquals((Double) 0.13, constantNet_behavourTest);
    constantNet_behavourTest = null;
    exec.runTick(inp);
    assertEquals((Double) 0.13, constantNet_behavourTest);
  }
  
  private IInnerNode<NodeType> mathNet() {
    InputLeaf i = new InputLeaf(InputType.ReaderBlocking, 0);
    ConstantLeaf cons = new ConstantLeaf(0.5);
    InnerNode n = new InnerNode(NodeType.Multiply, i, cons);
    OutputLeaf o = new OutputLeaf(0, OutType.Copy);
    InnerNode seq = new InnerNode(NodeType.Seq, n, o);
    ConstantLeaf cons2 = new ConstantLeaf(0.1);
    InnerNode add = new InnerNode(NodeType.Add, seq, cons2);
    OutputLeaf o2 = new OutputLeaf(1, OutType.Copy);
    return new InnerNode(NodeType.Seq, add, o2);
  }
  
  Double mathNet_beahvoiurTest_out1 = null;
  Double mathNet_beahvoiurTest_out2 = null;

  @Test
  public void mathNet_beahvoiurTest() {

    PetriConversationResult rez = toNet.toNet(mathNet());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), t -> mathNet_beahvoiurTest_out1 = t.getValue());
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(1), t -> mathNet_beahvoiurTest_out2 = t.getValue());

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(0.12));
    exec.runTick(inp);
    assertEquals((Double) 0.06, mathNet_beahvoiurTest_out1);
    assertEquals((Double) 0.16, mathNet_beahvoiurTest_out2);
  }
  
  
  private IInnerNode<NodeType> negateNet() {
    InputLeaf l = new InputLeaf(InputType.ReaderBlocking, 0);
    NegateLeaf neg = new NegateLeaf();
    OutputLeaf out = new OutputLeaf(0, OutType.Copy);
    InnerNode node = new InnerNode(NodeType.Seq, l, neg);
    return  new InnerNode(NodeType.Seq, node, out);
  }
  
  Double negate_beahvoiurTest = null;
  
  @Test
  public void negate_beahvoiurTest() {
    PetriConversationResult rez = toNet.toNet(negateNet());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), t -> negate_beahvoiurTest = t.getValue());
    
    
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(0.12));
    exec.runTick(inp);
    
    
    assertTrue( Math.abs(negate_beahvoiurTest + 0.12) < 0.000000001 );
    
  }


  private static IInnerNode<NodeType> inverseNet() {
    InputLeaf l = new InputLeaf(InputType.ReaderBlocking, 0);
    InversionLeaf neg = new InversionLeaf();
    OutputLeaf out = new OutputLeaf(0, OutType.Copy);
    InnerNode node = new InnerNode(NodeType.Seq, l, neg);
    return new InnerNode(NodeType.Seq, node, out);
  }
  
  Double inverseBehavour_test = null;

  @Test
  public void inverseBehavour_test() {

    PetriConversationResult rez = toNet.toNet(inverseNet());
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), t -> inverseBehavour_test = t.getValue());

    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(2.0));
    exec.runTick(inp);

    assertTrue(Math.abs(inverseBehavour_test - 0.5) < 0.00000000001);

  }



}
