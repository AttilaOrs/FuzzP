import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class VehicleCalcUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP5, iP9, iP11 ;
  /*ordinary places */
  public int P0, P1, P2, P3, P4, P6, P7, P8, P10 ;
  /* output transitions */
  public int oT2, oT4 ;
  /* table for out transitions */
  String oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT4_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T3, T5, T6, T7_d1, T8 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-2><-1><-1><-1><-1><FF>]"+//
  	"[<-2><-1>< 0>< 0>< 0><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
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
  String T5_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T6_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T7_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T8_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public VehicleCalcUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP5 = net.addInputPlace(50.0);
    iP9 = net.addInputPlace(0.0);
    iP11 = net.addInputPlace(0.0);
    P0 = net.addPlace(0.0);
    P1 = net.addPlace(50.0);
    P2 = net.addPlace(0.0);
    P3 = net.addPlace(0.0);
    P4 = net.addPlace(0.0);
    P6 = net.addPlace(50.0);
    P7 = net.addPlace(50.0);
    P8 = net.addPlace(50.0);
    P10 = net.addPlace(50.0);
    nameStore.addPlaceName(iP5, "iP5" );
    nameStore.addPlaceName(iP9, "iP9" );
    nameStore.addPlaceName(iP11, "iP11" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P10, "P10" );
  }

  private void addTransitions(){
    oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
    oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
    nameStore.addTransitionName(oT2, "oT2" );
    nameStore.addTransitionName(oT4, "oT4" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T6 = net.addTransition(0, parser.parseTable(T6_tableStr));
    T7_d1 = net.addTransition(1, parser.parseTable(T7_d1_tableStr));
    T8 = net.addTransition(0, parser.parseTable(T8_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T6, "T6" );
    nameStore.addTransitionName(T7_d1, "T7_d1" );
    nameStore.addTransitionName(T8, "T8" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P10, T0);
    net.addArcFromPlaceToTransition(iP5, T0);
    net.addArcFromPlaceToTransition(P4, T1);
    net.addArcFromPlaceToTransition(P7, T1);
    net.addArcFromPlaceToTransition(P1, oT2);
    net.addArcFromPlaceToTransition(P6, T3);
    net.addArcFromPlaceToTransition(P8, oT4);
    net.addArcFromPlaceToTransition(P0, T5);
    net.addArcFromPlaceToTransition(iP9, T5);
    net.addArcFromPlaceToTransition(P2, T6);
    net.addArcFromPlaceToTransition(P3, T7_d1);
    net.addArcFromPlaceToTransition(P3, T8);
    net.addArcFromPlaceToTransition(iP11, T8);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T1, P6);
    net.addArcFromTransitionToPlace(T1, P7);
    net.addArcFromTransitionToPlace(T3, P8);
    net.addArcFromTransitionToPlace(T3, P10);
    net.addArcFromTransitionToPlace(T5, P2);
    net.addArcFromTransitionToPlace(T6, P3);
    net.addArcFromTransitionToPlace(T6, P4);
    net.addArcFromTransitionToPlace(T7_d1, P2);
    net.addArcFromTransitionToPlace(T8, P0);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P7, new UnifiedToken(20.0));
  }

}