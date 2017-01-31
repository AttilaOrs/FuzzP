package core.FuzzyPetriLogic.PetriNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import core.FuzzyPetriLogic.BuildableFuzzyPetriNet;
import core.FuzzyPetriLogic.ExecutableFuzzyPetriNet;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Tables.OneXOneTable;

public class FuzzyPetriNet implements BuildableFuzzyPetriNet, ExecutableFuzzyPetriNet {

  private List<Integer> delayForTransition;
  private List<ITable> tableForTransition;
  private List<List<Integer>> fromTransToPlace;
  private List<List<Integer>> fromPlaceToTrans;
  private List<List<Integer>> placesNeededForTrans;
  private List<FuzzyToken> initialMarkingOfThePlaces;
  private transient Map<Integer, List<Consumer<FuzzyToken>>> actionsForOuptutTransirions;
  private List<Boolean> isInputPlaces;
  private List<Boolean> isOutputTransition;
  private Map<Integer, Map<Integer, Double>> weights;

  private int transitionCntr;
  private int placeCntr;

  public FuzzyPetriNet() {
    delayForTransition = new ArrayList<>();
    tableForTransition = new ArrayList<>();
    fromTransToPlace = new ArrayList<>();
    fromPlaceToTrans = new ArrayList<>();
    placesNeededForTrans = new ArrayList<>();
    initialMarkingOfThePlaces = new ArrayList<>();
    actionsForOuptutTransirions = new HashMap<>();
    isInputPlaces = new ArrayList<>();
    isOutputTransition = new ArrayList<>();
    weights = new HashMap<>();
  }

  @Override
  public int addTransition(int delay, ITable table) {
    int toRet = addTransitionWithoutMarking(delay, table);
    isOutputTransition.add(false);
    return toRet;
  }

  private int addTransitionWithoutMarking(int delay, ITable table) {
    delayForTransition.add(delay);
    tableForTransition.add(table);
    fromTransToPlace.add(new ArrayList<>());
    placesNeededForTrans.add(new ArrayList<>());
    return transitionCntr++;

  }

  @Override
  public int addOuputTransition(OneXOneTable table) {
    int trId = addTransitionWithoutMarking(0, table);
    actionsForOuptutTransirions.put(trId, new ArrayList<>());
    isOutputTransition.add(true);
    return trId;
  }

  @Override
  public int addPlace() {
    fromPlaceToTrans.add(new ArrayList<>());
    initialMarkingOfThePlaces.add(new FuzzyToken());
    isInputPlaces.add(false);
    weights.put(placeCntr, new HashMap<>());
    return placeCntr++;
  }

  @Override
  public int addInputPlace() {
    int placeId = addPlace();
    isInputPlaces.set(placeId, true);
    return placeId;
  }

  @Override
  public void addArcFromPlaceToTransition(int place, int transition, double weight) {
    if (fromPlaceToTrans.size() > place) {
      fromPlaceToTrans.get(place).add(transition);
      placesNeededForTrans.get(transition).add(place);
      weights.get(place).put(transition, weight);
    } else {
      throw new RuntimeException(
          "No place with nr " + place + " exists. There are only : " + fromPlaceToTrans.size() + " places");
    }
  }

  @Override
  public void addActionForOuputTransition(int ouputTransitionId, Consumer<FuzzyToken> action) {
    if (!actionsForOuptutTransirions.containsKey(ouputTransitionId)) {
      throw new RuntimeException(
          "T" + ouputTransitionId + " is not an ouput transition. addActionForOuputTransition is not aplicable");
    }
    actionsForOuptutTransirions.get(ouputTransitionId).add(action);
  }

  @Override
  public void addArcFromTransitionToPlace(int transition, int place) {
    if (fromTransToPlace.size() > transition) {
      fromTransToPlace.get(transition).add(place);
    } else {
      throw new RuntimeException("No transition with nr " + transition + " exists.");
    }
  }

  @Override
  public void setInitialMarkingForPlace(int placeID, FuzzyToken token) {
    initialMarkingOfThePlaces.set(placeID, token);
  }

  @Override
  public int getNrOfPlaces() {
    return placeCntr;
  }

  @Override
  public int getNrOfTransition() {
    return transitionCntr;
  }

  @Override
  public boolean isInputPlace(int placeID) {
    return isInputPlaces.get(placeID);
  }

  @Override
  public List<Integer> getPlacesNeededForTransition(int trId) {
    return placesNeededForTrans.get(trId);
  }

  @Override
  public List<Integer> getOutputTransitions() {
    return new ArrayList<>(actionsForOuptutTransirions.keySet());
  }

  @Override
  public int getDelayForTransition(int trId) {
    return delayForTransition.get(trId);
  }

  @Override
  public List<Integer> getOutputPlacesForTransition(int trId) {
    return this.fromTransToPlace.get(trId);
  }

  @Override
  public boolean isOuputTransition(int trId) {
    return actionsForOuptutTransirions.containsKey(trId);
  }

  @Override
  public FuzzyToken getInitialMarkingForPlace(int plId) {
    return initialMarkingOfThePlaces.get(plId);
  }

  @Override
  public List<Consumer<FuzzyToken>> getActionsForOuputTransition(int trId) {
    return actionsForOuptutTransirions.get(trId);
  }

  @Override
  public ITable getTableForTransition(int trId) {
    return tableForTransition.get(trId);
  }

  @Override
  public double getWeigth(int placeId, int trId) {
    return weights.get(placeId).get(trId);
  }

  public List<Integer> getTransitionAfterPlace(int placeId) {
    return fromPlaceToTrans.get(placeId);
  }
}