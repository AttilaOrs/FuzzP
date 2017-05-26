import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Main.UnifiedVizualizer;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;

public class IntersectionMain {
  public static void main(String args[]) {
    IntersectionUnifiedPetriMaker maker = new IntersectionUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    exec.setRecorder(fullRec);

    for (int i = 0; i < 20; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      // input of machines
      inp.put(maker.iP0, new UnifiedToken(20.0));
      inp.put(maker.iP2, new UnifiedToken(20.0));
      inp.put(maker.iP66, new UnifiedToken(20.0));
      inp.put(maker.iP67, new UnifiedToken(20.0));

      // input for splits
      inp.put(maker.iP1, new UnifiedToken(0.7));
      inp.put(maker.iP157, new UnifiedToken(0.65));
      inp.put(maker.iP161, new UnifiedToken(0.75));
      inp.put(maker.iP162, new UnifiedToken(0.55));

      // events
      if (i % 10 == 0) {
        inp.put(maker.iP149, new UnifiedToken(0.0));
      }

      if (i % 10 == 4) {
        inp.put(maker.iP155, new UnifiedToken(0.0));
      }

      if (i % 10 == 5) {
        inp.put(maker.iP153, new UnifiedToken(0.6));
      }
      if (i % 10 == 9) {
        inp.put(maker.iP160, new UnifiedToken(0.4));
      }

      exec.runTick(inp);


    }
    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);

  }

  public static List<Map<Integer, UnifiedToken>> createInput() {
    IntersectionUnifiedPetriMaker maker = new IntersectionUnifiedPetriMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    Random rnd = new Random();
    for (int i = 0; i < 200; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      // input of machines
      inp.put(maker.iP0, new UnifiedToken(20.0 + rnd.nextDouble() * 3.0));
      inp.put(maker.iP2, new UnifiedToken(20.0 + rnd.nextDouble() * 3.0));
      inp.put(maker.iP66, new UnifiedToken(20.0 + rnd.nextDouble() * 3.0));
      inp.put(maker.iP67, new UnifiedToken(20.0 + rnd.nextDouble() * 3.0));

      // input for splits
      inp.put(maker.iP1, new UnifiedToken(0.7 + rnd.nextDouble() * 0.2));
      inp.put(maker.iP157, new UnifiedToken(0.65 + rnd.nextDouble() * 0.2));
      inp.put(maker.iP161, new UnifiedToken(0.75 + rnd.nextDouble() * 0.2));
      inp.put(maker.iP162, new UnifiedToken(0.55 + rnd.nextDouble() * 0.2));

      // events
      if (i % 10 == 0) {
        inp.put(maker.iP149, new UnifiedToken(0.0));
      }

      if (i % 10 == 4) {
        inp.put(maker.iP155, new UnifiedToken(0.0));
      }

      if (i % 10 == 5) {
        inp.put(maker.iP153, new UnifiedToken(0.6));
      }
      if (i % 10 == 9) {
        inp.put(maker.iP160, new UnifiedToken(0.4));
      }
      toRet.add(inp);
    }
    return toRet;

  }

}
