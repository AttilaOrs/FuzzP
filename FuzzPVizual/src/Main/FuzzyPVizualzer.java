package Main;

import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;

import Controller.GlobalController;
import Model.FuzzyPVizualModel;
import View.MainView;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.Controller.FuzzyPetriNetSyncornousController;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import exampleNets.SelectionLikeTwoBranchExample;
import exampleNets.SimpleDelayPetriNetBuilder;

public class FuzzyPVizualzer {

  public static FuzzyPVizualModel createModel() {
    SelectionLikeTwoBranchExample bld = new SelectionLikeTwoBranchExample();
    int P1_inp  = 1;
    int P2_inp  = 2;
    HashMap<Integer, FuzzyDriver> ll = new HashMap<>();
    ll.put(2, FuzzyDriver.createDriverFromMinMax(-1.0, 1.0));
    FuzzyPetriNet net = bld.getNet();
    FullRecorder recorder = new FullRecorder();
    FuzzyPetriNetSyncornousController controller = new FuzzyPetriNetSyncornousController(ll, ll, net);
    controller.setRecorderForExecutor(recorder);
    
    Random rnd =  new Random();
    for (int i = 0; i < 100; i++) {
		HashMap<Integer, Double> inpMap = new HashMap<>();
		if(i%4 == 0){
			inpMap.put(P1_inp, rnd.nextDouble());
		} else if(i%5 == 2){
			inpMap.put(P2_inp, rnd.nextDouble());
		}
      controller.control(inpMap);
    }


    FuzzyPVizualModel model = new FuzzyPVizualModel();
    model.setNet(net);
    model.setFullRecorder(recorder);
    return model;
  }

  public static MainView visualize(FuzzyPetriNet net, FullRecorder recorder) {
    FuzzyPVizualModel model = new FuzzyPVizualModel();
    model.setNet(net);
    model.setFullRecorder(recorder);
    GlobalController controller = new GlobalController(model);
    MainView frame = new MainView(model, controller);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    frame.setResizable(true);
    frame.setVisible(true);
    return frame;
  }

  public static void main(String[] args) {
    FuzzyPVizualModel model = createModel();
    GlobalController controller = new GlobalController(model);
    MainView frame = new MainView(model, controller);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setResizable(true);
    frame.setVisible(true);
  }

}
