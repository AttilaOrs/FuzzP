import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class BipositionalControllerUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0, iP1 ;
  /*ordinary places */
  public int _P0, _P1, P0, P1, _P4, _P5, _P6, _P7, _P8, _P9, _P10, _P11, _P12, _P13, _P14, _P15, _P16, _P17, _P18, _P19, _P20, _P21, _P23, _P24, _P26 ;
  /* output transitions */
  public int oT0, oT1 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int df_oT0, T0, sp_oT0, T1, sp_oT1, df_T0, stop_oT0, df_T1, start_oT0, df_T2, stop_T10, start_T10, stop_T11, start_T2, start_T11, stop_T2, sp_T0, stop_T1, start_T0, stop_T0, start_T1 ;
  /* table for delay transitions */
  String df_oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String sp_oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String sp_oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String df_T0_tableStr = "{"+
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String stop_oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String df_T1_tableStr = "@-@ {"+
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String start_oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String df_T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String stop_T10_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String start_T10_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String stop_T11_tableStr = "{[< 0>< 0>< 0>< 0>< 0>< 0>]}";
  String start_T2_tableStr = "{[< 0>< 0><FF><FF><FF><FF>]}";
  String start_T11_tableStr = "{[< 0>< 0>< 0>< 0>< 0>< 0>]}";
  String stop_T2_tableStr = "{[<FF><FF><FF>< 0>< 0><FF>]}";
  String sp_T0_tableStr = "{[<-2,FF><-1,FF>< 0, 0><FF, 1><FF, 2><FF,FF>]}";
  String stop_T1_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String start_T0_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String stop_T0_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String start_T1_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public BipositionalControllerUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(100.0);
    iP1 = net.addInputPlace(100.0);
    _P0 = net.addPlace(100.0);
    _P1 = net.addPlace(100.0);
    P0 = net.addPlace(100.0);
    P1 = net.addPlace(100.0);
    _P4 = net.addPlace(100.0);
    _P5 = net.addPlace(100.0);
    _P6 = net.addPlace(100.0);
    _P7 = net.addPlace(100.0);
    _P8 = net.addPlace(100.0);
    _P9 = net.addPlace(100.0);
    _P10 = net.addPlace(100.0);
    _P11 = net.addPlace(100.0);
    _P12 = net.addPlace(100.0);
    _P13 = net.addPlace(100.0);
    _P14 = net.addPlace(100.0);
    _P15 = net.addPlace(100.0);
    _P16 = net.addPlace(100.0);
    _P17 = net.addPlace(100.0);
    _P18 = net.addPlace(100.0);
    _P19 = net.addPlace(100.0);
    _P20 = net.addPlace(100.0);
    _P21 = net.addPlace(100.0);
    _P23 = net.addPlace(100.0);
    _P24 = net.addPlace(100.0);
    _P26 = net.addPlace(100.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(_P0, "_P0" );
    nameStore.addPlaceName(_P1, "_P1" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(_P4, "_P4" );
    nameStore.addPlaceName(_P5, "_P5" );
    nameStore.addPlaceName(_P6, "_P6" );
    nameStore.addPlaceName(_P7, "_P7" );
    nameStore.addPlaceName(_P8, "_P8" );
    nameStore.addPlaceName(_P9, "_P9" );
    nameStore.addPlaceName(_P10, "_P10" );
    nameStore.addPlaceName(_P11, "_P11" );
    nameStore.addPlaceName(_P12, "_P12" );
    nameStore.addPlaceName(_P13, "_P13" );
    nameStore.addPlaceName(_P14, "_P14" );
    nameStore.addPlaceName(_P15, "_P15" );
    nameStore.addPlaceName(_P16, "_P16" );
    nameStore.addPlaceName(_P17, "_P17" );
    nameStore.addPlaceName(_P18, "_P18" );
    nameStore.addPlaceName(_P19, "_P19" );
    nameStore.addPlaceName(_P20, "_P20" );
    nameStore.addPlaceName(_P21, "_P21" );
    nameStore.addPlaceName(_P23, "_P23" );
    nameStore.addPlaceName(_P24, "_P24" );
    nameStore.addPlaceName(_P26, "_P26" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    nameStore.addTransitionName(oT1, "oT1" );
    df_oT0 = net.addTransition(0, parser.parseTable(df_oT0_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    sp_oT0 = net.addTransition(0, parser.parseTable(sp_oT0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    sp_oT1 = net.addTransition(0, parser.parseTable(sp_oT1_tableStr));
    df_T0 = net.addTransition(0, parser.parseTable(df_T0_tableStr));
    stop_oT0 = net.addTransition(0, parser.parseTable(stop_oT0_tableStr));
    df_T1 = net.addTransition(0, parser.parseTable(df_T1_tableStr));
    start_oT0 = net.addTransition(0, parser.parseTable(start_oT0_tableStr));
    df_T2 = net.addTransition(1, parser.parseTable(df_T2_tableStr));
    stop_T10 = net.addTransition(0, parser.parseTable(stop_T10_tableStr));
    start_T10 = net.addTransition(0, parser.parseTable(start_T10_tableStr));
    stop_T11 = net.addTransition(1, parser.parseTable(stop_T11_tableStr));
    start_T2 = net.addTransition(0, parser.parseTable(start_T2_tableStr));
    start_T11 = net.addTransition(1, parser.parseTable(start_T11_tableStr));
    stop_T2 = net.addTransition(0, parser.parseTable(stop_T2_tableStr));
    sp_T0 = net.addTransition(0, parser.parseTable(sp_T0_tableStr));
    stop_T1 = net.addTransition(0, parser.parseTable(stop_T1_tableStr));
    start_T0 = net.addTransition(0, parser.parseTable(start_T0_tableStr));
    stop_T0 = net.addTransition(0, parser.parseTable(stop_T0_tableStr));
    start_T1 = net.addTransition(0, parser.parseTable(start_T1_tableStr));
    nameStore.addTransitionName(df_oT0, "df_oT0" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(sp_oT0, "sp_oT0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(sp_oT1, "sp_oT1" );
    nameStore.addTransitionName(df_T0, "df_T0" );
    nameStore.addTransitionName(stop_oT0, "stop_oT0" );
    nameStore.addTransitionName(df_T1, "df_T1" );
    nameStore.addTransitionName(start_oT0, "start_oT0" );
    nameStore.addTransitionName(df_T2, "df_T2" );
    nameStore.addTransitionName(stop_T10, "stop_T10" );
    nameStore.addTransitionName(start_T10, "start_T10" );
    nameStore.addTransitionName(stop_T11, "stop_T11" );
    nameStore.addTransitionName(start_T2, "start_T2" );
    nameStore.addTransitionName(start_T11, "start_T11" );
    nameStore.addTransitionName(stop_T2, "stop_T2" );
    nameStore.addTransitionName(sp_T0, "sp_T0" );
    nameStore.addTransitionName(stop_T1, "stop_T1" );
    nameStore.addTransitionName(start_T0, "start_T0" );
    nameStore.addTransitionName(stop_T0, "stop_T0" );
    nameStore.addTransitionName(start_T1, "start_T1" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(_P24, df_oT0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(_P11, sp_oT0);
    net.addArcFromPlaceToTransition(iP1, T1);
    net.addArcFromPlaceToTransition(_P12, sp_oT1);
    net.addArcFromPlaceToTransition(_P17, df_T0);
    net.addArcFromPlaceToTransition(_P10, df_T0);
    net.addArcFromPlaceToTransition(_P19, stop_oT0);
    net.addArcFromPlaceToTransition(_P18, df_T1);
    net.addArcFromPlaceToTransition(_P13, df_T1);
    net.addArcFromPlaceToTransition(_P4, start_oT0);
    net.addArcFromPlaceToTransition(_P26, df_T2);
    net.addArcFromPlaceToTransition(_P1, stop_T10);
    net.addArcFromPlaceToTransition(_P7, stop_T10);
    net.addArcFromPlaceToTransition(_P16, start_T10);
    net.addArcFromPlaceToTransition(_P14, start_T10);
    net.addArcFromPlaceToTransition(P0, oT0);
    net.addArcFromPlaceToTransition(_P20, stop_T11);
    net.addArcFromPlaceToTransition(_P8, start_T2);
    net.addArcFromPlaceToTransition(_P8, start_T11);
    net.addArcFromPlaceToTransition(_P20, stop_T2);
    net.addArcFromPlaceToTransition(_P0, sp_T0);
    net.addArcFromPlaceToTransition(_P23, stop_T1);
    net.addArcFromPlaceToTransition(_P21, stop_T1);
    net.addArcFromPlaceToTransition(_P6, start_T0);
    net.addArcFromPlaceToTransition(_P15, start_T0);
    net.addArcFromPlaceToTransition(P1, oT1);
    net.addArcFromPlaceToTransition(_P21, stop_T0);
    net.addArcFromPlaceToTransition(_P5, stop_T0);
    net.addArcFromPlaceToTransition(_P9, start_T1);
    net.addArcFromPlaceToTransition(_P6, start_T1);
    net.addArcFromTransitionToPlace(df_oT0, _P0);
    net.addArcFromTransitionToPlace(T0, _P10);
    net.addArcFromTransitionToPlace(sp_oT0, _P16);
    net.addArcFromTransitionToPlace(T1, _P13);
    net.addArcFromTransitionToPlace(sp_oT1, _P1);
    net.addArcFromTransitionToPlace(df_T0, _P18);
    net.addArcFromTransitionToPlace(stop_oT0, P1);
    net.addArcFromTransitionToPlace(df_T1, _P24);
    net.addArcFromTransitionToPlace(df_T1, _P26);
    net.addArcFromTransitionToPlace(start_oT0, P0);
    net.addArcFromTransitionToPlace(df_T2, _P17);
    net.addArcFromTransitionToPlace(stop_T10, _P5);
    net.addArcFromTransitionToPlace(start_T10, _P15);
    net.addArcFromTransitionToPlace(stop_T11, _P7);
    net.addArcFromTransitionToPlace(start_T2, _P4);
    net.addArcFromTransitionToPlace(start_T11, _P14);
    net.addArcFromTransitionToPlace(stop_T2, _P19);
    net.addArcFromTransitionToPlace(sp_T0, _P11);
    net.addArcFromTransitionToPlace(sp_T0, _P12);
    net.addArcFromTransitionToPlace(stop_T1, _P21);
    net.addArcFromTransitionToPlace(stop_T1, _P23);
    net.addArcFromTransitionToPlace(start_T0, _P8);
    net.addArcFromTransitionToPlace(stop_T0, _P20);
    net.addArcFromTransitionToPlace(start_T1, _P6);
    net.addArcFromTransitionToPlace(start_T1, _P9);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(_P8, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P9, new UnifiedToken(10.0));
    net.setInitialMarkingForPlace(_P17, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P20, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(_P23, new UnifiedToken(-10.0));
  }

}