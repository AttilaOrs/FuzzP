package core.common.generaltable;

import java.util.Map;

import core.FuzzyPetriLogic.FuzzyValue;

public interface IGeneralTwoXOneTable {

  public Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> getTable();
}
