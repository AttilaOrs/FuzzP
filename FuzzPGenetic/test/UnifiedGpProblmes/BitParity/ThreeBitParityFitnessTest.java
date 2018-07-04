package UnifiedGpProblmes.BitParity;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;

public class ThreeBitParityFitnessTest {
  
  @Test
  public void test() {
    ThreeBitParityFitness fit = new ThreeBitParityFitness(false, false, false);
    UnifiedGpIndi creature = new UnifiedGpIndi(createSimpleRoot());
    double rez = fit.evaluate(creature);
    creature = new UnifiedGpIndi(createSimpleRootTwo());
    double rez2 = fit.evaluate(creature);
    assertTrue(rez+rez2 ==ThreeBitParityFitness.nums.length);
    
  }

  private IInnerNode<NodeType> createSimpleRoot() {
    return new InnerNode(NodeType.Loop, new DelayLeaf(0), new OutputLeaf(0, OutType.Copy));
  }

  private IInnerNode<NodeType> createSimpleRootTwo() {
    return new InnerNode(NodeType.Loop, new DelayLeaf(0), new DelayLeaf(1));
  }
}
