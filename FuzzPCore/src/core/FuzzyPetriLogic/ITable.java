package core.FuzzyPetriLogic;

import java.util.stream.Stream;

import core.common.generaltable.IGeneralTable;

public interface ITable extends IGeneralTable {
  public FuzzyToken[] execute(FuzzyToken[] inps);

  public boolean executable(FuzzyToken[] inps);

  @Override
  public boolean maybeExecutable(boolean[] inps);

  public Stream<FuzzyValue> cellsOneByOne();

  static void inpCheck(FuzzyToken[] inps, int i) {
    if (inps.length != i) {
      throw new RuntimeException("Wrong number of input");
    }
  }

  static void inpCheck(boolean[] inps, int i) {
    if (inps.length != i) {
      throw new RuntimeException("Wrong number of input");
    }
  }
}
