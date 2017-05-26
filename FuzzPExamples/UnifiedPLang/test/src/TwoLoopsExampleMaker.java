import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class TwoLoopsExampleMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP3, iP6 ;
  /*ordinary places */
  public int P0, P1, P2, P4, P5, P7, P8, P9 ;
  /* output transitions */
  public int oT2, oT5, oT8 ;
  /* table for out transitions */
  String oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT8_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T3_d3, T4, T6_d5, T7 ;
  /* table for delay transitions */
  String T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T1_tableStr = "@+@ {"+
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T3_d3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T4_tableStr = "@+@ {"+
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T6_d5_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T7_tableStr = "{"+
  	"[< 0, 0><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public TwoLoopsExampleMaker() {
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP3 = net.addInputPlace(2.0);
    iP6 = net.addInputPlace(2.0);
    P0 = net.addPlace(2.0);
    P1 = net.addPlace(2.0);
    P2 = net.addPlace(2.0);
    P4 = net.addPlace(2.0);
    P5 = net.addPlace(2.0);
    P7 = net.addPlace(2.0);
    P8 = net.addPlace(2.0);
    P9 = net.addPlace(2.0);
    nameStore.addPlaceName(iP3, "iP3" );
    nameStore.addPlaceName(iP6, "iP6" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P9, "P9" );
  }

  private void addTransitions(){
    oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
    oT5 =  net.addOuputTransition(parser.parseOneXOneTable(oT5_tableStr));
    oT8 =  net.addOuputTransition(parser.parseOneXOneTable(oT8_tableStr));
    nameStore.addTransitionName(oT2, "oT2" );
    nameStore.addTransitionName(oT5, "oT5" );
    nameStore.addTransitionName(oT8, "oT8" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T3_d3 = net.addTransition(3, parser.parseTable(T3_d3_tableStr));
    T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
    T6_d5 = net.addTransition(5, parser.parseTable(T6_d5_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T3_d3, "T3_d3" );
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T6_d5, "T6_d5" );
    nameStore.addTransitionName(T7, "T7" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(P1, T1);
    net.addArcFromPlaceToTransition(iP3, T1);
    net.addArcFromPlaceToTransition(P5, oT2);
    net.addArcFromPlaceToTransition(P4, T3_d3);
    net.addArcFromPlaceToTransition(P2, T4);
    net.addArcFromPlaceToTransition(iP6, T4);
    net.addArcFromPlaceToTransition(P8, oT5);
    net.addArcFromPlaceToTransition(P7, T6_d5);
    net.addArcFromPlaceToTransition(P7, T7);
    net.addArcFromPlaceToTransition(P4, T7);
    net.addArcFromPlaceToTransition(P9, oT8);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(T1, P4);
    net.addArcFromTransitionToPlace(T1, P5);
    net.addArcFromTransitionToPlace(T3_d3, P1);
    net.addArcFromTransitionToPlace(T4, P7);
    net.addArcFromTransitionToPlace(T4, P8);
    net.addArcFromTransitionToPlace(T6_d5, P2);
    net.addArcFromTransitionToPlace(T7, P0);
    net.addArcFromTransitionToPlace(T7, P9);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}