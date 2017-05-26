import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class MaxFinderTryOutMain {

  public static void main(String args[]) {
    MaxTableTryOutUnifiedPetriMaker maker = new MaxTableTryOutUnifiedPetriMaker();
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    exec.setRecorder(rec);

    for (int i = 0; i < 100; i++) {
      Map<Integer,UnifiedToken > inp = new HashMap<>();
      inp.put(maker.iP1, new UnifiedToken(Math.sin(i / 10.0)));
      inp.put(maker.iP2, new UnifiedToken(Math.cos(i / 10.0)));
      exec.runTick(inp);

    }
    UnifiedVizualizer.visualize(maker.net, rec, maker.nameStore);
  }

  public static List<Map<Integer, UnifiedToken>> createInput() {
    MaxTableTryOutUnifiedPetriMaker maker = new MaxTableTryOutUnifiedPetriMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(maker.iP1, new UnifiedToken(Math.sin(i / 10.0)));
      inp.put(maker.iP2, new UnifiedToken(Math.cos(i / 10.0)));
      toRet.add(inp);
    }
    return toRet;

  }

}
