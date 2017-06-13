package UnifiedPetriGA;

import java.util.List;

import structure.GPIndividSize;
import structure.IGPGreature;

public class BendingCreature implements IGPGreature {
  public final List<Integer> gen;

  public BendingCreature(List<Integer> gen) {
    this.gen = gen;
  }

  @Override
  public GPIndividSize getSizes() {
    return new GPIndividSize();
  }

}
