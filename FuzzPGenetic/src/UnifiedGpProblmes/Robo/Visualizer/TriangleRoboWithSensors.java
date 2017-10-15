package UnifiedGpProblmes.Robo.Visualizer;

import static java.lang.Math.toDegrees;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.IRobo;
import UnifiedGpProblmes.Robo.Simulator.InfraredSensorSimulator;
import UnifiedGpProblmes.Robo.Simulator.LineSensorsimulator;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class TriangleRoboWithSensors {
  private static final double hight = 0.06;
  public static final double pixel = 170;
  public static final double originalX = 400.0;
  public static final double originalY = 550.0;
  private IRobo roboSim;
  private Circle robo;
  private List<Circle> lineSensors;
  private List<Line> infrared;

  public TriangleRoboWithSensors(Pane pane, ISegmentProvider segProv, IRobo roboSim) {
    this.roboSim = roboSim;
    lineSensors = new ArrayList<>();

    robo = new Circle(pixel * hight);
    robo.setCenterX(originalX);
    robo.setCenterY(originalY);

    pane.getChildren().add(robo);
    for (LineSensorsimulator sensor : roboSim.getLineSensors()) {
      Circle l = new Circle(2.0);
      Point p = sensor.currentPos();
      l.setCenterX(xOnCansvas(p.x));
      l.setCenterY(yOnCansvas(p.y));
      l.setFill(javafx.scene.paint.Color.GREEN);
      lineSensors.add(l);
      pane.getChildren().add(l);
    }
    infrared = new ArrayList<>();
    for (InfraredSensorSimulator infra : roboSim.getInfraredSesors()) {
      Segment currentSeg = infra.getCurrentState();
      Line l = new Line(xOnCansvas(currentSeg.getStart().x), yOnCansvas(currentSeg.getStart().y),
          xOnCansvas(currentSeg.getEnd().x), yOnCansvas(currentSeg.getEnd().y));
      l.setFill(javafx.scene.paint.Color.YELLOW);
      l.setStroke(javafx.scene.paint.Color.YELLOW);
      infrared.add(l);
      pane.getChildren().add(l);
    }
  }

  public List<Optional<Double>> setCommandAndUpdate(double commandR, double commandL) {
    List<Optional<Double>> r = roboSim.simulate(commandR, commandL);
    robo.setRotate(toDegrees(roboSim.getRoboMoovmentSim().getAlfa()));
    robo.setCenterX(xOnCansvas(roboSim.getRoboMoovmentSim().getX()));
    robo.setCenterY(yOnCansvas(roboSim.getRoboMoovmentSim().getY()));
    for (int i = 0; i < roboSim.getLineSensors().size(); i++) {
      LineSensorsimulator sensor = roboSim.getLineSensors().get(i);
      Point p = sensor.currentPos();
      lineSensors.get(i).setCenterX(xOnCansvas(p.x));
      lineSensors.get(i).setCenterY(yOnCansvas(p.y));
      if (sensor.isSomthingThere()) {
        lineSensors.get(i).setFill(javafx.scene.paint.Color.RED);
      } else {
        lineSensors.get(i).setFill(javafx.scene.paint.Color.GREEN);
      }
    }
    for (int i = 0; i < roboSim.getInfraredSesors().size(); i++) {
      InfraredSensorSimulator sensor = roboSim.getInfraredSesors().get(i);
      Line ll = infrared.get(i);
      ll.setStartX(xOnCansvas(sensor.getCurrentState().getStart().x));
      ll.setStartY(yOnCansvas(sensor.getCurrentState().getStart().y));
      ll.setEndX(xOnCansvas(sensor.getCurrentState().getEnd().x));
      ll.setEndY(yOnCansvas(sensor.getCurrentState().getEnd().y));
      if (sensor.currentValue() > 0.0001) {
        ll.setFill(javafx.scene.paint.Color.ORANGE);
        ll.setStroke(javafx.scene.paint.Color.ORANGE);
      } else {
        ll.setFill(javafx.scene.paint.Color.YELLOW);
        ll.setStroke(javafx.scene.paint.Color.YELLOW);
      }

    }
    return r;
  }

  public static double xOnCansvas(double x) {
    return originalX + x * pixel;
  }

  public static double yOnCansvas(double y) {
    return originalY - y * pixel;
  }
  
  public List<Point> getCurrentPathPoints(){
    return roboSim.getVisitedPoints();
  }

}
