import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class MaxTableTryOutUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP1, iP2 ;
  /*ordinary places */
  public int P0 ;
  /* output transitions */
  public int oT1 ;
  /* table for out transitions */
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 2><FF>]"+//
  	"[< 0>< 0>< 0>< 1>< 2><FF>]"+//
  	"[< 1>< 1>< 1>< 1>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public MaxTableTryOutUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP1 = net.addInputPlace(1.0);
    iP2 = net.addInputPlace(1.0);
    P0 = net.addPlace(1.0);
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(iP2, "iP2" );
    nameStore.addPlaceName(P0, "P0" );
  }

  private void addTransitions(){
    oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
    nameStore.addTransitionName(oT1, "oT1" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    nameStore.addTransitionName(T0, "T0" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(iP1, T0);
    net.addArcFromPlaceToTransition(iP2, T0);
    net.addArcFromPlaceToTransition(P0, oT1);
    net.addArcFromTransitionToPlace(T0, P0);
  }

  private void putInitialMarking(){
  }

}