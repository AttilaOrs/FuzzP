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

public class FirstOrderPIControl {
	/*
	 * This example impelents P[k] = a*e[k] + b*e[k-1] + P[k-1]
	 */

	String reader = "" +
	    "{[<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NL><NM><ZR><PM><PL>]}";



	String doubleChannelAdder = ""//
	    + "{[<NL,NL><NL,NL><NL,NL><NM,NM><ZR,ZR>]" //
	    + " [<NL,NL><NL,NL><NM,NM><ZR,ZR><PM,PM>]" //
	    + " [<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL>]"//
	    + " [<NM,NM><ZR,ZR><PM,PM><PL,PL><PL,PL>]"//
	    + " [<ZR,ZR><PM,PM><PL,PL><PL,PL><PL,PL>]}";

	String doubleChannelDifferentiator = ""//
	    + "{[<ZR,ZR><PM,PM><PL,PL><PL,PL><PL,PL>]" //
	    + " [<NM,NM><ZR,ZR><PM,PM><PL,PL><PL,PL>]" //
	    + " [<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL>]"//
	    + " [<NL,NL><NL,NL><NM,NM><ZR,ZR><PM,PM>]"//
	    + " [<NL,NL><NL,NL><NL,NL><NM,NM><ZR,ZR>]}";

  String adder = String.join("\n", //
	    "{[<NL><NL><NL><NM><ZR>]", //
	    " [<NL><NL><NM><ZR><PM>]", //
	    " [<NL><NM><ZR><PM><PL>]", //
	    " [<NM><ZR><PM><PL><PL>]", //
	    " [<ZR><PM><PL><PL><PL>]}");

	public FirstOrderPIControl() {
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
		int t2 = net.addTransition(0, parser.parseTable(doubleChannelDifferentiator));
    net.addArcFromPlaceToTransition(p4InpCmd, t2, 1.0);
    net.addArcFromPlaceToTransition(p3, t2, 1.0);
    int p5 = net.addPlace();
    net.addArcFromTransitionToPlace(t2, p5);
    int p6 = net.addPlace();
    net.addArcFromTransitionToPlace(t2, p6);
		int t3 = net.addTransition(0, parser.parseTable(adder));//
    int t4delay = net.addTransition(1, OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p6, t4delay, 1.0);
    int p7Mem = net.addPlace();
    net.setInitialMarkingForPlace(p7Mem, FuzzyToken.zeroToken());
    net.addArcFromTransitionToPlace(t4delay, p7Mem);
    net.addArcFromPlaceToTransition(p7Mem, t3, 0.2);
    net.addArcFromPlaceToTransition(p5, t3, 0.8);
    int p8 = net.addPlace();
    net.addArcFromTransitionToPlace(t3, p8);
		int t5 = net.addTransition(0, parser.parseTable(doubleChannelAdder));
    net.addArcFromPlaceToTransition(p8, t5, 1.0);
    int p9 = net.addPlace();
    net.addArcFromTransitionToPlace(t5, p9);
    int p10 = net.addPlace();
    net.addArcFromTransitionToPlace(t5, p10);
    int t6delay = net.addTransition(1, OneXTwoTable.defaultTable());
    net.addArcFromPlaceToTransition(p10, t6delay, 1.0);
    net.addArcFromTransitionToPlace(t6delay, p1);
    int p11Mem = net.addPlace();
    net.setInitialMarkingForPlace(p11Mem, FuzzyToken.zeroToken());
    net.addArcFromTransitionToPlace(t6delay, p11Mem);
    net.addArcFromPlaceToTransition(p11Mem, t5, 1.0);

    int t7Out = net.addOuputTransition(OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p9, t7Out, 1.0);

    FuzzyDriver plantInDriver = FuzzyDriver.createDriverFromMinMax(-0.6, +0.6);
    FuzzyDriver userCommandInDriver = FuzzyDriver.createDriverFromMinMax(-0.6, +0.6);
    HashMap<Integer, FuzzyDriver> inputDrivers = new HashMap<>();
    inputDrivers.put(p2InpSys, plantInDriver);
    inputDrivers.put(p4InpCmd, userCommandInDriver);

    FuzzyDriver controlOutDriver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);
    HashMap<Integer, FuzzyDriver> outputDriver = new HashMap<>();
    outputDriver.put(t7Out, controlOutDriver);

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
      if (!controllerResult.containsKey(t7Out)) {
        System.err.println("command not fired in tick: " + i);
      } else {
        controlOut = controllerResult.get(t7Out);

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
		new FirstOrderPIControl();
	}
}
