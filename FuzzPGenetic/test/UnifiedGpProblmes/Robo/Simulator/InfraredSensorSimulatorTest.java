package UnifiedGpProblmes.Robo.Simulator;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;

public class InfraredSensorSimulatorTest {

  private static final double EPS = 0.0000000000000000000000000000001;
  private ISegmentProvider walls;
  private IRoboMoovmentDescritions robo;

  @Before
  public void setup() {
    walls = mock(ISegmentProvider.class);
    when(walls.getLineSegments()).thenReturn(Arrays.asList(new Segment(new Point(0.10, 0.50), new Point(1.00, 0.50))));
    robo = mock(IRoboMoovmentDescritions.class);
  }

  private void putRobot(double x, double y, double alfa) {
    when(robo.getX()).thenReturn(x);
    when(robo.getY()).thenReturn(y);
    when(robo.getAlfa()).thenReturn(alfa);
  }

  @Test
  public void notSeesingAnything() {
    putRobot(0, 0, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, walls);

    assertTrue(underTest.currentValue() < EPS);
  }

  @Test
  public void toFar() {
    putRobot(0.20, 0, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, walls);

    assertTrue(underTest.currentValue() < EPS);
  }

  @Test
  public void thereIsSomethng() {
    putRobot(0.20, 0.15, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, walls);

    assertTrue(underTest.currentValue() - 0.35 < EPS);
  }

  @Test
  public void offsetAngle() {
    putRobot(0.20, 0.15, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, Math.PI / 2, robo, walls);

    assertTrue(underTest.currentValue() < EPS);
  }

  @Test
  public void offsetAngleSecond() {
    putRobot(0.15, 0.30, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, -Math.PI / 4, robo, walls);

    assertTrue(underTest.currentValue() < EPS);
  }

  @Test
  public void offsetAngleThird() {
    putRobot(0.15, 0.40, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, Math.PI / 4, robo, walls);

    assertTrue(underTest.currentValue() > 0.95);
  }

}
