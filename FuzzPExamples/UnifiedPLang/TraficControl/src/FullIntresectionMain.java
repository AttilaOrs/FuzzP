import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class FullIntresectionMain {
  
  public static void main(String[] args){
    
    FullIntersectionUnifiedPetriMaker maker = new FullIntersectionUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);

    for (int i = 0; i < 20; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      // input of machines
      inp.put(maker.iP8, new UnifiedToken(20.0));
      inp.put(maker.iP9, new UnifiedToken(20.0));
      inp.put(maker.iP10, new UnifiedToken(20.0));
      inp.put(maker.iP11, new UnifiedToken(20.0));


      // input for splits
      inp.put(maker.iP4, new UnifiedToken(0.7));
      inp.put(maker.iP5, new UnifiedToken(0.55));
      inp.put(maker.iP6, new UnifiedToken(0.85));
      inp.put(maker.iP7, new UnifiedToken(0.25));


      // events
      if (i % 10 == 0) {
        inp.put(maker.iP0, new UnifiedToken(0.0));
      }

      if (i % 10 == 4) {
        inp.put(maker.iP1, new UnifiedToken(0.0));
      }

      if (i % 10 == 5) {
        inp.put(maker.iP2, new UnifiedToken(0.6));
      }
      if (i % 10 == 9) {
        inp.put(maker.iP3, new UnifiedToken(0.4));
      }

      exec.runTick(inp);


    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }
}

