package UnifiedGp.Tree.Visitors;

import static UnifiedGp.TestUtils.countNode;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.NodeType;

public class StaticSimplifierPetriBuilderTest {

  @Test
  public void test() {
    InnerNode i = new InnerNode(NodeType.Seq, new DelayLeaf(1), new BlockLeaf());
    InnerNode ii = new InnerNode(NodeType.Seq, i, new BlockLeaf());

    StaticSimplifierPetriBuilder staticSimplifier = new StaticSimplifierPetriBuilder();
    IInnerNode<NodeType> rez = staticSimplifier.simplifyTree(ii);

    assertThat(" Shoud have one blockinf node", countNode(rez, mm -> true) == 3);


  }

}
