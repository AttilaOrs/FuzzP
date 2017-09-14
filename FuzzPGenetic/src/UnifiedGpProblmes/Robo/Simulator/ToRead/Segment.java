package UnifiedGpProblmes.Robo.Simulator.ToRead;

public class Segment {
  
  final Point start, end;
  final Double A, B, C;

  public Segment(Point start, Point end) {
    this.start = start;
    this.end = end;
    A = start.y - end.y;
    B = end.x - start.x;
    C = start.x * end.y - end.x * start.y;
  }

  public double dist(Point from) {
    double den = A * A + B * B;
    double c_y = A * A * from.y - A * B * from.x - B * C;
    c_y = c_y / den;
    double c_x = B * B * from.x - A * B * from.y - A * C;
    c_x = c_x / den;
    Point distStartinPoint = new Point(c_x, c_y);
    if ((start.x - end.x) / (distStartinPoint.x - start.x) > 0.0) {
      // out of bounds;
      distStartinPoint = start;
    } else if ((distStartinPoint.x - end.x) / (end.x - start.x) > 0.0) {
      distStartinPoint = end;
    }
    return dist(from, distStartinPoint);
  }

  private static double dist(Point from, Point distStartinPoint) {
    double x = from.x - distStartinPoint.x;
    x *= x;
    double y = from.y - distStartinPoint.y;
    y *= y;
    return Math.sqrt(x + y);
  }

}
