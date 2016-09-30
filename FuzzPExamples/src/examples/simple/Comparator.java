package examples.simple;

import java.util.HashMap;

import Main.FuzzyPVizualzer;
import core.TableParser;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.Controller.FuzzyPetriNetSyncornousController;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;

public class Comparator {
  String differentiator = "" +
      "{[<ZR><NM><NL><NL><NL>]" +
      " [<PM><ZR><NM><NL><NL>]" +
      " [<PL><PM><ZR><NM><NL>]" +
      " [<PL><PL><PM><ZR><NM>]" +
      " [<PL><PL><PL><PM><ZR>]}";
  String separator = "{[<ZR,FF><ZR,FF><FF,FF><FF,ZR><FF,ZR>]}";

  public Comparator() {
    TableParser parser = new TableParser();

    FuzzyPetriNet net = new FuzzyPetriNet();
    int p0_inp = net.addInputPlace();
    int p1_inp = net.addInputPlace();
    int tr0 = net.addTransition(0, parser.parseTwoXOneTable(differentiator));
    net.addArcFromPlaceToTransition(p0_inp, tr0, 1.0);
    net.addArcFromPlaceToTransition(p1_inp, tr0, 1.0);

    int p2 = net.addPlace();
    net.addArcFromTransitionToPlace(tr0, p2);
    int t1 = net.addTransition(0, parser.parseOneXTwoTable(separator));
    net.addArcFromPlaceToTransition(p2, t1, 1.0);

    int p3 = net.addPlace();
    net.addArcFromTransitionToPlace(t1, p3);
    int p4 = net.addPlace();
    net.addArcFromTransitionToPlace(t1, p4);

    int t2_out = net.addOuputTransition(OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p3, t2_out, 1.0);

    int t3_out = net.addOuputTransition(OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p4, t3_out, 1.0);

   HashMap<Integer, FuzzyDriver>  drv = new HashMap<>();
   drv.put(p0_inp, FuzzyDriver.createDriverFromMinMax(-1.0, 1.0));
   drv.put(p1_inp, FuzzyDriver.createDriverFromMinMax(-1.0, 1.0));

    FuzzyPetriNetSyncornousController controller = new FuzzyPetriNetSyncornousController(drv, drv, net);
    FullRecorder recorder = new FullRecorder();
    controller.setRecorderForExecutor(recorder);

    for (int i = 0; i < 100; i++) {
      HashMap<Integer, Double> inps  = new HashMap<>();
      inps.put(p0_inp, Math.sin(i / 10.0));
      inps.put(p1_inp, Math.cos(i / 10.0));
      controller.control(inps);
      
    }
      
    FuzzyPVizualzer.visualize(net, recorder);
  }

  public static void main(String args[]) {
    new Comparator();
  }
}
