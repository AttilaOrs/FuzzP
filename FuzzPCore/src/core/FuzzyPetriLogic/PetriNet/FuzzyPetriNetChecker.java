package core.FuzzyPetriLogic.PetriNet;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;
import core.common.AbstractPetriNetChecker;

public class FuzzyPetriNetChecker
    extends AbstractPetriNetChecker<FuzzyToken, ITable, OneXOneTable, OneXTwoTable, TwoXOneTable, TwoXTwoTable> {

  public FuzzyPetriNetChecker() {
    super(OneXOneTable.class, OneXTwoTable.class, TwoXOneTable.class, TwoXTwoTable.class);
  }

}
