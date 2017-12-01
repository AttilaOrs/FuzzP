package UnifiedPetriRuleOptimizer;

import java.util.BitSet;
import java.util.Random;

import structure.operators.ICreatureGenerator;

public class Generator implements ICreatureGenerator<BitIndi> {

  private long nrOfRules;

  public Generator(long nrOfRules) {
    this.nrOfRules = nrOfRules;
  }

  @Override
  public BitIndi genearteRandomCreature(Random rnd) {
    BitSet bits = new BitSet((int) (nrOfRules * 3));
    for (int x = 0; x < nrOfRules; x++) {
      int i = rnd.nextInt(5);
      bits.set(x * 3, bitOf(i, 0));
      bits.set(x * 3 + 1, bitOf(i, 1));
      bits.set(x * 3 + 2, bitOf(i, 2));
    }
    return new BitIndi(bits);
  }

  boolean bitOf(int nr, int bits) {
    return (nr >> bits) % 2 == 0;
  }

}
