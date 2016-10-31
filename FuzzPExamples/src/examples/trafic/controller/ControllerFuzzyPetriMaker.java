package examples.trafic.controller;

import core.TableParser;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class ControllerFuzzyPetriMaker {
    public FuzzyPetriNet net;
    /*input places */
    public int iP0, iP2, iP1, iP3 ;
    /*ordinary places */
    int P12, P23, P11, P22, P14, P25, P13, P24, P15, P0, P1, P2, P10, P21, P20 ;
    /* output transitions */
    public int oT0, oT2, oT1, oT4, oT3;
    /* table for out transitions */
    String oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT2_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT3_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    /* ordinary transitions */
    int T10, T21, T20, T12, T11, T22, T0, T1, T2;
    /* table for ordinary transitions */
    String T10_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String T21_tableStr = "{"+
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String T20_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String T12_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String T11_tableStr = "{"+
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String T22_tableStr = "{"+
    	"[<NL><NL><NL><NM><ZR><FF>]"+//
    	"[<NL><NL><NM><ZR><PM><FF>]"+//
    	"[<NL><NM><ZR><PM><PL><FF>]"+//
    	"[<NM><ZR><PM><PL><PL><FF>]"+//
    	"[<ZR><PM><PL><PL><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String T0_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String T1_tableStr = "{"+
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    	"[<FF,FF><FF,FF><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NM,ZR><ZR,ZR><PM,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><NL,ZR><NM,ZR><ZR,ZR><FF,FF>]"+//
    	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
    "}";
    String T2_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";

    public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();

    private static TableParser parser = new TableParser(true);

    public ControllerFuzzyPetriMaker (){
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
        iP3 = net.addInputPlace();
        P12 = net.addPlace();
        P23 = net.addPlace();
        P11 = net.addPlace();
        P22 = net.addPlace();
        P14 = net.addPlace();
        P25 = net.addPlace();
        P13 = net.addPlace();
        P24 = net.addPlace();
        P15 = net.addPlace();
        P0 = net.addPlace();
        P1 = net.addPlace();
        P2 = net.addPlace();
        P10 = net.addPlace();
        P21 = net.addPlace();
        P20 = net.addPlace();
        nameStore.addPlaceName(iP0, "iP0" );
        nameStore.addPlaceName(iP2, "iP2" );
        nameStore.addPlaceName(iP1, "iP1" );
        nameStore.addPlaceName(iP3, "iP3" );
        nameStore.addPlaceName(P12, "P12" );
        nameStore.addPlaceName(P23, "P23" );
        nameStore.addPlaceName(P11, "P11" );
        nameStore.addPlaceName(P22, "P22" );
        nameStore.addPlaceName(P14, "P14" );
        nameStore.addPlaceName(P25, "P25" );
        nameStore.addPlaceName(P13, "P13" );
        nameStore.addPlaceName(P24, "P24" );
        nameStore.addPlaceName(P15, "P15" );
        nameStore.addPlaceName(P0, "P0" );
        nameStore.addPlaceName(P1, "P1" );
        nameStore.addPlaceName(P2, "P2" );
        nameStore.addPlaceName(P10, "P10" );
        nameStore.addPlaceName(P21, "P21" );
        nameStore.addPlaceName(P20, "P20" );
    }

    private void addTransitions(){
        oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
        oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
        oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
        oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
        oT3 =  net.addOuputTransition(parser.parseOneXOneTable(oT3_tableStr));
        nameStore.addTransitionName(oT0, "oT0" );
        nameStore.addTransitionName(oT2, "oT2" );
        nameStore.addTransitionName(oT1, "oT1" );
        nameStore.addTransitionName(oT4, "oT4" );
        nameStore.addTransitionName(oT3, "oT3" );
        T10 =  net.addTransition(0,parser.parseTable(T10_tableStr));
        T21 =  net.addTransition(0,parser.parseTable(T21_tableStr));
        T20 =  net.addTransition(0,parser.parseTable(T20_tableStr));
        T12 =  net.addTransition(0,parser.parseTable(T12_tableStr));
        T11 =  net.addTransition(0,parser.parseTable(T11_tableStr));
        T22 =  net.addTransition(0,parser.parseTable(T22_tableStr));
        T0 =  net.addTransition(0,parser.parseTable(T0_tableStr));
        T1 =  net.addTransition(0,parser.parseTable(T1_tableStr));
        T2 =  net.addTransition(12,parser.parseTable(T2_tableStr));
        nameStore.addTransitionName(T10, "T10" );
        nameStore.addTransitionName(T21, "T21" );
        nameStore.addTransitionName(T20, "T20" );
        nameStore.addTransitionName(T12, "T12" );
        nameStore.addTransitionName(T11, "T11" );
        nameStore.addTransitionName(T22, "T22" );
        nameStore.addTransitionName(T0, "T0" );
        nameStore.addTransitionName(T1, "T1" );
        nameStore.addTransitionName(T2, "T2" );
    }

    private void addArcs(){
        net.addArcFromPlaceToTransition(P10, T10, 1.0);
        net.addArcFromPlaceToTransition(P21, T21, 1.0);
        net.addArcFromPlaceToTransition(iP2, T21, 1.0);
        net.addArcFromPlaceToTransition(P20, T20, 1.0);
        net.addArcFromPlaceToTransition(P12, T12, 1.0);
        net.addArcFromPlaceToTransition(iP1, T12, 1.0);
        net.addArcFromPlaceToTransition(P11, T11, 1.0);
        net.addArcFromPlaceToTransition(iP0, T11, 1.0);
        net.addArcFromPlaceToTransition(P22, T22, 1.0);
        net.addArcFromPlaceToTransition(iP3, T22, 1.0);
        net.addArcFromPlaceToTransition(P0, T0, 1.0);
        net.addArcFromPlaceToTransition(P13, T1, 1.0);
        net.addArcFromPlaceToTransition(P23, T1, 1.0);
        net.addArcFromPlaceToTransition(P2, T2, 1.0);
        net.addArcFromPlaceToTransition(P14, oT0, 1.0);
        net.addArcFromPlaceToTransition(P24, oT2, 1.0);
        net.addArcFromPlaceToTransition(P15, oT1, 1.0);
        net.addArcFromPlaceToTransition(P1, oT4, 1.0);
        net.addArcFromPlaceToTransition(P25, oT3, 1.0);
        net.addArcFromTransitionToPlace(T10, P11);
        net.addArcFromTransitionToPlace(T10, P14);
        net.addArcFromTransitionToPlace(T21, P22);
        net.addArcFromTransitionToPlace(T21, P25);
        net.addArcFromTransitionToPlace(T20, P21);
        net.addArcFromTransitionToPlace(T20, P24);
        net.addArcFromTransitionToPlace(T12, P13);
        net.addArcFromTransitionToPlace(T11, P12);
        net.addArcFromTransitionToPlace(T11, P15);
        net.addArcFromTransitionToPlace(T22, P23);
        net.addArcFromTransitionToPlace(T0, P10);
        net.addArcFromTransitionToPlace(T0, P20);
        net.addArcFromTransitionToPlace(T1, P1);
        net.addArcFromTransitionToPlace(T1, P2);
        net.addArcFromTransitionToPlace(T2, P0);
    }

    private void putInitialMarking(){
        net.setInitialMarkingForPlace(P0, FuzzyToken.buildFromString("<0.00,0.00,1.00,0.00,0.00>"));
    }

}