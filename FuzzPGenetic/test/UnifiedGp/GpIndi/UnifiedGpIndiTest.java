package UnifiedGp.GpIndi;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.NodeType;
import structure.GPIndividSize;

public class UnifiedGpIndiTest {

  @Test
  public void size_test() {
    InputLeaf l = new InputLeaf(InputType.ReaderBlocking, 0);
    InputLeaf l2 = new InputLeaf(InputType.ReaderBlocking, 0);
    InputLeaf l3 = new InputLeaf(InputType.ReaderBlocking, 0);
    InnerNode seq = new InnerNode(NodeType.Seq, l, l2);
    InnerNode seq2 = new InnerNode(NodeType.Add, l3, seq);
    UnifiedGpIndi indi = new UnifiedGpIndi(seq2);

    GPIndividSize size = indi.getSizes();

    assertTrue(size.leafs == 3);
    assertTrue(size.depth == 2);
    assertTrue(size.ops == 2);
  }

}
