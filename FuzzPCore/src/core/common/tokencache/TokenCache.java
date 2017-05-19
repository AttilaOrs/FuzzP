package core.common.tokencache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

public class TokenCache<TokenType> implements ITokenCache<TokenType> {
  
  private static final int MAX_CACHED = 10;
  public static int _YES = 0;
  public static int _d_ADD = 0;
  public static int _c_ADD = 0;
  public static int _n_ADD = 0;

  private final PriorityQueue<TokenCacheEntry<TokenType>> entryQueue;
  private final Map<TokenCacheKey<TokenType>, TokenCacheEntry<TokenType>> cacheMap;
  private int callCntr;
  private final int maxCached;
  private Comparator<TokenCacheEntry<?>> cmp;

  public TokenCache() {
    this(MAX_CACHED);
  }


  public TokenCache(int maxCached) {
    entryQueue = new PriorityQueue<>(maxCached, new TokenCacheEntry.DefaultComparator());
    cacheMap = new HashMap<>(maxCached);
    callCntr = 0;
    this.maxCached = maxCached;
    cmp = new TokenCacheEntry.DefaultComparator();
  }

  @Override
  public Optional<TokenType[]> getCached(TokenType[] inp) {
    TokenCacheKey<TokenType> key = new TokenCacheKey<>(inp);
    if (cacheMap.containsKey(key)) {
      TokenCacheEntry<TokenType> entry = cacheMap.get(key);
      TokenType[] rez = entry.getContent(callCntr);
      // to repioritize
      entryQueue.remove(entry);
      entryQueue.add(entry);
      _YES++;
      return Optional.of(rez);
    }
    return Optional.empty();
  }

  @Override
  public void addRezultToCache(TokenType[] inp, TokenType[] out) {
    callCntr++;
    TokenCacheKey<TokenType> key = new TokenCacheKey<>(inp);
    TokenCacheEntry<TokenType> entry = new TokenCacheEntry<>(callCntr, out, key);
    addIfPossible(key, entry);
  }

  private void addIfPossible(TokenCacheKey<TokenType> key, TokenCacheEntry<TokenType> entry) {
    if (entryQueue.size() < maxCached) {
      putBoth(key, entry);
      _d_ADD++;
    } else {
      TokenCacheEntry<TokenType> pp = entryQueue.peek();
      if (cmp.compare(entry, pp) > 0) {
        _c_ADD++;
        entryQueue.remove(pp);
        cacheMap.remove(pp.getKey());
        putBoth(key, entry);
      } else {
        _n_ADD++;
      }
    }


  }


  private void putBoth(TokenCacheKey<TokenType> key, TokenCacheEntry<TokenType> entry) {
    entryQueue.add(entry);
    cacheMap.put(key, entry);
  }


  public IntSummaryStatistics getUsageStats() {
    return entryQueue.stream().mapToInt(e -> e.getAllCalled()).summaryStatistics();

  }

}
