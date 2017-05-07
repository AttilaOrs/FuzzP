import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class RoomModelUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP2, iP1, iP3 ;
  /*ordinary places */
  public int P0, _P1, _P2, P2, P3, _P5, P4, P5, P6, _P9, _P10, P30, P10, P32, P12, P34, P11, P33, P14, P36, P13, P35, _P22, P15, _P24, P21, P20 ;
  /* output transitions */
  public int oT1 ;
  /* table for out transitions */
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T30, T10, T32, T31, T20, T34, T11, T35, T0, rs_T1, rs_T2, T3, rs_T0, T4, T5, T6, rs_T3, T7, T8, T9, rs_oT0 ;
  /* table for delay transitions */
  String T30_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T10_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T32_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T31_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T20_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T34_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T11_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T35_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String rs_T1_tableStr = "{"+
  	"[< 0>< 0>< 0>< 0>< 0><-2>]"+//
  	"[< 0>< 0>< 0>< 0>< 0><-1>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 0>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 1>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String rs_T2_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T3_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String rs_T0_tableStr = "{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T4_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T5_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T6_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String rs_T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T7_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T8_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T9_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String rs_oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public RoomModelUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP2 = net.addInputPlace(0.0);
    iP1 = net.addInputPlace(100.0);
    iP3 = net.addInputPlace(0.0);
    P0 = net.addPlace(100.0);
    _P1 = net.addPlace(1.0);
    _P2 = net.addPlace(1.0);
    P2 = net.addPlace(100.0);
    P3 = net.addPlace(100.0);
    _P5 = net.addPlace(1.0);
    P4 = net.addPlace(100.0);
    P5 = net.addPlace(1.0);
    P6 = net.addPlace(1.0);
    _P9 = net.addPlace(1.0);
    _P10 = net.addPlace(1.0);
    P30 = net.addPlace(100.0);
    P10 = net.addPlace(100.0);
    P32 = net.addPlace(100.0);
    P12 = net.addPlace(100.0);
    P34 = net.addPlace(100.0);
    P11 = net.addPlace(100.0);
    P33 = net.addPlace(100.0);
    P14 = net.addPlace(100.0);
    P36 = net.addPlace(100.0);
    P13 = net.addPlace(100.0);
    P35 = net.addPlace(100.0);
    _P22 = net.addPlace(0.0);
    P15 = net.addPlace(1.0);
    _P24 = net.addPlace(0.0);
    P21 = net.addPlace(1.0);
    P20 = net.addPlace(1.0);
    nameStore.addPlaceName(iP2, "iP2" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(iP3, "iP3" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(_P1, "_P1" );
    nameStore.addPlaceName(_P2, "_P2" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(_P5, "_P5" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(_P9, "_P9" );
    nameStore.addPlaceName(_P10, "_P10" );
    nameStore.addPlaceName(P30, "P30" );
    nameStore.addPlaceName(P10, "P10" );
    nameStore.addPlaceName(P32, "P32" );
    nameStore.addPlaceName(P12, "P12" );
    nameStore.addPlaceName(P34, "P34" );
    nameStore.addPlaceName(P11, "P11" );
    nameStore.addPlaceName(P33, "P33" );
    nameStore.addPlaceName(P14, "P14" );
    nameStore.addPlaceName(P36, "P36" );
    nameStore.addPlaceName(P13, "P13" );
    nameStore.addPlaceName(P35, "P35" );
    nameStore.addPlaceName(_P22, "_P22" );
    nameStore.addPlaceName(P15, "P15" );
    nameStore.addPlaceName(_P24, "_P24" );
    nameStore.addPlaceName(P21, "P21" );
    nameStore.addPlaceName(P20, "P20" );
  }

  private void addTransitions(){
    oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
    nameStore.addTransitionName(oT1, "oT1" );
    T30 = net.addTransition(0, parser.parseTable(T30_tableStr));
    T10 = net.addTransition(0, parser.parseTable(T10_tableStr));
    T32 = net.addTransition(0, parser.parseTable(T32_tableStr));
    T31 = net.addTransition(0, parser.parseTable(T31_tableStr));
    T20 = net.addTransition(0, parser.parseTable(T20_tableStr));
    T34 = net.addTransition(0, parser.parseTable(T34_tableStr));
    T11 = net.addTransition(0, parser.parseTable(T11_tableStr));
    T35 = net.addTransition(1, parser.parseTable(T35_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    rs_T1 = net.addTransition(0, parser.parseTable(rs_T1_tableStr));
    rs_T2 = net.addTransition(0, parser.parseTable(rs_T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    rs_T0 = net.addTransition(0, parser.parseTable(rs_T0_tableStr));
    T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T6 = net.addTransition(0, parser.parseTable(T6_tableStr));
    rs_T3 = net.addTransition(1, parser.parseTable(rs_T3_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    T8 = net.addTransition(0, parser.parseTable(T8_tableStr));
    T9 = net.addTransition(0, parser.parseTable(T9_tableStr));
    rs_oT0 = net.addTransition(0, parser.parseTable(rs_oT0_tableStr));
    nameStore.addTransitionName(T30, "T30" );
    nameStore.addTransitionName(T10, "T10" );
    nameStore.addTransitionName(T32, "T32" );
    nameStore.addTransitionName(T31, "T31" );
    nameStore.addTransitionName(T20, "T20" );
    nameStore.addTransitionName(T34, "T34" );
    nameStore.addTransitionName(T11, "T11" );
    nameStore.addTransitionName(T35, "T35" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(rs_T1, "rs_T1" );
    nameStore.addTransitionName(rs_T2, "rs_T2" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(rs_T0, "rs_T0" );
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T6, "T6" );
    nameStore.addTransitionName(rs_T3, "rs_T3" );
    nameStore.addTransitionName(T7, "T7" );
    nameStore.addTransitionName(T8, "T8" );
    nameStore.addTransitionName(T9, "T9" );
    nameStore.addTransitionName(rs_oT0, "rs_oT0" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P0, T30);
    net.addArcFromPlaceToTransition(iP2, T10);
    net.addArcFromPlaceToTransition(P32, T32);
    net.addArcFromPlaceToTransition(P33, T32);
    net.addArcFromPlaceToTransition(P4, T31);
    net.addArcFromPlaceToTransition(P14, T31);
    net.addArcFromPlaceToTransition(P20, T20);
    net.addArcFromPlaceToTransition(P21, T20);
    net.addArcFromPlaceToTransition(P34, T34);
    net.addArcFromPlaceToTransition(iP3, T11);
    net.addArcFromPlaceToTransition(P36, T35);
    net.addArcFromPlaceToTransition(P30, T0);
    net.addArcFromPlaceToTransition(_P1, rs_T1);
    net.addArcFromPlaceToTransition(_P24, rs_T1);
    net.addArcFromPlaceToTransition(_P2, rs_T2);
    net.addArcFromPlaceToTransition(P2, T3);
    net.addArcFromPlaceToTransition(P11, T3);
    net.addArcFromPlaceToTransition(_P5, rs_T0);
    net.addArcFromPlaceToTransition(_P22, rs_T0);
    net.addArcFromPlaceToTransition(P3, T4);
    net.addArcFromPlaceToTransition(P6, T4);
    net.addArcFromPlaceToTransition(P5, T5);
    net.addArcFromPlaceToTransition(P6, T5);
    net.addArcFromPlaceToTransition(iP1, T6);
    net.addArcFromPlaceToTransition(_P10, rs_T3);
    net.addArcFromPlaceToTransition(P10, T7);
    net.addArcFromPlaceToTransition(P12, T7);
    net.addArcFromPlaceToTransition(P13, T8);
    net.addArcFromPlaceToTransition(P21, T8);
    net.addArcFromPlaceToTransition(P13, T9);
    net.addArcFromPlaceToTransition(P15, T9);
    net.addArcFromPlaceToTransition(_P9, rs_oT0);
    net.addArcFromPlaceToTransition(P35, oT1);
    net.addArcFromTransitionToPlace(T30, P30);
    net.addArcFromTransitionToPlace(T30, P32);
    net.addArcFromTransitionToPlace(T10, _P22);
    net.addArcFromTransitionToPlace(T32, P34);
    net.addArcFromTransitionToPlace(T31, P33);
    net.addArcFromTransitionToPlace(T20, P21);
    net.addArcFromTransitionToPlace(T20, P20);
    net.addArcFromTransitionToPlace(T34, P35);
    net.addArcFromTransitionToPlace(T34, P36);
    net.addArcFromTransitionToPlace(T11, _P24);
    net.addArcFromTransitionToPlace(T35, P0);
    net.addArcFromTransitionToPlace(T0, P10);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(rs_T1, _P2);
    net.addArcFromTransitionToPlace(rs_T2, _P9);
    net.addArcFromTransitionToPlace(rs_T2, _P10);
    net.addArcFromTransitionToPlace(T3, P3);
    net.addArcFromTransitionToPlace(rs_T0, _P1);
    net.addArcFromTransitionToPlace(T4, P4);
    net.addArcFromTransitionToPlace(T5, P6);
    net.addArcFromTransitionToPlace(T5, P5);
    net.addArcFromTransitionToPlace(T6, P11);
    net.addArcFromTransitionToPlace(T6, P12);
    net.addArcFromTransitionToPlace(rs_T3, _P5);
    net.addArcFromTransitionToPlace(T7, P13);
    net.addArcFromTransitionToPlace(T8, P13);
    net.addArcFromTransitionToPlace(T9, P14);
    net.addArcFromTransitionToPlace(rs_oT0, P15);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(20.0));
    net.setInitialMarkingForPlace(_P5, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P5, new UnifiedToken(1.3888888E-4));
    net.setInitialMarkingForPlace(P20, new UnifiedToken(0.016));
  }

}