package UnifiedGpProblmes.RoomHeatControl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import AlgoImpl.IterationLogger;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomController;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomSimulator;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomSimulator.Rezult;
import commonUtil.PlotUtils;

public class RoomSimulatorTest {

  int cntr = 0;

  @Test
  public void test() {
    IterationLogger logger = new IterationLogger();
    RoomSimulator w = new RoomSimulator(RoomScenario.winterMorning());
    w.setIterationLogger(logger);
    Rezult rez = w.simulate(c -> {
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
    System.out.println(">>>>" + rez);
    assertTrue(rez.heatrerOn == 90);
  }

  @Test
  public void test_bang_bang() {
    IterationLogger logger = new IterationLogger();
    RoomSimulator w = new RoomSimulator(RoomScenario.winterMorning());
    w.setIterationLogger(logger);
    Rezult rez = w.simulate(c -> {
      if (c > RoomSimulator.upper_Limit_acc) {
        return RoomController.ControllEvent.StopHeating;
      }
      if (c < RoomSimulator.lower_Limit_acc) {
        return RoomController.ControllEvent.StartHeating;
      }
      return RoomController.ControllEvent.None;
    });
    PlotUtils.plot(logger.getLogsForPlottingContatinigStrings(""),
        "test_RoomSimulatorTest.jpg");
    System.out.println(rez);
  }

}
