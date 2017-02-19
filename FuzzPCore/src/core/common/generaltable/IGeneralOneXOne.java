package core.common.generaltable;

import java.util.Map;

import core.FuzzyPetriLogic.FuzzyValue;

public interface IGeneralOneXOne {

  public Map<FuzzyValue, FuzzyValue> getTable();
}
