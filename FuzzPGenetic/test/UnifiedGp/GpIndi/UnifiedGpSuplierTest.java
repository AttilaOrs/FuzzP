package UnifiedGp.GpIndi;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.TestUtils;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilder;

public class UnifiedGpSuplierTest {

  @Test
  public void test() {

    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 10.0);
    inpScale.put(1, 10.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 10.0);
    outScale.put(1, 10.0);
    ProblemSpecification spec = new ProblemSpecificationImpl(10.0, 3, inpScale, outScale);
    TreeBuilderCongigGeneralImpl g = new TreeBuilderCongigGeneralImpl(spec);

    TreeBuilder<NodeType> bld = new TreeBuilder<>(g);
    UnifiedGpSuplier sup = new UnifiedGpSuplier(bld);

    Random rnd = new Random();
    int sumDepth = 0;
    int maxDepth = 0;
    int hasLoop = 0;

    for (int i = 0; i < 1000; i++) {
      UnifiedGpIndi indi = sup.genearteRandomCreature(rnd);
      int curentDepth = indi.getSizes().depth ;
      sumDepth += curentDepth;
      maxDepth = (maxDepth < curentDepth)? curentDepth : maxDepth;
      int loopCount = TestUtils.nodeCount(indi.root, (node) -> node.getType().equals(NodeType.PosNegSplit));
      hasLoop += (loopCount > 0) ? 1 : 0;
    }
    
    assertTrue(hasLoop > 300);
    assertTrue((sumDepth / 1000.0) > 4.5);
    assertTrue(maxDepth == 10);
  }


}
