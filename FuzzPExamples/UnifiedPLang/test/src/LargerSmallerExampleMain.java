import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class LargerSmallerExampleMain {

  public static void main(String[] args) {
    LargeSmallerExampleMaker maker = new LargeSmallerExampleMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    // DebuggerRecorder<UnifiedToken> fullRec = new DebuggerRecorder<>();
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);
    for (Map<Integer, UnifiedToken> inp : createInput()) {
      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }

  public static List<Map<Integer, UnifiedToken>> createInput() {
    LargeSmallerExampleMaker maker = new LargeSmallerExampleMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    for (int cntr = 0; cntr < 40; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      double inpuValue = ((cntr / 10) + 1.0) * ((cntr % 10 < 5) ? 1.0 : -1.0);
      inp.put(maker.iP1, new UnifiedToken(inpuValue));
      toRet.add(inp);
    }
    return toRet;
  }
}
