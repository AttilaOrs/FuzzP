package UnifiedGpProblmes.Robo.Visualizer;


import UnifiedGpProblmes.Robo.Simulator.Lines;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RoboVisualizer extends Application {
  private static final double SENS = 0.5;
  private static final double CNTR = 1.0;
  double currentCoommandBoth = 0.0;
  double currentCoommandDiff = 0.0;
  private TriangleRoboWithSensors s;

  @Override
  public void start(Stage stage) {

    Pane canvas = new Pane();
    Scene scene = new Scene(canvas, 1000, 1000, Color.TRANSPARENT);
    s = new TriangleRoboWithSensors(canvas, Lines.getPoint());
    LinesVizualzier viz = new LinesVizualzier(canvas, Lines.getPoint());


    stage.initStyle(StageStyle.TRANSPARENT);
    scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
      }
    });
    scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode().equals(KeyCode.LEFT)) {
        currentCoommandDiff += SENS;
      }
      if (e.getCode().equals(KeyCode.UP)) {
        currentCoommandBoth += CNTR;
      }
      if (e.getCode().equals(KeyCode.RIGHT)) {
        currentCoommandDiff -= SENS;
      }
      if (e.getCode().equals(KeyCode.DOWN)) {
        currentCoommandBoth -= CNTR;
      }
    });
    scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
      if (e.getCode().equals(KeyCode.RIGHT) || e.getCode().equals(KeyCode.LEFT)) {
        currentCoommandDiff = 0.0;
      }

    });

    stage.setScene(scene);
    stage.show();

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent t) {
        double leftC = (currentCoommandBoth + currentCoommandDiff / 2);
        double rightC = (currentCoommandBoth - currentCoommandDiff / 2);
        s.setCommandAndUpdate(rightC, leftC);
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public static void main(String[] args) {
    launch();
  }
}