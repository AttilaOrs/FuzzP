package Main;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import Controller.GlobalController;
import Model.FuzzyPVizualModel;
import View.MainView;
import config.UnifiedConfigurator;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.common.recoder.FullRecorder;

public class UnifiedVizualizer {

  static String reader = "" +
      "{[<-2><-1>< 0>< 1>< 2>]" +
      " [<-2><-1>< 0>< 1>< 2>]" +
      " [<-2><-1>< 0>< 1>< 2>]" +
      " [<-2><-1>< 0>< 1>< 2>]" +
      " [<-2><-1>< 0>< 1>< 2>]}";

  static String positivEnabled = "{[<FF,FF><FF,FF><FF,FF><1,1><1,2>]}";
  static String neagtiveEnabled = "{[<-1,-2><-1,-1><FF,FF><FF,FF><FF,FF>]}";

  static String allTwoMinus = "@-@" +
      "{[<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]}";

  public static FuzzyPVizualModel<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet> createDefaultModel() {
    UnifiedPetriNet petriNet = new UnifiedPetriNet();
    UnifiedTableParser tableParser = new UnifiedTableParser();
    int p0 = petriNet.addPlace(4.0);
    petriNet.setInitialMarkingForPlace(p0, new UnifiedToken(0.0));
    int iP1 = petriNet.addInputPlace(4.0);
    int p2 = petriNet.addPlace(4.0);
    int t0 = petriNet.addTransition(0, tableParser.parseTwoXOneTable(reader));
    petriNet.addArcFromPlaceToTransition(p0, t0);
    petriNet.addArcFromPlaceToTransition(iP1, t0);
    petriNet.addArcFromTransitionToPlace(t0, p2);

    int t7 = petriNet.addTransition(0, tableParser.parseTwoXOneTable(allTwoMinus));
    petriNet.addArcFromPlaceToTransition(p2, t7);
    int p7 = petriNet.addPlace(4.0);
    petriNet.addArcFromTransitionToPlace(t7, p7);

    int p8 = petriNet.addPlace(4.0);
    petriNet.setInitialMarkingForPlace(p8, new UnifiedToken(-1.000000001));
    int p9 = petriNet.addPlace(4.0);
    int t8 = petriNet.addTransition(0, UnifiedOneXTwoTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p8, t8);
    petriNet.addArcFromTransitionToPlace(t8, p8);
    petriNet.addArcFromTransitionToPlace(t8, p9);
    petriNet.addArcFromPlaceToTransition(p9, t7);

    int t1 = petriNet.addTransition(0, tableParser.parseOneXTwoTable(positivEnabled));
    petriNet.addArcFromPlaceToTransition(p7, t1);
    int p3 = petriNet.addPlace(4.0);
    petriNet.addArcFromTransitionToPlace(t1, p3);
    int ot2 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p3, ot2);
    int p4 = petriNet.addPlace(4.0); // itt folytatod
    petriNet.addArcFromTransitionToPlace(t1, p4);

    int t3 = petriNet.addTransition(0, tableParser.parseOneXTwoTable(neagtiveEnabled));
    petriNet.addArcFromPlaceToTransition(p7, t3);
    int p5 = petriNet.addPlace(4.0);
    petriNet.addArcFromTransitionToPlace(t3, p5);
    int ot4 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p5, ot4);
    int p6 = petriNet.addPlace(4.0); // itt folytatod
    petriNet.addArcFromTransitionToPlace(t3, p6);

    int t5 = petriNet.addTransitionVariableDelay(1.0, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p4, t5);
    petriNet.addArcFromTransitionToPlace(t5, p0);

    int t6 = petriNet.addTransition(1, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p6, t6);
    petriNet.addArcFromTransitionToPlace(t6, p0);

    StringBuilder firstOutput = new StringBuilder();
    StringBuilder secondOuput = new StringBuilder();
    StringBuilder inputs = new StringBuilder();

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(petriNet);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    exec.setRecorder(rec);
    for (int cntr = 0; cntr < 40; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      double inpuValue = ((cntr / 10) + 1.0) * ((cntr % 10 < 5) ? 1.0 : -1.0);
      inp.put(iP1, new UnifiedToken(inpuValue));
      exec.runTick(inp);
      inputs.append(cntr).append(" ").append(inpuValue).append("\n");
    }
    FuzzyPVizualModel<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet> model = new FuzzyPVizualModel<>(
        UnifiedConfigurator::new);
    model.setNet(petriNet);
    model.setFullRecorder(rec);
    return model;
  }

  public static void visualize(UnifiedPetriNet net, FullRecorder<UnifiedToken> rec,
      TransitionPlaceNameStore nameStore) {
    FuzzyPVizualModel<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet> model = new FuzzyPVizualModel<>(
        UnifiedConfigurator::new);
    model.setNet(net);
    model.setFullRecorder(rec);
    model.setNameStore(nameStore);
    GlobalController controller = new GlobalController(model);
    MainView<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet> frame = new MainView<>(model,
        controller, true, true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setResizable(true);
    frame.setVisible(true);

  }

  public static void main(String[] args) {
    FuzzyPVizualModel<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet> model = null;
    GlobalController controller;
      model = createDefaultModel();

    controller = new GlobalController(model);
    MainView<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet> frame = new MainView<>(model,
        controller, true, true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
