package UnifiedGpProblmes.Robo.Visualizer;

import static UnifiedGpProblmes.Robo.Visualizer.TriangleRoboWithSensors.xOnCansvas;
import static UnifiedGpProblmes.Robo.Visualizer.TriangleRoboWithSensors.yOnCansvas;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class LinesVizualzier {

  public LinesVizualzier(Pane pane, ISegmentProvider segProv) {
    for(Segment segment :segProv.getLineSegments()){
      Line l = new Line(xOnCansvas(segment.getStart().x), yOnCansvas(segment.getStart().y),
          xOnCansvas(segment.getEnd().x), yOnCansvas(segment.getEnd().y));
      pane.getChildren().add(l);
    }
    

  }
}
