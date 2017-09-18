package UnifiedGpProblmes.Robo.Simulator.ToRead;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

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
    if ((start.x - end.x) / (distStartinPoint.x - start.x) > 0.0 || (start.y - end.y) / (distStartinPoint.y - start.y) > 0.0) {
      // out of bounds;
      distStartinPoint = start;
    } else if ((distStartinPoint.x - end.x) / (end.x - start.x) > 0.0 || (distStartinPoint.y - end.y) / (end.y - start.y) > 0.0) {
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

  public Point getStart() {
    return start;
  }

  public Point getEnd() {
    return end;
  }

  public List<Segment> createSmallerSegments(double length) {
    List<Segment> segments = new ArrayList<>();
    if (dist(start, end) <= length) {
      segments.add(this);
    } else {
      Point p = nextSegmentPoint(start, length);
      segments.add(new Segment(start, p));
      while (dist(p, end) > length) {
        Point newP = nextSegmentPoint(p, length);
        segments.add(new Segment(p, newP));
        p = newP;
      }
      segments.add(new Segment(p, end));

    }
    return segments;
  }

  private Point nextSegmentPoint(Point from, double length) {
    if (A != 0.0) {
      double alfa = from.x - C / A;
      double aa = (B * B / (A * A)) + 1;
      double bb = -2.0 * (alfa * (B / A) + from.y);
      double cc = alfa * alfa + from.y * from.y - length * length;
      double[] yy = qudraticSol(aa, bb, cc);
      if (yy == null) {
        double xxx = (from.x + end.x) / 2.0;
        double yyy = (from.y + end.y) / 2.0;
        Point w = new Point(xxx, yyy);
        return w;

      }
      double y_sol = ((from.y - yy[0]) / (yy[0] - end.y) > 0.0) ? yy[0] : yy[1];
      double x_sol = -1.0 * (B * y_sol + C) / A;
      return new Point(x_sol, y_sol);
    } else if (B != 0.0) {
      double dir = (end.x - start.x) / Math.abs(start.x - end.x);
      Point toRet = new Point(from.x + length * dir, from.y);
      return toRet;
    }
    System.err.println("we got a problem");
    return null;
  }

  private double[] qudraticSol(double aa, double bb, double cc) {
    double delta = bb * bb - 4 * aa * cc;
    if (delta < 0.0) {
      return null;
    }
    delta = sqrt(delta);
    return new double[] { (-1.0 * bb - delta) / (2.0 * aa), (-1.0 * bb + delta) / (2.0 * aa) };
  }
  
  @Override
  public String toString(){
    return start +"---->"+end;
  }


}
