package UnifiedGpProblmes.Robo.Visualizer;


import java.util.HashMap;
import java.util.Map;

import UnifiedGpProblmes.Robo.Simulator.Lines;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.tokencache.TokenCacheDisabling;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RoboPetriVizualizer extends Application {
  double currentCoommandBoth = 0.0;
  double currentCoommandDiff = 0.0;
  private TriangleRoboWithSensors s;
  static private UnifiedPetriNet net;
  static private int fiInp, seInp, fiOut, seOut;


  double commonCmd = 0.0;
  double diffCmd = 0.0;
  int cntr = 0;
  boolean[] sensorsOut = new boolean[] { false, false };
  @Override
  public void start(Stage stage) {

    Pane canvas = new Pane();
    Scene scene = new Scene(canvas, 1000, 1000, Color.WHITE);
    s = new TriangleRoboWithSensors(canvas, Lines.getPoint());
    LinesVizualzier viz = new LinesVizualzier(canvas, Lines.getPoint());

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    net.addActionForOuputTransition(fiOut, i -> commonCmd = i.getValue());
    net.addActionForOuputTransition(seOut, i -> diffCmd = i.getValue());


    scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
      }
    });

    stage.setScene(scene);
    stage.show();

    Map<Integer, UnifiedToken> inp = new HashMap<>();

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent t) {
        inp.clear();
        inp.put(fiInp, sensorsOut[0] ? new UnifiedToken(1.0) : new UnifiedToken());
        inp.put(seInp, sensorsOut[1] ? new UnifiedToken(1.0) : new UnifiedToken());
        exec.runTick(inp);

        double commandR = commonCmd + diffCmd / 2.0;
        double commandL = commonCmd - diffCmd / 2.0;
        sensorsOut = s.setCommandAndUpdate(commandR, commandL);
        commonCmd = 0.0;
        diffCmd = 0.0;
        System.out.println(cntr++);
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public static void main(String[] args) {
    PetriNetJsonSaver<UnifiedPetriNet> load = new PetriNetJsonSaver<UnifiedPetriNet>();
    RoboPetriVizualizer.net = load.load("Petri.json", UnifiedPetriNet.class);
    fiInp = 418;
    seInp = 430;
    fiOut = 317;
    seOut = 334;
    launch();
  }
}