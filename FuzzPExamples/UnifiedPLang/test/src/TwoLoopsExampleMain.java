import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class TwoLoopsExampleMain {

  public static void main(String[] args) {
    TwoLoopsExampleMaker maker = new TwoLoopsExampleMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);
    for (Map<Integer, UnifiedToken> inp : createInput()) {
      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }

  public static List<Map<Integer, UnifiedToken>> createInput() {
    TwoLoopsExampleMaker maker = new TwoLoopsExampleMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();

    for (int cntr = 0; cntr <= 60; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      if (cntr % 3 == 0) {
        double inpValue = Math.sin((cntr + 0.00001) / 10.0);
        inp.put(maker.iP3, new UnifiedToken(inpValue));
      }
      if (cntr % 5 == 0) {
        double inpValue = Math.sin((cntr + 0.00001) / 10.0 + 0.5);
        inp.put(maker.iP6, new UnifiedToken(inpValue));
      }
      toRet.add(inp);
    }
    return toRet;

  }
}
