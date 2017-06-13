package UnifiedPetriGA;

import java.util.ArrayList;
import java.util.Random;

import structure.operators.ICreatureBreeder;

public class BendingBreeder implements ICreatureBreeder<BendingCreature> {

  @Override
  public BendingCreature[] breed(BendingCreature mother, BendingCreature father, Random rnd) {
    int size = mother.gen.size();
    int index = rnd.nextInt(size);
    ArrayList<Integer> motherFirstChildFuzzy = new ArrayList<>(mother.gen.size());
    ArrayList<Integer> fatherFirstChildFuzzy = new ArrayList<>(size);
    motherFirstChildFuzzy.addAll(mother.gen.subList(0, index));
    fatherFirstChildFuzzy.addAll(father.gen.subList(0, index));
    motherFirstChildFuzzy.addAll(father.gen.subList(index, size));
    fatherFirstChildFuzzy.addAll(mother.gen.subList(index, size));
    return new BendingCreature[] { new BendingCreature(motherFirstChildFuzzy),
        new BendingCreature(fatherFirstChildFuzzy) };
  }

}
