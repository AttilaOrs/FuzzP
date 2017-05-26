import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class first_exampleUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP3, iP5 ;
  /*ordinary places */
  public int P0, P1, P2, P4, P6, P7 ;
  /* output transitions */
  public int oT5 ;
  /* table for out transitions */
  String oT5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T2_d1, T3 ;
  /* table for delay transitions */
  String T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T1_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T2_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T3_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-1><-1><-1><-1><-1><FF>]"+//
  	"[< 0>< 0>< 0>< 0>< 0><FF>]"+//
  	"[< 1>< 1>< 1>< 1>< 1><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  /* variable delay transitions */
  public int T4_vd ;
  /* table for variable delay */
  String T4_vd_tableStr = "{"+
  	"[<-2, 0><-2, 0><-2, 0><-2, 0><-2, 0><FF,FF>]"+//
  	"[<-1, 0><-1, 0><-1, 0><-1, 0><-1, 0><FF,FF>]"+//
  	"[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0><FF,FF>]"+//
  	"[< 1, 0>< 1, 0>< 1, 0>< 1, 0>< 1, 0><FF,FF>]"+//
  	"[< 2, 0>< 2, 0>< 2, 0>< 2, 0>< 2, 0><FF,FF>]"+//
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
    iP3 = net.addInputPlace(1.0);
    iP5 = net.addInputPlace(20.0);
    P0 = net.addPlace(1.0);
    P1 = net.addPlace(20.0);
    P2 = net.addPlace(1.0);
    P4 = net.addPlace(20.0);
    P6 = net.addPlace(1.0);
    P7 = net.addPlace(20.0);
    nameStore.addPlaceName(iP3, "iP3" );
    nameStore.addPlaceName(iP5, "iP5" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P7, "P7" );
  }

  private void addTransitions(){
    oT5 =  net.addOuputTransition(parser.parseOneXOneTable(oT5_tableStr));
    nameStore.addTransitionName(oT5, "oT5" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2_d1 = net.addTransition(1, parser.parseTable(T2_d1_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2_d1, "T2_d1" );
    nameStore.addTransitionName(T3, "T3" );
    T4_vd = net.addTransitionVariableDelay(1.0, parser.parseTable(T4_vd_tableStr));
    nameStore.addTransitionName(T4_vd, "T4_vd" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(P1, T1);
    net.addArcFromPlaceToTransition(iP3, T1);
    net.addArcFromPlaceToTransition(P4, T2_d1);
    net.addArcFromPlaceToTransition(iP5, T3);
    net.addArcFromPlaceToTransition(P2, T3);
    net.addArcFromPlaceToTransition(P4, T4_vd);
    net.addArcFromPlaceToTransition(P6, T4_vd);
    net.addArcFromPlaceToTransition(P7, oT5);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(T1, P4);
    net.addArcFromTransitionToPlace(T2_d1, P1);
    net.addArcFromTransitionToPlace(T3, P6);
    net.addArcFromTransitionToPlace(T4_vd, P7);
    net.addArcFromTransitionToPlace(T4_vd, P0);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}