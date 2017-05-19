import java.util.HashMap;
import java.util.Map;

import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.tokencache.TokenCache;

public class RoomMeasureMain {

  public static void main(String[] args) {

    long start = System.currentTimeMillis();
    for (int x = 0; x < 1; x++) {

      RoomModelUnifiedPetriMaker maker = new RoomModelUnifiedPetriMaker();
      UnifiedPetrinetCacheTableResultWrapper wp = new UnifiedPetrinetCacheTableResultWrapper(maker.net,
          () -> new TokenCache<>(5));
      SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(wp, false);
      for (int i = 0; i < 60 * 60; i++) {
        Map<Integer, UnifiedToken> inp = new HashMap<>();

        inp.put(maker.iP1, new UnifiedToken(10.0)); // outside temp
        if (i % 120 * 10 == 10) {
          inp.put(maker.iP2, new UnifiedToken(0.0)); // open
        }
        if (i % 120 * 10 == 50) {
          inp.put(maker.iP3, new UnifiedToken(0.0)); // close
        }
        if (i > 60 * 30) {
          inp.put(maker.iP4, new UnifiedToken(40.0));
        }

        exec.runTick(inp);

      }
      wp.printStats();
    }
    long end = System.currentTimeMillis();
    System.out.println((end - start) / 30);
    System.out.println(TokenCache._YES);
    System.out.println(TokenCache._d_ADD);
    System.out.println(TokenCache._c_ADD);
    System.out.println(TokenCache._n_ADD);
  }
}
