package core.common.generaltable;

import java.util.List;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyValue;

public interface IGeneralOneXTwoTable {

  public List<Map<FuzzyValue, FuzzyValue>> getTables();
}
