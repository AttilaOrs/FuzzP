package UnifiedGpProblmes.Robo.Visualizer;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;
import UnifiedGpProblmes.Robo.TwoSensorsLineFallowerFitnes;
import UnifiedGpProblmes.Robo.Simulator.LineReader;
import UnifiedGpProblmes.Robo.Simulator.TwoSensorLineFallowerRobot;
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

public class RoboPetriVizualizer extends Application {
  double currentCoommandBoth = 0.0;
  double currentCoommandDiff = 0.0;
  private TriangleRoboWithSensors s;
  static private UnifiedPetriNet net;
  static private int fiInp, seInp, fiOut, seOut;


  double commonCmd = 0.0;
  double diffCmd = 0.0;
  int cntr = 0;
  List<Optional<Double>> sensorsOut = Arrays.asList(Optional.empty(), Optional.empty());
  @Override
  public void start(Stage stage) {

    Pane canvas = new Pane();
    Scene scene = new Scene(canvas, 1000, 1000, Color.WHITE);
    Segments segments = LineReader.getProblem();
    s = new TriangleRoboWithSensors(canvas, new TwoSensorLineFallowerRobot(segments));
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
        inp.put(fiInp, UnifiedToken.fromOptional(sensorsOut.get(0)));
        inp.put(seInp, UnifiedToken.fromOptional(sensorsOut.get(1)));
        exec.runTick(inp);

        double commandR = commonCmd + diffCmd / 2.0;
        double commandL = commonCmd - diffCmd / 2.0;
        sensorsOut = s.setCommandAndUpdate(commandR, commandL);
        System.out.println(commandL + " " + commandR + "[" + diffCmd + "]" + commonCmd);
        commonCmd = 0.0;
        diffCmd = 0.0;
        // System.out.println((cntr++) + " "+
        // segments.smallSegmentsTouchedByPoints(s.getCurrentPathPoints()));
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public static void handMain() {
    double rot = 3.0;
    double sp = 1.0;

    INode<NodeType> seqFF = new InnerNode(NodeType.Seq, new InputLeaf(InputType.EnableIfNonPhi, 1),
        new ConstantLeaf(rot * -1.0));
    INode<NodeType> seqF = new InnerNode(NodeType.Seq, seqFF,
        new InnerNode(NodeType.Seq, new OutputLeaf(1, OutType.Copy), new DelayLeaf(1)));
    INode<NodeType> seqSS = new InnerNode(NodeType.Seq, new InputLeaf(InputType.EnableIfNonPhi, 0),
        new ConstantLeaf(rot));
    INode<NodeType> seqS = new InnerNode(NodeType.Seq, seqSS, 
        new InnerNode(NodeType.Seq, new OutputLeaf(1, OutType.Copy), new DelayLeaf(1)));
    InnerNode select = new InnerNode(NodeType.Selc, seqS, seqF);
    InnerNode bigSelect = new InnerNode(NodeType.Selc, select, new DelayLeaf(1));
    InnerNode seq = new InnerNode(NodeType.Seq, new ConstantLeaf(sp), new OutputLeaf(0, OutType.Copy));
    InnerNode conc = new InnerNode(NodeType.Conc, seq, bigSelect);
    IInnerNode<NodeType> root = new InnerNode(NodeType.Loop, conc, new DelayLeaf(1));
    UnifiedGpIndi rez = new UnifiedGpIndi(root);
    TwoSensorsLineFallowerFitnes mm = new TwoSensorsLineFallowerFitnes(LineReader.getProblem());
    double rr = mm.evaluate(rez);
    System.out.println(rr);
    PetriNetJsonSaver<UnifiedPetriNet> load = new PetriNetJsonSaver<UnifiedPetriNet>();
    RoboPetriVizualizer.net = mm.getRez().net;
    fiInp = mm.getRez().inpNrToInpPlace.get(0);
    seInp = mm.getRez().inpNrToInpPlace.get(1);
    fiOut = mm.getRez().outNrToOutTr.get(0);
    seOut = mm.getRez().outNrToOutTr.get(1);
    launch();
  }

  public static void main(String[] args) {
    loadMain();
    // handMain();
  }

  private static void loadMain() {
    PetriNetJsonSaver<UnifiedPetriNet> load = new PetriNetJsonSaver<UnifiedPetriNet>();
    RoboPetriVizualizer.net = load.load("Petri.json", UnifiedPetriNet.class);
    fiInp = 422;
    seInp = 437;
    fiOut = 327;
    seOut = 339;
    launch();
  }
}