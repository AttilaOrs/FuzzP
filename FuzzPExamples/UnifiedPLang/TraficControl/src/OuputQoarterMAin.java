import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class OuputQoarterMAin {

  public static void main(String args[]) {

    OuputQuarterUnifiedPetriMaker maker = new OuputQuarterUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);

    for (int i = 0; i < 20; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(maker.iP0, new UnifiedToken((double) i));
      inp.put(maker.iP1, new UnifiedToken((i + 1.0) * 2));
      exec.runTick(inp);

    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }

}
