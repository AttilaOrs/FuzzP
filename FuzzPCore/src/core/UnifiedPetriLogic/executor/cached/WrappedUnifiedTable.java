package core.UnifiedPetriLogic.executor.cached;

import java.util.List;
import java.util.Optional;

import core.UnifiedPetriLogic.IContex;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.tokencache.ITokenCache;

public class WrappedUnifiedTable implements IUnifiedTable {

  IUnifiedTable original;
  private ITokenCache<UnifiedToken> cache;

  public WrappedUnifiedTable(IUnifiedTable original, ITokenCache<UnifiedToken> cache) {
    this.original = original;
    this.cache = cache;
  }

  @Override
  public UnifiedToken[] execute(UnifiedToken[] inputs, IContex ct) {
    Optional<UnifiedToken[]> rez = cache.getCached(inputs);
    if (rez.isPresent()) {
      return rez.get();
    }
    UnifiedToken[] calc = original.execute(inputs, ct);
    cache.addRezultToCache(inputs, calc);
    return calc;
  }

  @Override
  public boolean executable(UnifiedToken[] inputs, IContex ct) {
    return original.executable(inputs, ct);
  }

  @Override
  public IUnifiedTable myClone() {
    return original.myClone();
  }

  @Override
  public List<Double> deduceScale(List<Double> inpScales) {
    return original.deduceScale(inpScales);
  }

  public ITokenCache<UnifiedToken> getCache() {
    return cache;
  }

  @Override
  public boolean maybeExecutable(boolean[] ar) {
    return original.maybeExecutable(ar);
  }

}
