import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class printUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0 ;
  /*ordinary places */
  public int P0, P1 ;
  /* output transitions */
  public int oT2, oT1 ;
  /* table for out transitions */
  String oT2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public printUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(0.0);
    P0 = net.addPlace(0.0);
    P1 = net.addPlace(0.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
  }

  private void addTransitions(){
    oT2 =  net.addOuputTransition(parser.parseOneXOneTable(oT2_tableStr));
    oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
    nameStore.addTransitionName(oT2, "oT2" );
    nameStore.addTransitionName(oT1, "oT1" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    nameStore.addTransitionName(T0, "T0" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P0, oT2);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(P1, oT1);
    net.addArcFromTransitionToPlace(T0, P1);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}