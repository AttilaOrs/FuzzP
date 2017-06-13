package structure.operators;

import java.util.Random;

import structure.IGPGreature;

public interface ICreatureMutator<TCreature extends IGPGreature> {

  TCreature mutate(TCreature creature, Random random);

}
