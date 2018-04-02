package UnifiedGpProblmes.RoomHeatControl;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import AlgoImpl.IterationLogger;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomController;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomSimulator;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomSimulator.Rezult;

public class RoomSimulatorTest {

  int cntr = 0;

  @Test
  public void test() {
    IterationLogger logger = new IterationLogger();
    RoomSimulator w = new RoomSimulator(RoomScenario.winterMorning());
    w.setIterationLogger(logger);
    Rezult rez = w.simulate((c, d) -> {
      cntr++;
      if (cntr % 10 == 5) {
        return RoomController.ControllEvent.StartHeating;
      } else if (cntr % 10 == 0) {
        return RoomController.ControllEvent.StopHeating;
      }
      return RoomController.ControllEvent.None;
    });
    // PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(""),
    // "test_RoomSimulatorTest.jpg");
    assertTrue(rez.heatrerOn == 90);
  }

  @Test
  public void test_bang_bang() throws FileNotFoundException {
    IterationLogger logger = new IterationLogger();

    /*
     * Gson gs = new Gson(); JsonReader reader = new JsonReader(new
     * FileReader(Main.MORNING_SCENARIO_FILE)); RoomScenario moringScneario =
     * gs.fromJson(reader, RoomScenario.class);
     * 
     * RoomSimulator w = new RoomSimulator(moringScneario);
     */

    RoomSimulator w = new RoomSimulator(RoomScenario.winterMorning());
    w.setIterationLogger(logger);
    double delta = 0.5;
    Rezult rez = w.simulate((c, d) -> {
      if (c > d + delta) {
        return RoomController.ControllEvent.StopHeating;
      }
      if (c < d - delta) {
        return RoomController.ControllEvent.StartHeating;
      }
      return RoomController.ControllEvent.None;
    });
    double f = 0.7 / (1.0 + rez.incorrectState) + 0.3 / (1.0 + rez.offLimit);
    assertTrue(rez.incorrectState < 30);
    /*
     * System.out.println(f); System.out.println(rez);
     * PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(""),
     * "test_RoomSimulatorTest.jpg");
     */
  }

}
