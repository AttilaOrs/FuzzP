package UnifiedGp.GpIndi;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.MemoryLeaf;
import UnifiedGp.Tree.Nodes.NegateLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;

public class UnifromCrossOverTest {

  UnifiedGpIndi createIndiOne() {
    InnerNode seq = new InnerNode(NodeType.Seq, new DelayLeaf(2), new OutputLeaf(0, OutType.Copy));
    InnerNode mult = new InnerNode(NodeType.Multiply, new ConstantLeaf(2.0), new NegateLeaf());
    InnerNode root = new InnerNode(NodeType.Loop, seq, mult);
    return new UnifiedGpIndi(root);
  }

  UnifiedGpIndi createIndiTwo() {
    InnerNode split = new InnerNode(NodeType.PosNegSplit, new InputLeaf(InputType.EnableIfPhi, 1), new MemoryLeaf(3));
    InnerNode conc = new InnerNode(NodeType.Conc, split, new BlockLeaf());
    InnerNode add = new InnerNode(NodeType.Add, conc, new OutputLeaf(2, OutType.Copy));
    return new UnifiedGpIndi(add);
  }

  @Test
  public void test() {
    UnifromCrossOver cr = new UnifromCrossOver(false, 50);
    String[] comps1 = createIndiOne().getRoot().toString().split(" ");
    String[] comps2 = createIndiTwo().getRoot().toString().split(" ");

    UnifiedGpIndi[] rez = cr.breed(createIndiOne(), createIndiTwo(), new Random());
    String all = rez[0].getRoot().toString() + rez[1].getRoot().toString();
    for (String comp : comps1) {
      assertTrue(all.contains(comp));
    }
    for (String comp : comps2) {
      assertTrue(all.contains(comp));
    }
  }

}
