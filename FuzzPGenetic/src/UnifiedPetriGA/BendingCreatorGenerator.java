package UnifiedPetriGA;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import structure.operators.ICreatureGenerator;

public class BendingCreatorGenerator implements ICreatureGenerator<BendingCreature> {

  private int genNr;

  public BendingCreatorGenerator(int genNr) {
    this.genNr = genNr;
  }

  @Override
  public BendingCreature genearteRandomCreature(Random rnd) {
    List<Integer> fuzzyVals = Stream.generate(() -> rnd.nextInt(6)).limit(genNr).collect(Collectors.toList());
    return new BendingCreature(fuzzyVals);
  }

}
