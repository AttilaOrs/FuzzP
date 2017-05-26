

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class ControllerMain {

  public static void main(String args[]) {
    ControllerUnifiedPetriMaker maker = new ControllerUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);
    for(int i = 0; i < 10 ; i++){
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(maker.iP15, new UnifiedToken(0.0));
      inp.put(maker.iP3, new UnifiedToken(30.0));
      inp.put(maker.iP4, new UnifiedToken(20.0));
      inp.put(maker.iP7, new UnifiedToken(10.0));
      inp.put(maker.iP8, new UnifiedToken(40.0));
      exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);
  }

  public static List<Map<Integer, UnifiedToken>> createInp() {
    ControllerUnifiedPetriMaker maker = new ControllerUnifiedPetriMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    Random rnd = new Random();
    for (int i = 0; i < 10; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(maker.iP15, new UnifiedToken(0.0));
      inp.put(maker.iP3, new UnifiedToken(30.0 + rnd.nextDouble() * 5.0));
      inp.put(maker.iP4, new UnifiedToken(20.0 + rnd.nextDouble() * 5.0));
      inp.put(maker.iP7, new UnifiedToken(10.0 + rnd.nextDouble() * 5.0));
      inp.put(maker.iP8, new UnifiedToken(40.0 + rnd.nextDouble() * 5.0));
      toRet.add(inp);
    }
    return toRet;

  }

}
