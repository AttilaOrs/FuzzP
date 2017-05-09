

import java.util.HashMap;
import java.util.Map;

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

}
