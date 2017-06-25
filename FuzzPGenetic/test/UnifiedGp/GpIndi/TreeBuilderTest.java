package UnifiedGp.GpIndi;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import UnifiedGp.ProblemSpecification;
import UnifiedGp.PrroblemSpecificationImpl;

public class TreeBuilderTest {

  @Test
  public void test() {

    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 10.0);
    inpScale.put(1, 10.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 10.0);
    outScale.put(1, 10.0);
    ProblemSpecification spec = new PrroblemSpecificationImpl(10.0, 3, inpScale, outScale);
    TreeBuilderCongigGeneralImpl g = new TreeBuilderCongigGeneralImpl(spec);

    TreeBuilder bld = new TreeBuilder(g);
    Random rnd = new Random();
    UnifiedGpIndi i = bld.genearteRandomCreature(rnd);
    assertTrue(i.getSizes().depth < 11);
  }

}
