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
import core.FuzzyPetriLogic.Tables.OneXTwoTable;

public class FirstOrderControlProportionalController {
	/*
	 * This example impelents a real proprtioanl controller ::::::::::::::::
	 * P[k]= a*e[k] + P(t-1)
	 */


	String reader = "" +
	    "{[<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]}";


	String differentiator = "" +
	    "{[<ZR><PM><PL><PL><PL>]" +
	    " [<NM><ZR><PM><PL><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NL><NM><ZR><PM>]" +
	    " [<NL><NL><NL><NM><ZR>]}";

  
	String doubleChannelAdder = ""//
	    + "{[<NL,NL><NL,NL><NL,NL><NM,NM><ZR,ZR>]" //
	    + " [<NL,NL><NL,NL><NM,NM><ZR,ZR><PM,PM>]" //
	    + " [<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL>]"//
	    + " [<NM,NM><ZR,ZR><PM,PM><PL,PL><PL,PL>]"//
	    + " [<ZR,ZR><PM,PM><PL,PL><PL,PL><PL,PL>]}";
  
  public FirstOrderControlProportionalController() {
		TableParser parser = new TableParser();
    FuzzyPetriNet net = new FuzzyPetriNet();
    
    int p0 = net.addPlace();
    net.setInitialMarkingForPlace(p0, FuzzyToken.zeroToken());
    int t0 = net.addTransition(0, OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p0, t0, 1.0);

    int p1 = net.addPlace();
    net.addArcFromTransitionToPlace(t0, p1);
    int p2InpSys = net.addInputPlace();
		int t1 = net.addTransition(0, parser.parseTable(reader));
    net.addArcFromPlaceToTransition(p1, t1, 1.0);
    net.addArcFromPlaceToTransition(p2InpSys, t1, 1.0);
    int p3 = net.addPlace();
    net.addArcFromTransitionToPlace(t1, p3);
    int p4InpCmd = net.addInputPlace();
		int t2 = net.addTransition(0, parser.parseTable(differentiator));
    net.addArcFromPlaceToTransition(p4InpCmd, t2, 1.0);
    net.addArcFromPlaceToTransition(p3, t2, 1.0);
    int p5 = net.addPlace();
    net.addArcFromTransitionToPlace(t2, p5);
		int t3 = net.addTransition(0, parser.parseTable(doubleChannelAdder));
    net.addArcFromPlaceToTransition(p5, t3, 1.0);
    int p6 = net.addPlace();
    net.addArcFromTransitionToPlace(t3, p6);
    int p7 = net.addPlace();
    net.addArcFromTransitionToPlace(t3, p7);
    int t4delay = net.addTransition(1, OneXTwoTable.defaultTable());
    net.addArcFromPlaceToTransition(p7, t4delay, 1.0);
    net.addArcFromTransitionToPlace(t4delay, p1);
    int p8Mem = net.addPlace();
    net.setInitialMarkingForPlace(p8Mem, FuzzyToken.zeroToken());
    net.addArcFromTransitionToPlace(t4delay, p8Mem);
    net.addArcFromPlaceToTransition(p8Mem, t3, 1.0);

    int t5Out = net.addOuputTransition(OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p6, t5Out, 1.0);



    FuzzyDriver plantInDriver = FuzzyDriver.createDriverFromMinMax(-0.6, +0.6);
    FuzzyDriver userCommandInDriver = FuzzyDriver.createDriverFromMinMax(-0.6, +0.6);
    HashMap<Integer, FuzzyDriver> inputDrivers = new HashMap<>();
    inputDrivers.put(p2InpSys, plantInDriver);
    inputDrivers.put(p4InpCmd, userCommandInDriver);

    FuzzyDriver controlOutDriver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);
    HashMap<Integer, FuzzyDriver> outputDriver = new HashMap<>();
    outputDriver.put(t5Out, controlOutDriver);

    FuzzyPetriNetSyncornousController controller = new FuzzyPetriNetSyncornousController(inputDrivers, outputDriver,
        net);
    FullRecorder recorder = new FullRecorder();
    controller.setRecorderForExecutor(recorder);

    FirstOrderSystem plant = new FirstOrderSystem(0.5, 0.7, 0.2, 0.3);

    double command = 0.6;
    double prevSysOut = 0.0;
    ArrayList<Double> realOuts = new ArrayList<>();

    HashMap<Integer, Double> inputs = new HashMap<>();
    for (int i = 0; i < 50; i++) {
      if (i > 25) {
        command = 0.35;
      }
      inputs.put(p2InpSys, command);
      inputs.put(p4InpCmd, prevSysOut);
      
      Map<Integer, Double> controllerResult = controller.control(inputs);

      double controlOut = 0.0;
      if (!controllerResult.containsKey(t5Out)) {
        System.err.println("command not fired in tick: " + i);
      } else {
        controlOut = controllerResult.get(t5Out);

      }
      prevSysOut = plant.executeSystem(controlOut);
      realOuts.add(prevSysOut);
    }

    MainView mainView = FuzzyPVizualzer.visualize(net, recorder);

    Map<String, List<Double>> forPlot = new HashMap<>();
    forPlot.put("real_out", realOuts);

    Plotter plotter = new Plotter(forPlot);
    mainView.addInteractivePanel("Real out", plotter.makeInteractivePlot());

  }

  public static void main(String args[]) {
    new FirstOrderControlProportionalController();
  }
}
