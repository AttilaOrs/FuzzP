package plant;

import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class ControllerUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP8, iP7, iP15, iP4, iP3 ;
  /*ordinary places */
  public int P12, P11, P14, P13, P0, P1, P2, P5, P6, P9, P10 ;
  /* output transitions */
  public int oT6, oT19 ;
  /* table for out transitions */
  String oT6_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT19_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T4, T5, T7, T8, T0, T1, T2, T3 ;
  /* table for delay transitions */
  String T4_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-2><-1><-1><-1><-1><FF>]"+//
  	"[<-2><-1>< 0>< 0>< 0><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T5_tableStr = "{"+
  	"[<-2,-2><-2,-2><-1,-1><-1,-1>< 0, 0><FF,FF>]"+//
  	"[<-2,-2><-1,-1><-1,-1>< 0, 0>< 1, 1><FF,FF>]"+//
  	"[<-1,-1><-1,-1>< 0, 0>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[<-1,-1>< 0, 0>< 1, 1>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[< 0, 0>< 1, 1>< 1, 1>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T7_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T8_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T0_tableStr = "{"+
  	"[<-2,-2><-2,-2><-1,-1><-1,-1>< 0, 0><FF,FF>]"+//
  	"[<-2,-2><-1,-1><-1,-1>< 0, 0>< 1, 1><FF,FF>]"+//
  	"[<-1,-1><-1,-1>< 0, 0>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[<-1,-1>< 0, 0>< 1, 1>< 1, 1>< 2, 2><FF,FF>]"+//
  	"[< 0, 0>< 1, 1>< 1, 1>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T1_tableStr = "{"+
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T2_tableStr = "{"+
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T3_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-2><-1><-1><-1><-1><FF>]"+//
  	"[<-2><-1>< 0>< 0>< 0><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public ControllerUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP8 = net.addInputPlace(50.0);
    iP7 = net.addInputPlace(50.0);
    iP15 = net.addInputPlace(0.0);
    iP4 = net.addInputPlace(50.0);
    iP3 = net.addInputPlace(50.0);
    P12 = net.addPlace(50.0);
    P11 = net.addPlace(50.0);
    P14 = net.addPlace(50.0);
    P13 = net.addPlace(50.0);
    P0 = net.addPlace(0.0);
    P1 = net.addPlace(0.0);
    P2 = net.addPlace(0.0);
    P5 = net.addPlace(50.0);
    P6 = net.addPlace(50.0);
    P9 = net.addPlace(50.0);
    P10 = net.addPlace(50.0);
    nameStore.addPlaceName(iP8, "iP8" );
    nameStore.addPlaceName(iP7, "iP7" );
    nameStore.addPlaceName(iP15, "iP15" );
    nameStore.addPlaceName(iP4, "iP4" );
    nameStore.addPlaceName(iP3, "iP3" );
    nameStore.addPlaceName(P12, "P12" );
    nameStore.addPlaceName(P11, "P11" );
    nameStore.addPlaceName(P14, "P14" );
    nameStore.addPlaceName(P13, "P13" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P9, "P9" );
    nameStore.addPlaceName(P10, "P10" );
  }

  private void addTransitions(){
    oT6 =  net.addOuputTransition(parser.parseOneXOneTable(oT6_tableStr));
    oT19 =  net.addOuputTransition(parser.parseOneXOneTable(oT19_tableStr));
    nameStore.addTransitionName(oT6, "oT6" );
    nameStore.addTransitionName(oT19, "oT19" );
    T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    T8 = net.addTransition(0, parser.parseTable(T8_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T7, "T7" );
    nameStore.addTransitionName(T8, "T8" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P11, oT6);
    net.addArcFromPlaceToTransition(P6, T4);
    net.addArcFromPlaceToTransition(iP8, T4);
    net.addArcFromPlaceToTransition(P9, T5);
    net.addArcFromPlaceToTransition(P10, T5);
    net.addArcFromPlaceToTransition(P12, T7);
    net.addArcFromPlaceToTransition(P14, T8);
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(iP15, T0);
    net.addArcFromPlaceToTransition(P1, T1);
    net.addArcFromPlaceToTransition(iP3, T1);
    net.addArcFromPlaceToTransition(P13, oT19);
    net.addArcFromPlaceToTransition(P2, T2);
    net.addArcFromPlaceToTransition(iP4, T2);
    net.addArcFromPlaceToTransition(P5, T3);
    net.addArcFromPlaceToTransition(iP7, T3);
    net.addArcFromTransitionToPlace(T4, P10);
    net.addArcFromTransitionToPlace(T5, P11);
    net.addArcFromTransitionToPlace(T5, P12);
    net.addArcFromTransitionToPlace(T7, P13);
    net.addArcFromTransitionToPlace(T7, P14);
    net.addArcFromTransitionToPlace(T8, P0);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(T1, P5);
    net.addArcFromTransitionToPlace(T2, P6);
    net.addArcFromTransitionToPlace(T3, P9);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}