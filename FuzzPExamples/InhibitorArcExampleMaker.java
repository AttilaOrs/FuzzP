import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class first_exampleUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0, iP5 ;
  /*ordinary places */
  public int P1, P2, P3, P4, P6 ;
  /* output transitions */
  public int oT4 ;
  /* table for out transitions */
  String oT4_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1_d1, T2_d1, T3 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T1_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T2_d1_tableStr = "{"+
  	"[<FF><FF><FF><FF><FF>< 2>]"+//
  	"[<FF><FF><FF><FF><FF>< 1>]"+//
  	"[<FF><FF><FF><FF><FF>< 0>]"+//
  	"[<FF><FF><FF><FF><FF><-1>]"+//
  	"[<FF><FF><FF><FF><FF><-2>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T3_tableStr = "@*@ {"+
  	"[< 2, 2>< 2, 2>< 2, 2>< 1, 1>< 0, 0><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 1, 1>< 0, 0><-1,-1><FF,FF>]"+//
  	"[< 2, 2>< 1, 1>< 0, 0><-1,-1><-2,-2><FF,FF>]"+//
  	"[< 1, 1>< 0, 0><-1,-1><-2,-2><-2,-2><FF,FF>]"+//
  	"[< 0, 0><-1,-1><-2,-2><-2,-2><-2,-2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public first_exampleUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(1.0);
    iP5 = net.addInputPlace(20.0);
    P1 = net.addPlace(1.0);
    P2 = net.addPlace(2.0);
    P3 = net.addPlace(1.0);
    P4 = net.addPlace(1.0);
    P6 = net.addPlace(1.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP5, "iP5" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P6, "P6" );
  }

  private void addTransitions(){
    oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
    nameStore.addTransitionName(oT4, "oT4" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1_d1 = net.addTransition(1, parser.parseTable(T1_d1_tableStr));
    T2_d1 = net.addTransition(1, parser.parseTable(T2_d1_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1_d1, "T1_d1" );
    nameStore.addTransitionName(T2_d1, "T2_d1" );
    nameStore.addTransitionName(T3, "T3" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(P1, T0);
    net.addArcFromPlaceToTransition(P2, T1_d1);
    net.addArcFromPlaceToTransition(P3, T2_d1);
    net.addArcFromPlaceToTransition(P1, T2_d1);
    net.addArcFromPlaceToTransition(iP5, T3);
    net.addArcFromPlaceToTransition(P4, T3);
    net.addArcFromPlaceToTransition(P6, oT4);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(T1_d1, P1);
    net.addArcFromTransitionToPlace(T2_d1, P4);
    net.addArcFromTransitionToPlace(T3, P3);
    net.addArcFromTransitionToPlace(T3, P6);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P1, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P3, new UnifiedToken(1.0));
  }

}