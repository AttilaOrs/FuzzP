package UnifiedPetriGA;

import java.util.ArrayList;
import java.util.Random;

import structure.operators.ICreatureMutator;

public class BendingCreatureMutator implements ICreatureMutator<BendingCreature> {
  public static double changeRatio = 0.05;
  private double usedRatio;

  public BendingCreatureMutator() {
    this(changeRatio);
  }

  public BendingCreatureMutator(double ratio) {
    usedRatio = ratio;
  }

  @Override
  public BendingCreature mutate(BendingCreature creature, Random random) {
    ArrayList<Integer> fuzz = new ArrayList<>(creature.gen);
    int nrOfChanhe = (int) (fuzz.size() * usedRatio);
    nrOfChanhe = (nrOfChanhe == 0) ? 1 : nrOfChanhe;
    for (int i = 0; i < nrOfChanhe; i++) {
      int index = random.nextInt(fuzz.size());
      int element = random.nextInt(6);
      fuzz.set(index, element);
    }
    return new BendingCreature(fuzz);
  }

}
