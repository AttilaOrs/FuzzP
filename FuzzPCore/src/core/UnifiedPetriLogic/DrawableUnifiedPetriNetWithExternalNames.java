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
    String fullName = getFullName(transitionId);
    if (fullName.length() > 6) {
      return "T" + transitionId + " " + getOpString(transitionId);
    }
    return fullName;
  }

  private String getFullName(int transitionId) {
    return myNameStore.getTransitionName(transitionId) + getOpString(transitionId);
  }

  @Override
  public String getTransitionFullText(int transitionId) {
    return myNameStore.getTransitionName(transitionId) + "\n" + getOpString(transitionId);
  }

}
