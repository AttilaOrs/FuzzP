package UnifiedPetriRuleOptimizer;

import java.util.BitSet;

import structure.GPIndividSize;
import structure.IGPGreature;

public class BitIndi implements IGPGreature {

  private final BitSet myGenome;

  public BitIndi(BitSet set) {
    myGenome = set;
  }

  public BitSet getGenome() {
    return myGenome;
  }

  @Override
  public GPIndividSize getSizes() {
    return new GPIndividSize();
  }

  @Override
  public IGPGreature myClone() {
    return new BitIndi((BitSet) myGenome.clone());
  }

}
