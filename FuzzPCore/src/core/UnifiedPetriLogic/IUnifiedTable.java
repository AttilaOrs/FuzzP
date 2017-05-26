package core.UnifiedPetriLogic;

import java.util.List;

import core.common.generaltable.IGeneralTable;

public interface IUnifiedTable extends IGeneralTable {

  UnifiedToken[] execute(UnifiedToken[] inputs, IContex ct);

  boolean executable(UnifiedToken[] inputs, IContex ct);

  IUnifiedTable myClone();

  List<Double> deduceScale(List<Double> inpScales);

  @Override
  boolean maybeExecutable(boolean[] ar);


}
