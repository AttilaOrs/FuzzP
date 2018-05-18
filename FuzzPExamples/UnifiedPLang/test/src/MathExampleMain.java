import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

public class MathExampleMain {

  public static void main(String args[]) {
    MathExampleUnifiedPetriMaker maker = new MathExampleUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(fullRec, debugRec));
    exec.setRecorder(multiRec);
    List<Double> inp0 = Arrays.asList(0.0, 1.0, 2.0, 1.0, 0.0, 1.0, 2.0, 1.0, 0.0);
    List<Double> inp1 = Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, -1.0, -1.0, -1.0, -1.0);

    for (int i = 0; i < inp0.size(); i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(maker.iP0, new UnifiedToken(inp0.get(i)));
      inp.put(maker.iP1, new UnifiedToken(inp1.get(i)));
      exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);
    PetriDotDrawerVertical drawer = new PetriDotDrawerVertical(new DrawableUnifiedPetriNetWithExternalNames(maker.net,
        TransitionPlaceNameStore.createOrdinarNames(maker.net)));
    drawer.makeImage("math");
    String p6 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.P6, t -> ((UnifiedToken) t).getValue());
    String iP0 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.iP0, t -> ((UnifiedToken) t).getValue());
    String iP1 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.iP1, t -> ((UnifiedToken) t).getValue());

    PlotUtils.writeToFile("mathIP0.txt", iP0);
    PlotUtils.writeToFile("mathIP1.txt", iP1);
    PlotUtils.writeToFile("mathOUT.txt", p6);

  }
}
