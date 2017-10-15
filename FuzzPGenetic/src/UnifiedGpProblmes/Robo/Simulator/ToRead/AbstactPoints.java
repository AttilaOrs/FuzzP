package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstactPoints implements ISegmentProvider {

  private transient List<Segment> smallSegments;
  private static final double EPS_TOUCH = 0.01;
  private static final double SEG_LENGHT = 0.05;

  @Override
  public List<Segment> getSmallSegments() {
    if (smallSegments == null) {
      smallSegments = new ArrayList<>();
      for (Segment seg : getLineSegments()) {
        smallSegments.addAll(seg.createSmallerSegments(SEG_LENGHT));
      }
    }
    return smallSegments;
  }

  @Override
  public PathResult smallSegmentsTouchedByPoints(List<Point> path) {
    int touchedAtAll = 0;
    ArrayList<Integer> order = new ArrayList<>();
    for (Segment seg : getSmallSegments()) {
      for (int pointIndex = 0; pointIndex < path.size(); pointIndex++) {
        Point p = path.get(pointIndex);
        double d = seg.dist(p);
        if (d < EPS_TOUCH) {
          touchedAtAll += 1;
          order.add(pointIndex);
          break;
        }
      }
    }
    int touchedInOrder = 0;
    for (int i = 1; i < order.size() - 1; i++) {
      if (order.get(i - 1) <= order.get(i) && order.get(i) < order.get(i + 1)) {
        touchedInOrder++;
      }
    }
    Integer lastIndex = order.get(order.size() - 1);
    return new PathResult(touchedAtAll, touchedInOrder, lastIndex);
  }

}
