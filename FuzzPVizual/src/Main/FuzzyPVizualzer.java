package Main;

import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;

import Controller.GlobalController;
import Model.FuzzyPVizualModel;
import View.MainView;
import config.FuzzyConfigurator;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Controller.FuzzyPetriNetSyncornousController;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.common.recoder.FullRecorder;
import exampleNets.TwoLoopNet;

public class FuzzyPVizualzer {

  static FuzzyPVizualModel<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> createModel() {
		TwoLoopNet loopNet = new TwoLoopNet();
		HashMap<Integer, FuzzyDriver> ll = new HashMap<>();
		ll.put(2, FuzzyDriver.createDriverFromMinMax(-1.0, 1.0));
		FuzzyPetriNet net = loopNet.getNet();
    FullRecorder<FuzzyToken> recorder = new FullRecorder<FuzzyToken>();
		FuzzyPetriNetSyncornousController controller = new FuzzyPetriNetSyncornousController(ll, ll, net);
		controller.setRecorderForExecutor(recorder);

		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
			HashMap<Integer, Double> inpMap = new HashMap<>();
			if (i % 4 == 0) {
				inpMap.put(loopNet.p4_inp, rnd.nextDouble());
			}
			controller.control(inpMap);
		}

    FuzzyPVizualModel<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> model = new FuzzyPVizualModel<>(
        wet -> new FuzzyConfigurator());
		model.setNet(net);
		model.setFullRecorder(recorder);
		return model;
	}

  public static MainView<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> visualize(FuzzyPetriNet net,
      FullRecorder<FuzzyToken> recorder,
      TransitionPlaceNameStore nameStrore) {
    FuzzyPVizualModel<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> model = new FuzzyPVizualModel<>(
        nn -> new FuzzyConfigurator());
        model.setNameStore(nameStrore);
        model.setNet(net);
        model.setFullRecorder(recorder);
        GlobalController controller = new GlobalController(model);
    MainView<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> frame = new MainView<>(model, controller, true, true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(true);
        frame.setVisible(true);
        return frame;
    }

  public static MainView<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> visualize(FuzzyPetriNet net,
      FullRecorder<FuzzyToken> recorder) {
    return visualize(net, recorder, TransitionPlaceNameStore.createOrdinarNames(net));
	}

	public static void main(String[] args) {
    FuzzyPVizualModel<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> model = createModel();
		GlobalController controller = new GlobalController(model);
    MainView<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> frame = new MainView<>(model, controller, true, true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
