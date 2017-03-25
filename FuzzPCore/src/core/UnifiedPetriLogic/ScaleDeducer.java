package core.UnifiedPetriLogic;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ScaleDeducer {

  public static UnifiedPetriNet deduceScale(UnifiedPetriNet original) {
    Map<Integer, Double> rezulted = (new ScaleDeducer(original)).currentIdea.entrySet().stream()
        .map(e -> new AbstractMap.SimpleEntry<Integer, Double>(e.getKey(), e.getValue().scale))
        .collect(toMap(e -> e.getKey(), e -> e.getValue()));
    return UnifiedPetriNetCloner.cloneUnifiedPetriNetWithModifiedScale(original, rezulted);

  }

  public static List<Integer> unscaledPlaces(UnifiedPetriNet petri) {
    return IntStream.range(0, petri.getNrOfPlaces()).filter(plId -> petri.getScale(plId) < 0.0)
        .mapToObj(i -> i).collect(toList());

  }

  private static final int MAX_UPDATE = 40;
  Map<Integer, PlaceScale> currentIdea;
  UnifiedPetriNet net;

  private ScaleDeducer(UnifiedPetriNet net) {
    this.net = net;
    currentIdea = new HashMap<>();
    preparePlaces();
    takeConclusion();
  }

  private void takeConclusion() {
    boolean noChanage = false;
    int maxUpdate = 0;
    while (maxUpdate < MAX_UPDATE && (!noChanage)) {
      noChanage = true;

      for (int transId = 0; transId < net.getNrOfTransition(); transId++) {
        List<Integer> preOfTransition = net.getPlacesNeededForTransition(transId);
        List<Integer> postOfTransition = net.getOutputPlacesForTransition(transId);
        if (allHasClue(preOfTransition)) {
          IUnifiedTable table = net.getTableForTransition(transId);
          List<Double> inpScales = preOfTransition.stream().map(plId -> currentIdea.get(plId).scale).collect(toList());
          List<Double> outputScales = table.deduceScale(inpScales);
          for (int index = 0; index < postOfTransition.size(); index++) {
            // System.out.println(postOfTransition.size() ==
            // outputScales.size());
            boolean thisSame = currentIdea.get(postOfTransition.get(index)).setClueIfNotSame(outputScales.get(index));
            noChanage &= thisSame;
            int currentIdeadUpdated = currentIdea.get(postOfTransition.get(index)).updated;
            maxUpdate = (currentIdeadUpdated > maxUpdate) ? currentIdeadUpdated : maxUpdate;
          }
        }
      }
    }
  }

  private boolean allHasClue(List<Integer> preOfTransition) {
    return !preOfTransition.stream().map(plId -> currentIdea.get(plId)).filter(PlaceScale::hosNoClue).findAny()
        .isPresent();
  }

  private void preparePlaces() {
    for (int placeId = 0; placeId < net.getNrOfPlaces(); placeId++) {
      double scale = net.getScale(placeId);
      if (net.isInputPlace(placeId)) {
        if (scale < 0.0) {
          System.err.println(placeId + " is input place but does not hase scale");
          scale = 0.0;
        }
        currentIdea.put(placeId, new PlaceScale(true, true, scale, 0));
      } else if (scale > 0.0) {
        currentIdea.put(placeId, new PlaceScale(true, true, scale, 0));
      } else if (!net.getInitialMarkingForPlace(placeId).isPhi()) {
        Double initToken = net.getInitialMarkingForPlace(placeId).getValue();
        scale = (scale > initToken) ? scale : initToken;
        currentIdea.put(placeId, new PlaceScale(false, true, scale, 0));
      } else {
        currentIdea.put(placeId, new PlaceScale(false, false, 0.0, 0));
      }
    }
  }

  private static class PlaceScale {
    private static final double EPS = 0.000001;
    Boolean fixed, hasClue;
    Double scale;
    Integer updated;

    public PlaceScale(Boolean fixed, Boolean hasClue, Double scale, Integer updated) {
      this.fixed = fixed;
      this.scale = scale;
      this.updated = updated;
      this.hasClue = hasClue;
    }

    public boolean setClueIfNotSame(Double newClue) {
      if (Math.abs(scale - newClue) > EPS && (!fixed)) {
        scale = newClue;
        updated += 1;
        hasClue = true;
        return false;
      }
      return true;
    }

    public boolean hosNoClue() {
      return !hasClue;
    }
  }

}
