package core.FuzzyPetriLogic;

import java.util.List;
import java.util.function.Consumer;

public interface ExecutableFuzzyPetriNet {

  int getNrOfPlaces();

  int getNrOfTransition();

  List<Integer> getPlacesNeededForTransition(int trId);

  List<Integer> getOutputPlacesForTransition(int trId);

  boolean isInputPlace(int placeID);

  boolean isOuputTransition(int trId);

  List<Integer> getOutputTransitions();

  int getDelayForTransition(int trId);

  FuzzyToken getInitialMarkingForPlace(int plId);

  List<Consumer<FuzzyToken>> getActionsForOuputTransition(int trId);

  ITable getTableForTransition(int trId);

  double getWeigth(int placeId, int trId);

}
