import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class RoomStateUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP10, iP11 ;
  /*ordinary places */
  public int P0, P1, P2, P3, P4 ;
  /* output transitions */
  public int oT0 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T2, T3 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[< 2>< 2>< 2>< 2>< 2><-2>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><-1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 0>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 1>]"+//
  	"[< 2>< 2>< 2>< 2>< 2>< 2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T1_tableStr = "{"+
  	"[< 0>< 0>< 0>< 0>< 0><-2>]"+//
  	"[< 0>< 0>< 0>< 0>< 0><-1>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 0>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 1>]"+//
  	"[< 0>< 0>< 0>< 0>< 0>< 2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T2_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public RoomStateUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP10 = net.addInputPlace(0.0);
    iP11 = net.addInputPlace(0.0);
    P0 = net.addPlace(1.0);
    P1 = net.addPlace(1.0);
    P2 = net.addPlace(1.0);
    P3 = net.addPlace(1.0);
    P4 = net.addPlace(1.0);
    nameStore.addPlaceName(iP10, "iP10" );
    nameStore.addPlaceName(iP11, "iP11" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(1, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P3, oT0);
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(iP10, T0);
    net.addArcFromPlaceToTransition(P1, T1);
    net.addArcFromPlaceToTransition(iP11, T1);
    net.addArcFromPlaceToTransition(P2, T2);
    net.addArcFromPlaceToTransition(P4, T3);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T1, P2);
    net.addArcFromTransitionToPlace(T2, P3);
    net.addArcFromTransitionToPlace(T2, P4);
    net.addArcFromTransitionToPlace(T3, P0);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}