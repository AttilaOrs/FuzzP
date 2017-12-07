package UnifiedPetriRuleOptimizer.PiFitness;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import UnifiedPetriRuleOptimizer.Generator;

public class FirstOrderFitnesTest {

  @Test
  public void test() {
    PiUnifiedPetriMaker maker = new PiUnifiedPetriMaker();
    BitIndiDecoder dec = new BitIndiDecoder(maker.net,
        IntStream.range(0, maker.net.getNrOfTransition()).mapToObj(t -> t).collect(toList()));
    FirstOrderFitnes fitnes = new FirstOrderFitnes(dec, maker.oT26, maker.iP35, maker.iP36);
    Generator gen = new Generator(430);
    double fi = fitnes.evaluate(gen.genearteRandomCreature(new Random()));
    double fii = fitnes.evaluate(gen.genearteRandomCreature(new Random()));
    assertTrue(fi != fii);

  }

}
