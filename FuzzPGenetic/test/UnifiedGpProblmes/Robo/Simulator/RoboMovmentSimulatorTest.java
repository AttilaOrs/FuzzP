package UnifiedGpProblmes.Robo.Simulator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RoboMovmentSimulatorTest {

  @Test
  public void mooves() {
    RoboMovmentSimulator sim = new RoboMovmentSimulator();
    for (int i = 0; i < 100; i++) {
      sim.setLeftCommand(10.0);
      sim.setRightCommand(10.0);
      sim.simulateTimeUnit();
      assertTrue(sim.getX() < 0.001);
    }
    assertTrue(sim.getY() > 6);

  }

}
