package core.UnifiedPetriLogic;

import java.util.List;
import java.util.function.Consumer;

public interface ReadableUnifiedPetriNet {

  public int getNrOfPlaces();

  public int getNrOfTransition();

  public boolean isInputPlace(int placeID);

  public List<Integer> getPlacesNeededForTransition(int trId);

  public List<Integer> getOutputTransitions();

  public int getDelayForTransition(int trId);

  public boolean hasTransitionDelay(int trId);

  public List<Integer> getOutputPlacesForTransition(int trId);

  public boolean isOuputTransition(int trId);

  public UnifiedToken getInitialMarkingForPlace(int plId);

  public List<Integer> getTransitionAfterPlace(int placeId);

  public List<Integer> getTransitinsBeforePlace(Integer placeId);

  public List<Consumer<UnifiedToken>> getActionsForOuputTransition(int trId);

  public IUnifiedTable getTableForTransition(int trId);

  public Double getDelayMultiplierForTransition(int trId);

  public double getScale(int placeId);
}
