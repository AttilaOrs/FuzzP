import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
      if (i % (120 * 10) == 10) {
        inp.put(maker.iP2, new UnifiedToken(0.0)); // open
      }
      if (i % (120 * 10) == 100) {
        inp.put(maker.iP3, new UnifiedToken(0.0)); // close
      }
      if (i > 60 * 30) {
        inp.put(maker.iP4, new UnifiedToken(40.0));
      }

      exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);




  }

  public static List<Map<Integer, UnifiedToken>> createInpForRoom() {
    RoomModelUnifiedPetriMaker maker = new RoomModelUnifiedPetriMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    Random rnd = new Random();
    for (int i = 0; i < 60 * 60; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();

      inp.put(maker.iP1, new UnifiedToken(10.0 + rnd.nextDouble() * 10.0)); // outside
                                                                            // temp
      if (i % 120 * 100 == 10) {
        inp.put(maker.iP2, new UnifiedToken(0.0)); // open
      }
      if (i % 120 * 100 == 1000) {
        inp.put(maker.iP3, new UnifiedToken(0.0)); // close
      }
      if (i > 60 * 30) {
        inp.put(maker.iP4, new UnifiedToken(40.0 + rnd.nextDouble() * 10.0));
      }
      toRet.add(inp);
    }
    return toRet;

  }

}
