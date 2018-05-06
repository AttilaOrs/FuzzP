import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class LaneUnifiedPetriMaker2 {
  UnifiedPetriNet net ;
  /*input places */
  public int iP2, iP1, iP3 ;
  /*ordinary places */
  public int P0, P4 ;
  /* output transitions */
  public int oT3 ;
  /* table for out transitions */
  String oT3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T2 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><FF,FF>]"+//
  	"[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><FF,FF>]"+//
  	"[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0><FF,FF>]"+//
  	"[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T1_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T2_tableStr = "@-@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public LaneUnifiedPetriMaker2() {
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP2 = net.addInputPlace(50.0);
    iP1 = net.addInputPlace(50.0);
    iP3 = net.addInputPlace(0.0);
    P0 = net.addPlace(50.0);
    P4 = net.addPlace(50.0);
    nameStore.addPlaceName(iP2, "iP2" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(iP3, "iP3" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P4, "P4" );
  }

  private void addTransitions(){
    oT3 =  net.addOuputTransition(parser.parseOneXOneTable(oT3_tableStr));
    nameStore.addTransitionName(oT3, "oT3" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(iP3, T0);
    net.addArcFromPlaceToTransition(iP1, T1);
    net.addArcFromPlaceToTransition(P0, T1);
    net.addArcFromPlaceToTransition(P0, T2);
    net.addArcFromPlaceToTransition(iP2, T2);
    net.addArcFromPlaceToTransition(P4, oT3);
    net.addArcFromTransitionToPlace(T0, P0);
    net.addArcFromTransitionToPlace(T0, P4);
    net.addArcFromTransitionToPlace(T1, P0);
    net.addArcFromTransitionToPlace(T2, P0);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}