import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class HeaterStateUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0, iP2, iP1, iP3 ;
  /*ordinary places */
  public int P0, P1, P2, P3, P4, P5, P6 ;
  /* output transitions */
  public int oT0 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T4, T5, T0, T1, T2, T3 ;
  /* table for delay transitions */
  String T4_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T0_tableStr = "{"+
  	"[< 1>< 1>< 1>< 1>< 1><-2>]"+//
  	"[< 1>< 1>< 1>< 1>< 1><-1>]"+//
  	"[< 1>< 1>< 1>< 1>< 1>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T1_tableStr = "{"+
  	"[< 1>< 1>< 1>< 1>< 1><-2>]"+//
  	"[< 1>< 1>< 1>< 1>< 1><-1>]"+//
  	"[< 1>< 1>< 1>< 1>< 1>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T2_tableStr = "{"+
  	"[< 0>< 0>< 0>< 0>< 0><-2>]"+//
  	"[< 0>< 0>< 0>< 0>< 0><-1>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 0>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 1>]"+//
  	"[< 1>< 1>< 1>< 1>< 1>< 2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T3_tableStr = "{"+
  	"[< 0>< 0>< 0>< 0>< 0><-2>]"+//
  	"[< 0>< 0>< 0>< 0>< 0><-1>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 0>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 1>]"+//
  	"[< 1>< 1>< 1>< 1>< 1>< 2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public HeaterStateUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(0.0);
    iP2 = net.addInputPlace(0.0);
    iP1 = net.addInputPlace(0.0);
    iP3 = net.addInputPlace(0.0);
    P0 = net.addPlace(1.0);
    P1 = net.addPlace(1.0);
    P2 = net.addPlace(1.0);
    P3 = net.addPlace(1.0);
    P4 = net.addPlace(1.0);
    P5 = net.addPlace(1.0);
    P6 = net.addPlace(1.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP2, "iP2" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(iP3, "iP3" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
    T5 = net.addTransition(1, parser.parseTable(T5_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P4, T4);
    net.addArcFromPlaceToTransition(P5, T5);
    net.addArcFromPlaceToTransition(P6, oT0);
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(P1, T1);
    net.addArcFromPlaceToTransition(iP1, T1);
    net.addArcFromPlaceToTransition(P2, T2);
    net.addArcFromPlaceToTransition(iP2, T2);
    net.addArcFromPlaceToTransition(P3, T3);
    net.addArcFromPlaceToTransition(iP3, T3);
    net.addArcFromTransitionToPlace(T4, P5);
    net.addArcFromTransitionToPlace(T4, P6);
    net.addArcFromTransitionToPlace(T5, P0);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T1, P2);
    net.addArcFromTransitionToPlace(T2, P3);
    net.addArcFromTransitionToPlace(T3, P4);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}