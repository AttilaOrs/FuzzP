package UnifiedGpProblmes.Robo.Simulator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;

public class InfraredSensorSimulator {

  /**
   * See <a
   * href="https://www.pololu.com/file/download/GP2D120-DATA-SHEET.pdf?file_id=0J157>
   * data sheet </a> for charateristics
   */
  public static InfraredSensorSimulator createSmall(double ovifsetX, double ofsetY, double offsetAlfa,
      IRoboMoovmentDescritions robo, ISegmentProvider walls) {
    List<double[]> d = new ArrayList<>();
    d.add(new double[] { 0.0, 0.0 });
    d.add(new double[] { 0.7, 1.4 });
    d.add(new double[] { 1.1, 1.85 });
    d.add(new double[] { 2.0, 2.2 });
    d.add(new double[] { 3.0, 3.05 });
    d.add(new double[] { 3.0, 3.05 });
    d.add(new double[] { 3.5, 2.95 });
    d.add(new double[] { 4.0, 2.75 });
    d.add(new double[] { 5.0, 2.35 });
    d.add(new double[] { 6.0, 2.0 });
    d.add(new double[] { 8.0, 1.55 });
    d.add(new double[] { 10.0, 1.25 });
    d.add(new double[] { 12.0, 1.05 });
    d.add(new double[] { 14.0, 0.95 });
    d.add(new double[] { 16.0, 0.8 });
    d.add(new double[] { 20.0, 0.65 });
    d.add(new double[] { 26.0, 0.55 });
    d.add(new double[] { 35.0, 0.35 });
    d.add(new double[] { 40.0, 0.30 });
    return new InfraredSensorSimulator(ovifsetX, ofsetY, offsetAlfa, robo, walls, d, 40.0);
  }


  private double ofsetX;
  private double ofsetY;
  private double ofsetAlfa;
  private double rangeMax;
  private IRoboMoovmentDescritions robo;
  private ISegmentProvider walls;

  private List<double[]> voltageCharateristicCoordinates;

  private InfraredSensorSimulator(double ofsetX, double ofsetY, double offsetAlfa,
      IRoboMoovmentDescritions robo, ISegmentProvider walls, List<double[]> voltageCharateristicCoordinates,
      double rangeMax) {
    this.ofsetX = ofsetX;
    this.ofsetY = ofsetY;
    this.ofsetAlfa = offsetAlfa;
    this.robo = robo;
    this.walls = walls;
    this.voltageCharateristicCoordinates = voltageCharateristicCoordinates;
    this.rangeMax = rangeMax;
  }

  public Segment getCurrentState() {
    double xOne = robo.getX()+ofsetX;
    double yOne = robo.getY()+ofsetY;
    double currentAlfa = robo.getAlfa() + ofsetAlfa;
    double xTwo = xOne - sin(currentAlfa) * rangeMax;
    double yTwo = yOne + cos(currentAlfa) * rangeMax;
    return new Segment(new Point(xOne, yOne), new Point(xTwo, yTwo));
  }

  public double currentValue() {
    Segment curretnState = getCurrentState();
    double currentDist = Double.MAX_VALUE;
    for (Segment wallSegment : walls.getLineSegments()) {
      Optional<Point> i = wallSegment.commonPoint(curretnState);
      if(i.isPresent()){
        double p = i.get().distance(curretnState.getStart());
        currentDist = (currentDist > p) ? p : currentDist;
      }

    }
    return calcSensorValue(currentDist);
  }

  private double calcSensorValue(double currentDist) {
    if (currentDist > this.rangeMax || currentDist < 0.000001) {
      return 0.0;
    } else {
      int index = -1;
      for (int i = 0; i < this.voltageCharateristicCoordinates.size(); i++) {
        if (voltageCharateristicCoordinates.get(i)[0] > currentDist) {
          index = i;
          break;
        }
      }
      double v2 = voltageCharateristicCoordinates.get(index)[1];
      double v1 = voltageCharateristicCoordinates.get(index - 1)[1];
      double d1 = voltageCharateristicCoordinates.get(index - 1)[0];
      double d2 = voltageCharateristicCoordinates.get(index)[0];
      return ((v2 - v1) * ((d1 - currentDist) / (d1 - d2)) - v1) * (-1.0);

    }

  }

}
