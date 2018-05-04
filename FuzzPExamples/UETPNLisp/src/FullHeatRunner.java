import static commonUtil.PlotUtils.plot;
import static commonUtil.PlotUtils.writeToFile;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import AlgoImpl.IterationLogger;
import UETPNLisp.UETPNLisp;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGpProblmes.RoomHeatControl.Behavour.FullHeastControllSimpleDescriptor;
import UnifiedGpProblmes.RoomHeatControl.Behavour.FullHeatControllSimpleDescription;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;

public class FullHeatRunner {

  public static final String MORNING_SCENARIO_FILE = "MorningRoomScenario.json";
  public static final String EVENING_SCENARIO_FILE = "EveningRoomScenario.json";
  public static final String FITNESS_SCENARIO_FILE = "FitnessScenario.json";
  private static RoomScenario moringScneario = null;
  private static RoomScenario eveningScenario = null;
  private static RoomScenario fitnessScenario = null;

  public static void main(String args[]) throws FileNotFoundException {
    initScenarios();
    INode<NodeType> root = UETPNLisp.fromFile(new File("heat2.uls"));
    FullHeastControllSimpleDescriptor desc = new FullHeastControllSimpleDescriptor(
        asList(moringScneario, eveningScenario, fitnessScenario));
    desc.setLogging(true);
    FullHeatControllSimpleDescription rez = desc.evaluate(new UnifiedGpIndi((IInnerNode<NodeType>) root));
    System.out.println(rez);
    int cntr = 0;
    for( IterationLogger log: desc.getLoggers()) {
      plot(log.getLogsForPlottingContatinigStrings(""), "scen" + (cntr));
      writeToFile("scen" + cntr + ".dat", log.exportToDat(""));
      cntr++;
    }
    

    
  }

  public static void initScenarios() throws FileNotFoundException {
    Gson gs = new Gson();
    JsonReader reader = new JsonReader(new FileReader(MORNING_SCENARIO_FILE));
    moringScneario = gs.fromJson(reader, RoomScenario.class);
    reader = new JsonReader(new FileReader(EVENING_SCENARIO_FILE));
    eveningScenario = gs.fromJson(reader, RoomScenario.class);
    reader = new JsonReader(new FileReader(FITNESS_SCENARIO_FILE));
    fitnessScenario = gs.fromJson(reader, RoomScenario.class);
  }
}
