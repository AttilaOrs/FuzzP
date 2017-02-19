package core.UnifiedPetriLogic;

import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;
import core.common.AbstractPetriNetChecker;

public class UnifiedPetriNetChecker
    extends
    AbstractPetriNetChecker<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedOneXTwoTable, UnifiedTwoXOneTable, UnifiedTwoXTwoTable> {

  public UnifiedPetriNetChecker() {
    super(UnifiedOneXOneTable.class, UnifiedOneXTwoTable.class, UnifiedTwoXOneTable.class, UnifiedTwoXTwoTable.class);
  }

}
