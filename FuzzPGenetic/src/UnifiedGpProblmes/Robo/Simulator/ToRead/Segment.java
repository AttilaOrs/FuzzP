package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.Line;

public class Segment {
  

  final Point start, end;
  final Line holdingLine;

  public Segment(Point start, Point end) {
    this.start = start;
    this.end = end;
    double A = start.y - end.y;
    double B = end.x - start.x;
    double C = start.x * end.y - end.x * start.y;
    holdingLine = new Line(A, B, C);
  }

  public double dist(Point from) {
    Point distStartinPoint = holdingLine.orthogonalFromPoint(from);
    if (outOfStart(distStartinPoint)) {
      // out of bounds;
      distStartinPoint = start;
    } else if (outOfEnd(distStartinPoint)) {
      distStartinPoint = end;
    }
    return dist(from, distStartinPoint);
  }

  private boolean outOfEnd(Point distStartinPoint) {
    return (distStartinPoint.x - end.x) / (end.x - start.x) > 0.0
        || (distStartinPoint.y - end.y) / (end.y - start.y) > 0.0;
  }

  private boolean outOfStart(Point distStartinPoint) {
    return (start.x - end.x) / (distStartinPoint.x - start.x) > 0.0
        || (start.y - end.y) / (distStartinPoint.y - start.y) > 0.0;
  }

  public Optional<Point> commonPoint(Segment other) {
    return other.holdingLine.commonWith(holdingLine)
        .filter(p -> (!outOfEnd(p)) && (!outOfStart(p)) && (!other.outOfEnd(p))
        && (!other.outOfStart(p)));

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
    double d= dist(from, end);
    double x_sol = from.x + (length/d)*(end.x - from.x);
    double y_sol = from.y + (length/d)*(end.y - from.y);
    return new Point(x_sol, y_sol);
  }

  @Override
  public String toString(){
    return start +"---->"+end;
  }
  

}
