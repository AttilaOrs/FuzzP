import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class SplitterUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP6, iP9 ;
  /*ordinary places */
  public int P0, P1, P2, P3, P4, P5, P7, P8, P10 ;
  /* output transitions */
  public int oT0, oT2 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T1, T3, T4, T5, T6, T7 ;
  /* table for delay transitions */
  String T1_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T3_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T4_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T5_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T6_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T7_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public SplitterUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP6 = net.addInputPlace(1.0);
    iP9 = net.addInputPlace(30.0);
    P0 = net.addPlace(1.0);
    P1 = net.addPlace(1.0);
    P2 = net.addPlace(30.0);
    P3 = net.addPlace(30.0);
    P4 = net.addPlace(30.0);
    P5 = net.addPlace(1.0);
    P7 = net.addPlace(30.0);
    P8 = net.addPlace(1.0);
    P10 = net.addPlace(1.0);
    nameStore.addPlaceName(iP6, "iP6" );
    nameStore.addPlaceName(iP9, "iP9" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P10, "P10" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    nameStore.addTransitionName(oT2, "oT2" );
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T6 = net.addTransition(0, parser.parseTable(T6_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T6, "T6" );
    nameStore.addTransitionName(T7, "T7" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P7, oT0);
    net.addArcFromPlaceToTransition(P5, T1);
    net.addArcFromPlaceToTransition(P3, T1);
    net.addArcFromPlaceToTransition(P4, oT2);
    net.addArcFromPlaceToTransition(P8, T3);
    net.addArcFromPlaceToTransition(P10, T3);
    net.addArcFromPlaceToTransition(iP6, T4);
    net.addArcFromPlaceToTransition(iP9, T5);
    net.addArcFromPlaceToTransition(P0, T6);
    net.addArcFromPlaceToTransition(P2, T6);
    net.addArcFromPlaceToTransition(P10, T7);
    net.addArcFromPlaceToTransition(P1, T7);
    net.addArcFromTransitionToPlace(T1, P7);
    net.addArcFromTransitionToPlace(T3, P10);
    net.addArcFromTransitionToPlace(T3, P8);
    net.addArcFromTransitionToPlace(T4, P0);
    net.addArcFromTransitionToPlace(T4, P1);
    net.addArcFromTransitionToPlace(T5, P2);
    net.addArcFromTransitionToPlace(T5, P3);
    net.addArcFromTransitionToPlace(T6, P4);
    net.addArcFromTransitionToPlace(T7, P5);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P8, new UnifiedToken(1.0));
  }

}