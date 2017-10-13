package UnifiedGpProblmes.Robo.Simulator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;

public class InfraredSensorSimulator {

  private double ofsetX;
  private double ofsetY;
  private double ofsetAlfa;
  private double range;
  private IRoboMoovmentDescritions robo;
  private ISegmentProvider walls;

  public InfraredSensorSimulator(double ofsetX, double ofsetY, double offsetAlfa, double range,
      IRoboMoovmentDescritions robo, ISegmentProvider walls) {
    this.ofsetX = ofsetX;
    this.ofsetY = ofsetY;
    this.ofsetAlfa = offsetAlfa;
    this.range = range;
    this.robo = robo;
    this.walls = walls;
  }

  public Segment getCurrentState() {
    double xOne = robo.getX()+ofsetX;
    double yOne = robo.getY()+ofsetY;
    double currentAlfa = robo.getAlfa() + ofsetAlfa;
    double xTwo = xOne - sin(currentAlfa) * range;
    double yTwo = yOne + cos(currentAlfa) * range;
    return new Segment(new Point(xOne, yOne), new Point(xTwo, yTwo));
  }

  public double currentValue() {
    // TODO here to cont
    return 0.0;
  }

}
