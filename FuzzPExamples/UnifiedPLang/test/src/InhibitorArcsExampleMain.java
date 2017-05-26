import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class InhibitorArcsExampleMain {

  public static void main(String[] args) {
    InhibitorArcExampleMaker maker = new InhibitorArcExampleMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    // DebuggerRecorder<UnifiedToken> fullRec = new DebuggerRecorder<>();
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);
    for (Map<Integer, UnifiedToken> inp : createInput()) {
      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }

  static int cntr = 0;;

  public static List<Map<Integer, UnifiedToken>> createInput() {
    InhibitorArcExampleMaker maker = new InhibitorArcExampleMaker();
    int[] eventTick = new int[] { 1, 2, 3, 7, 11, 13, 17, 23, 29, 31, 37 };
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    for (cntr = 0; cntr < 40; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      double inp_x5 = -0.9;
      inp.put(maker.iP5, new UnifiedToken(inp_x5));
      if (IntStream.of(eventTick).anyMatch(x -> x == cntr)) {
        inp.put(maker.iP0, new UnifiedToken(0.0));
      }
      toRet.add(inp);
    }
    return toRet;
  }

}
