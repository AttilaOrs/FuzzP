package UnifiedGpProblmes.Robo.Visualizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.FiveSensorLineFollowerRobot;
import UnifiedGpProblmes.Robo.Simulator.LineReader;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segments;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FullRecorder;
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
public class RoboPetriVisulaizerFiveSensors extends Application {

  double currentCoommandBoth = 0.0;
  double currentCoommandDiff = 0.0;
  private TriangleRoboWithSensors s;
  static private UnifiedPetriNet net;
  static private List<Integer> inpsPlaceId;
  static private Integer fiOut, seOut;


  double commonCmd = 0.0;
  double diffCmd = 0.0;
  int cntr = 0;
  List<Optional<Double>> sensorsOut = Arrays.asList(Optional.empty(), Optional.empty(), Optional.empty(),
      Optional.empty(), Optional.empty());
  @Override
  public void start(Stage stage) {

    Pane canvas = new Pane();
    Scene scene = new Scene(canvas, 1000, 1000, Color.WHITE);
    Segments segments = LineReader.getProblem();
    s = new TriangleRoboWithSensors(canvas, new FiveSensorLineFollowerRobot(segments));
    LinesVizualzier viz = new LinesVizualzier(canvas, segments, javafx.scene.paint.Color.BLUE);

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    net.addActionForOuputTransition(fiOut, i -> commonCmd = i.getValue());
    net.addActionForOuputTransition(seOut, i -> diffCmd = i.getValue());
    FullRecorder<UnifiedToken> tk = new FullRecorder<>();
    exec.setRecorder(tk);


    scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Platform.exit();
        // System.exit(0);
      }
    });

    stage.setScene(scene);
    stage.show();

    Map<Integer, UnifiedToken> inp = new HashMap<>();

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent t) {
        inp.clear();
        for(int inpIndex = 0; inpIndex < inpsPlaceId.size(); inpIndex++){
          if(inpsPlaceId.get(inpIndex)!=-1){
            inp.put(inpsPlaceId.get(inpIndex), UnifiedToken.fromOptional(sensorsOut.get(inpIndex)));
          }
          
        }
        exec.runTick(inp);

        double commandR = commonCmd + diffCmd / 2.0;
        double commandL = commonCmd - diffCmd / 2.0;
        sensorsOut = s.setCommandAndUpdate(commandR, commandL);
        commonCmd = 0.0;
        diffCmd = 0.0;
        System.out.println((cntr++) + " " +
            segments.smallSegmentsTouchedByPoints(s.getCurrentPathPoints()));
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }


  public static void main(String[] args) {
    loadMain();
  }

  private static void loadMain() {
    PetriNetJsonSaver<UnifiedPetriNet> load = new PetriNetJsonSaver<UnifiedPetriNet>();
    RoboPetriVisulaizerFiveSensors.net = load.load("Petri_sensor.json", UnifiedPetriNet.class);
    inpsPlaceId = Arrays.asList(385, 390, 401, 413, -1);

    fiOut = 364;
    seOut = 377;
    launch();
  }
}


