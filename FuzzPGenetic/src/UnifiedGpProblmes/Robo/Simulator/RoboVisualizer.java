package UnifiedGpProblmes.Robo.Simulator;


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
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RoboVisualizer extends Application {
  int originalX = 400;
  int originalY = 400;
  RoboMovmentSimulator sim = new RoboMovmentSimulator();
  double currentCoommandR = 0.0;
  double currentCoommandL = 0.0;
  protected double pixel = 5.0;

  @Override
  public void start(Stage stage) {

    Pane canvas = new Pane();
    Scene scene = new Scene(canvas, 800, 800, Color.TRANSPARENT);
    Polygon ball = new Polygon();
    ball.getPoints().addAll(new Double[] {
        5.0, 5.0,
        0.0, 0.0,
        10.0, 0.0 });
    ball.relocate(originalX, originalY);

    canvas.getChildren().add(ball);

    stage.initStyle(StageStyle.TRANSPARENT);
    scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
      }
    });
    scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode().equals(KeyCode.Q)) {
        currentCoommandL += 1.0;
      }
      if (e.getCode().equals(KeyCode.W)) {
        currentCoommandL -= 1.0;
      }
      if (e.getCode().equals(KeyCode.O)) {
        currentCoommandR += 1.0;
      }
      if (e.getCode().equals(KeyCode.P)) {
        currentCoommandR -= 1.0;
      }

    });
    stage.setScene(scene);
    stage.show();

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent t) {
        sim.setLeftCommand(currentCoommandL);
        sim.setRightCommand(currentCoommandR);
        sim.simulateTimeUnit();
        double newX = originalX + sim.getX() * pixel;
        double newY = originalY + sim.getY() * pixel;
        ball.setRotate(360 - Math.toDegrees(sim.getAlfa()));
        ball.relocate(newX, newY);
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public static void main(String[] args) {
    launch();
  }
}