package examples.trafic.buffer;

import core.TableParser;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class BufferFuzzyPetriMaker {
    public FuzzyPetriNet net;
    /*input places */
    public int iP2, iP1, iP4, iP3 ;
    /*ordinary places */
    int _P0, _P1, _P2, _P3, _P4, _P5, _P6, _P7, _P8, P5, P6, P7, P8, _P16, _P18, _P19 ;
    /* output transitions */
    public int oT6, oT5, oT7;
    /* table for out transitions */
    String oT6_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT7_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    /* ordinary transitions */
    int sp_oT0, T1, sp_oT1, T2, T3, T4, sp_T12, sp_T11, sp_T22, sp_T21, sp_T1, sp_T0;
    /* table for ordinary transitions */
    String sp_oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T1_tableStr = "{"+
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
    "}";
    String sp_oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T2_tableStr = "{"+
            "[<NL,NL><NL,NL><NL,NL><NL,NL><NL,NL><FF,FF>]" + //
            "[<NM,NM><NM,NM><NM,NM><NM,NM><NM,NM><FF,FF>]" + //
            "[<ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]" + //
            "[<PM,PM><PM,PM><PM,PM><PM,PM><PM,PM><FF,FF>]" + //
            "[<PL,PL><PL,PL><PL,PL><PL,PL><PL,PL><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String T3_tableStr = "{"+
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]" + //
            "[<FF,FF><FF,FF><NM,PM><NM,PM><PM,PM><FF,FF>]" + //
            "[<FF,FF><FF,FF><NL,PL><NM,PL><ZR,PL><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String T4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String sp_T12_tableStr = "{"+
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]" + //
            "[<FF,FF><FF,FF><PM,ZR><PM,PM><ZR,PM><FF,FF>]" + //
            "[<FF,FF><FF,FF><PL,ZR><PL,PL><ZR,PL><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String sp_T11_tableStr = "{"+
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]" + //
            "[<FF,FF><FF,FF><PM,ZR><ZR,ZR><ZR,PM><FF,FF>]" + //
            "[<FF,FF><FF,FF><PL,ZR><ZR,ZR><ZR,PL><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String sp_T22_tableStr = "{"+
            "[<NL><NL><NL><NM><ZR><FF>]" + //
            "[<NL><NL><NM><ZR><PM><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NM><ZR><PM><PL><PL><FF>]" + //
            "[<ZR><PM><PL><PL><PL><FF>]" + //
            "[<FF><FF><FF><FF><FF><FF>]" + //
    "}";
    String sp_T21_tableStr = "{"+
            "[<NL><NL><NL><NM><ZR><FF>]" + //
            "[<NL><NL><NM><ZR><PM><FF>]" + //
            "[<NL><NM><ZR><PM><PL><FF>]" + //
            "[<NM><ZR><PM><PL><PL><FF>]" + //
            "[<ZR><PM><PL><PL><PL><FF>]" + //
            "[<FF><FF><FF><FF><FF><FF>]" + //
    "}";
    String sp_T1_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String sp_T0_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";

    public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();

    private static TableParser parser = new TableParser(true);

    public BufferFuzzyPetriMaker (){
        net = new FuzzyPetriNet();
        addPlaces();
        addTransitions();
        addArcs();
        putInitialMarking();

    }

    private void addPlaces(){
        iP2 = net.addInputPlace();
        iP1 = net.addInputPlace();
        iP4 = net.addInputPlace();
        iP3 = net.addInputPlace();
        _P0 = net.addPlace();
        _P1 = net.addPlace();
        _P2 = net.addPlace();
        _P3 = net.addPlace();
        _P4 = net.addPlace();
        _P5 = net.addPlace();
        _P6 = net.addPlace();
        _P7 = net.addPlace();
        _P8 = net.addPlace();
        P5 = net.addPlace();
        P6 = net.addPlace();
        P7 = net.addPlace();
        P8 = net.addPlace();
        _P16 = net.addPlace();
        _P18 = net.addPlace();
        _P19 = net.addPlace();
        nameStore.addPlaceName(iP2, "iP2");
        nameStore.addPlaceName(iP1, "iP1");
        nameStore.addPlaceName(iP4, "iP4");
        nameStore.addPlaceName(iP3, "iP3");
        nameStore.addPlaceName(_P0, "_P0");
        nameStore.addPlaceName(_P1, "_P1");
        nameStore.addPlaceName(_P2, "_P2");
        nameStore.addPlaceName(_P3, "_P3");
        nameStore.addPlaceName(_P4, "_P4");
        nameStore.addPlaceName(_P5, "_P5");
        nameStore.addPlaceName(_P6, "_P6");
        nameStore.addPlaceName(_P7, "_P7");
        nameStore.addPlaceName(_P8, "_P8");
        nameStore.addPlaceName(P5, "P5");
        nameStore.addPlaceName(P6, "P6");
        nameStore.addPlaceName(P7, "P7");
        nameStore.addPlaceName(P8, "P8");
        nameStore.addPlaceName(_P16, "_P16");
        nameStore.addPlaceName(_P18, "_P18");
        nameStore.addPlaceName(_P19, "_P19");
    }

    private void addTransitions(){
        oT6 =  net.addOuputTransition(parser.parseOneXOneTable(oT6_tableStr));
        oT5 =  net.addOuputTransition(parser.parseOneXOneTable(oT5_tableStr));
        oT7 =  net.addOuputTransition(parser.parseOneXOneTable(oT7_tableStr));
        nameStore.addTransitionName(oT6, "oT6");
        nameStore.addTransitionName(oT5, "oT5");
        nameStore.addTransitionName(oT7, "oT7");
        sp_oT0 =  net.addTransition(0,parser.parseTable(sp_oT0_tableStr));
        T1 =  net.addTransition(0,parser.parseTable(T1_tableStr));
        sp_oT1 =  net.addTransition(0,parser.parseTable(sp_oT1_tableStr));
        T2 =  net.addTransition(0,parser.parseTable(T2_tableStr));
        T3 =  net.addTransition(0,parser.parseTable(T3_tableStr));
        T4 =  net.addTransition(0,parser.parseTable(T4_tableStr));
        sp_T12 =  net.addTransition(0,parser.parseTable(sp_T12_tableStr));
        sp_T11 =  net.addTransition(0,parser.parseTable(sp_T11_tableStr));
        sp_T22 =  net.addTransition(0,parser.parseTable(sp_T22_tableStr));
        sp_T21 =  net.addTransition(0,parser.parseTable(sp_T21_tableStr));
        sp_T1 =  net.addTransition(0,parser.parseTable(sp_T1_tableStr));
        sp_T0 =  net.addTransition(0,parser.parseTable(sp_T0_tableStr));
        nameStore.addTransitionName(sp_oT0, "sp_oT0");
        nameStore.addTransitionName(T1, "T1");
        nameStore.addTransitionName(sp_oT1, "sp_oT1");
        nameStore.addTransitionName(T2, "T2");
        nameStore.addTransitionName(T3, "T3");
        nameStore.addTransitionName(T4, "T4");
        nameStore.addTransitionName(sp_T12, "sp_T12");
        nameStore.addTransitionName(sp_T11, "sp_T11");
        nameStore.addTransitionName(sp_T22, "sp_T22");
        nameStore.addTransitionName(sp_T21, "sp_T21");
        nameStore.addTransitionName(sp_T1, "sp_T1");
        nameStore.addTransitionName(sp_T0, "sp_T0");
    }

    private void addArcs(){
        net.addArcFromPlaceToTransition(P8, oT6, 1.0);
        net.addArcFromPlaceToTransition(P7, oT5, 1.0);
        net.addArcFromPlaceToTransition(P6, oT7, 1.0);
        net.addArcFromPlaceToTransition(_P7, sp_oT0, 1.0);
        net.addArcFromPlaceToTransition(P5, T1, 1.0);
        net.addArcFromPlaceToTransition(iP1, T1, 1.0);
        net.addArcFromPlaceToTransition(_P6, sp_oT1, 1.0);
        net.addArcFromPlaceToTransition(P5, T2, 1.0);
        net.addArcFromPlaceToTransition(iP2, T2, 1.0);
        net.addArcFromPlaceToTransition(iP3, T3, 0.1);
        net.addArcFromPlaceToTransition(P5, T3, 1.0);
        net.addArcFromPlaceToTransition(iP4, T4, 1.0);
        net.addArcFromPlaceToTransition(_P18, sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P1, sp_T12, 1.0);
        net.addArcFromPlaceToTransition(_P19, sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P16, sp_T11, 1.0);
        net.addArcFromPlaceToTransition(_P8, sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P3, sp_T22, 1.0);
        net.addArcFromPlaceToTransition(_P5, sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P4, sp_T21, 1.0);
        net.addArcFromPlaceToTransition(_P2, sp_T1, 1.0);
        net.addArcFromPlaceToTransition(_P0, sp_T0, 0.5);
        net.addArcFromTransitionToPlace(sp_oT0, P7);
        net.addArcFromTransitionToPlace(T1, P5);
        net.addArcFromTransitionToPlace(sp_oT1, P8);
        net.addArcFromTransitionToPlace(T2, P5);
        net.addArcFromTransitionToPlace(T2, P6);
        net.addArcFromTransitionToPlace(T3, P5);
        net.addArcFromTransitionToPlace(T3, _P0);
        net.addArcFromTransitionToPlace(T4, _P2);
        net.addArcFromTransitionToPlace(sp_T12, _P4);
        net.addArcFromTransitionToPlace(sp_T12, _P8);
        net.addArcFromTransitionToPlace(sp_T11, _P5);
        net.addArcFromTransitionToPlace(sp_T11, _P3);
        net.addArcFromTransitionToPlace(sp_T22, _P6);
        net.addArcFromTransitionToPlace(sp_T21, _P7);
        net.addArcFromTransitionToPlace(sp_T1, _P1);
        net.addArcFromTransitionToPlace(sp_T1, _P16);
        net.addArcFromTransitionToPlace(sp_T0, _P19);
        net.addArcFromTransitionToPlace(sp_T0, _P18);
    }

    private void putInitialMarking(){
    }

}