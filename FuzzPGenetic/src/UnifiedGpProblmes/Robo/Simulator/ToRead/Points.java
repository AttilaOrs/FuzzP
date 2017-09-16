package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Points implements ISegmentProvider {
  List<Point> Elements;
  transient List<Segment> segments;

  @Override
  public Collection<Segment> getLineSegments() {
    if (segments == null) {
      segments = new ArrayList<>();
      Point p0 = Elements.get(0);
      Point last = new Point(0, 0);
      Double multi = null;
      for (int i = 1; i < Elements.size(); i++) {
        Point newLast = p0.relative(Elements.get(i));
        if (multi == null) {
          // the first segment is 2 m long
          multi = Math.sqrt(newLast.x * newLast.x + newLast.y * newLast.y) * 0.5;
        }
        newLast = new Point(newLast.x / multi, newLast.y / multi);
        segments.add(new Segment(last, newLast));
        last = newLast;
      }
    }
    return segments;
  }



}
