package UnifiedPetriRuleOptimizer.PiFitness;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import UnifiedPetriRuleOptimizer.Generator;

public class FirstOrderFitnesTest {

  @Test
  public void test() {
    FirstOrderFitnes fitnes = new FirstOrderFitnes();
    Generator gen = new Generator(430);
    double fi = fitnes.evaluate(gen.genearteRandomCreature(new Random()));
    double fii = fitnes.evaluate(gen.genearteRandomCreature(new Random()));
    assertTrue(fi != fii);

  }

}
