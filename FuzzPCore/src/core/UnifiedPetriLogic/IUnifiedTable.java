package core.UnifiedPetriLogic;

import java.util.List;

public interface IUnifiedTable {

  UnifiedToken[] execute(UnifiedToken[] inputs, IContex ct);

  boolean executable(UnifiedToken[] inputs, IContex ct);

  IUnifiedTable myClone();

  List<Double> deduceScale(List<Double> inpScales);


}
