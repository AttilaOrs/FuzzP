import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

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
    INode<NodeType> root = UETPNLisp.fromFile(new File("heat.uls"));
    FullHeastControllSimpleDescriptor desc = new FullHeastControllSimpleDescriptor(
        Arrays.asList(moringScneario, eveningScenario, fitnessScenario));
    FullHeatControllSimpleDescription rez = desc.evaluate(new UnifiedGpIndi((IInnerNode<NodeType>) root));
    System.out.println(rez);
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
