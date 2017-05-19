package core.UnifiedPetriLogic;

import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.common.ReadableAbstactPetriNet;

public interface ReadableUnifiedPetriNet
    extends ReadableAbstactPetriNet<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable> {

  public double getScale(int placeId);

  public IContex getContextForTransition(int trId);
}
