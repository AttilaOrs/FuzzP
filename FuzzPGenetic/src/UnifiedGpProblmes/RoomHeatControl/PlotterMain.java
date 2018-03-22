package UnifiedGpProblmes.RoomHeatControl;

import java.io.FileNotFoundException;

import AlgoImpl.IterationLogger;
import UnifiedPetriRuleOptimizer.Room.RoomFitnes;
import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;

public class PlotterMain {

  private static final int[] inps = new int[] { 156, 166 };
  private static final int[] outs = new int[] { 145, 150 };
  private static final String file = "piRez_n26.json";
  private static final String outFile = "room_ga_diff.dat";

  public static void main(String[] args) throws FileNotFoundException {
    Main.initScenarios();
    PetriNetJsonSaver<UnifiedPetriNet> loader = new PetriNetJsonSaver<>();
    UnifiedPetriNet net = loader.load(file, UnifiedPetriNet.class);

    RoomFitnes fit = new RoomFitnes(null, inps, outs, Main.moringScneario);
    IterationLogger logger = new IterationLogger();
    fit.setLogger(logger);
    fit.simulateNet(net);

    String dat = logger.exportToDat("");
    PlotUtils.writeToFile(outFile, dat);

  }

}
