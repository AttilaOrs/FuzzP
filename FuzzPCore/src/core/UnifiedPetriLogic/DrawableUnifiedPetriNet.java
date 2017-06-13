package core.UnifiedPetriLogic;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;
import structure.DrawableNet;

public class DrawableUnifiedPetriNet implements DrawableNet {
  public static final boolean DEFAULT_DRAW_SCALE = true;


  UnifiedPetriNet myNet;

  private boolean drawScale;

  public DrawableUnifiedPetriNet(UnifiedPetriNet upn) {
    this(upn, DEFAULT_DRAW_SCALE, null);
  }

  TransitionPlaceNameStore nameStore;

  private boolean reprezentInitialMarking;

  public DrawableUnifiedPetriNet(UnifiedPetriNet upn, boolean drawScale, TransitionPlaceNameStore nameStore) {
    myNet = upn;
    this.drawScale = drawScale;
    this.nameStore = nameStore;
  }

  @Override
  public int getNrOfPlaces() {
    return myNet.getNrOfPlaces();
  }

  @Override
  public int getNrOfTransition() {
    return myNet.getNrOfTransition();
  }

  @Override
  public boolean isInputPlace(int placeID) {
    return myNet.isInputPlace(placeID);
  }

  @Override
  public boolean isOuputTransition(int trId) {
    return myNet.isOuputTransition(trId);
  }

  public Stream<DrawableNet.Arc> getTransitionPlaceArcs() {
    return IntStream.range(0, getNrOfTransition()).boxed().flatMap(trId -> myNet.getOutputPlacesForTransition(trId)
        .stream().map(plId -> new DrawableNet.Arc("", plId, trId, false)));
  }

  public Stream<DrawableNet.Arc> getPlaceTransitinArc() {
    return IntStream.range(0, getNrOfPlaces()).boxed().flatMap(placeID -> myNet.getTransitionAfterPlace(placeID)
        .stream()
        .map(trID -> new DrawableNet.Arc("", placeID, trID, true)));

  }
  @Override
  public Stream<Arc> getArcs() {
    return Stream.concat(getTransitionPlaceArcs(), getPlaceTransitinArc());
  }

  @Override
  public String getPlaceName(int placeId) {
    String name = "P" + placeId;
    if (nameStore != null) {
      name = nameStore.getPlaceName(placeId);
    }
    if (drawScale) {
      name += "(" + myNet.getScale(placeId) + ")";
    }
    if (reprezentInitialMarking) {
      name += ((!myNet.getInitialMarkingForPlace(placeId).isPhi()) ? " \u25CF" : "");
    }
    return name;
    }

  @Override
  public String getTransitionName(int transitionId) {
    String name = "T" + transitionId;
    if (nameStore != null) {
      name = nameStore.getTransitionName(transitionId);
    }
    return name + getOpString(transitionId);
  }

  protected String getOpString(int transitionId) {
    IUnifiedTable ww = myNet.getTableForTransition(transitionId);
    String opStr = "";
    if(ww instanceof UnifiedTwoXOneTable  ){
      opStr = " " +((UnifiedTwoXOneTable)ww).getOpertaor().getSign();
    }
    if(ww instanceof UnifiedTwoXTwoTable ) {
      opStr = " " +((UnifiedTwoXTwoTable)ww).getOpertaor().getSign();
    }
    return opStr;
  }

  @Override
  public void setReprezentInitialMarking(boolean reprezentInitialMarking) {
    this.reprezentInitialMarking = reprezentInitialMarking;


  }

  @Override
  public String getPlaceFullText(int placeId) {
    return getPlaceName(placeId) + "\n(" + myNet.getScale(placeId) + ")";
  }

  @Override
  public String getTransitionFullText(int placeId) {
    return getTransitionName(placeId);
  }

}
