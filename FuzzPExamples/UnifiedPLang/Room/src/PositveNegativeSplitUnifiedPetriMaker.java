import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class PositveNegativeSplitUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0 ;
  /*ordinary places */
  public int P1, P2, P4, P5, P6, P7, P8, P9 ;
  /* output transitions */
  public int oT0, oT1 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T4, T0, T1, T2, T3 ;
  /* table for delay transitions */
  String T4_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T0_tableStr = "{"+
  	"[< 0,FF>< 0,FF>< 0,FF>< 0,FF>< 0,FF><FF,FF>]"+//
  	"[< 0,FF>< 0,FF>< 0,FF>< 0,FF>< 0,FF><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  	"[<FF, 0><FF, 0><FF, 0><FF, 0><FF, 0><FF,FF>]"+//
  	"[<FF, 0><FF, 0><FF, 0><FF, 0><FF, 0><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T1_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T2_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T3_tableStr = "{"+
  	"[<FF><FF><FF><FF><FF>< 0>]"+//
  	"[<FF><FF><FF><FF><FF>< 0>]"+//
  	"[<FF><FF><FF><FF><FF>< 0>]"+//
  	"[<FF><FF><FF><FF><FF>< 0>]"+//
  	"[<FF><FF><FF><FF><FF>< 0>]"+//
  	"[< 0>< 0>< 0>< 0>< 0><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public PositveNegativeSplitUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(1.0);
    P1 = net.addPlace(1.0);
    P2 = net.addPlace(1.0);
    P4 = net.addPlace(1.0);
    P5 = net.addPlace(1.0);
    P6 = net.addPlace(1.0);
    P7 = net.addPlace(1.0);
    P8 = net.addPlace(1.0);
    P9 = net.addPlace(1.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P9, "P9" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    nameStore.addTransitionName(oT1, "oT1" );
    T4 = net.addTransition(1, parser.parseTable(T4_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P8, T4);
    net.addArcFromPlaceToTransition(P5, oT0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(P9, T0);
    net.addArcFromPlaceToTransition(P7, oT1);
    net.addArcFromPlaceToTransition(P1, T1);
    net.addArcFromPlaceToTransition(P2, T2);
    net.addArcFromPlaceToTransition(P4, T3);
    net.addArcFromPlaceToTransition(P6, T3);
    net.addArcFromTransitionToPlace(T4, P9);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(T1, P4);
    net.addArcFromTransitionToPlace(T1, P5);
    net.addArcFromTransitionToPlace(T2, P6);
    net.addArcFromTransitionToPlace(T2, P7);
    net.addArcFromTransitionToPlace(T3, P8);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P9, new UnifiedToken(0.0));
  }

}