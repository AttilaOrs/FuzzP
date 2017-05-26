package core.common;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.common.generaltable.IGeneralTable;
import core.common.recoder.FullRecordable;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;

public abstract class AbstractExecutor<TTokenType extends FullRecordable<TTokenType>, TTableType extends IGeneralTable, TOutTable extends TTableType, TPetriNet extends ReadableAbstactPetriNet<TTokenType, TTableType, TOutTable>> {

  public static final boolean DEFALT_EANBLE_CACHE = true;
  public static boolean OLD_VERSION = false; // left here only for measuremnt

  private final boolean enableCache;

  protected TPetriNet myNet;
  public List<TTokenType> stateOfPlaces;
  public List<Integer> delayStateOfTransitions;
  protected List<TTokenType[]> valueHoldInTransitions;
  public List<Integer> orderOfTransition;
  private Map<StateCacheEntry, List<Integer>> cache;

  protected IGeneralPetriBehavoiurRecorder<TTokenType> recorder;

  public AbstractExecutor(TPetriNet net, boolean enablechecking, boolean enableChache) {
		this.myNet = net;
		intilazeOrder();
		resetSimulator();
    recorder = IGeneralPetriBehavoiurRecorder.empty();
		if (enablechecking) {
      checkPetriNet();
		}
    this.enableCache = enableChache;
    if (enableChache) {
      cache = new HashMap<>();
    }
	}
  public AbstractExecutor(TPetriNet net, boolean enablechecking) {
    this(net, enablechecking, DEFALT_EANBLE_CACHE);
	}

  public TPetriNet getNet() {
    return myNet;
  }

  public void setRecorder(IGeneralPetriBehavoiurRecorder<TTokenType> rec) {
    this.recorder = rec;
  }

  protected abstract void checkPetriNet();

  protected abstract TTokenType[] emptyTokenArray();

  protected abstract TTokenType[] tokenArray(int size);

  protected abstract TTokenType phi();

  protected abstract boolean executable(TTokenType[] inps, int trId);

  public void resetSimulator() {
    stateOfPlaces = IntStream.range(0, myNet.getNrOfPlaces())
        .mapToObj(plId -> myNet.getInitialMarkingForPlace(plId).myClone()).collect(toList());
    delayStateOfTransitions = Stream.generate(() -> 0).limit(myNet.getNrOfTransition()).collect(toList());
    valueHoldInTransitions = Stream.generate(() -> emptyTokenArray()).limit(myNet.getNrOfTransition())
        .collect(toList());

  }

  private void intilazeOrder() {
    orderOfTransition = new ArrayList<>();
    List<Integer> trWithInputs = IntStream.range(0, myNet.getNrOfTransition())
        .filter(trID -> myNet.getPlacesNeededForTransition(trID).stream()
            .filter(plId -> myNet.isInputPlace(plId)).findAny().isPresent())
        .boxed().collect(toList());

    orderOfTransition.addAll(trWithInputs);

    List<Integer> trWithOutputs = myNet.getOutputTransitions().stream()
        .filter(trId -> !orderOfTransition.contains(trId)).collect(toList());
    orderOfTransition.addAll(trWithOutputs);

    List<Integer> trWithoutDelays = IntStream.range(0, myNet.getNrOfTransition())
        .filter(trId -> !orderOfTransition.contains(trId))
        .filter(trId -> !myNet.hasTransitionDelay(trId)).boxed().collect(toList());
    orderOfTransition.addAll(trWithoutDelays);

    List<Integer> trWithFixDelays = IntStream.range(0, myNet.getNrOfTransition())
        .filter(trId -> !orderOfTransition.contains(trId))
        .filter(trId -> myNet.getDelayForTransition(trId) != 0).boxed().sorted((trId, trId2) -> Integer
            .compare(myNet.getDelayForTransition(trId), myNet.getDelayForTransition(trId2)))
        .collect(toList());
    orderOfTransition.addAll(trWithFixDelays);

    List<Integer> trWithVariableDelay = IntStream.range(0, myNet.getNrOfTransition())
        .filter(trId -> !orderOfTransition.contains(trId))
        .filter(trId -> myNet.getDelayMultiplierForTransition(trId) != 0.0).boxed().sorted((trId, trId2) -> Double
            .compare(myNet.getDelayMultiplierForTransition(trId), myNet.getDelayMultiplierForTransition(trId2)))
        .collect(toList());
    orderOfTransition.addAll(trWithVariableDelay);

  }

  private void executeFirableTransitionsOld() {
    int loopCntr = 0; // overcome infinite loop in the system
    boolean happendSomthing = true;
    ArrayList<Integer> currentOrderOfTransitions = new ArrayList<>();
    currentOrderOfTransitions.addAll(orderOfTransition);
    while (happendSomthing && loopCntr < orderOfTransition.size() * 2) {
      happendSomthing = false;
      loopCntr++;
      for (int trListIndex = 0; trListIndex < currentOrderOfTransitions.size(); trListIndex++) {
        int currentTrans = currentOrderOfTransitions.get(trListIndex);
        if (fireable(currentTrans)) {
          happendSomthing = true;
          // overcame allways executable transitions
          currentOrderOfTransitions.remove(trListIndex);
          currentOrderOfTransitions.add(currentTrans);
          startFire(currentTrans);
          break;
        }
      }
    }
  }

  protected void executeFirableTransitions() {
    if (OLD_VERSION) {
      executeFirableTransitionsOld();
      return;
    }
    int loopCntr = 0; // overcome infinite loop in the system
    boolean happendSomthing = true;
    while (happendSomthing && loopCntr < orderOfTransition.size() * 2) {
      happendSomthing = false;
      loopCntr++;
      for (Integer currentTrans : possibleToExecute()) {
        if (fireable(currentTrans)) {
          happendSomthing = true;
          startFire(currentTrans);
          break;
        }
      }
    }
  }

  protected List<Integer> possibleToExecute() {
    if (enableCache) {
      return possibleToExecuteBasedOnCache();
    }
    List<Integer> toRet = new ArrayList<Integer>();
    for (Integer trId : orderOfTransition) {
      if (fireable(trId)) {
        toRet.add(trId);
      }
    }
    return toRet;
  }


  protected void setInputPlacesWithToken(Map<Integer, TTokenType> inputs) {
    if (inputs != null) {
      for (Integer inpPlaceId : inputs.keySet()) {
        if (!myNet.isInputPlace(inpPlaceId)) {
          throw new RuntimeException("Non input place: P" + inpPlaceId + " cannot be setted runtime");
        }
        TTokenType current = stateOfPlaces.get(inpPlaceId);
        TTokenType newToken = current.unite(inputs.get(inpPlaceId));
        recorder.inputPuttedInPlace(inpPlaceId, newToken);
        stateOfPlaces.set(inpPlaceId, newToken);
      }
    }

  }

  protected abstract TTokenType transformOnArc(TTokenType toke, int trId, int plId);

  private TTokenType[] getInpsFromTr(int trId, boolean replaceWithPhi) {
    List<Integer> places = myNet.getPlacesNeededForTransition(trId);
    TTokenType[] toRet = tokenArray(places.size());
    for (int i = 0; i < places.size(); i++) {
      int placeId = places.get(i);
      TTokenType currentTokenInPlace = transformOnArc(stateOfPlaces.get(placeId), trId, placeId);
      toRet[i] = currentTokenInPlace;

      if (replaceWithPhi) {
        stateOfPlaces.set(placeId, phi());
        recorder.tokenTakenOutFromPlaceToTransition(placeId, trId, currentTokenInPlace);
      }
    }
    return toRet;

  }

  protected abstract TTokenType[] executaTable(int trId, TTokenType[] inps);

  protected abstract int calcMultiDelay(TTokenType token, Double multi);

  private void startFire(int currentTrans) {
    TTokenType[] inpsForTrs = getInpsFromTr(currentTrans, true);
    TTokenType[] res = executaTable(currentTrans, inpsForTrs);
    valueHoldInTransitions.set(currentTrans, res);
    recorder.transitionFiredStarted(currentTrans);
    if (myNet.getDelayForTransition(currentTrans) != 0) {
      delayStateOfTransitions.set(currentTrans, myNet.getDelayForTransition(currentTrans));
    } else if (myNet.getDelayMultiplierForTransition(currentTrans) != 0.0 && (!inpsForTrs[0].isPhi())) {
      Integer delay = calcMultiDelay(inpsForTrs[0], myNet.getDelayMultiplierForTransition(currentTrans));

      if (delay > 0) {
        delayStateOfTransitions.set(currentTrans, delay);
      } else {
        finsihFire(currentTrans);
      }
    } else {
      finsihFire(currentTrans);
    }

  }

  private boolean fireable(int currentTrans) {
    if (delayStateOfTransitions.get(currentTrans) != 0) {
      return false;
    }
    TTokenType[] inps = getInpsFromTr(currentTrans, false);
    return executable(inps, currentTrans);
  }

  protected void updateDelayStateOfTransitionsAllreadyInFire() {

    for (int trId = 0; trId < myNet.getNrOfTransition(); trId++) {
      Integer state = delayStateOfTransitions.get(trId);
      if (state > 0) {
        if (state == 1) {
          finsihFire(trId);
        }
        delayStateOfTransitions.set(trId, state - 1);
      }
    }

  }

  private void finsihFire(int trId) {
    TTokenType[] res = valueHoldInTransitions.get(trId);
    valueHoldInTransitions.set(trId, emptyTokenArray());
    int nrOfNeededOut = myNet.getOutputPlacesForTransition(trId).size() + ((myNet.isOuputTransition(trId)) ? 1 : 0);
    if (nrOfNeededOut != res.length) {
      throw new RuntimeException("Ucorrect Petri Structure at trastition " + trId + ", ouputs needed"
          + nrOfNeededOut + " and exists " + res.length);
    }

    int tokenIndex = 0;
    if (myNet.isOuputTransition(trId)) {
      myNet.getActionsForOuputTransition(trId).forEach(c -> c.accept(res[0].myClone()));
      recorder.ouputTransitionFired(trId, res[0]);
      tokenIndex = 1;
    }
    for (int plID : myNet.getOutputPlacesForTransition(trId)) {
      putInPlace(plID, res[tokenIndex++], trId);
    }

    recorder.transitionFiredEnded(trId);

  }

  private void putInPlace(int plID, TTokenType tToken, int trId) {
    TTokenType united = stateOfPlaces.get(plID).unite(tToken);
    stateOfPlaces.set(plID, united);
    recorder.tokenPuttedInPlaceFromTransition(plID, trId, tToken);

  }

  private static class StateCacheEntry {
    final boolean[] state;

    public StateCacheEntry(boolean[] state) {
      this.state = state;
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(state);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof StateCacheEntry) {
        return Arrays.equals(state, ((StateCacheEntry) o).state);
      }
      return false;
    }

    @Override
    public String toString() {
      return "CE" + Arrays.toString(state);
    }

  }

  protected List<Integer> possibleToExecuteBasedOnCache() {
    boolean[] st = new boolean[stateOfPlaces.size()];
    for (int i = 0; i < stateOfPlaces.size(); i++) {
      st[i] = !stateOfPlaces.get(i).isPhi();
    }
    StateCacheEntry cacheEntry = new StateCacheEntry(st);
    if (!cache.containsKey(cacheEntry)) {

      // build up
      List<Integer> maybeRunnableTransitions = new ArrayList<>();
      for (Integer trIdIndex : orderOfTransition) {
        List<Integer> inpPlaces = myNet.getPlacesNeededForTransition(trIdIndex);
        boolean[] maybeInps = new boolean[inpPlaces.size()];
        for (int i = 0; i < inpPlaces.size(); i++) {
          maybeInps[i] = st[inpPlaces.get(i)];
        }
        if (myNet.getTableForTransition(trIdIndex).maybeExecutable(maybeInps)) {
          maybeRunnableTransitions.add(trIdIndex);
        }
      }
      cache.put(cacheEntry, maybeRunnableTransitions);
    }
    return cache.get(cacheEntry);
  }

}
