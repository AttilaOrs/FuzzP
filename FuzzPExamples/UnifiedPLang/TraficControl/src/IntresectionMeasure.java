import java.util.List;
import java.util.Map;

import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.AbstractExecutor;
import core.common.tokencache.TokenCacheDisabling;

public class IntresectionMeasure {
  public static void main(String args[]) {
    int TRY = 10;
    AbstractExecutor.OLD_VERSION = false;

    long start = System.nanoTime();
    for (int qq = 0; qq < TRY; qq++) {
      IntersectionUnifiedPetriMaker maker = new IntersectionUnifiedPetriMaker();
      UnifiedPetrinetCacheTableResultWrapper wrp = new UnifiedPetrinetCacheTableResultWrapper(maker.net,
          () -> new TokenCacheDisabling<>(3));
      SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(wrp, false, true);
      List<Map<Integer, UnifiedToken>> inps = IntersectionMain.createInput();

      for (Map<Integer, UnifiedToken> inp : inps) {
        exec.runTick(inp);
      }

    }
    long end = System.nanoTime();
    System.out.println(((end - start) / 1000000) / TRY);
    /*
     * System.out.println("built_up " + UnifiedAbstactExecutor._BUILT_UP);
     * System.out.println("not built up " +
     * UnifiedAbstactExecutor._NO_BUILD_UP); System.out.println("cache size " +
     * UnifiedAbstactExecutor._CACHE.size());
     */
  }

}
