import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class LargeSmallerExampleMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP1 ;
  /*ordinary places */
  public int P0, P2, P3, P4, P5, P6, P7, P8, P9 ;
  /* output transitions */
  public int oT4, oT6 ;
  /* table for out transitions */
  String oT4_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT6_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T2, T3, T5, T7 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T1_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T2_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T3_tableStr = "{[<FF,FF><FF,FF><FF,FF>< 1, 1>< 1, 2><FF,FF>]}";
  String T5_tableStr = "{[<-1,-2><-1,-1><FF,FF><FF,FF><FF,FF><FF,FF>]}";
  String T7_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* variable delay transitions */
  public int T8_vd ;
  /* table for variable delay */
  String T8_vd_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public LargeSmallerExampleMaker() {
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP1 = net.addInputPlace(4.0);
    P0 = net.addPlace(4.0);
    P2 = net.addPlace(4.0);
    P3 = net.addPlace(4.0);
    P4 = net.addPlace(4.0);
    P5 = net.addPlace(4.0);
    P6 = net.addPlace(4.0);
    P7 = net.addPlace(4.0);
    P8 = net.addPlace(4.0);
    P9 = net.addPlace(4.0);
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
    nameStore.addPlaceName(P7, "P7" );
    nameStore.addPlaceName(P8, "P8" );
    nameStore.addPlaceName(P9, "P9" );
  }

  private void addTransitions(){
    oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
    oT6 =  net.addOuputTransition(parser.parseOneXOneTable(oT6_tableStr));
    nameStore.addTransitionName(oT4, "oT4" );
    nameStore.addTransitionName(oT6, "oT6" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    T5 = net.addTransition(0, parser.parseTable(T5_tableStr));
    T7 = net.addTransition(0, parser.parseTable(T7_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T7, "T7" );
    T8_vd = net.addTransitionVariableDelay(1.0, parser.parseTable(T8_vd_tableStr));
    nameStore.addTransitionName(T8_vd, "T8_vd" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(iP1, T0);
    net.addArcFromPlaceToTransition(P2, T1);
    net.addArcFromPlaceToTransition(P5, T1);
    net.addArcFromPlaceToTransition(P4, T2);
    net.addArcFromPlaceToTransition(P3, T3);
    net.addArcFromPlaceToTransition(P6, oT4);
    net.addArcFromPlaceToTransition(P3, T5);
    net.addArcFromPlaceToTransition(P8, oT6);
    net.addArcFromPlaceToTransition(P7, T7);
    net.addArcFromPlaceToTransition(P9, T8_vd);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(T1, P3);
    net.addArcFromTransitionToPlace(T2, P4);
    net.addArcFromTransitionToPlace(T2, P5);
    net.addArcFromTransitionToPlace(T3, P6);
    net.addArcFromTransitionToPlace(T3, P7);
    net.addArcFromTransitionToPlace(T5, P8);
    net.addArcFromTransitionToPlace(T5, P9);
    net.addArcFromTransitionToPlace(T7, P0);
    net.addArcFromTransitionToPlace(T8_vd, P0);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P4, new UnifiedToken(-1.000000001));
  }

}