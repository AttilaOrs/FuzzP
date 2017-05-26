import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class PosNegDeciderMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP1 ;
  /*ordinary places */
  public int P0, P2, P3, P4, P5, P6 ;
  /* output transitions */
  public int oT2, oT4 ;
  /* table for out transitions */
  String oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT4_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T3, T5_d1, T6_d1 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T1_tableStr = "{[<FF,FF><FF,FF><FF,FF>< 1, 1>< 1, 2><FF,FF>]}";
  String T3_tableStr = "{[<-1,-2><-1,-1><FF,FF><FF,FF><FF,FF><FF,FF>]}";
  String T5_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T6_d1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public PosNegDeciderMaker() {
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP1 = net.addInputPlace(1.0);
    P0 = net.addPlace(1.0);
    P2 = net.addPlace(1.0);
    P3 = net.addPlace(1.0);
    P4 = net.addPlace(1.0);
    P5 = net.addPlace(1.0);
    P6 = net.addPlace(1.0);
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
    nameStore.addPlaceName(P6, "P6" );
  }

  private void addTransitions(){
    oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
    oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
    nameStore.addTransitionName(oT2, "oT2" );
    nameStore.addTransitionName(oT4, "oT4" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    T5_d1 = net.addTransition(1, parser.parseTable(T5_d1_tableStr));
    T6_d1 = net.addTransition(1, parser.parseTable(T6_d1_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T3, "T3" );
    nameStore.addTransitionName(T5_d1, "T5_d1" );
    nameStore.addTransitionName(T6_d1, "T6_d1" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(iP1, T0);
    net.addArcFromPlaceToTransition(P2, T1);
    net.addArcFromPlaceToTransition(P3, oT2);
    net.addArcFromPlaceToTransition(P2, T3);
    net.addArcFromPlaceToTransition(P5, oT4);
    net.addArcFromPlaceToTransition(P4, T5_d1);
    net.addArcFromPlaceToTransition(P6, T6_d1);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(T1, P3);
    net.addArcFromTransitionToPlace(T1, P4);
    net.addArcFromTransitionToPlace(T3, P5);
    net.addArcFromTransitionToPlace(T3, P6);
    net.addArcFromTransitionToPlace(T5_d1, P0);
    net.addArcFromTransitionToPlace(T6_d1, P0);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}