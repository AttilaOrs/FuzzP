import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class VehicleCalcExtendedUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP2, iP10, iP12 ;
  /*ordinary places */
  public int P0, P1, P3, P4, P5, P6, P7, P8, P9, P11 ;
  /* output transitions */
  public int oT2, oT4, oT5 ;
  /* table for out transitions */
  String oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT4_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T3, T6, T7, T8_d1, T9 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2,-2><-2,-1><-2, 0><-2, 1><-2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1><-1, 0><-1, 1><-1, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 0, 1>< 0, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 1, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,-2><FF,-1><FF, 0><FF, 1><FF, 2><FF,FF>]"+//
  "}";
  String T1_tableStr = "{"+
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T3_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T6_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T7_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T8_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T9_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public VehicleCalcExtendedUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP2 = net.addInputPlace(50.0);
    iP10 = net.addInputPlace(0.0);
    iP12 = net.addInputPlace(0.0);
    P0 = net.addPlace(50.0);
    P1 = net.addPlace(50.0);
    P3 = net.addPlace(0.0);
    P4 = net.addPlace(0.0);
    P5 = net.addPlace(0.0);
    P6 = net.addPlace(0.0);
    P7 = net.addPlace(50.0);
    P8 = net.addPlace(50.0);
    P9 = net.addPlace(50.0);
    P11 = net.addPlace(50.0);
    nameStore.addPlaceName(iP2, "iP2" );
    nameStore.addPlaceName(iP10, "iP10" );
    nameStore.addPlaceName(iP12, "iP12" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P9, "P9" );
    nameStore.addPlaceName(P11, "P11" );
  }

  private void addTransitions(){
    oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
    oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
    oT5 =  net.addOuputTransition(parser.parseOneXOneTable(oT5_tableStr));
    nameStore.addTransitionName(oT2, "oT2" );
    nameStore.addTransitionName(oT4, "oT4" );
    nameStore.addTransitionName(oT5, "oT5" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    T6 = net.addTransition(0, parser.parseTable(T6_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    T8_d1 = net.addTransition(1, parser.parseTable(T8_d1_tableStr));
    T9 = net.addTransition(0, parser.parseTable(T9_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(T6, "T6" );
    nameStore.addTransitionName(T7, "T7" );
    nameStore.addTransitionName(T8_d1, "T8_d1" );
    nameStore.addTransitionName(T9, "T9" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P11, T0);
    net.addArcFromPlaceToTransition(iP2, T0);
    net.addArcFromPlaceToTransition(P6, T1);
    net.addArcFromPlaceToTransition(P8, T1);
    net.addArcFromPlaceToTransition(P0, oT2);
    net.addArcFromPlaceToTransition(P7, T3);
    net.addArcFromPlaceToTransition(P9, oT4);
    net.addArcFromPlaceToTransition(P1, oT5);
    net.addArcFromPlaceToTransition(P3, T6);
    net.addArcFromPlaceToTransition(iP10, T6);
    net.addArcFromPlaceToTransition(P4, T7);
    net.addArcFromPlaceToTransition(P5, T8_d1);
    net.addArcFromPlaceToTransition(P5, T9);
    net.addArcFromPlaceToTransition(iP12, T9);
    net.addArcFromTransitionToPlace(T0, P0);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T1, P7);
    net.addArcFromTransitionToPlace(T1, P8);
    net.addArcFromTransitionToPlace(T3, P9);
    net.addArcFromTransitionToPlace(T3, P11);
    net.addArcFromTransitionToPlace(T6, P4);
    net.addArcFromTransitionToPlace(T7, P5);
    net.addArcFromTransitionToPlace(T7, P6);
    net.addArcFromTransitionToPlace(T8_d1, P4);
    net.addArcFromTransitionToPlace(T9, P3);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P3, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P8, new UnifiedToken(20.0));
  }

}