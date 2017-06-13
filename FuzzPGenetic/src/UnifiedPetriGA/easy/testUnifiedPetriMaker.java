package UnifiedPetriGA.easy;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class testUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0, iP1 ;
  /*ordinary places */
  public int P0, P1 ;
  /* output transitions */
  public int oT0, oT1 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2,-2><-2,-1><-2, 0><-2, 1><-2, 2><FF,FF>]"+//
  	"[<-1,-2><-1,-1><-1, 0><-1, 1><-1, 2><FF,FF>]"+//
  	"[< 0,-2>< 0,-1>< 0, 0>< 0, 1>< 0, 2><FF,FF>]"+//
  	"[< 1,-2>< 1,-1>< 1, 0>< 1, 1>< 1, 2><FF,FF>]"+//
  	"[< 2,-2>< 2,-1>< 2, 0>< 2, 1>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public testUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(50.0);
    iP1 = net.addInputPlace(50.0);
    P0 = net.addPlace(50.0);
    P1 = net.addPlace(50.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    nameStore.addTransitionName(oT1, "oT1" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    nameStore.addTransitionName(T0, "T0" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P0, oT0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(iP1, T0);
    net.addArcFromPlaceToTransition(P1, oT1);
    net.addArcFromTransitionToPlace(T0, P0);
    net.addArcFromTransitionToPlace(T0, P1);
  }

  private void putInitialMarking(){
  }

}