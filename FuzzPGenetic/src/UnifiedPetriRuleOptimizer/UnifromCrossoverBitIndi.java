package UnifiedPetriRuleOptimizer;

import java.util.BitSet;
import java.util.Random;

import structure.operators.ICreatureBreeder;

public class UnifromCrossoverBitIndi implements ICreatureBreeder<BitIndi> {

  private static final double defaultExchangeRate = 0.50;

  private final double exchaneRate;

  public UnifromCrossoverBitIndi(double exchangeRate) {
    this.exchaneRate = exchangeRate;
  }

  public UnifromCrossoverBitIndi() {
    this(defaultExchangeRate);
  }

  @Override
  public BitIndi[] breed(BitIndi mother, BitIndi father, Random rnd) {
    BitSet motherSon =  (BitSet) mother.getGenome().clone();
    BitSet fatherSon =  (BitSet) father.getGenome().clone();

    for (int ruleIndex = 0; ruleIndex < motherSon.size() / 3; ruleIndex++) {
      if (rnd.nextDouble() < exchaneRate) {
        for (int smallIndex = 0; smallIndex < 3; smallIndex++) {
          int curentIndex = ruleIndex * 3 + smallIndex;
          boolean original = motherSon.get(curentIndex);
          motherSon.set(curentIndex, fatherSon.get(curentIndex));
          fatherSon.set(curentIndex, original);
        }
      }
    }

    return new BitIndi[] { new BitIndi(motherSon), new BitIndi(fatherSon) };
  }

}
