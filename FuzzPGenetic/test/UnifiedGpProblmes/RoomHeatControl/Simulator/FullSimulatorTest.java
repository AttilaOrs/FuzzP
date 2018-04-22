package UnifiedGpProblmes.RoomHeatControl.Simulator;

import org.junit.Test;

import AlgoImpl.IterationLogger;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController.Control;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController.ControllEvent;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullSimulator.Rezult;

public class FullSimulatorTest {

  @Test
  public void test() {
    FullSimulator sim = new FullSimulator(RoomScenario.winterMorning());
    IterationLogger logger = new IterationLogger();
    sim.setIterationLogger(logger);
    
    Rezult rez = sim.simulate((sensorReading, roomTempRef, waterTemp, waterRef) -> {
      return new Control(ControllEvent.StartHeating, 0.0);
    });

  }

}
