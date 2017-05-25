package core.common.tokencache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

public class TokenCacheDisabling<TokenType> implements ITokenCache<TokenType> {

  private static final int MAX_CACHED = 10;

  private final PriorityQueue<TokenCacheEntry<TokenType>> entryQueue;
  private final Map<TokenCacheKey<TokenType>, TokenCacheEntry<TokenType>> cacheMap;
  private int callCntr;
  private final int maxCached;
  private Comparator<TokenCacheEntry<?>> cmp;
  private int lastReplaced;
  private boolean disableCache;

  public TokenCacheDisabling() {
    this(MAX_CACHED);
  }

  public TokenCacheDisabling(int maxCached) {
    entryQueue = new PriorityQueue<>(maxCached, new TokenCacheEntry.DefaultComparator());
    cacheMap = new HashMap<>(maxCached);
    callCntr = 0;
    this.maxCached = maxCached;
    cmp = new TokenCacheEntry.DefaultComparator();
    lastReplaced = 0;
    disableCache = false;
  }

  @Override
  public Optional<TokenType[]> getCached(TokenType[] inp) {
    if (disableCache) {
      return Optional.empty();
    }
    TokenCacheKey<TokenType> key = new TokenCacheKey<>(inp);
    if (cacheMap.containsKey(key)) {
      TokenCacheEntry<TokenType> entry = cacheMap.get(key);
      TokenType[] rez = entry.getContent(callCntr);
      // to repioritize
      entryQueue.remove(entry);
      entryQueue.add(entry);
      return Optional.of(rez);
    }
    return Optional.empty();
  }

  @Override
  public void addRezultToCache(TokenType[] inp, TokenType[] out) {
    if (disableCache) {
      return;
    }
    callCntr++;
    TokenCacheKey<TokenType> key = new TokenCacheKey<>(inp);
    TokenCacheEntry<TokenType> entry = new TokenCacheEntry<>(callCntr, out, key);
    addIfPossible(key, entry);
  }

  private void addIfPossible(TokenCacheKey<TokenType> key, TokenCacheEntry<TokenType> entry) {
    if (entryQueue.size() < maxCached) {
      putBoth(key, entry);
    } else {
      TokenCacheEntry<TokenType> pp = entryQueue.peek();
      if (cmp.compare(entry, pp) > 0) {
        entryQueue.remove(pp);
        cacheMap.remove(pp.getKey());
        putBoth(key, entry);
        lastReplaced++;
        if (lastReplaced > maxCached) {
          disableCache = true;
        }
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
