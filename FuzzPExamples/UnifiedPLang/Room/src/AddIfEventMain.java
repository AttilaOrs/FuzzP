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

public class AddIfEventMain {

  public static void main(String args[]) {
	  AddIfEventUnifiedPetriMaker maker = new AddIfEventUnifiedPetriMaker();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(maker.net);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(fullRec, debugRec));
    exec.setRecorder(multiRec);
    List<Integer> prims =  Arrays.asList(2,3,5,7,11, 13, 17);

    for (int i =0; i <15; i++) {
        Map<Integer, UnifiedToken> inp = new HashMap<>();
        if(prims.contains(i)) {
			inp.put(maker.iP3, new UnifiedToken(0.0));
        }
        exec.runTick(inp);
    }

    UnifiedVizualizer.visualize(maker.net, fullRec, maker.nameStore);
    PetriDotDrawerVertical drawer = new PetriDotDrawerVertical(new DrawableUnifiedPetriNetWithExternalNames(maker.net,
        TransitionPlaceNameStore.createOrdinarNames(maker.net)));
    drawer.makeImage("pos");
    String ip0 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.iP3, t -> ((UnifiedToken) t).getValue());
    String p8 = fullRec.evolutionOfPlaceDatFormatOnceInTick(maker.P8, t -> ((UnifiedToken) t).getValue());

    PlotUtils.writeToFile("posIP0.txt", ip0);
    PlotUtils.writeToFile("posP8.txt", p8);

  }
}
