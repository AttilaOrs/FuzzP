package core.UnifiedPetriLogic.tables;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.UnifiedPetriLogic.IUnifiedTable;

public abstract class AbstractTable implements IUnifiedTable {
  private transient FuzzyDriver defaultDriver;

  protected FuzzyDriver defaultDriver() {
    if (defaultDriver == null) {
      defaultDriver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);
    }
    return defaultDriver;

  }

}
