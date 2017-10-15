package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.ArrayList;

public class Walls {

  public static ISegmentProvider getWalls() {
    ArrayList<Point> p = new ArrayList<>();
    p.add(new Point(-0.0, 0.80));
    p.add(new Point(-0.20, 0.80));
    p.add(new Point(-0.0, 0.0));
    return new Points(p, 0.40);
  }

}
