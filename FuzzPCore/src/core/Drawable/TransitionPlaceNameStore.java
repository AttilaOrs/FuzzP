package core.Drawable;

import java.util.HashMap;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import core.common.AbstractPetriNet;

/**
 * Created by ors.kilyen on 10/6/2016.
 */
public class TransitionPlaceNameStore {

  private HashMap<String, Integer> transitionNameToId;
  private HashMap<String, Integer> placeNameToId;
  private HashMap<Integer, String> transitionIdToName;
  private HashMap<Integer, String> placeIdToName;

  public TransitionPlaceNameStore() {
    transitionNameToId = new HashMap<>();
    placeNameToId = new HashMap<>();
    transitionIdToName = new HashMap<>();
    placeIdToName = new HashMap<>();
  }

  public void addPlaceName(int placeId, String placeName) {
    placeNameToId.put(placeName, placeId);
    placeIdToName.put(placeId, placeName);
  }

  public void addTransitionName(int transitionId, String transitionName) {
    transitionNameToId.put(transitionName, transitionId);
    transitionIdToName.put(transitionId, transitionName);
  }

  public String getPlaceName(int placeId) {
    return placeIdToName.get(placeId);
  }

  public String getTransitionName(int tansId) {
    return transitionIdToName.get(tansId);
  }

  public int getTransitionId(String transitionName) {
    return transitionNameToId.get(transitionName);
  }

  public int getPlaceId(String placeName) {
    return placeNameToId.get(placeName);
  }

  public boolean isPlaceName(String placeName) {
    return placeNameToId.containsKey(placeName);
  }

  public boolean isTransitionName(String transitionName) {
    return transitionNameToId.containsKey(transitionName);
  }

  public static TransitionPlaceNameStore createOrdinarNames(AbstractPetriNet<?, ?, ?> net) {
    TransitionPlaceNameStore store = new TransitionPlaceNameStore();
    IntStream.range(0, net.getNrOfPlaces()).filter(net::isInputPlace)
        .forEach(id -> store.addPlaceName(id, "iP" + id));
    IntStream.range(0, net.getNrOfPlaces()).filter(((IntPredicate) net::isInputPlace).negate())
        .forEach(id -> store.addPlaceName(id, "P" + id));
    IntStream.range(0, net.getNrOfTransition()).filter(net::isOuputTransition)
        .forEach(trId -> store.addTransitionName(trId, "oT" + trId));
    IntStream.range(0, net.getNrOfTransition()).filter(((IntPredicate) net::isOuputTransition).negate())
        .filter(((IntPredicate) net::hasTransitionDelay).negate())
        .forEach(trId -> store.addTransitionName(trId, "T" + trId));
    IntStream.range(0, net.getNrOfTransition()).filter(((IntPredicate) net::isOuputTransition).negate())
        .filter(tr -> net.getDelayForTransition(tr) > 0)
        .forEach(trId -> store.addTransitionName(trId, "T" + trId + "_d" + net.getDelayForTransition(trId)));
    IntStream.range(0, net.getNrOfTransition()).filter(((IntPredicate) net::isOuputTransition).negate())
        .filter(tr -> net.getDelayMultiplierForTransition(tr) > 0.0)
        .forEach(trId -> store.addTransitionName(trId, "T" + trId + "_vd" + net.getDelayMultiplierForTransition(trId)));
    return store;
  }

}
