import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class PosNegDeciderMain {

  public static void main(String[] args) {
    PosNegDeciderMaker maker = new PosNegDeciderMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);
    for (Map<Integer, UnifiedToken> inp : createInput()) {
      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }

  public static List<Map<Integer, UnifiedToken>> createInput() {
    PosNegDeciderMaker maker = new PosNegDeciderMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();

    for (int cntr = 0; cntr < 100; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(maker.iP1, new UnifiedToken(Math.sin((cntr + 0.00001) / 10.0)));
      toRet.add(inp);
    }
    return toRet;

  }

}
