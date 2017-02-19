package core.UnifiedPetriLogic;

public interface IUnifiedTable {

  UnifiedToken[] execute(UnifiedToken[] inputs, IContex ct);

  boolean executable(UnifiedToken[] inputs, IContex ct);

}
