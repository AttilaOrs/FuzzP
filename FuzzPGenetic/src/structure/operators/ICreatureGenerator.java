package structure.operators;

import java.util.Random;

public interface ICreatureGenerator<TCreature> {

	TCreature genearteRandomCreature(Random rnd);

}
