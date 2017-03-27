import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.FuzzyPetriLogic.PetriNet.DebuggerRecorder;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import core.common.recoder.MultiRecorder;

public class QarterIntersectionMain {
  
  public static void main(String[] args){
    QarterIntersectionUnifiedPetriMaker maker = new QarterIntersectionUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(fullRec, debugRec));
    exec.setRecorder(multiRec);
    
    
    for (int i = 0; i < 20; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      if(i % 10== 3){
        inp.put(maker.iP28, new UnifiedToken(0.0));
      }
      if(i % 10 == 8){
        inp.put(maker.iP33, new UnifiedToken(0.0));
      }
      inp.put(maker.iP34, new UnifiedToken(0.7));
      inp.put(maker.iP31, new UnifiedToken(10.0));
      exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);
    
  }

}
