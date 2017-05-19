package core.FuzzyPetriLogic;

import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.common.ReadableAbstactPetriNet;

public interface ReadableFuzzyPetriNet extends ReadableAbstactPetriNet<FuzzyToken, ITable, OneXOneTable> {

  public double getWeigth(int placeId, int trId);
}
