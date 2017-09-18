package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.Collection;
import java.util.List;

public interface ISegmentProvider {

  Collection<Segment> getLineSegments();
  
  int segmentsTouchedByPoints(List<Point> path);

}
