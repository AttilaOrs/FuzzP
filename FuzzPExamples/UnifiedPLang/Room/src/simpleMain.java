import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Main.UnifiedVizualizer;
import commonUtil.PlotUtils;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.DrawableUnifiedPetriNetWithExternalNames;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.DebuggerRecorder;
import core.common.recoder.FullRecorder;
import core.common.recoder.MultiRecorder;
import dotDrawer.PetriDotDrawerVertical;

public class simpleMain {

  public static void main(String args[]) {
    simpleUnifiedPetriMaker maker = new simpleUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(fullRec, debugRec));
    exec.setRecorder(multiRec);

    for (double i0 = -1.0; i0 <= 1.0; i0 += 0.5) {
      for (double i1 = -1.0; i1 <= 1.0; i1 += 0.5) {
        Map<Integer, UnifiedToken> inp = new HashMap<>();
        inp.put(maker.iP0, new UnifiedToken(i0));
        inp.put(maker.iP1, new UnifiedToken(i1));
        exec.runTick(inp);
      }
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);
    PetriDotDrawerVertical drawer = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNetWithExternalNames(maker.net,
            TransitionPlaceNameStore.createOrdinarNames(maker.net)));
    drawer.makeImage("simple");
    String ip0 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.iP0, t -> ((UnifiedToken) t).getValue());
    String ip1 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.iP1, t -> ((UnifiedToken) t).getValue());
    String p2 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.P2, t -> ((UnifiedToken) t).getValue());

    PlotUtils.writeToFile("ip0.txt", ip0);
    PlotUtils.writeToFile("ip1.txt", ip1);
    PlotUtils.writeToFile("p2.txt", p2);

  }

}
