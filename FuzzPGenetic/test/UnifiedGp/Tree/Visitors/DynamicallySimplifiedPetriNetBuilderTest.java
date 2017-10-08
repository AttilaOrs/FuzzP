package UnifiedGp.Tree.Visitors;

import static UnifiedGp.TestUtils.countNode;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.TestUtils;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
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

    int count = countNode(newNet, node -> node.getType().equals(NodeType.Block));

    assertTrue(count == 1);
  }

  @Test
  public void allSimpleOps_concIsSimplified() {
    IInnerNode<NodeType> ss = TestUtils.allSimpleOps();
    PetriConversationResult rez = toNet.toNet(ss);
    Set<Integer> fired = new HashSet<>();
    fired.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));

    IInnerNode<NodeType> newNet = bld.createSimplifiedTree(ss, fired, rez.nodeTransitionMapping.get());

    int blockCount = countNode(newNet, node -> node.getType().equals(NodeType.Block));
    int allCount = countNode(newNet, node -> true);
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

    int blockCount = countNode(newNet, node -> node.getType().equals(NodeType.Block));
    int allCount = countNode(newNet, node -> true);
    assertTrue(blockCount == 1);
    assertTrue(allCount == 7);
  }

  @Test
  public void ConcToSeqIfBlock() {
    IInnerNode<NodeType> root = new InnerNode(NodeType.Conc, new DelayLeaf(1), new DelayLeaf(2));
    PetriConversationResult rez = toNet.toNet(root);
    Set<Integer> fired = new HashSet<>();
    fired.addAll(Arrays.asList(0, 1, 2));

    IInnerNode<NodeType> newNet = bld.createSimplifiedTree(root, fired, rez.nodeTransitionMapping.get());

    int blockCount = countNode(newNet, node -> node.getType().isBlock());
    assertTrue(blockCount == 1);

    int seqCount = countNode(newNet, node -> node.getType().equals(NodeType.Seq));
    assertTrue(seqCount == 1);

    int concCntr = countNode(newNet, node -> node.getType().equals(NodeType.Conc));
    assertTrue(concCntr == 0);

  }
    

}
