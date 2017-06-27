package UnifiedGpProblmes.FirstOrderSystem;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;

public class FirstOrderFitnesTest {

  @Test
  public void test() {
    FirstOrderFitnes f = new FirstOrderFitnes();
    UnifiedGpIndi creature = new UnifiedGpIndi(createConstantNet(0.1));
    double i = f.evaluate(creature);

    UnifiedGpIndi creature2 = new UnifiedGpIndi(createConstantNet(0.9));
    double i2 = f.evaluate(creature2);

    assertTrue(i < i2);


  }

  public static IInnerNode<NodeType> createConstantNet(double value) {
    ConstantLeaf c = new ConstantLeaf(value);
    OutputLeaf o = new OutputLeaf(0, OutType.Copy);
    DelayLeaf d = new DelayLeaf(1);
    InnerNode i = new InnerNode(NodeType.Seq, c, o);
    return new InnerNode(NodeType.Loop, i, d);
  }

}
