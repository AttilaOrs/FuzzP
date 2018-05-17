import static java.util.Arrays.asList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import AlgoImpl.pools.behaviour.BehaviourStore;
import UETPNLisp.UETPNLisp;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGpProblmes.RoomHeatControl.Behavour.BinaryDesc.FullHeastControllBinaryDescriptor;
import UnifiedGpProblmes.RoomHeatControl.Behavour.BinaryDesc.FullHeatBinaryDescripton;
import UnifiedGpProblmes.RoomHeatControl.Behavour.BinaryDesc.OverallFitness;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import commonUtil.PlotUtils;

public class FitnessAnalizer {

	public static final String MORNING_SCENARIO_FILE = "MorningRoomScenario.json";
	public static final String EVENING_SCENARIO_FILE = "EveningRoomScenario.json";
	public static final String FITNESS_SCENARIO_FILE = "FitnessScenario.json";
	private static RoomScenario moringScneario = null;
	private static RoomScenario eveningScenario = null;
	private static RoomScenario fitnessScenario = null;

	public static void main(String[] args) throws IOException {
		initScenarios();
		String w = Files.walk(Paths.get("."))
				.filter(p -> p.toString().endsWith("rez.txt") && p.toString().contains("princ")).map(p -> {
					try {
						return Files.readAllLines(p);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;

				}).filter(f -> f != null || f.isEmpty()).map(i -> i.get(0)).map(str -> {
					int r = str.indexOf('(');
					String treeStr = str.substring(r);
					INode<NodeType> root = UETPNLisp.fromString(treeStr);
					FullHeastControllBinaryDescriptor desc = new FullHeastControllBinaryDescriptor(
							asList(moringScneario, eveningScenario, fitnessScenario));
					FullHeatBinaryDescripton rez = desc
							.evaluate(new UnifiedGpIndi((IInnerNode<NodeType>) root));
					
					BehaviourStore<FullHeatBinaryDescripton > ps   = new BehaviourStore<>(null);
					OverallFitness fitnes = new OverallFitness((tank, room, size) -> ((0.95 * room + 0.05 * tank) * size), false, 1.0);
					ps.store(0, rez);
					fitnes.setStore(ps);
					return Double.toString( fitnes.evaluate(0));
        }).collect(Collectors.joining(" "));
		PlotUtils.writeToFile("firs_fi", w);

    w = Files.walk(Paths.get(".")).filter(p -> p.toString().endsWith("rez.txt") && p.toString().contains("princ")).map(p -> {
      try {
        return Files.readAllLines(p);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;

    }).filter(f -> f != null || f.isEmpty()).map(i -> i.get(0)).map(str -> {
      int r = str.indexOf('(');
      String treeStr = str.substring(r);
      INode<NodeType> root = UETPNLisp.fromString(treeStr);
      FullHeastControllBinaryDescriptor desc = new FullHeastControllBinaryDescriptor(asList(moringScneario, eveningScenario, fitnessScenario));
      FullHeatBinaryDescripton rez = desc.evaluate(new UnifiedGpIndi((IInnerNode<NodeType>) root));

      BehaviourStore<FullHeatBinaryDescripton> ps = new BehaviourStore<>(null);
      OverallFitness fitnes = new OverallFitness((tank, room, size) -> ((0.80 * room + 0.20 * tank) * size), true, 0.5);
      ps.store(0, rez);
      fitnes.setStore(ps);
      return Double.toString(fitnes.evaluate(0));
    }).collect(Collectors.joining(" "));
    PlotUtils.writeToFile("second_fi", w);
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
