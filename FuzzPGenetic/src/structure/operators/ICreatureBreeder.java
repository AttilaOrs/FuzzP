package structure.operators;

import java.util.Random;

import structure.IGPGreature;

public interface ICreatureBreeder<TCreature extends IGPGreature> {

  public TCreature[] breed(TCreature mother, TCreature father, Random rnd);

}
