import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class QarterIntersectionUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP28, iP31, iP33, iP34 ;
  /*ordinary places */
  public int P0, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21, P22, P23, P24, P25, P26, P27, P29, P30, P32 ;
  /* output transitions */
  public int oT26, oT27 ;
  /* table for out transitions */
  String oT26_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT27_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T2, T3, T4, T5, T6, T7, T8_d1, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 ;
  /* table for delay transitions */
  String T0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T4_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T6_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T7_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T8_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T9_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T10_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T11_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T12_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T13_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T14_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T15_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T16_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T17_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><FF,FF>]"+//
  	"[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,FF>]"+//
  	"[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0><FF,FF>]"+//
  	"[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T18_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T19_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-2><-1><-1><-1><-1><FF>]"+//
  	"[<-2><-1>< 0>< 0>< 0><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T20_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T21_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T22_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T23_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T24_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T25_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public QarterIntersectionUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP28 = net.addInputPlace(0.0);
    iP31 = net.addInputPlace(50.0);
    iP33 = net.addInputPlace(0.0);
    iP34 = net.addInputPlace(1.0);
    P0 = net.addPlace(1.0);
    P1 = net.addPlace(50.0);
    P2 = net.addPlace(1.0);
    P3 = net.addPlace(50.0);
    P4 = net.addPlace(50.0);
    P5 = net.addPlace(50.0);
    P6 = net.addPlace(50.0);
    P7 = net.addPlace(50.0);
    P8 = net.addPlace(50.0);
    P9 = net.addPlace(50.0);
    P10 = net.addPlace(50.0);
    P11 = net.addPlace(50.0);
    P12 = net.addPlace(1.0);
    P13 = net.addPlace(1.0);
    P14 = net.addPlace(0.0);
    P15 = net.addPlace(1.0);
    P16 = net.addPlace(0.0);
    P17 = net.addPlace(50.0);
    P18 = net.addPlace(30.0);
    P19 = net.addPlace(50.0);
    P20 = net.addPlace(1.0);
    P21 = net.addPlace(50.0);
    P22 = net.addPlace(50.0);
    P23 = net.addPlace(50.0);
    P24 = net.addPlace(0.0);
    P25 = net.addPlace(0.0);
    P26 = net.addPlace(0.0);
    P27 = net.addPlace(0.0);
    P29 = net.addPlace(50.0);
    P30 = net.addPlace(50.0);
    P32 = net.addPlace(50.0);
    nameStore.addPlaceName(iP28, "iP28" );
    nameStore.addPlaceName(iP31, "iP31" );
    nameStore.addPlaceName(iP33, "iP33" );
    nameStore.addPlaceName(iP34, "iP34" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P9, "P9" );
    nameStore.addPlaceName(P10, "P10" );
    nameStore.addPlaceName(P11, "P11" );
    nameStore.addPlaceName(P12, "P12" );
    nameStore.addPlaceName(P13, "P13" );
    nameStore.addPlaceName(P14, "P14" );
    nameStore.addPlaceName(P15, "P15" );
    nameStore.addPlaceName(P16, "P16" );
    nameStore.addPlaceName(P17, "P17" );
    nameStore.addPlaceName(P18, "P18" );
    nameStore.addPlaceName(P19, "P19" );
    nameStore.addPlaceName(P20, "P20" );
    nameStore.addPlaceName(P21, "P21" );
    nameStore.addPlaceName(P22, "P22" );
    nameStore.addPlaceName(P23, "P23" );
    nameStore.addPlaceName(P24, "P24" );
    nameStore.addPlaceName(P25, "P25" );
    nameStore.addPlaceName(P26, "P26" );
    nameStore.addPlaceName(P27, "P27" );
    nameStore.addPlaceName(P29, "P29" );
    nameStore.addPlaceName(P30, "P30" );
    nameStore.addPlaceName(P32, "P32" );
  }

  private void addTransitions(){
    oT26 =  net.addOuputTransition(parser.parseOneXOneTable(oT26_tableStr));
    oT27 =  net.addOuputTransition(parser.parseOneXOneTable(oT27_tableStr));
    nameStore.addTransitionName(oT26, "oT26" );
    nameStore.addTransitionName(oT27, "oT27" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T6 = net.addTransition(0, parser.parseTable(T6_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    T8_d1 = net.addTransition(1, parser.parseTable(T8_d1_tableStr));
    T9 = net.addTransition(0, parser.parseTable(T9_tableStr));
    T10 = net.addTransition(0, parser.parseTable(T10_tableStr));
    T11 = net.addTransition(0, parser.parseTable(T11_tableStr));
    T12 = net.addTransition(0, parser.parseTable(T12_tableStr));
    T13 = net.addTransition(0, parser.parseTable(T13_tableStr));
    T14 = net.addTransition(0, parser.parseTable(T14_tableStr));
    T15 = net.addTransition(0, parser.parseTable(T15_tableStr));
    T16 = net.addTransition(0, parser.parseTable(T16_tableStr));
    T17 = net.addTransition(0, parser.parseTable(T17_tableStr));
    T18 = net.addTransition(0, parser.parseTable(T18_tableStr));
    T19 = net.addTransition(0, parser.parseTable(T19_tableStr));
    T20 = net.addTransition(0, parser.parseTable(T20_tableStr));
    T21 = net.addTransition(0, parser.parseTable(T21_tableStr));
    T22 = net.addTransition(0, parser.parseTable(T22_tableStr));
    T23 = net.addTransition(0, parser.parseTable(T23_tableStr));
    T24 = net.addTransition(0, parser.parseTable(T24_tableStr));
    T25 = net.addTransition(0, parser.parseTable(T25_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T6, "T6" );
    nameStore.addTransitionName(T7, "T7" );
    nameStore.addTransitionName(T8_d1, "T8_d1" );
    nameStore.addTransitionName(T9, "T9" );
    nameStore.addTransitionName(T10, "T10" );
    nameStore.addTransitionName(T11, "T11" );
    nameStore.addTransitionName(T12, "T12" );
    nameStore.addTransitionName(T13, "T13" );
    nameStore.addTransitionName(T14, "T14" );
    nameStore.addTransitionName(T15, "T15" );
    nameStore.addTransitionName(T16, "T16" );
    nameStore.addTransitionName(T17, "T17" );
    nameStore.addTransitionName(T18, "T18" );
    nameStore.addTransitionName(T19, "T19" );
    nameStore.addTransitionName(T20, "T20" );
    nameStore.addTransitionName(T21, "T21" );
    nameStore.addTransitionName(T22, "T22" );
    nameStore.addTransitionName(T23, "T23" );
    nameStore.addTransitionName(T24, "T24" );
    nameStore.addTransitionName(T25, "T25" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P32, T0);
    net.addArcFromPlaceToTransition(P21, T1);
    net.addArcFromPlaceToTransition(iP28, T2);
    net.addArcFromPlaceToTransition(iP33, T3);
    net.addArcFromPlaceToTransition(P24, T4);
    net.addArcFromPlaceToTransition(P16, T4);
    net.addArcFromPlaceToTransition(iP31, T5);
    net.addArcFromPlaceToTransition(P25, T6);
    net.addArcFromPlaceToTransition(iP34, T7);
    net.addArcFromPlaceToTransition(P26, T8_d1);
    net.addArcFromPlaceToTransition(P6, T9);
    net.addArcFromPlaceToTransition(P20, T10);
    net.addArcFromPlaceToTransition(P22, T11);
    net.addArcFromPlaceToTransition(P13, T12);
    net.addArcFromPlaceToTransition(P9, T12);
    net.addArcFromPlaceToTransition(P0, T13);
    net.addArcFromPlaceToTransition(P15, T13);
    net.addArcFromPlaceToTransition(P12, T14);
    net.addArcFromPlaceToTransition(P10, T14);
    net.addArcFromPlaceToTransition(P18, T15);
    net.addArcFromPlaceToTransition(P4, T15);
    net.addArcFromPlaceToTransition(P5, T16);
    net.addArcFromPlaceToTransition(P18, T16);
    net.addArcFromPlaceToTransition(P18, T17);
    net.addArcFromPlaceToTransition(P3, T17);
    net.addArcFromPlaceToTransition(P2, T18);
    net.addArcFromPlaceToTransition(P0, T18);
    net.addArcFromPlaceToTransition(P17, T19);
    net.addArcFromPlaceToTransition(P23, T19);
    net.addArcFromPlaceToTransition(P19, T20);
    net.addArcFromPlaceToTransition(P1, T21);
    net.addArcFromPlaceToTransition(P11, T22);
    net.addArcFromPlaceToTransition(P26, T23);
    net.addArcFromPlaceToTransition(P14, T23);
    net.addArcFromPlaceToTransition(P27, T24);
    net.addArcFromPlaceToTransition(P30, T24);
    net.addArcFromPlaceToTransition(P29, T25);
    net.addArcFromPlaceToTransition(P8, oT26);
    net.addArcFromPlaceToTransition(P7, oT27);
    net.addArcFromTransitionToPlace(T0, P3);
    net.addArcFromTransitionToPlace(T1, P23);
    net.addArcFromTransitionToPlace(T2, P16);
    net.addArcFromTransitionToPlace(T3, P14);
    net.addArcFromTransitionToPlace(T4, P25);
    net.addArcFromTransitionToPlace(T5, P5);
    net.addArcFromTransitionToPlace(T6, P26);
    net.addArcFromTransitionToPlace(T6, P27);
    net.addArcFromTransitionToPlace(T7, P20);
    net.addArcFromTransitionToPlace(T8_d1, P25);
    net.addArcFromTransitionToPlace(T9, P4);
    net.addArcFromTransitionToPlace(T9, P22);
    net.addArcFromTransitionToPlace(T10, P13);
    net.addArcFromTransitionToPlace(T10, P15);
    net.addArcFromTransitionToPlace(T11, P9);
    net.addArcFromTransitionToPlace(T11, P10);
    net.addArcFromTransitionToPlace(T12, P11);
    net.addArcFromTransitionToPlace(T13, P12);
    net.addArcFromTransitionToPlace(T14, P1);
    net.addArcFromTransitionToPlace(T15, P18);
    net.addArcFromTransitionToPlace(T16, P18);
    net.addArcFromTransitionToPlace(T17, P18);
    net.addArcFromTransitionToPlace(T17, P21);
    net.addArcFromTransitionToPlace(T18, P0);
    net.addArcFromTransitionToPlace(T18, P2);
    net.addArcFromTransitionToPlace(T19, P19);
    net.addArcFromTransitionToPlace(T20, P6);
    net.addArcFromTransitionToPlace(T21, P8);
    net.addArcFromTransitionToPlace(T22, P7);
    net.addArcFromTransitionToPlace(T23, P24);
    net.addArcFromTransitionToPlace(T24, P29);
    net.addArcFromTransitionToPlace(T24, P30);
    net.addArcFromTransitionToPlace(T25, P32);
    net.addArcFromTransitionToPlace(T25, P17);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P2, new UnifiedToken(1.0));
    net.setInitialMarkingForPlace(P18, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P24, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P30, new UnifiedToken(20.0));
  }

}