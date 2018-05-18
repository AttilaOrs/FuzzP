import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class MathExampleUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0, iP1 ;
  /*ordinary places */
  public int P11, P2, P3, P4, P5, P6, P7, P8, P9, P10 ;
  /* output transitions */
  public int oT0 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T4, T5, T10, T9, T1, T2, T3 ;
  /* table for delay transitions */
  String T4_tableStr = "@*@ {"+
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T5_tableStr = "{[< 0>< 0>< 0>< 0>< 0><FF>]}";
  String T10_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T9_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T1_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><FF,FF>]"+//
  	"[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,FF>]"+//
  	"[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0><FF,FF>]"+//
  	"[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T3_tableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-1><-1><-1><-1><-1><FF>]"+//
  	"[< 0>< 0>< 0>< 0>< 0><FF>]"+//
  	"[< 1>< 1>< 1>< 1>< 1><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public MathExampleUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(3.0);
    iP1 = net.addInputPlace(3.0);
    P11 = net.addPlace(3.0);
    P2 = net.addPlace(3.0);
    P3 = net.addPlace(3.0);
    P4 = net.addPlace(3.0);
    P5 = net.addPlace(3.0);
    P6 = net.addPlace(3.0);
    P7 = net.addPlace(3.0);
    P8 = net.addPlace(3.0);
    P9 = net.addPlace(3.0);
    P10 = net.addPlace(3.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(P11, "P11" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
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
    T5 = net.addTransition(1, parser.parseTable(T5_tableStr));
    T10 = net.addTransition(0, parser.parseTable(T10_tableStr));
    T9 = net.addTransition(0, parser.parseTable(T9_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(1, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(0, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T4, "T4" );
    nameStore.addTransitionName(T5, "T5" );
    nameStore.addTransitionName(T10, "T10" );
    nameStore.addTransitionName(T9, "T9" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P5, T4);
    net.addArcFromPlaceToTransition(P11, T4);
    net.addArcFromPlaceToTransition(P7, T5);
    net.addArcFromPlaceToTransition(P10, T10);
    net.addArcFromPlaceToTransition(iP1, T10);
    net.addArcFromPlaceToTransition(P8, T9);
    net.addArcFromPlaceToTransition(P6, oT0);
    net.addArcFromPlaceToTransition(iP0, T1);
    net.addArcFromPlaceToTransition(P9, T1);
    net.addArcFromPlaceToTransition(P2, T2);
    net.addArcFromPlaceToTransition(P3, T3);
    net.addArcFromPlaceToTransition(P4, T3);
    net.addArcFromTransitionToPlace(T4, P6);
    net.addArcFromTransitionToPlace(T4, P7);
    net.addArcFromTransitionToPlace(T5, P8);
    net.addArcFromTransitionToPlace(T10, P11);
    net.addArcFromTransitionToPlace(T9, P9);
    net.addArcFromTransitionToPlace(T9, P10);
    net.addArcFromTransitionToPlace(T1, P2);
    net.addArcFromTransitionToPlace(T1, P4);
    net.addArcFromTransitionToPlace(T2, P3);
    net.addArcFromTransitionToPlace(T3, P5);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P3, new UnifiedToken(0.0));
    net.setInitialMarkingForPlace(P7, new UnifiedToken(1.0));
  }

}