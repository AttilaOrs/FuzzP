import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.FuzzyPetriLogic.PetriNet.DebuggerRecorder;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import core.common.recoder.MultiRecorder;

public class RommStateMain {

  public static void main(String[] args) {
    RoomStateUnifiedPetriMaker maker = new RoomStateUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(fullRec, debugRec));
    exec.setRecorder(multiRec);
    for (int i = 0; i < 15; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      if (i % 5 == 1) {
        inp.put(maker.iP10, new UnifiedToken(0.0));
      }
      if (i % 5 == 4) {
        inp.put(maker.iP11, new UnifiedToken(0.0));
      }
      exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }


}
