package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Points extends AbstactPoints {
  private static final double FIRST_SEGMENT_LENGTH = 0.40;
  public List<Point> Elements;
  transient List<Segment> segments;
  transient Double firstSegLenght;

  public Points() {
    this(new ArrayList<>(), FIRST_SEGMENT_LENGTH);

  }

  public Points(List<Point> p, double firstSegLength) {
    this.Elements = p;
    this.firstSegLenght = firstSegLength;
  }

  @Override
  public Collection<Segment> getLineSegments() {
    if (firstSegLenght == null) {
      firstSegLenght = FIRST_SEGMENT_LENGTH;
    }
    if (segments == null) {
      segments = new ArrayList<>();
      Point p0 = Elements.get(0);
      Point last = new Point(0, 0);
      Double multi = null;
      for (int i = 1; i < Elements.size(); i++) {
        Point newLast = p0.relative(Elements.get(i));
        if (multi == null) {
          // the first segment is 2 m long
          multi = Math.sqrt(newLast.x * newLast.x + newLast.y * newLast.y) * (1.0 / firstSegLenght);
        }
        newLast = new Point(newLast.x / multi, newLast.y / multi);
        segments.add(new Segment(last, newLast));
        last = newLast;
      }
      segments.remove(segments.size() - 1);

    }
    return segments;
  }
  
  
    

  public Points myClone() {
    ArrayList<Point> l = new ArrayList<>();
    for (Point p : Elements) {
      l.add(p.myClone());
    }
    Points toRet = new Points();
    toRet.Elements = l;
    return toRet;

  }



}
