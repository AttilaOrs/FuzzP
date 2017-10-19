package UnifiedGpProblmes.Robo.Simulator;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segments;

public class InfraredSensorSimulatorTest {

  private static final double EPS = 0.0000000000000000000000000000001;
  private ISegmentProvider horizontalWall;
  private IRoboMoovmentDescritions robo;
  private ISegmentProvider verticalWall;

  @Before
  public void setup() {
    horizontalWall = mock(ISegmentProvider.class);
    when(horizontalWall.getLineSegments()).thenReturn(Arrays.asList(new Segment(new Point(0.10, 0.50), new Point(1.00, 0.50))));
    verticalWall = mock(ISegmentProvider.class);
    when(verticalWall.getLineSegments())
        .thenReturn(Arrays.asList(new Segment(new Point(0.50, 0.50), new Point(0.50, 1.50))));
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
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, horizontalWall);

    assertTrue(underTest.currentValue() < EPS);
  }

  @Test
  public void toFar() {
    putRobot(0.20, 0, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, horizontalWall);

    assertTrue(underTest.currentValue() < EPS);
  }

  @Test
  public void thereIsSomethng() {
    putRobot(0.20, 0.15, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, horizontalWall);

    assertTrue(underTest.currentValue() - 0.35 < EPS);
  }

  @Test
  public void offsetAngle() {
    putRobot(0.20, 0.15, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, Math.PI / 2, robo, horizontalWall);

    assertTrue(underTest.currentValue() < EPS);
  }

  @Test
  public void offsetAngleSecond() {
    putRobot(0.15, 0.30, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, -Math.PI / 4, robo, horizontalWall);

    assertTrue(underTest.currentValue() < EPS);
  }

  @Test
  public void offsetAngleThird() {
    putRobot(0.15, 0.40, 0);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, Math.PI / 4, robo, horizontalWall);

    assertTrue(underTest.currentValue() > 0.95);
  }

  @Test
  public void inTheBack() {
    putRobot(0.50, 0.70, Math.PI);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, horizontalWall);
    assertTrue(underTest.currentValue() > 0.90);
  }

  @Test
  public void verticalWall() {
    putRobot(0.75, 1.0, -Math.PI / 2);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, verticalWall);
    assertTrue(underTest.currentValue() > 0.5);
  }

  @Test
  public void verticalWallOtherSide() {
    putRobot(0.25, 1.0, Math.PI / 2);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, 0, robo, verticalWall);
    assertTrue(underTest.currentValue() > 0.5);
  }

  @Test
  public void verticalWallOtherAgain() {
    putRobot(0.25, 1.0, Math.PI / 2);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(0, 0, -Math.PI / 18.0, robo, verticalWall);
    assertTrue(underTest.currentValue() > 0.5);
  }

  @Test
  public void realWalls() {
    putRobot(0.14, 2.5, -0.79);

    ArrayList<Segment> p = new ArrayList<>();
    p.add(new Segment(new Point(1.10, 1.90), new Point(+1.30, 1.90)));
    p.add(new Segment(new Point(-0.10, 2.50), new Point(-0.10, 2.9)));
    Segments walls = new Segments(p);
    InfraredSensorSimulator underTest = InfraredSensorSimulator.createSmall(-0.03, 0.06, -0.1745, robo,
        walls);
    assertTrue(underTest.currentValue() > 0.5);
  }

}
