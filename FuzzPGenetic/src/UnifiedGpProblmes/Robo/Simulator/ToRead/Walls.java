package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.ArrayList;

public class Walls {

  public static ISegmentProvider getWalls() {
    ArrayList<Segment> p = new ArrayList<>();
    p.add(new Segment(new Point(1.10, 1.90), new Point(+1.30, 1.90)));
    p.add(new Segment(new Point(-0.10, 2.50), new Point(-0.10, 2.9)));
    return new Segments(p);
  }

}
