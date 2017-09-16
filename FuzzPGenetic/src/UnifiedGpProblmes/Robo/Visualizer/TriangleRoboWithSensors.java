package UnifiedGpProblmes.Robo.Visualizer;

import static java.lang.Math.toDegrees;

import java.util.ArrayList;
import java.util.List;

import UnifiedGpProblmes.Robo.Simulator.LineSensorsimulator;
import UnifiedGpProblmes.Robo.Simulator.TwoSensorLineFallowerRobot;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class TriangleRoboWithSensors {
  private static final double base = 0.08;
  private static final double hight = 0.06;
  public static final double pixel = 170;
  public static final double originalX = 400.0;
  public static final double originalY = 650.0;
  private TwoSensorLineFallowerRobot roboSim;
  private Circle robo;
  private List<Circle> sensors;

  public TriangleRoboWithSensors(Pane pane, ISegmentProvider segProv) {
    roboSim = new TwoSensorLineFallowerRobot(segProv);
    sensors = new ArrayList<>();

    robo = new Circle(pixel * hight);
    robo.setCenterX(originalX);
    robo.setCenterY(originalY);

    pane.getChildren().add(robo);
    for (LineSensorsimulator sensor : roboSim.getSensors()) {
      Circle l = new Circle(2.0);
      Point p = sensor.currentPos();
      l.setCenterX(xOnCansvas(p.x));
      l.setCenterY(yOnCansvas(p.y));
      l.setFill(javafx.scene.paint.Color.GREEN);
      sensors.add(l);
      pane.getChildren().add(l);
    }
  }

  public void setCommandAndUpdate(double commandR, double commandL) {
    boolean[] r = roboSim.simulate(commandR, commandL);
    robo.setRotate(toDegrees(roboSim.getRoboMoovmentSim().getAlfa()));
    robo.setCenterX(xOnCansvas(roboSim.getRoboMoovmentSim().getX()));
    robo.setCenterY(yOnCansvas(roboSim.getRoboMoovmentSim().getY()));
    for (int i = 0; i < roboSim.getSensors().size(); i++) {
      LineSensorsimulator sensor = roboSim.getSensors().get(i);
      Point p = sensor.currentPos();
      sensors.get(i).setCenterX(xOnCansvas(p.x));
      sensors.get(i).setCenterY(yOnCansvas(p.y));
      if (sensor.isSomthingThere()) {
        sensors.get(i).setFill(javafx.scene.paint.Color.RED);
      } else {
        sensors.get(i).setFill(javafx.scene.paint.Color.GREEN);
      }
    }

  }

  public static double xOnCansvas(double x) {
    return originalX + x * pixel;
  }

  public static double yOnCansvas(double y) {
    return originalY - y * pixel;
  }

}
