package examples.trafic.splitter;

import core.TableParser;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class SplitterFuzzyPetriMaker {
    public FuzzyPetriNet net;
    /*input places */
    public int iP0, iP1 ;
    /*ordinary places */
    int P0, P12, P11, P22, P1, P2, P14, P3, P13, P21;
    /* output transitions */
    public int oT0, oT1;
    /* table for out transitions */
    String oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    /* ordinary transitions */
    int T21, T12, T11, T22, T0, T1;
    /* table for ordinary transitions */
    String T21_tableStr = "{" +
            "[<NL><NL><NL><NM><ZR><FF>]" + //
            "[<NL><NL><NM><ZR><PM><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NM><ZR><PM><PL><PL><FF>]" + //
            "[<ZR><PM><PL><PL><PL><FF>]" + //
            "[<FF><FF><FF><FF><FF><FF>]" + //
            "}";
    String T12_tableStr = "{"+
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]" + //
            "[<FF,FF><FF,FF><PM,ZR><PM,PM><ZR,PM><FF,FF>]" + //
            "[<FF,FF><FF,FF><PL,ZR><PL,PL><ZR,PL><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String T11_tableStr = "{"+
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]" + //
            "[<FF,FF><FF,FF><PM,ZR><ZR,ZR><ZR,PM><FF,FF>]" + //
            "[<FF,FF><FF,FF><PL,ZR><ZR,ZR><ZR,PL><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "}";
    String T22_tableStr = "{" +
            "[<NL><NL><NL><NM><ZR><FF>]" + //
            "[<NL><NL><NM><ZR><PM><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NM><ZR><PM><PL><PL><FF>]" + //
            "[<ZR><PM><PL><PL><PL><FF>]" + //
            "[<FF><FF><FF><FF><FF><FF>]" + //
    "}";
    String T0_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String T1_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";


    private static TableParser parser = new TableParser(true);

    public SplitterFuzzyPetriMaker (){
        net = new FuzzyPetriNet();
        addPlaces();
        addTransitions();
        addArcs();
        putInitialMarking();

    }

    private void addPlaces(){
        iP0 = net.addInputPlace();
        iP1 = net.addInputPlace();
        P0 = net.addPlace();
        P12 = net.addPlace();
        P11 = net.addPlace();
        P22 = net.addPlace();
        P1 = net.addPlace();
        P2 = net.addPlace();
        P14 = net.addPlace();
        P3 = net.addPlace();
        P13 = net.addPlace();
        P21 = net.addPlace();
    }

    private void addTransitions(){
        oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
        oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
        T21 = net.addTransition(0, parser.parseTable(T21_tableStr));
        T12 =  net.addTransition(0,parser.parseTable(T12_tableStr));
        T11 =  net.addTransition(0,parser.parseTable(T11_tableStr));
        T22 = net.addTransition(0, parser.parseTable(T22_tableStr));
        T0 =  net.addTransition(0,parser.parseTable(T0_tableStr));
        T1 =  net.addTransition(0,parser.parseTable(T1_tableStr));
    }

    private void addArcs(){
        net.addArcFromPlaceToTransition(P11, T21, 1.0);
        net.addArcFromPlaceToTransition(P12, T21, 1.0);
        net.addArcFromPlaceToTransition(P2, T12, 1.0);
        net.addArcFromPlaceToTransition(P3, T12, 1.0);
        net.addArcFromPlaceToTransition(P0, T11, 1.0);
        net.addArcFromPlaceToTransition(P1, T11, 1.0);
        net.addArcFromPlaceToTransition(P14, T22, 1.0);
        net.addArcFromPlaceToTransition(P13, T22, 1.0);
        net.addArcFromPlaceToTransition(P21, oT0, 1.0);
        net.addArcFromPlaceToTransition(iP0, T0, 0.5);
        net.addArcFromPlaceToTransition(P22, oT1, 1.0);
        net.addArcFromPlaceToTransition(iP1, T1, 1.0);
        net.addArcFromTransitionToPlace(T21, P21);
        net.addArcFromTransitionToPlace(T12, P12);
        net.addArcFromTransitionToPlace(T12, P14);
        net.addArcFromTransitionToPlace(T11, P11);
        net.addArcFromTransitionToPlace(T11, P13);
        net.addArcFromTransitionToPlace(T22, P22);
        net.addArcFromTransitionToPlace(T0, P0);
        net.addArcFromTransitionToPlace(T0, P2);
        net.addArcFromTransitionToPlace(T1, P3);
        net.addArcFromTransitionToPlace(T1, P1);
    }

    private void putInitialMarking(){
    }

}