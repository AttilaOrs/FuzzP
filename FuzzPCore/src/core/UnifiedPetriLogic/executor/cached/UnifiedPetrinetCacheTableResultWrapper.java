package core.UnifiedPetriLogic.executor.cached;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

import core.UnifiedPetriLogic.IContex;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.ReadableUnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.tokencache.ITokenCache;
import core.common.tokencache.TokenCache;

public class UnifiedPetrinetCacheTableResultWrapper implements ReadableUnifiedPetriNet {

  private ReadableUnifiedPetriNet original;
  private Supplier<ITokenCache<UnifiedToken>> cacheFactory;
  private Map<Integer, IUnifiedTable> tables;

  public UnifiedPetrinetCacheTableResultWrapper(ReadableUnifiedPetriNet original,
      Supplier<ITokenCache<UnifiedToken>> cacheFactory) {
    this.original = original;
    this.cacheFactory = cacheFactory;
    tables = new HashMap<>();
  }

  @Override
  public int getNrOfPlaces() {
    return original.getNrOfPlaces();
  }

  @Override
  public int getNrOfTransition() {
    return original.getNrOfTransition();
  }

  @Override
  public boolean isInputPlace(int placeID) {
    return original.isInputPlace(placeID);
  }

  @Override
  public List<Integer> getPlacesNeededForTransition(int trId) {
    return original.getPlacesNeededForTransition(trId);
  }

  @Override
  public List<Integer> getOutputTransitions() {
    return original.getOutputTransitions();
  }

  @Override
  public int getDelayForTransition(int trId) {
    return original.getDelayForTransition(trId);
  }

  @Override
  public boolean hasTransitionDelay(int trId) {
    return original.hasTransitionDelay(trId);
  }

  @Override
  public List<Integer> getOutputPlacesForTransition(int trId) {
    return original.getOutputPlacesForTransition(trId);
  }

  @Override
  public boolean isOuputTransition(int trId) {
    return original.isOuputTransition(trId);
  }

  @Override
  public UnifiedToken getInitialMarkingForPlace(int plId) {
    return original.getInitialMarkingForPlace(plId);
  }

  @Override
  public List<Integer> getTransitionAfterPlace(int placeId) {
    return original.getTransitionAfterPlace(placeId);
  }

  @Override
  public List<Integer> getTransitinsBeforePlace(Integer placeId) {
    return original.getTransitinsBeforePlace(placeId);
  }

  @Override
  public List<Consumer<UnifiedToken>> getActionsForOuputTransition(int trId) {
    return original.getActionsForOuputTransition(trId);
  }

  @Override
  public IUnifiedTable getTableForTransition(int trId) {
    if (!tables.containsKey(trId)) {
      WrappedUnifiedTable wraped = new WrappedUnifiedTable(original.getTableForTransition(trId), cacheFactory.get());
      tables.put(trId, wraped);
    }
    return tables.get(trId);
  }

  @Override
  public Double getDelayMultiplierForTransition(int trId) {
    return original.getDelayMultiplierForTransition(trId);
  }

  @Override
  public double getScale(int placeId) {
    return original.getScale(placeId);
  }

  @Override
  public IContex getContextForTransition(int trId) {
    return original.getContextForTransition(trId);
  }

  public void printStats() {
    for (Entry<Integer, IUnifiedTable> e : tables.entrySet()) {
      WrappedUnifiedTable ww = (WrappedUnifiedTable) e.getValue();
      TokenCache<UnifiedToken> cache = (TokenCache<UnifiedToken>) ww.getCache();
      IntSummaryStatistics stats = cache.getUsageStats();
      System.out.println(stats);
    }

  }

}
