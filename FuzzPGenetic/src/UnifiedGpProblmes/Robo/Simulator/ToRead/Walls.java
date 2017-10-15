package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.ArrayList;

public class Walls {

  public static ISegmentProvider getWalls() {
    ArrayList<Segment> p = new ArrayList<>();
    p.add(new Segment(new Point(0.90, 1.90), new Point(+1.50, 1.90)));
    p.add(new Segment(new Point(-0.10, 2.40), new Point(-0.10, 3.00)));
    return new Segments(p);
  }

}
