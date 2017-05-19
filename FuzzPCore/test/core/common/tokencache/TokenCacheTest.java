package core.common.tokencache;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import core.UnifiedPetriLogic.UnifiedToken;

public class TokenCacheTest {

  @Test
  public void simple() {
    TokenCache<UnifiedToken> cache = new TokenCache<>();

    Optional<UnifiedToken[]> rez = cache.getCached(arr(0.5, 0.7));
    assertTrue(!rez.isPresent());

    cache.addRezultToCache(arr(0.5, 0.7), arr(0.3));
    rez = cache.getCached(arr(0.5, 0.7));
    assertTrue(rez.isPresent());
    UnifiedToken[] comesBack = rez.get();
    assertTrue(Arrays.equals(comesBack, arr(0.3)));

  }

  @Test
  public void newest_remains() {
    TokenCache<UnifiedToken> cache = new TokenCache<>(3);

    cache.addRezultToCache(arr(0.1, 0.7), arr(0.3));
    cache.addRezultToCache(arr(0.2, 0.7), arr(0.3));
    cache.addRezultToCache(arr(0.3, 0.7), arr(0.3));
    cache.addRezultToCache(arr(0.4, 0.7), arr(0.3));

    assertTrue(cache.getCached(arr(0.2, 0.7)).isPresent());
    assertTrue(cache.getCached(arr(0.3, 0.7)).isPresent());
    assertTrue(cache.getCached(arr(0.4, 0.7)).isPresent());
  }

  @Test
  public void secnd_newest_remains() {
    TokenCache<UnifiedToken> cache = new TokenCache<>(3);

    cache.addRezultToCache(arr(0.1, 0.7), arr(0.3));
    cache.getCached(arr(0.1, 0.7));
    cache.addRezultToCache(arr(0.2, 0.7), arr(0.3));
    cache.addRezultToCache(arr(0.3, 0.7), arr(0.3));
    cache.addRezultToCache(arr(0.4, 0.7), arr(0.3));

    assertTrue(cache.getCached(arr(0.1, 0.7)).isPresent());
    assertTrue(cache.getCached(arr(0.3, 0.7)).isPresent());
    assertTrue(cache.getCached(arr(0.4, 0.7)).isPresent());
  }

  @Test
  public void least_ferquent_is_deleted() {
    TokenCache<UnifiedToken> cache = new TokenCache<>(3);
    cache.addRezultToCache(arr(0.1, 0.7), arr(0.3));
    cache.getCached(arr(0.1, 0.7));
    cache.getCached(arr(0.1, 0.7));
    cache.addRezultToCache(arr(0.2, 0.7), arr(0.3));
    cache.getCached(arr(0.2, 0.7));
    cache.addRezultToCache(arr(0.3, 0.7), arr(0.3));
    cache.addRezultToCache(arr(0.4, 0.7), arr(0.3));

    assertTrue(cache.getCached(arr(0.1, 0.7)).isPresent());
    assertTrue(cache.getCached(arr(0.2, 0.7)).isPresent());
    assertTrue(cache.getCached(arr(0.4, 0.7)).isPresent());
  }

  @Test
  public void full_chache_no_new_elements_added() {
    TokenCache<UnifiedToken> cache = new TokenCache<>(3);
    cache.addRezultToCache(arr(0.1, 0.7), arr(0.3));
    cache.getCached(arr(0.1, 0.7));
    cache.getCached(arr(0.1, 0.7));
    cache.addRezultToCache(arr(0.2, 0.7), arr(0.3));
    cache.getCached(arr(0.2, 0.7));
    cache.addRezultToCache(arr(0.3, 0.7), arr(0.3));
    cache.getCached(arr(0.3, 0.7));
    cache.addRezultToCache(arr(0.4, 0.7), arr(0.3));

    assertTrue(cache.getCached(arr(0.1, 0.7)).isPresent());
    assertTrue(cache.getCached(arr(0.2, 0.7)).isPresent());
    assertTrue(cache.getCached(arr(0.3, 0.7)).isPresent());
    assertTrue(!cache.getCached(arr(0.4, 0.7)).isPresent());
  }



  private static UnifiedToken[] arr(Double... r) {
    return Arrays.stream(r).map(d -> new UnifiedToken(d)).toArray(i -> new UnifiedToken[r.length]);
  }


}
