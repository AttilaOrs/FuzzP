package core.UnifiedPetriLogic;

import core.Drawable.TransitionPlaceNameStore;

public class DrawableUnifiedPetriNetWithExternalNames extends DrawableUnifiedPetriNet {
  TransitionPlaceNameStore myNameStore;

  public DrawableUnifiedPetriNetWithExternalNames(UnifiedPetriNet upn, TransitionPlaceNameStore store) {
    super(upn);
    myNameStore = store;
  }

  @Override
  public String getPlaceName(int placeId) {
    return myNameStore.getPlaceName(placeId);
  }
  
  @Override
  public String getTransitionName(int transitionId) {
    return myNameStore.getTransitionName(transitionId) + getOpString(transitionId);
  }

}
