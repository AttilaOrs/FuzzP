package examples.async.basin;

import java.util.HashMap;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.common.recoder.FullRecorder;

public class BasinController {

  private FuzzyPetriNet net;
  private FullRecorder<FuzzyToken> rec;
  private AsyncronRunnableExecutor execcutor;
  private int iP1;
  FuzzyDriver waterDriver = FuzzyDriver.createDriverFromMinMax(55.0, 65.0);

  static String reader = "" +
      "{[<NL><NM><ZR><PM><PL>]" +
      " [<NL><NM><ZR><PM><PL>]" +
      " [<NL><NM><ZR><PM><PL>]" +
      " [<NL><NM><ZR><PM><PL>]" +
      " [<NL><NM><ZR><PM><PL>]}";

  static String t3Table = "{[<NL,FF><FF,FF><FF,FF><FF,FF><FF,PL>]}";

  public BasinController(Basin bs, int simPeriod) {

    FuzzyTableParser parser = new FuzzyTableParser();
    net = new FuzzyPetriNet();
    int P0 = net.addPlace();
    iP1 = net.addInputPlace();
    int t0 = net.addTransition(0, parser.parseTwoXOneTable(reader));
    net.addArcFromPlaceToTransition(P0, t0, 1.0);
    net.addArcFromPlaceToTransition(iP1, t0, 1.0);

    net.setInitialMarkingForPlace(P0, FuzzyToken.zeroToken());

    int p2 = net.addPlace();
    net.addArcFromTransitionToPlace(t0, p2);
    int t1 = net.addTransition(0, OneXTwoTable.defaultTable());
    net.addArcFromPlaceToTransition(p2, t1, 1.0);
    int p3 = net.addPlace(); // from here to ouput
    net.addArcFromTransitionToPlace(t1, p3);
    int p4 = net.addPlace();
    net.addArcFromTransitionToPlace(t1, p4);
    int tr2 = net.addTransition(1, OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p4, tr2, 1.0);
    net.addArcFromTransitionToPlace(tr2, P0);
    int tr3 = net.addTransition(0, parser.parseOneXTwoTable(t3Table));
    net.addArcFromPlaceToTransition(p3, tr3, 1.0);
    int p5 = net.addPlace();
    int p6 = net.addPlace();
    net.addArcFromTransitionToPlace(tr3, p5);
    net.addArcFromTransitionToPlace(tr3, p6);
    int oTr4 = net.addOuputTransition(OneXOneTable.defaultTable());
    int oTr5 = net.addOuputTransition(OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p5, oTr4, 1.0);
    net.addArcFromPlaceToTransition(p6, oTr5, 1.0);
    net.addActionForOuputTransition(oTr4, tk -> bs.setValveOff());
    net.addActionForOuputTransition(oTr5, tk -> bs.setValveOn());



    rec = new FullRecorder<>();
    execcutor = new AsyncronRunnableExecutor(net, simPeriod);
    execcutor.setRecorder(rec);

  }

  public void start() {
    (new Thread(execcutor)).start();
  }

  public void stop() {
    execcutor.stop();
  }

  public void setWater(double water) {
    HashMap<Integer, FuzzyToken> inps = new HashMap<>();
    inps.put(iP1, waterDriver.fuzzifie(water));
    execcutor.putTokenInInputPlace(inps);
  }

  public FuzzyPetriNet getNet() {
    return net;
  }

  public FullRecorder<FuzzyToken> getRecorder() {
    return rec;
  }

}
