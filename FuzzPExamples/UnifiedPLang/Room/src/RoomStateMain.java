import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.DebuggerRecorder;
import core.common.recoder.FullRecorder;
import core.common.recoder.MultiRecorder;

public class RoomStateMain {

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

  public static List<Map<Integer, UnifiedToken>> createInpForRoom() {
    RoomStateUnifiedPetriMaker maker = new RoomStateUnifiedPetriMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    for (int i = 0; i < 60 * 60; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      if (i % 5 == 1) {
        inp.put(maker.iP10, new UnifiedToken(0.0));
      }
      if (i % 5 == 4) {
        inp.put(maker.iP11, new UnifiedToken(0.0));
      }
      toRet.add(inp);
    }
    return toRet;

  }


}
