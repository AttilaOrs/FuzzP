package core.common.generaltable;

import java.util.List;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyValue;

public interface IGeneralTwoXTwoTable {

  public List<Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>>> getTables();

}
