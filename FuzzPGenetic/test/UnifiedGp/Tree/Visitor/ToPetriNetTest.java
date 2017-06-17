package UnifiedGp.Tree.Visitor;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.ScaleProvider;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.Operator;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import core.UnifiedPetriLogic.UnifiedPetriNet;

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

}
