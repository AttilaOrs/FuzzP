package core.UnifiedPetriLogic;

import java.util.Map;

import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;

public class UnifiedPetriNetCloner {

  public static UnifiedPetriNet cloneUnifiedPetriNet(UnifiedPetriNet source) {
    return (new UnifiedPetriNetCloner(source)).aim;
  }

  public static UnifiedPetriNet cloneUnifiedPetriNetWithModifiedScale(UnifiedPetriNet source,
      Map<Integer, Double> newScale) {
    return (new UnifiedPetriNetCloner(source, newScale, null)).aim;
  }

  public static UnifiedPetriNet cloneUnifiedPetriNetWithModifiedTabler(UnifiedPetriNet source,
      Map<Integer, IUnifiedTable> newTable) {
    return (new UnifiedPetriNetCloner(source, null, newTable)).aim;
  }

  private UnifiedPetriNet source;
  private UnifiedPetriNet aim;
  private Map<Integer, Double> newScale;
  private Map<Integer, IUnifiedTable> newTable;


  private UnifiedPetriNetCloner(UnifiedPetriNet source, Map<Integer, Double> newScale,
      Map<Integer, IUnifiedTable> newTables) {
    this.source = source;
    this.aim = new UnifiedPetriNet();
    this.newScale = newScale;
    this.newTable = newTables;
    createPlaces();
    createTransitions();
    addArcs();
  }

  private UnifiedPetriNetCloner(UnifiedPetriNet source) {
    this(source, null, null);
  }


  private void addArcs() {
    for (int transId = 0; transId < source.getNrOfTransition(); transId++) {
      for (Integer sourcePlace : source.getPlacesNeededForTransition(transId)) {
        aim.addArcFromPlaceToTransition(sourcePlace, transId);
      }
      for (Integer aimPlace : source.getOutputPlacesForTransition(transId)) {
        aim.addArcFromTransitionToPlace(transId, aimPlace);
      }
    }
  }

  private void createTransitions() {
    for (int transId = 0; transId < source.getNrOfTransition(); transId++) {
      if (source.isOuputTransition(transId)) {
        aim.addOuputTransition((UnifiedOneXOneTable) getTable(transId));
      } else if (source.getDelayMultiplierForTransition(transId) != 0.0) {
        aim.addTransitionVariableDelay(source.getDelayMultiplierForTransition(transId),
            source.getTableForTransition(transId).myClone());
      } else {
        aim.addTransition(source.getDelayForTransition(transId), getTable(transId));
      }
    }
  }

  private IUnifiedTable getTable(Integer transId) {
    if (newTable == null || !newTable.containsKey(transId)) {
      return source.getTableForTransition(transId).myClone();
    } else {
      return newTable.get(transId);
    }

  }

  private void createPlaces() {
    for (int placeId = 0; placeId < source.getNrOfPlaces(); placeId++) {

      double scale = source.getScale(placeId);
      if (newScale != null) {
        scale = newScale.get(placeId);
      }
      if (source.isInputPlace(placeId)) {
        aim.addInputPlace(scale);
      } else {
        aim.addPlace(scale);
      }
      aim.setInitialMarkingForPlace(placeId, source.getInitialMarkingForPlace(placeId));
    }
  }


}
