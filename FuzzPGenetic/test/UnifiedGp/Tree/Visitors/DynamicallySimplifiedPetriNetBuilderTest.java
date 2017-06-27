package UnifiedGp.Tree.Visitors;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.TestUtils;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;

public class DynamicallySimplifiedPetriNetBuilderTest {

  private ToPetriNet toNet;
  private DynamicallySimplifiedPetriNetBuilder bld;

  @Before
  public void setUp() {
    toNet = new ToPetriNet(TestUtils.simpleScaleProvider(2.0), true);
    bld = new DynamicallySimplifiedPetriNetBuilder();

  }

  @Test
  public void simpleSeq() {
    IInnerNode<NodeType> ss = TestUtils.simpleSeq();
    PetriConversationResult rez = toNet.toNet(ss);
    Set<Integer> fired = new HashSet<>();
    fired.add(0);
    
    IInnerNode<NodeType> newNet = bld.createSimplifiedTree(ss, fired, rez.nodeTransitionMapping.get());

    int count = TestUtils.nodeCount(newNet, node -> node.getType().equals(NodeType.Block));

    assertTrue(count == 1);
  }

  @Test
  public void allSimpleOps_concIsSimplified() {
    IInnerNode<NodeType> ss = TestUtils.allSimpleOps();
    PetriConversationResult rez = toNet.toNet(ss);
    Set<Integer> fired = new HashSet<>();
    fired.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));

    IInnerNode<NodeType> newNet = bld.createSimplifiedTree(ss, fired, rez.nodeTransitionMapping.get());

    int blockCount = TestUtils.nodeCount(newNet, node -> node.getType().equals(NodeType.Block));
    int allCount = TestUtils.nodeCount(newNet, node -> true);
    assertTrue(blockCount == 1);
    assertTrue(allCount == 3);
  }

    
  @Test
  public void allSimpleOps_selectionIsSimplifed() {
    IInnerNode<NodeType> ss = TestUtils.allSimpleOps();
    PetriConversationResult rez = toNet.toNet(ss);
    Set<Integer> fired = new HashSet<>();
    fired.addAll(Arrays.asList(0, 1, 2, 3, 4));

    IInnerNode<NodeType> newNet = bld.createSimplifiedTree(ss, fired, rez.nodeTransitionMapping.get());

    int blockCount = TestUtils.nodeCount(newNet, node -> node.getType().equals(NodeType.Block));
    int allCount = TestUtils.nodeCount(newNet, node -> true);
    assertTrue(blockCount == 1);
    assertTrue(allCount == 7);
  }
    

}
