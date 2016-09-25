package Main;

import java.util.HashMap;

import javax.swing.JFrame;

import Controller.GlobalController;
import Model.FuzzyPVizualModel;
import View.MainView;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.Controller.FuzzyPetriNetSyncornousController;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import exampleNets.SimpleDelayPetriNetBuilder;

public class FuzzyPVizualzer {

  public static FuzzyPVizualModel createModel() {
    SimpleDelayPetriNetBuilder bld = new SimpleDelayPetriNetBuilder();
    HashMap<Integer, FuzzyDriver> ll = new HashMap<>();
    ll.put(2, FuzzyDriver.createDriverFromMinMax(-1.0, 1.0));
    FuzzyPetriNet net = bld.getNet();
    FullRecorder recorder = new FullRecorder();
    FuzzyPetriNetSyncornousController controller = new FuzzyPetriNetSyncornousController(ll, ll, net);
    controller.setRecorderForExecutor(recorder);
    
    HashMap<Integer, Double> inpMap = new HashMap<>();
    for (int i = 0; i < 100; i++) {
    inpMap.put(2, i/100.0);
      
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
