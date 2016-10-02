package examples.coopeartiv.roomtemeparture.components;

import java.util.HashMap;
import java.util.Map;

import core.TableParser;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import examples.coopeartiv.roomtemeparture.model.Plant;

public class OutsideTermoComponent {

  static String reader = "" +
      "{[<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]" +
      " [<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]" +
      " [<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]" +
      " [<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]" +
      " [<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]}";


  private FuzzyPetriNet net;


  private int p1OustideTemInp;


  private int t3Out;


  private FuzzyDriver outsideTempDriver;

  private FuzzyDriver tankWaterTemeDriver;

  private FullRecorder rec;

  private AsyncronRunnableExecutor execcutor;

  public OutsideTermoComponent(Plant plant, WaterTankControllerComponent comp, long simPeriod) {
    buildPetrNet();
    outsideTempDriver = FuzzyDriver.createDriverFromMinMax(-30, 10);
    tankWaterTemeDriver = FuzzyDriver.createDriverFromMinMax(45, 68);
    net.addActionForOuputTransition(t3Out, tk -> {
      comp.setWaterRefTemp(tankWaterTemeDriver.defuzzify(tk));
    });
    rec = new FullRecorder();
    execcutor = new AsyncronRunnableExecutor(net, simPeriod);
    execcutor.setRecorder(rec);
  }


  private void buildPetrNet() {
    net = new FuzzyPetriNet();
    TableParser parser = new TableParser();
    int p0 = net.addPlace();
    net.setInitialMarkingForPlace(p0, FuzzyToken.zeroToken());
    p1OustideTemInp = net.addInputPlace();
    int t0 = net.addTransition(0, parser.parseTable(reader));
    net.addArcFromPlaceToTransition(p0, t0, 1.0);
    net.addArcFromPlaceToTransition(p1OustideTemInp, t0, 1.0);
    int p2 = net.addPlace();
    net.addArcFromTransitionToPlace(t0, p2);
    int t1 = net.addTransition(1, OneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p2, t1, 1.0);
    net.addArcFromTransitionToPlace(t1, p0);
    
    int p3 = net.addPlace();
    net.addArcFromTransitionToPlace(t0, p3);
    t3Out = net.addOuputTransition(parser.parseOneXOneTable("{[<PL> <PM> <ZR><NM><NL>]}"));
    net.addArcFromPlaceToTransition(p3, t3Out, 1.0);
  }

  public void start() {
    (new Thread(execcutor)).start();
  }

  public void stop() {
    execcutor.stop();
  }

  public void setOutsideTemp(double waterRefTemp) {
    Map<Integer, FuzzyToken> inps = new HashMap<Integer, FuzzyToken>();
    inps.put(p1OustideTemInp, outsideTempDriver.fuzzifie(waterRefTemp));
    execcutor.putTokenInInputPlace(inps);
  }

  public FuzzyPetriNet getNet() {
    return net;
  }

  public FullRecorder getRecorder() {
    return rec;
  }
}
