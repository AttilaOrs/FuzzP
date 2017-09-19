package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.Collection;
import java.util.List;

public interface ISegmentProvider {

  Collection<Segment> getLineSegments();
  
  PathResult smallSegmentsTouchedByPoints(List<Point> path);

  List<Segment> getSmallSegments();
  
  public static class PathResult {
    public final int touchedAtAll;
    public final int touchedInOrder;
    
    public PathResult(int touchedAtAll, int touchedInOrder){
      this.touchedAtAll = touchedAtAll;
      this.touchedInOrder = touchedInOrder;
    }
    
    public String toString(){
      return "touched "+ touchedAtAll +" in order " +touchedInOrder;
    }
  }

}
