package examples.coopeartiv.roomtemeparture.components;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import core.TableParser;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import examples.coopeartiv.roomtemeparture.model.Plant;

public class RoomTemperatureControllerComponent {

    static String reader = "" +
            "{[<NL><NM><ZR><PM><PL>]" +
            " [<NL><NM><ZR><PM><PL>]" +
            " [<NL><NM><ZR><PM><PL>]" +
            " [<NL><NM><ZR><PM><PL>]" +
            " [<NL><NM><ZR><PM><PL>]}";

    static String doubleChannelDifferentiator = ""//
            + "{[<ZR,ZR><NM,NM><NL,NL><NL,NL><NL,NL>]" //
            + " [<PM,PM><ZR,ZR><NM,NM><NL,NL><NL,NL>]" //
            + " [<PL,PL><PM,PM><ZR,ZR><NM,NM><NL,NL>]"//
            + " [<PL,PL><PL,PL><PM,PM><ZR,ZR><NM,NM>]"//
            + " [<PL,PL><PL,PL><PL,PL><PM,PM><ZR,ZR>]}";

    static String t3Table = "{[<FF,ZR>,<FF,FF>, <FF,FF>, <FF,FF>, <ZR, FF>]}";

    private int p1RefInp;
    private FuzzyPetriNet net;

    private FuzzyDriver temepartureDriver;

    private FullRecorder rec;

    private AsyncronRunnableExecutor execcutor;

    private int p3RealInp;

    public RoomTemperatureControllerComponent(Plant plant, long simPeriod) {
        net = new FuzzyPetriNet();
        TableParser parser = new TableParser();

        int p0 = net.addPlace();
        net.setInitialMarkingForPlace(p0, FuzzyToken.zeroToken());
        p1RefInp = net.addInputPlace();
        int t0 = net.addTransition(0, parser.parseTwoXOneTable(reader));
        net.addArcFromPlaceToTransition(p0, t0, 1.0);
        net.addArcFromPlaceToTransition(p1RefInp, t0, 1.0);
        int p2 = net.addPlace();
        net.addArcFromTransitionToPlace(t0, p2);
        p3RealInp = net.addInputPlace();
        int t1 = net.addTransition(0, parser.parseTwoXTwoTable(doubleChannelDifferentiator));
        net.addArcFromPlaceToTransition(p2, t1, 1.0);
        net.addArcFromPlaceToTransition(p3RealInp, t1, 1.0);
        int p4 = net.addPlace();
        net.addArcFromTransitionToPlace(t1, p4);
        int t2 = net.addTransition(1, OneXOneTable.defaultTable());
        net.addArcFromPlaceToTransition(p4, t2, 1.0);
        net.addArcFromTransitionToPlace(t2, p0);

        int p5 = net.addPlace();
        net.addArcFromTransitionToPlace(t1, p5);
        int t3 = net.addTransition(0, parser.parseOneXTwoTable(t3Table));
        int p6 = net.addPlace();
        net.addArcFromTransitionToPlace(t3, p6);
        int t4 = net.addOuputTransition(OneXOneTable.defaultTable());
        net.addArcFromPlaceToTransition(p6, t4, 1.0);
        int p7 = net.addPlace();
        net.addArcFromTransitionToPlace(t3, p7);
        int t5 = net.addOuputTransition(OneXOneTable.defaultTable());
        net.addArcFromPlaceToTransition(p7, t5, 1.0);
        net.addArcFromPlaceToTransition(p5, t3, 120.0);
        net.addActionForOuputTransition(t4, new Consumer<FuzzyToken>() {
            @Override
            public void accept(FuzzyToken tk) {
                plant.setHeatingOn(true);
            }
        });

        net.addActionForOuputTransition(t5, new Consumer<FuzzyToken>() {
            @Override
            public void accept(FuzzyToken t) {
                plant.setHeatingOn(false);
            }
        });

        temepartureDriver = FuzzyDriver.createDriverFromMinMax(-40, 40);

        rec = new FullRecorder();
        execcutor = new AsyncronRunnableExecutor(net, simPeriod);
        execcutor.setRecorder(rec);
    }

    public void start() {
        (new Thread(execcutor)).start();
    }

    public void stop() {
        execcutor.stop();
    }

    public void setInput(double roomTemperatureRef, double roomTemperature) {
        Map<Integer, FuzzyToken> inps = new HashMap<Integer, FuzzyToken>();
        inps.put(p1RefInp, temepartureDriver.fuzzifie(roomTemperatureRef));
        inps.put(p3RealInp, temepartureDriver.fuzzifie(roomTemperature));
        execcutor.putTokenInInputPlace(inps);
    }

    public FuzzyPetriNet getNet() {
        return net;
    }

    public FullRecorder getRecorder() {
        return rec;
    }

}
