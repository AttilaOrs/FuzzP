package examples.simple.maximumfinder;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.TableParser;

public class MaximumFinderFuzzyPetriMaker {
    public FuzzyPetriNet net;
    /*input places */
    public int iP0, iP2, iP1 ;
    /*ordinary places */
    int _P0, _P1, _P2, _P3, _P4, _P5, _P6, P1, _P8, P2, _P10, P3, _P12, _P13, _P14, _P15, _P16, _P17, _P18, _P19, _P20, _P21, _P22, _P23, _P24, _P25, _P26, _P27, _P28, _P29, _P30, _P31, _P32 ;
    /* output transitions */
    public int oT0, oT2, oT1;
    /* table for out transitions */
    String oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT2_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    /* ordinary transitions */
    int _T0, _T1, _T2, _T3, _T4, _T5, _T6, _T7, T0, _T9, T1, _T11, T2, _T13, _T14, _T15, _T16, _T17, _T18, _T19, _T20, _T21, _T22, _T23, _T24, _T25, _T26, _T27, _T30, _T32;
    /* table for ordinary transitions */
    String _T0_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T1_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T2_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T3_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T4_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T5_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T6_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T7_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String T0_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String _T9_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T1_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String _T11_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T2_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String _T13_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T14_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T15_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T16_tableStr = "{[<FF,ZR><FF,ZR><FF,FF><ZR,FF><ZR,FF><FF,FF>]}";
    String _T17_tableStr = "{"+
    	"[<ZR><NM><NL><NL><NL><FF>]"+//
    	"[<PM><ZR><NM><NL><NL><FF>]"+//
    	"[<PL><PM><ZR><NM><NL><FF>]"+//
    	"[<PL><PL><PM><ZR><NM><FF>]"+//
    	"[<PL><PL><PL><PM><ZR><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T18_tableStr = "{[<FF,ZR><FF,ZR><FF,FF><ZR,FF><ZR,FF><FF,FF>]}";
    String _T19_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T20_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T21_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T22_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T23_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T24_tableStr = "{[<FF,ZR><FF,ZR><FF,FF><ZR,FF><ZR,FF><FF,FF>]}";
    String _T25_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T26_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String _T27_tableStr = "{"+
    	"[<ZR><NM><NL><NL><NL><FF>]"+//
    	"[<PM><ZR><NM><NL><NL><FF>]"+//
    	"[<PL><PM><ZR><NM><NL><FF>]"+//
    	"[<PL><PL><PM><ZR><NM><FF>]"+//
    	"[<PL><PL><PL><PM><ZR><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T30_tableStr = "{"+
    	"[<NL><NL><NM><NM><ZR><FF>]"+//
    	"[<NL><NM><NM><ZR><PM><FF>]"+//
    	"[<NM><NM><ZR><PM><PM><FF>]"+//
    	"[<NM><ZR><PM><PM><PL><FF>]"+//
    	"[<ZR><PM><PM><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String _T32_tableStr = "{"+
    	"[<ZR><NM><NL><NL><NL><FF>]"+//
    	"[<PM><ZR><NM><NL><NL><FF>]"+//
    	"[<PL><PM><ZR><NM><NL><FF>]"+//
    	"[<PL><PL><PM><ZR><NM><FF>]"+//
    	"[<PL><PL><PL><PM><ZR><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";


    private static TableParser parser = new TableParser(true);

    public MaximumFinderFuzzyPetriMaker (){
        net = new FuzzyPetriNet();
        addPlaces();
        addTransitions();
        addArcs();
        putInitialMarking();

    }

    private void addPlaces(){
        iP0 = net.addInputPlace();
        iP2 = net.addInputPlace();
        iP1 = net.addInputPlace();
        _P0 = net.addPlace();
        _P1 = net.addPlace();
        _P2 = net.addPlace();
        _P3 = net.addPlace();
        _P4 = net.addPlace();
        _P5 = net.addPlace();
        _P6 = net.addPlace();
        P1 = net.addPlace();
        _P8 = net.addPlace();
        P2 = net.addPlace();
        _P10 = net.addPlace();
        P3 = net.addPlace();
        _P12 = net.addPlace();
        _P13 = net.addPlace();
        _P14 = net.addPlace();
        _P15 = net.addPlace();
        _P16 = net.addPlace();
        _P17 = net.addPlace();
        _P18 = net.addPlace();
        _P19 = net.addPlace();
        _P20 = net.addPlace();
        _P21 = net.addPlace();
        _P22 = net.addPlace();
        _P23 = net.addPlace();
        _P24 = net.addPlace();
        _P25 = net.addPlace();
        _P26 = net.addPlace();
        _P27 = net.addPlace();
        _P28 = net.addPlace();
        _P29 = net.addPlace();
        _P30 = net.addPlace();
        _P31 = net.addPlace();
        _P32 = net.addPlace();
    }

    private void addTransitions(){
        oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
        oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
        oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
        _T0 =  net.addTransition(0,parser.parseTable(_T0_tableStr));
        _T1 =  net.addTransition(0,parser.parseTable(_T1_tableStr));
        _T2 =  net.addTransition(0,parser.parseTable(_T2_tableStr));
        _T3 =  net.addTransition(0,parser.parseTable(_T3_tableStr));
        _T4 =  net.addTransition(0,parser.parseTable(_T4_tableStr));
        _T5 =  net.addTransition(0,parser.parseTable(_T5_tableStr));
        _T6 =  net.addTransition(0,parser.parseTable(_T6_tableStr));
        _T7 =  net.addTransition(0,parser.parseTable(_T7_tableStr));
        T0 =  net.addTransition(0,parser.parseTable(T0_tableStr));
        _T9 =  net.addTransition(0,parser.parseTable(_T9_tableStr));
        T1 =  net.addTransition(0,parser.parseTable(T1_tableStr));
        _T11 =  net.addTransition(0,parser.parseTable(_T11_tableStr));
        T2 =  net.addTransition(0,parser.parseTable(T2_tableStr));
        _T13 =  net.addTransition(0,parser.parseTable(_T13_tableStr));
        _T14 =  net.addTransition(0,parser.parseTable(_T14_tableStr));
        _T15 =  net.addTransition(0,parser.parseTable(_T15_tableStr));
        _T16 =  net.addTransition(0,parser.parseTable(_T16_tableStr));
        _T17 =  net.addTransition(0,parser.parseTable(_T17_tableStr));
        _T18 =  net.addTransition(0,parser.parseTable(_T18_tableStr));
        _T19 =  net.addTransition(0,parser.parseTable(_T19_tableStr));
        _T20 =  net.addTransition(0,parser.parseTable(_T20_tableStr));
        _T21 =  net.addTransition(0,parser.parseTable(_T21_tableStr));
        _T22 =  net.addTransition(0,parser.parseTable(_T22_tableStr));
        _T23 =  net.addTransition(0,parser.parseTable(_T23_tableStr));
        _T24 =  net.addTransition(0,parser.parseTable(_T24_tableStr));
        _T25 =  net.addTransition(0,parser.parseTable(_T25_tableStr));
        _T26 =  net.addTransition(0,parser.parseTable(_T26_tableStr));
        _T27 =  net.addTransition(0,parser.parseTable(_T27_tableStr));
        _T30 =  net.addTransition(0,parser.parseTable(_T30_tableStr));
        _T32 =  net.addTransition(0,parser.parseTable(_T32_tableStr));
    }

    private void addArcs(){
        net.addArcFromPlaceToTransition(_P12, _T0, 1.0);
        net.addArcFromPlaceToTransition(_P13, _T0, 1.0);
        net.addArcFromPlaceToTransition(_P1, _T1, 1.0);
        net.addArcFromPlaceToTransition(_P6, _T1, 1.0);
        net.addArcFromPlaceToTransition(_P3, _T2, 1.0);
        net.addArcFromPlaceToTransition(_P12, _T2, 1.0);
        net.addArcFromPlaceToTransition(_P3, _T3, 1.0);
        net.addArcFromPlaceToTransition(_P6, _T3, 1.0);
        net.addArcFromPlaceToTransition(_P0, _T4, 1.0);
        net.addArcFromPlaceToTransition(_P1, _T4, 1.0);
        net.addArcFromPlaceToTransition(_P0, _T5, 1.0);
        net.addArcFromPlaceToTransition(_P10, _T5, 1.0);
        net.addArcFromPlaceToTransition(_P5, _T6, 1.0);
        net.addArcFromPlaceToTransition(_P8, _T6, 1.0);
        net.addArcFromPlaceToTransition(_P5, _T7, 1.0);
        net.addArcFromPlaceToTransition(_P13, _T7, 1.0);
        net.addArcFromPlaceToTransition(iP0, T0, 1.0);
        net.addArcFromPlaceToTransition(_P14, _T9, 1.0);
        net.addArcFromPlaceToTransition(iP1, T1, 1.0);
        net.addArcFromPlaceToTransition(_P16, _T11, 1.0);
        net.addArcFromPlaceToTransition(iP2, T2, 1.0);
        net.addArcFromPlaceToTransition(_P30, _T13, 1.0);
        net.addArcFromPlaceToTransition(_P22, _T14, 1.0);
        net.addArcFromPlaceToTransition(_P24, _T15, 1.0);
        net.addArcFromPlaceToTransition(_P15, _T16, 1.0);
        net.addArcFromPlaceToTransition(_P27, _T17, 1.0);
        net.addArcFromPlaceToTransition(_P29, _T17, 1.0);
        net.addArcFromPlaceToTransition(_P19, _T18, 1.0);
        net.addArcFromPlaceToTransition(_P21, _T19, 1.0);
        net.addArcFromPlaceToTransition(_P25, _T20, 1.0);
        net.addArcFromPlaceToTransition(_P26, _T21, 1.0);
        net.addArcFromPlaceToTransition(_P17, _T22, 1.0);
        net.addArcFromPlaceToTransition(_P32, _T23, 1.0);
        net.addArcFromPlaceToTransition(_P31, _T24, 1.0);
        net.addArcFromPlaceToTransition(_P23, _T25, 1.0);
        net.addArcFromPlaceToTransition(_P28, _T26, 1.0);
        net.addArcFromPlaceToTransition(_P20, _T27, 1.0);
        net.addArcFromPlaceToTransition(_P18, _T27, 1.0);
        net.addArcFromPlaceToTransition(P1, oT0, 1.0);
        net.addArcFromPlaceToTransition(P3, oT2, 1.0);
        net.addArcFromPlaceToTransition(_P8, _T30, 1.0);
        net.addArcFromPlaceToTransition(_P10, _T30, 1.0);
        net.addArcFromPlaceToTransition(P2, oT1, 1.0);
        net.addArcFromPlaceToTransition(_P4, _T32, 1.0);
        net.addArcFromPlaceToTransition(_P2, _T32, 1.0);
        net.addArcFromTransitionToPlace(_T0, _P21);
        net.addArcFromTransitionToPlace(_T1, _P26);
        net.addArcFromTransitionToPlace(_T2, _P22);
        net.addArcFromTransitionToPlace(_T3, _P22);
        net.addArcFromTransitionToPlace(_T4, _P30);
        net.addArcFromTransitionToPlace(_T5, _P30);
        net.addArcFromTransitionToPlace(_T6, _P24);
        net.addArcFromTransitionToPlace(_T7, _P24);
        net.addArcFromTransitionToPlace(T0, _P20);
        net.addArcFromTransitionToPlace(T0, _P29);
        net.addArcFromTransitionToPlace(_T9, _P1);
        net.addArcFromTransitionToPlace(T1, _P27);
        net.addArcFromTransitionToPlace(T1, _P2);
        net.addArcFromTransitionToPlace(_T11, _P10);
        net.addArcFromTransitionToPlace(T2, _P4);
        net.addArcFromTransitionToPlace(T2, _P18);
        net.addArcFromTransitionToPlace(_T13, P1);
        net.addArcFromTransitionToPlace(_T14, P3);
        net.addArcFromTransitionToPlace(_T15, P2);
        net.addArcFromTransitionToPlace(_T16, _P16);
        net.addArcFromTransitionToPlace(_T16, _P14);
        net.addArcFromTransitionToPlace(_T17, _P19);
        net.addArcFromTransitionToPlace(_T18, _P17);
        net.addArcFromTransitionToPlace(_T18, _P23);
        net.addArcFromTransitionToPlace(_T19, _P0);
        net.addArcFromTransitionToPlace(_T20, _P3);
        net.addArcFromTransitionToPlace(_T21, _P5);
        net.addArcFromTransitionToPlace(_T22, _P6);
        net.addArcFromTransitionToPlace(_T23, _P13);
        net.addArcFromTransitionToPlace(_T24, _P32);
        net.addArcFromTransitionToPlace(_T24, _P28);
        net.addArcFromTransitionToPlace(_T25, _P12);
        net.addArcFromTransitionToPlace(_T26, _P8);
        net.addArcFromTransitionToPlace(_T27, _P31);
        net.addArcFromTransitionToPlace(_T30, _P25);
        net.addArcFromTransitionToPlace(_T32, _P15);
    }

    private void putInitialMarking(){
    }

}