import java.util.List;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FullRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class GeneralRun {

  public static void runNet(UnifiedPetriNet net, List<Map<Integer, UnifiedToken>> inps,
      TransitionPlaceNameStore names) {
    UnifiedPetrinetCacheTableResultWrapper wrapper = new UnifiedPetrinetCacheTableResultWrapper(net,
        () -> new TokenCacheDisabling<>(10));

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(wrapper, false, true);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);
    long start = System.nanoTime();
    for (Map<Integer, UnifiedToken> inp : inps) {
      exec.runTick(inp);
    }
    long end = System.nanoTime();
    // System.out.println((end - start) / inps.size());
    UnifiedVizualizer.visualize(net, fullRec, names);


  }

}
