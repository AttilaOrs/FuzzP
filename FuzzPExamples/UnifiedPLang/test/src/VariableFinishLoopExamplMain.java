import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class VariableFinishLoopExamplMain {

  public static void main(String[] args) {
    VariableFinishLoopExampleMaker maker = new VariableFinishLoopExampleMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);
    for (Map<Integer, UnifiedToken> inp : createInput()) {
      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }

  public static List<Map<Integer, UnifiedToken>> createInput() {
    VariableFinishLoopExampleMaker maker = new VariableFinishLoopExampleMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();

    for (int cntr = 0; cntr <= 100; cntr++) {
      double fi = Math.sin((cntr + 0.0001) / 20.0);
      double se = Math.floor((cntr + 0.0001) / 10.0) + 1.0;
      Map<Integer, UnifiedToken> inpMap = new HashMap<>();
      inpMap.put(maker.iP3, new UnifiedToken(fi));
      inpMap.put(maker.iP5, new UnifiedToken(se));
      toRet.add(inpMap);
    }
    return toRet;

  }
}
