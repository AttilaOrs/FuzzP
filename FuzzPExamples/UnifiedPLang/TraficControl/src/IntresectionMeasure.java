import java.util.HashMap;
import java.util.Map;

import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.tokencache.TokenCacheDisabling;

public class IntresectionMeasure {
  public static void main(String args[]) {
    long start = System.currentTimeMillis();
    for (int qq = 0; qq < 10; qq++) {
      IntersectionUnifiedPetriMaker maker = new IntersectionUnifiedPetriMaker();
      UnifiedPetrinetCacheTableResultWrapper wrp = new UnifiedPetrinetCacheTableResultWrapper(maker.net,
          () -> new TokenCacheDisabling<>(15));
      SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(wrp, false);

      for (int i = 0; i < 60; i++) {
        Map<Integer, UnifiedToken> inp = new HashMap<>();
        // input of machines
        inp.put(maker.iP0, new UnifiedToken(20.0));
        inp.put(maker.iP2, new UnifiedToken(20.0));
        inp.put(maker.iP66, new UnifiedToken(20.0));
        inp.put(maker.iP67, new UnifiedToken(20.0));

        // input for splits
        inp.put(maker.iP1, new UnifiedToken(0.7));
        inp.put(maker.iP157, new UnifiedToken(0.65));
        inp.put(maker.iP161, new UnifiedToken(0.75));
        inp.put(maker.iP162, new UnifiedToken(0.55));

        // events
        if (i % 10 == 0) {
          inp.put(maker.iP149, new UnifiedToken(0.0));
        }

        if (i % 10 == 4) {
          inp.put(maker.iP155, new UnifiedToken(0.0));
        }

        if (i % 10 == 5) {
          inp.put(maker.iP153, new UnifiedToken(0.6));
        }
        if (i % 10 == 9) {
          inp.put(maker.iP160, new UnifiedToken(0.4));
        }

        exec.runTick(inp);

      }

    }
    long end = System.currentTimeMillis();
    System.out.println((end - start) / 10);
  }

}
