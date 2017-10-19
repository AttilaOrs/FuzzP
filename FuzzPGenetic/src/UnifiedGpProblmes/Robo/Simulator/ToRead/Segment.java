package UnifiedGpProblmes.Robo.Simulator.ToRead;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.Line;

public class Segment {
  

  private static final double EPS = 0.00001;
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
    return from.distance(distStartinPoint);
  }

  private boolean outOfEnd(Point distStartinPoint) {

    if (abs(end.x - start.x) < EPS && abs(end.y - start.y) < EPS) {
      return (abs(end.x - distStartinPoint.x) > EPS) || (abs(end.y - distStartinPoint.y) > EPS);
    }
    if (abs(end.x - start.x) < EPS) {
      return (abs(end.x - distStartinPoint.x) > EPS)
          || signum(end.y - distStartinPoint.y) != signum(distStartinPoint.y - start.y);
    }
    if (abs(end.y - start.y) < EPS) {
      return (abs(end.y - distStartinPoint.y) > EPS)
          || signum(end.x - distStartinPoint.x) != signum(distStartinPoint.x - start.x);
    }

    return signum(end.x - distStartinPoint.x) != signum(distStartinPoint.x - start.x) ||
        signum(end.y - distStartinPoint.y) != signum(distStartinPoint.y - start.y);
  }

  private boolean outOfStart(Point distStartinPoint) {
    if (abs(end.x - start.x) < EPS && abs(end.y - start.y) < EPS) {
      return (abs(start.x - distStartinPoint.x) > EPS) || (abs(start.y - distStartinPoint.y) > EPS);
    }

    if (abs(end.x - start.x) < EPS) {
      return (abs(start.x - distStartinPoint.x) > EPS)
          || signum(start.y - distStartinPoint.y) != signum(start.y - end.y);
    }
    if (abs(end.y - start.y) < EPS) {
      return (abs(start.y - distStartinPoint.y) > EPS)
          || signum(start.x - distStartinPoint.x) != signum(start.x - end.x);
    }
    return signum(start.x - distStartinPoint.x) != signum(start.x - end.x) ||
        signum(start.y - distStartinPoint.y) != signum(start.y - end.y);
  }

  public Optional<Point> commonPoint(Segment other) {

    return other.holdingLine.commonWith(holdingLine)
        .filter(p -> (!outOfEnd(p)) && (!outOfStart(p)) && (!other.outOfEnd(p))
        && (!other.outOfStart(p)));

  }



  public Point getStart() {
    return start;
  }

  public Point getEnd() {
    return end;
  }

  public List<Segment> createSmallerSegments(double length) {
    List<Segment> segments = new ArrayList<>();
    if (start.distance(end) <= length) {
      segments.add(this);
    } else {
      Point p = nextSegmentPoint(start, length);
      segments.add(new Segment(start, p));
      while (p.distance(end) > length) {
        Point newP = nextSegmentPoint(p, length);
        segments.add(new Segment(p, newP));
        p = newP;
      }
      segments.add(new Segment(p, end));

    }
    return segments;
  }

  private Point nextSegmentPoint(Point from, double length) {
    double d = from.distance(end);
    double x_sol = from.x + (length/d)*(end.x - from.x);
    double y_sol = from.y + (length/d)*(end.y - from.y);
    return new Point(x_sol, y_sol);
  }

  @Override
  public String toString(){
    return start +"---->"+end;
  }

  public Segment myClone() {
    return new Segment(start.myClone(), end.myClone());
  }
  

}
