import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class OuputQuarterUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0, iP1 ;
  /*ordinary places */
  public int _P0, _P1, _P2, _P3, P2, _P5, _P6, _P9, _P10, _P11, _P12, _P13 ;
  /* output transitions */
  public int oT0 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int out_lane_T2, out_T1, out_lane_oT3, out_T10, T0, T1, out_oT0, out_T2, out_lane_T0, out_T3, out_lane_T1 ;
  /* table for delay transitions */
  String out_lane_T2_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String out_T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String out_lane_oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String out_T10_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String out_oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String out_T2_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String out_lane_T0_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String out_T3_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String out_lane_T1_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public OuputQuarterUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(50.0);
    iP1 = net.addInputPlace(50.0);
    _P0 = net.addPlace(50.0);
    _P1 = net.addPlace(50.0);
    _P2 = net.addPlace(50.0);
    _P3 = net.addPlace(50.0);
    P2 = net.addPlace(50.0);
    _P5 = net.addPlace(50.0);
    _P6 = net.addPlace(50.0);
    _P9 = net.addPlace(50.0);
    _P10 = net.addPlace(50.0);
    _P11 = net.addPlace(50.0);
    _P12 = net.addPlace(50.0);
    _P13 = net.addPlace(50.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(_P0, "_P0" );
    nameStore.addPlaceName(_P1, "_P1" );
    nameStore.addPlaceName(_P2, "_P2" );
    nameStore.addPlaceName(_P3, "_P3" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(_P5, "_P5" );
    nameStore.addPlaceName(_P6, "_P6" );
    nameStore.addPlaceName(_P9, "_P9" );
    nameStore.addPlaceName(_P10, "_P10" );
    nameStore.addPlaceName(_P11, "_P11" );
    nameStore.addPlaceName(_P12, "_P12" );
    nameStore.addPlaceName(_P13, "_P13" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    out_lane_T2 = net.addTransition(0, parser.parseTable(out_lane_T2_tableStr));
    out_T1 = net.addTransition(0, parser.parseTable(out_T1_tableStr));
    out_lane_oT3 = net.addTransition(0, parser.parseTable(out_lane_oT3_tableStr));
    out_T10 = net.addTransition(0, parser.parseTable(out_T10_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    out_oT0 = net.addTransition(0, parser.parseTable(out_oT0_tableStr));
    out_T2 = net.addTransition(0, parser.parseTable(out_T2_tableStr));
    out_lane_T0 = net.addTransition(0, parser.parseTable(out_lane_T0_tableStr));
    out_T3 = net.addTransition(0, parser.parseTable(out_T3_tableStr));
    out_lane_T1 = net.addTransition(0, parser.parseTable(out_lane_T1_tableStr));
    nameStore.addTransitionName(out_lane_T2, "out_lane_T2" );
    nameStore.addTransitionName(out_T1, "out_T1" );
    nameStore.addTransitionName(out_lane_oT3, "out_lane_oT3" );
    nameStore.addTransitionName(out_T10, "out_T10" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(out_oT0, "out_oT0" );
    nameStore.addTransitionName(out_T2, "out_T2" );
    nameStore.addTransitionName(out_lane_T0, "out_lane_T0" );
    nameStore.addTransitionName(out_T3, "out_T3" );
    nameStore.addTransitionName(out_lane_T1, "out_lane_T1" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(_P5, out_lane_T2);
    net.addArcFromPlaceToTransition(_P11, out_lane_T2);
    net.addArcFromPlaceToTransition(_P10, out_T1);
    net.addArcFromPlaceToTransition(_P6, out_lane_oT3);
    net.addArcFromPlaceToTransition(_P9, out_T10);
    net.addArcFromPlaceToTransition(P2, oT0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(iP1, T1);
    net.addArcFromPlaceToTransition(_P0, out_oT0);
    net.addArcFromPlaceToTransition(_P2, out_T2);
    net.addArcFromPlaceToTransition(_P5, out_lane_T0);
    net.addArcFromPlaceToTransition(_P13, out_lane_T0);
    net.addArcFromPlaceToTransition(_P3, out_T3);
    net.addArcFromPlaceToTransition(_P1, out_T3);
    net.addArcFromPlaceToTransition(_P12, out_lane_T1);
    net.addArcFromPlaceToTransition(_P5, out_lane_T1);
    net.addArcFromTransitionToPlace(out_lane_T2, _P5);
    net.addArcFromTransitionToPlace(out_T1, _P12);
    net.addArcFromTransitionToPlace(out_lane_oT3, _P1);
    net.addArcFromTransitionToPlace(out_T10, _P11);
    net.addArcFromTransitionToPlace(out_T10, _P13);
    net.addArcFromTransitionToPlace(T0, _P9);
    net.addArcFromTransitionToPlace(T1, _P10);
    net.addArcFromTransitionToPlace(out_oT0, P2);
    net.addArcFromTransitionToPlace(out_T2, _P2);
    net.addArcFromTransitionToPlace(out_T2, _P3);
    net.addArcFromTransitionToPlace(out_lane_T0, _P5);
    net.addArcFromTransitionToPlace(out_lane_T0, _P6);
    net.addArcFromTransitionToPlace(out_T3, _P0);
    net.addArcFromTransitionToPlace(out_lane_T1, _P5);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(_P2, new UnifiedToken(20.0));
    net.setInitialMarkingForPlace(_P5, new UnifiedToken(10.0));
  }

}