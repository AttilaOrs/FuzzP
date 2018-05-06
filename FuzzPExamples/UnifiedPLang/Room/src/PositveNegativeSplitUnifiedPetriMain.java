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

public class PositveNegativeSplitUnifiedPetriMain {

  public static void main(String args[]) {
    PositveNegativeSplitUnifiedPetriMaker maker = new PositveNegativeSplitUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(fullRec, debugRec));
    exec.setRecorder(multiRec);

    for (double i0 = -1.0; i0 <= 1.0; i0 += 0.11) {
        Map<Integer, UnifiedToken> inp = new HashMap<>();
        inp.put(maker.iP0, new UnifiedToken(i0));
        exec.runTick(inp);
    }
    for (double i0 = 1.0; i0 >= -1.0; i0 -= 0.11) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(maker.iP0, new UnifiedToken(i0));
      exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);
    PetriDotDrawerVertical drawer = new PetriDotDrawerVertical(new DrawableUnifiedPetriNetWithExternalNames(maker.net,
        TransitionPlaceNameStore.createOrdinarNames(maker.net)));
    drawer.makeImage("loop");
    String ip0 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.iP0, t -> ((UnifiedToken) t).getValue());
    String p5 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.P5, t -> ((UnifiedToken) t).getValue());
    String p6 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.P6, t -> ((UnifiedToken) t).getValue());

    PlotUtils.writeToFile("lip0.txt", ip0);
    PlotUtils.writeToFile("lp5.txt", p5);
    PlotUtils.writeToFile("lp6.txt", p6);

  }
}
