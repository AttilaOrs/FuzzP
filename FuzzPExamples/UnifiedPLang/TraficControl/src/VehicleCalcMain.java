import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import core.FuzzyPetriLogic.PetriNet.DebuggerRecorder;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import core.common.recoder.MultiRecorder;

public class VehicleCalcMain {
  
  
  public static void main(String args[]){
    VehicleCalcUnifiedPetriMaker maker = new VehicleCalcUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(fullRec, debugRec));
    exec.setRecorder(multiRec);
    
    
    for (int i = 0; i < 10; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      if(i == 0){
        inp.put(maker.iP9, new UnifiedToken(0.0));
      }
      if(i == 5){
        inp.put(maker.iP11, new UnifiedToken(0.0));
      }
      inp.put(maker.iP5, new UnifiedToken(i * 6.0));
      exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);
    
    
  }

}
