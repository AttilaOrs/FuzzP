package UnifiedGpProblmes.Robo.Simulator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.DoubleSummaryStatistics;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;

public class LineSensorsimulator {

  private static final double EPS = 0.003;


  private final double offsetX;
  private final double offsetY;
  private final IRoboMoovmentDescritions robo;
  private final ISegmentProvider segments;

  public LineSensorsimulator(double offsetX, double offsetY, IRoboMoovmentDescritions robo, ISegmentProvider segments) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.robo = robo;
    this.segments = segments;
  }

  public Point currentPos(){
    double alfa = robo.getAlfa();
    return new Point(
        robo.getX() + sin(alfa) * offsetY + cos(alfa) * offsetX,
        robo.getY() + cos(alfa) * offsetY - sin(alfa) * offsetX);
  }

  public boolean isSomthingThere() {
    Point cu = currentPos();
    DoubleSummaryStatistics i = segments.getLineSegments().stream().mapToDouble(s -> s.dist(cu)).summaryStatistics();
    return i.getMin() < EPS;
  }

}
