import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class HeaterModelUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int aas;
  /*ordinary places */
  public int P0, P12, P1, P11, P2, P3, P5, P6, P7, P8, P9, P10 ;
  /* output transitions */
  public int oT0 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T4, T5, T6, T7, T8, T0, T1, T3 ;
  /* table for delay transitions */
  String T4_tableStr = "{[< 0>< 0>< 0><-1><-2><FF>]}";
  String T5_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T6_tableStr = "@*@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T7_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T8_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T1_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T3_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public HeaterModelUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    P0 = net.addPlace(100.0);
    P12 = net.addPlace(100.0);
    P1 = net.addPlace(100.0);
    P11 = net.addPlace(100.0);
    P2 = net.addPlace(100.0);
    P3 = net.addPlace(100.0);
    P5 = net.addPlace(100.0);
    P6 = net.addPlace(100.0);
    P7 = net.addPlace(1.0);
    P8 = net.addPlace(1.0);
    P9 = net.addPlace(100.0);
    P10 = net.addPlace(100.0);
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P12, "P12" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P11, "P11" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P9, "P9" );
    nameStore.addPlaceName(P10, "P10" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    T4 = net.addTransition(0, parser.parseTable(T4_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T6 = net.addTransition(0, parser.parseTable(T6_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    T8 = net.addTransition(0, parser.parseTable(T8_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T6, "T6" );
    nameStore.addTransitionName(T7, "T7" );
    nameStore.addTransitionName(T8, "T8" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T3, "T3" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P5, T4);
    net.addArcFromPlaceToTransition(P7, T5);
    net.addArcFromPlaceToTransition(P8, T5);
    net.addArcFromPlaceToTransition(P8, T6);
    net.addArcFromPlaceToTransition(P6, T6);
    net.addArcFromPlaceToTransition(P10, T7);
    net.addArcFromPlaceToTransition(P9, T7);
    net.addArcFromPlaceToTransition(P11, T8);
    net.addArcFromPlaceToTransition(P12, oT0);
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(P2, T1);
    net.addArcFromPlaceToTransition(P3, T1);
    net.addArcFromPlaceToTransition(P1, T3);
    net.addArcFromPlaceToTransition(P3, T3);
    net.addArcFromTransitionToPlace(T4, P6);
    net.addArcFromTransitionToPlace(T5, P8);
    net.addArcFromTransitionToPlace(T5, P7);
    net.addArcFromTransitionToPlace(T6, P9);
    net.addArcFromTransitionToPlace(T7, P11);
    net.addArcFromTransitionToPlace(T8, P0);
    net.addArcFromTransitionToPlace(T8, P12);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T0, P10);
    net.addArcFromTransitionToPlace(T1, P3);
    net.addArcFromTransitionToPlace(T1, P2);
    net.addArcFromTransitionToPlace(T3, P5);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(67.0));
    net.setInitialMarkingForPlace(P2, new UnifiedToken(23.0));
    net.setInitialMarkingForPlace(P7, new UnifiedToken(2.6E-4));
  }

}