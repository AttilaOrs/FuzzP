package structure;

import java.util.stream.Stream;

public interface DrawableNet {

  int getNrOfPlaces();

  int getNrOfTransition();

  boolean isInputPlace(int placeID);

  boolean isOuputTransition(int trId);

  Stream<Arc> getArcs();

  String getPlaceName(int placeId);

  String getTransitionName(int transitionId);

  void setReprezentInitialMarking(boolean reprezentInitialMarking);

  public static class Arc {
    public String label;
    public int placeId;
    public int transitionId;
    public boolean arcFromPlaceToTransition;

    public Arc(String label, int placeId, int transitionId, boolean arcFromPlaceToTransition) {
      this.label = label;
      this.placeId = placeId;
      this.transitionId = transitionId;
      this.arcFromPlaceToTransition = arcFromPlaceToTransition;
    }


  }

}
