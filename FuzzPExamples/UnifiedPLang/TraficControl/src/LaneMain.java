import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.FuzzyPetriLogic.PetriNet.DebuggerRecorder;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import core.common.recoder.MultiRecorder;

public class LaneMain {
  public static void main(String args[]) {
    LaneUnifiedPetriMaker maker = new LaneUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(fullRec, debugRec));
    exec.setRecorder(multiRec);

    for (int tick = 0; tick < 50; tick++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      if (tick % 10 < 5) {
        inp.put(maker.iP3, new UnifiedToken((double) (10 - tick % 10)));
      } else {
        inp.put(maker.iP2, new UnifiedToken((double) (10 - tick % 10)));
      }
      if (tick % 7 == 0) {
        inp.put(maker.iP4, new UnifiedToken(0.0));
      }

      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);
  }

}
