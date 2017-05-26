package core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import core.common.generaltable.IGeneralTable;

public abstract class AbstractPetriNet<PetriTokenType, ITableType extends IGeneralTable, OutTable extends ITableType>
    implements ReadableAbstactPetriNet<PetriTokenType, ITableType, OutTable> {

  protected List<Integer> delayForTransition;
  protected List<Double> delayMulitiperForTransition;
  protected List<ITableType> tableForTransition;
  protected List<List<Integer>> fromTransToPlace;
  protected List<List<Integer>> fromPlaceToTrans;
  protected List<List<Integer>> placesNeededForTrans;
  protected List<PetriTokenType> initialMarkingOfThePlaces;
  protected List<Boolean> isInputPlaces;
  protected List<Boolean> isOutputTransition;

  protected int transitionCntr;
  protected int placeCntr;

  protected transient Map<Integer, List<Consumer<PetriTokenType>>> actionsForOuptutTransirions;

  private transient Supplier<PetriTokenType> phiSuplier;

  public AbstractPetriNet(Supplier<PetriTokenType> phiSuplier) {
    this.phiSuplier = phiSuplier;

    delayForTransition = new ArrayList<>();
    tableForTransition = new ArrayList<>();
    fromTransToPlace = new ArrayList<>();
    fromPlaceToTrans = new ArrayList<>();
    placesNeededForTrans = new ArrayList<>();
    initialMarkingOfThePlaces = new ArrayList<>();
    actionsForOuptutTransirions = new HashMap<>();
    isInputPlaces = new ArrayList<>();
    isOutputTransition = new ArrayList<>();
    delayMulitiperForTransition = new ArrayList<>();
  }

  public int addTransition(int delay, ITableType table) {
    int toRet = addTransitionWithoutMarking(delay, table);
    isOutputTransition.add(false);
    delayMulitiperForTransition.add(0.0);
    return toRet;
  }

  public int addTransitionVariableDelay(double delayMulti, ITableType table) {
    int toRet = addTransition(0, table);
    delayMulitiperForTransition.add(delayMulti);
    return toRet;
  }

  protected int addTransitionWithoutMarking(int delay, ITableType table) {
    delayForTransition.add(delay);
    tableForTransition.add(table);
    fromTransToPlace.add(new ArrayList<>());
    placesNeededForTrans.add(new ArrayList<>());
    return transitionCntr++;

  }

  public int addOuputTransition(OutTable table) {
    int trId = addTransitionWithoutMarking(0, table);
    delayMulitiperForTransition.add(0.0);
    actionsForOuptutTransirions.put(trId, new ArrayList<>());
    isOutputTransition.add(true);
    return trId;
  }

  protected int addPlace() {
    fromPlaceToTrans.add(new ArrayList<>());
    initialMarkingOfThePlaces.add(phiSuplier.get());
    isInputPlaces.add(false);
    return placeCntr++;
  }

  public void setInitialMarkingForPlace(int placeID, PetriTokenType token) {
    initialMarkingOfThePlaces.set(placeID, token);
  }

  public void addActionForOuputTransition(int ouputTransitionId, Consumer<PetriTokenType> action) {
    if (!isOuputTransition(ouputTransitionId)) {
      throw new RuntimeException(
          "T" + ouputTransitionId + " is not an ouput transition. addActionForOuputTransition is not aplicable");
    }
    if (actionsForOuptutTransirions == null) {
      actionsForOuptutTransirions = new HashMap<>();
    }
    if (!actionsForOuptutTransirions.containsKey(ouputTransitionId)) {
      actionsForOuptutTransirions.put(ouputTransitionId, new ArrayList<>());
    }
    actionsForOuptutTransirions.get(ouputTransitionId).add(action);
  }

  public void addArcFromTransitionToPlace(int transition, int place) {
    if (fromTransToPlace.size() > transition) {
      fromTransToPlace.get(transition).add(place);
    } else {
      throw new RuntimeException("No transition with nr " + transition + " exists.");
    }
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
  public boolean hasTransitionDelay(int trId) {
    return !(delayForTransition.get(trId) == 0 && delayMulitiperForTransition.get(trId) == 0.0);
  }

  @Override
  public List<Integer> getOutputPlacesForTransition(int trId) {
    return this.fromTransToPlace.get(trId);
  }

  @Override
  public boolean isOuputTransition(int trId) {
    return this.isOutputTransition.get(trId);
  }

  @Override
  public PetriTokenType getInitialMarkingForPlace(int plId) {
    return initialMarkingOfThePlaces.get(plId);
  }

  @Override
  public List<Integer> getTransitionAfterPlace(int placeId) {
    return fromPlaceToTrans.get(placeId);
  }

  @Override
  public List<Integer> getTransitinsBeforePlace(Integer placeId) {
    List<Integer> toRet = new ArrayList<>();
    for (Integer trId = 0; trId < getNrOfTransition(); trId++) {
      List<Integer> outPlaces = getOutputPlacesForTransition(trId);
      if (outPlaces.contains(placeId)) {
        toRet.add(trId);
      }
    }
    return toRet;
  }

  @Override
  public List<Consumer<PetriTokenType>> getActionsForOuputTransition(int trId) {
    return actionsForOuptutTransirions.get(trId);
  }

  @Override
  public ITableType getTableForTransition(int trId) {
    return tableForTransition.get(trId);
  }

  @Override
  public Double getDelayMultiplierForTransition(int trId) {
    return this.delayMulitiperForTransition.get(trId);
  }

}
