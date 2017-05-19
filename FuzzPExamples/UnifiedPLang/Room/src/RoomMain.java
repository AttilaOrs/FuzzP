import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class RoomMain {
  public static void main(String[] args) {

    RoomModelUnifiedPetriMaker maker = new RoomModelUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);
    for (int i = 0; i < 60 * 60; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();

      inp.put(maker.iP1, new UnifiedToken(10.0)); // outside temp
      if (i % 120 * 10 == 10) {
        inp.put(maker.iP2, new UnifiedToken(0.0)); // open
      }
      if (i % 120 * 10 == 50) {
        inp.put(maker.iP3, new UnifiedToken(0.0)); // close
      }
      if (i > 60 * 30) {
        inp.put(maker.iP4, new UnifiedToken(40.0));
      }

      exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);



  }

}
