package core.FuzzyPetriLogic;

import java.util.function.Consumer;

import core.FuzzyPetriLogic.Tables.OneXOneTable;

public interface BuildableFuzzyPetriNet {

  public int addTransition(int delay, ITable table);

  public int addOuputTransition(OneXOneTable table);

  public int addPlace();

  public int addInputPlace();

  public void addArcFromTransitionToPlace(int transition, int place);

  public void addArcFromPlaceToTransition(int place, int transition, double weight);

  public void setInitialMarkingForPlace(int placeID, FuzzyToken token);

  public void addActionForOuputTransition(int ouputTransitionId, Consumer<FuzzyToken> action);
}
