package core.common;

import java.util.List;
import java.util.function.Consumer;

public interface ReadableAbstactPetriNet<PetriTokenType, ITableType, OutTable extends ITableType> {

  public int getNrOfPlaces();

  public int getNrOfTransition();

  public boolean isInputPlace(int placeID);

  public List<Integer> getPlacesNeededForTransition(int trId);

  public List<Integer> getOutputTransitions();

  public int getDelayForTransition(int trId);

  public boolean hasTransitionDelay(int trId);

  public List<Integer> getOutputPlacesForTransition(int trId);

  public boolean isOuputTransition(int trId);

  public PetriTokenType getInitialMarkingForPlace(int plId);

  public List<Integer> getTransitionAfterPlace(int placeId);

  public List<Integer> getTransitinsBeforePlace(Integer placeId);

  public List<Consumer<PetriTokenType>> getActionsForOuputTransition(int trId);

  public ITableType getTableForTransition(int trId);

  public Double getDelayMultiplierForTransition(int trId);
}
