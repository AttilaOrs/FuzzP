package examples.trafic.coordinator;

import core.TableParser;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class CooridinatorFuzzyPetriMaker {
    public FuzzyPetriNet net;
    /*input places */
    public int iP0 ;
    /*ordinary places */
    int P12, P23, P11, P22, P25, P24, P26, P19, P0, P1, P2, P3, P4, P5, P6, P50, P10, P21;
    /* output transitions */
    public int oT0, oT2, oT1, oT3;
    /* table for out transitions */
    String oT0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT2_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String oT3_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    /* ordinary transitions */
    int T10, T21, T20, T11, T22, T0, T1, T2, T3, T4, T5, T50;
    /* table for ordinary transitions */
    String T10_tableStr = "{" +
            "[<NL><NL><NL><NL><NL><NL>]" + //
            "[<NL><NL><NL><NL><NL><NL>]" + //
            "[<NL><NL><NL><NL><NL><NL>]" + //
            "[<PL><PL><PL><PL><PL><PL>]" + //
            "[<PL><PL><PL><PL><PL><PL>]" + //
            "[<FF><FF><FF><FF><FF><FF>]" + //
            "}";
    String T21_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String T20_tableStr = "{"+
            "[<PL,FF><PL,FF><FF,FF><FF,PL><FF,PL><FF,FF>]" + //
            "[<PL,FF><PL,FF><FF,FF><FF,PL><FF,PL><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
            "[<FF,PL><FF,PL><FF,FF><PL,FF><PL,FF><FF,FF>]" + //
            "[<FF,PL><FF,PL><FF,FF><PL,FF><PL,FF><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String T11_tableStr = "{"+
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><NL,NL>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><NL,NL>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><ZR,ZR>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><PL,PL>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><PL,PL>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String T22_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String T0_tableStr = "{"+
            "[<NL,NL><NL,NL><NL,NL><NL,NL><NL,NL><FF,FF>]" + //
            "[<NM,NM><NM,NM><NM,NM><NM,NM><NM,NM><FF,FF>]" + //
            "[<ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><ZR,ZR><FF,FF>]" + //
            "[<PM,PM><PM,PM><PM,PM><PM,PM><PM,PM><FF,FF>]" + //
            "[<PL,PL><PL,PL><PL,PL><PL,PL><PL,PL><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String T1_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T2_tableStr = "{" +
            "[<PL><PM><ZR><PM><PL><FF>]" + //
            "[<PL><PM><ZR><PM><PL><FF>]" + //
            "[<PL><PM><ZR><PM><PL><FF>]" + //
            "[<PL><PM><ZR><PM><PL><FF>]" + //
            "[<PL><PM><ZR><PM><PL><FF>]" + //
            "[<FF><FF><FF><FF><FF><FF>]" + //
            "}";
    String T3_tableStr = "{"+
            "[<NL,ZR><NL,PM><NL,PL><NL,PL><NL,PL><FF,FF>]" + //
            "[<NM,NM><NM,ZR><NM,PM><NM,PL><NM,PL><FF,FF>]" + //
            "[<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL><FF,FF>]" + //
            "[<PM,NL><PM,NL><PM,NM><PM,ZR><PM,PM><FF,FF>]" + //
            "[<PL,NL><PL,NL><PL,NL><PL,NM><PL,ZR><FF,FF>]" + //
            "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
    "}";
    String T4_tableStr = "{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL><FF,FF>]}";
    String T5_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T50_tableStr = "{[<PL,PL><PM,PL><PM,ZR><PM,NL><PL,NL><FF,FF>]}";

    public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();

    private static TableParser parser = new TableParser(true);

    public CooridinatorFuzzyPetriMaker (){
        net = new FuzzyPetriNet();
        addPlaces();
        addTransitions();
        addArcs();
        putInitialMarking();

    }

    private void addPlaces(){
        iP0 = net.addInputPlace();
        P12 = net.addPlace();
        P23 = net.addPlace();
        P11 = net.addPlace();
        P22 = net.addPlace();
        P25 = net.addPlace();
        P24 = net.addPlace();
        P26 = net.addPlace();
        P19 = net.addPlace();
        P0 = net.addPlace();
        P1 = net.addPlace();
        P2 = net.addPlace();
        P3 = net.addPlace();
        P4 = net.addPlace();
        P5 = net.addPlace();
        P6 = net.addPlace();
        P50 = net.addPlace();
        P10 = net.addPlace();
        P21 = net.addPlace();
        nameStore.addPlaceName(iP0, "iP0" );
        nameStore.addPlaceName(P12, "P12" );
        nameStore.addPlaceName(P23, "P23" );
        nameStore.addPlaceName(P11, "P11" );
        nameStore.addPlaceName(P22, "P22" );
        nameStore.addPlaceName(P25, "P25" );
        nameStore.addPlaceName(P24, "P24" );
        nameStore.addPlaceName(P26, "P26" );
        nameStore.addPlaceName(P19, "P19");
        nameStore.addPlaceName(P0, "P0" );
        nameStore.addPlaceName(P1, "P1" );
        nameStore.addPlaceName(P2, "P2" );
        nameStore.addPlaceName(P3, "P3" );
        nameStore.addPlaceName(P4, "P4" );
        nameStore.addPlaceName(P5, "P5");
        nameStore.addPlaceName(P6, "P6");
        nameStore.addPlaceName(P50, "P50");
        nameStore.addPlaceName(P10, "P10" );
        nameStore.addPlaceName(P21, "P21" );
    }

    private void addTransitions(){
        oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
        oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
        oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
        oT3 =  net.addOuputTransition(parser.parseOneXOneTable(oT3_tableStr));
        nameStore.addTransitionName(oT0, "oT0" );
        nameStore.addTransitionName(oT2, "oT2" );
        nameStore.addTransitionName(oT1, "oT1" );
        nameStore.addTransitionName(oT3, "oT3" );
        T10 =  net.addTransition(0,parser.parseTable(T10_tableStr));
        T21 =  net.addTransition(0,parser.parseTable(T21_tableStr));
        T20 =  net.addTransition(0,parser.parseTable(T20_tableStr));
        T11 =  net.addTransition(1,parser.parseTable(T11_tableStr));
        T22 =  net.addTransition(0,parser.parseTable(T22_tableStr));
        T0 =  net.addTransition(0,parser.parseTable(T0_tableStr));
        T1 =  net.addTransition(12,parser.parseTable(T1_tableStr));
        T2 =  net.addTransition(0,parser.parseTable(T2_tableStr));
        T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
        T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
        T5 = net.addTransition(1, parser.parseTable(T5_tableStr));
        T50 = net.addTransition(0, parser.parseTable(T50_tableStr));
        nameStore.addTransitionName(T10, "T10" );
        nameStore.addTransitionName(T21, "T21" );
        nameStore.addTransitionName(T20, "T20" );
        nameStore.addTransitionName(T11, "T11" );
        nameStore.addTransitionName(T22, "T22" );
        nameStore.addTransitionName(T0, "T0" );
        nameStore.addTransitionName(T1, "T1" );
        nameStore.addTransitionName(T2, "T2" );
        nameStore.addTransitionName(T3, "T3" );
        nameStore.addTransitionName(T4, "T4");
        nameStore.addTransitionName(T5, "T5");
        nameStore.addTransitionName(T50, "T50");
    }

    private void addArcs(){
        net.addArcFromPlaceToTransition(P10, T10, 1.0);
        net.addArcFromPlaceToTransition(P11, T10, 1.0);
        net.addArcFromPlaceToTransition(P21, T21, 1.0);
        net.addArcFromPlaceToTransition(P12, T20, 1.0);
        net.addArcFromPlaceToTransition(P19, T20, 1.0);
        net.addArcFromPlaceToTransition(P11, T11, 1.0);
        net.addArcFromPlaceToTransition(P12, T11, 1.0);
        net.addArcFromPlaceToTransition(P24, T22, 1.0);
        net.addArcFromPlaceToTransition(iP0, T0, 0.916666667);
        net.addArcFromPlaceToTransition(P0, T0, 1.0);
        net.addArcFromPlaceToTransition(P1, T1, 1.0);
        net.addArcFromPlaceToTransition(P3, T2, 1.0);
        net.addArcFromPlaceToTransition(P2, T2, 1.0);
        net.addArcFromPlaceToTransition(P4, T3, 1.0);
        net.addArcFromPlaceToTransition(P3, T3, 1.0);
        net.addArcFromPlaceToTransition(P5, T4, 1.0);
        net.addArcFromPlaceToTransition(P6, T5, 1.0);
        net.addArcFromPlaceToTransition(P22, oT0, 1.0);
        net.addArcFromPlaceToTransition(P25, oT2, 1.0);
        net.addArcFromPlaceToTransition(P23, oT1, 1.0);
        net.addArcFromPlaceToTransition(P50, T50, 1.0);
        net.addArcFromPlaceToTransition(P26, oT3, 1.0);
        net.addArcFromTransitionToPlace(T10, P11);
        net.addArcFromTransitionToPlace(T21, P22);
        net.addArcFromTransitionToPlace(T21, P23);
        net.addArcFromTransitionToPlace(T20, P21);
        net.addArcFromTransitionToPlace(T20, P24);
        net.addArcFromTransitionToPlace(T11, P12);
        net.addArcFromTransitionToPlace(T11, P11);
        net.addArcFromTransitionToPlace(T22, P25);
        net.addArcFromTransitionToPlace(T22, P26);
        net.addArcFromTransitionToPlace(T0, P50);
        net.addArcFromTransitionToPlace(T0, P1);
        net.addArcFromTransitionToPlace(T1, P0);
        net.addArcFromTransitionToPlace(T2, P3);
        net.addArcFromTransitionToPlace(T3, P4);
        net.addArcFromTransitionToPlace(T3, P5);
        net.addArcFromTransitionToPlace(T4, P19);
        net.addArcFromTransitionToPlace(T4, P6);
        net.addArcFromTransitionToPlace(T5, P3);
        net.addArcFromTransitionToPlace(T50, P2);
        net.addArcFromTransitionToPlace(T50, P10);
    }

    private void putInitialMarking(){
        net.setInitialMarkingForPlace(P0, FuzzyToken.buildFromString("<0.00,0.00,1.00,0.00,0.00>"));
        net.setInitialMarkingForPlace(P3, FuzzyToken.buildFromString("<0.00,0.00,1.00,0.00,0.00>"));
        net.setInitialMarkingForPlace(P4, FuzzyToken.buildFromString("<0.00,0.00,5.00,1.00,0.00>"));
    }

}