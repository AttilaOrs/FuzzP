package examples.syncronous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.FuzzyPVizualzer;
import Main.Plotter;
import View.MainView;
import core.TableParser;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Controller.FuzzyPetriNetSyncornousController;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;

public class FirstOderControlExampleSimplest {
	/*
	 * This example shows how to implement a Petri net which tryes to control
	 * the a FOS by multipliying the error Something like: P[k] = Kp*e[k]
	 * 
	 */ 

	String reader = "" +
	    "{[<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]}";

	String differentiator = "" +
	    "{[<ZR><NM><NL><NL><NL>]" +
	    " [<PM><ZR><NM><NL><NL>]" +
	    " [<PL><PM><ZR><NM><NL>]" +
	    " [<PL><PL><PM><ZR><NM>]" +
	    " [<PL><PL><PL><PM><ZR>]}";

	String doubleChannelDifferentiator = ""//
	    + "{[<ZR,ZR><NM,NM><NL,NL><NL,NL><NL,NL>]" //
	    + " [<PM,PM><ZR,ZR><NM,NM><NL,NL><NL,NL>]" //
	    + " [<PL,PL><PM,PM><ZR,ZR><NM,NM><NL,NL>]"//
	    + " [<PL,PL><PL,PL><PM,PM><ZR,ZR><NM,NM>]"//
	    + " [<PL,PL><PL,PL><PL,PL><PM,PM><ZR,ZR>]}";


  public FirstOderControlExampleSimplest() {
		TableParser parser = new TableParser();

    FuzzyPetriNet net = new FuzzyPetriNet();
    int p0 = net.addPlace();
    net.setInitialMarkingForPlace(p0, FuzzyToken.zeroToken());

    int t0 = net.addTransition(0, OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p0, t0, 1.0);

    int p1 = net.addPlace();
    net.addArcFromTransitionToPlace(t0, p1);
    int p2Inp = net.addInputPlace();
		int t1 = net.addTransition(0, parser.parseTable(reader));
    net.addArcFromPlaceToTransition(p1, t1, 1.0);
    net.addArcFromPlaceToTransition(p2Inp, t1, 1.0);
    int p3 = net.addPlace();
    net.addArcFromTransitionToPlace(t1, p3);
    int p4Inp = net.addInputPlace();
		int t2 = net.addTransition(0, parser.parseTable(doubleChannelDifferentiator));
    net.addArcFromPlaceToTransition(p3, t2, 1.0);
    net.addArcFromPlaceToTransition(p4Inp, t2, 1.0);
    int p5 = net.addPlace();
    int p6 = net.addPlace();
    net.addArcFromTransitionToPlace(t2, p5);
    net.addArcFromTransitionToPlace(t2, p6);
    int t3 = net.addTransition(1, OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p5, t3, 1.0);
    net.addArcFromTransitionToPlace(t3, p1);

    int t4Out = net.addOuputTransition(OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p6, t4Out, 2.5);
    
    FuzzyDriver plantInDriver = FuzzyDriver.createDriverFromMinMax(-0.6, +0.6);
    FuzzyDriver userCommandInDriver = FuzzyDriver.createDriverFromMinMax(-0.6, +0.6);
    HashMap<Integer, FuzzyDriver> inputDrivers = new HashMap<>();
    inputDrivers.put(p2Inp, plantInDriver);
    inputDrivers.put(p4Inp, userCommandInDriver);
    
    FuzzyDriver controlOutDriver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);
    HashMap<Integer, FuzzyDriver> outputDriver = new HashMap<>();
    outputDriver.put(t4Out, controlOutDriver);
    
    FuzzyPetriNetSyncornousController controller = new FuzzyPetriNetSyncornousController(inputDrivers, outputDriver,
        net);
    FullRecorder recorder = new FullRecorder();
    controller.setRecorderForExecutor(recorder);


    FirstOrderSystem plant = new FirstOrderSystem(0.5, 0.7, 0.2, 0.3);

    double command = 0.6;
    double prevSysOut = 0.0;
    ArrayList<Double> realOuts = new ArrayList<>();


    // now we simulate the colsed loop behaviour of the system
    HashMap<Integer , Double> inputs = new HashMap<>();
    for (int i = 0; i < 50; i++) {
      if(i>25){
    	  command = 0.35;
      }
      inputs.put(p2Inp, command);
      inputs.put(p4Inp, prevSysOut);
      
      Map<Integer, Double> controllerResult = controller.control(inputs);
    	
      double controlOut = 0.0;
      if (!controllerResult.containsKey(t4Out)) {
        // Atention!! in this example never heappens but if the Petri-net does
        // not fire in the current tick the output will be null;
        System.err.println("command not fired in tick: " + i);
      } else {
        controlOut = controllerResult.get(t4Out);

      }
      prevSysOut = plant.executeSystem(controlOut);
      realOuts.add(prevSysOut);
    }

    /*
     * Usage of the viusalizer: You can select the a place or transition by left
     * click and discelect with right click. If you select a place the evolution
     * of the token is drawn in the plot view. The point marc here when a token
     * enters or leavs the place: the lines are only visual helpers. It you
     * select a transition you can view the incorportaed table in the table
     * view.
     * 
     */
    MainView mainView = FuzzyPVizualzer.visualize(net, recorder);

    // we also plot the real ouput of the system to a file
    Map<String, List<Double>> forPlot = new HashMap<>();
    forPlot.put("real_out", realOuts);

    Plotter plotter = new Plotter(forPlot);
    mainView.addInteractivePanel("Real out", plotter.makeInteractivePlot());
  }

  public static void main(String args[]) {
    new FirstOderControlExampleSimplest();

  }

}
