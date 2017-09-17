package UnifiedGpProblmes.Robo.Visualizer;

import static UnifiedGpProblmes.Robo.Visualizer.TriangleRoboWithSensors.xOnCansvas;
import static UnifiedGpProblmes.Robo.Visualizer.TriangleRoboWithSensors.yOnCansvas;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class LinesVizualzier {

  public LinesVizualzier(Pane pane, ISegmentProvider segProv) {
    int cntr = 0;
    for(Segment segment :segProv.getLineSegments()){
      Line l = new Line(xOnCansvas(segment.getStart().x), yOnCansvas(segment.getStart().y),
          xOnCansvas(segment.getEnd().x), yOnCansvas(segment.getEnd().y));
      if (cntr % 2 == 0) {
        l.setFill(javafx.scene.paint.Color.BLUE);
        l.setStroke(javafx.scene.paint.Color.BLUE);
      } else {
        l.setFill(javafx.scene.paint.Color.BLACK);
        l.setStroke(javafx.scene.paint.Color.BLACK);
      }
      cntr++;

      pane.getChildren().add(l);
    }
    

  }
}
