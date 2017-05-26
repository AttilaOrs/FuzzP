package core.UnifiedPetriLogic.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.ReadableUnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedPetriNetChecker;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.common.AbstractExecutor;

public abstract class UnifiedAbstactExecutor
    extends AbstractExecutor<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, ReadableUnifiedPetriNet> {

  public static boolean DEFALT_EANBLE_CACHE = true;

  private final boolean enableCache;



  public UnifiedAbstactExecutor(ReadableUnifiedPetriNet net, boolean enablechecking) {
    this(net, enablechecking, DEFALT_EANBLE_CACHE);
  }

  public UnifiedAbstactExecutor(ReadableUnifiedPetriNet net) {
    this(net, true);
  }

  public UnifiedAbstactExecutor(ReadableUnifiedPetriNet net, boolean enablechecking, boolean enableCache) {
    super(net, enablechecking);
    this.enableCache = enableCache;
  }


  @Override
  protected void checkPetriNet() {
    UnifiedPetriNetChecker checker = new UnifiedPetriNetChecker();
    if (!checker.checkPetriNet(myNet)) {
      throw new RuntimeException(checker.getErrorMsg());
    }

  }

  @Override
  protected UnifiedToken[] emptyTokenArray() {
    return new UnifiedToken[0];
  }

  @Override
  protected UnifiedToken[] tokenArray(int size) {
    return new UnifiedToken[size];
  }

  @Override
  protected UnifiedToken phi() {
    return new UnifiedToken();
  }

  @Override
  protected UnifiedToken transformOnArc(UnifiedToken toke, int trId, int plId) {
    return toke;
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

  }

  private HashMap<StateCacheEntry, List<Integer>> cache = new HashMap<>();

  private boolean maybeRunnableBasedOnCache(int trId) {
    if (!enableCache) {
      return true;
    }

    boolean[] st = new boolean[stateOfPlaces.size()];
    for (int i = 0; i < stateOfPlaces.size(); i++) {
      st[i] = stateOfPlaces.get(i).isPhi();
    }
    StateCacheEntry cacheEntry = new StateCacheEntry(st);
    if (!cache.containsKey(cacheEntry)) {
      // build up
      List<Integer> maybeRunnableTransitions = new ArrayList<>();
      for (int trIdIndex = 0; trIdIndex < myNet.getNrOfTransition(); trIdIndex++) {
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
    return cache.get(cacheEntry).contains(trId);

  }

  @Override
  protected boolean executable(UnifiedToken[] inps, int trId) {
    return maybeRunnableBasedOnCache(trId) && myNet.getTableForTransition(trId).executable(inps,
        myNet.getContextForTransition(trId));
  }

  @Override
  protected UnifiedToken[] executaTable(int trId, UnifiedToken[] inps) {
    return myNet.getTableForTransition(trId).execute(inps, myNet.getContextForTransition(trId));
  }

  @Override
  protected int calcMultiDelay(UnifiedToken token, Double multi) {
    return (int) Math.floor(multi * token.getValue());
  }

}
