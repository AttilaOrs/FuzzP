package UnifiedPetriRuleOptimizer;

import static UnifiedPetriRuleOptimizer.Generator.bitOf;

import java.util.BitSet;
import java.util.Random;

import structure.operators.ICreatureMutator;

public class Mutator implements ICreatureMutator<BitIndi> {

  private static double mutPercent = 0.05;

  @Override
  public BitIndi mutate(BitIndi creature, Random random) {
    BitSet w = (BitSet) creature.getGenome().clone();
    double nrOfMutations = w.size() / 3 * mutPercent;
    nrOfMutations = (nrOfMutations == 0) ? 1 : nrOfMutations;
    for (int i = 0; i < nrOfMutations; i++) {
      int index = random.nextInt(w.size() / 3);
      int fuzzyRule = random.nextInt(5);
      w.set(index * 3, bitOf(fuzzyRule, 0));
      w.set(index * 3 + 1, bitOf(fuzzyRule, 1));
      w.set(index * 3 + 2, bitOf(fuzzyRule, 2));
    }

    return new BitIndi(w);
  }

}
