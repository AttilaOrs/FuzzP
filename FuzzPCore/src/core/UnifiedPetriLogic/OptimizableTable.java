package core.UnifiedPetriLogic;

import java.util.Iterator;
import java.util.stream.Stream;

import core.FuzzyPetriLogic.FuzzyValue;

public interface OptimizableTable {

  Stream<FuzzyValue> getValues();

  IUnifiedTable newTableBasedOnValues(Iterator<FuzzyValue> vals);


}
