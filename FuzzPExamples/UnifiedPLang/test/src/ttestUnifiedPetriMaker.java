import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class ttestUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0 ;
  /*ordinary places */
  public int P1 ;
  /* output transitions */
  public int oT0 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0 ;
  /* table for delay transitions */
  String T0_tableStr = "{[< 1>< 1>< 1>< 1>< 1><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public ttestUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(1.0);
    P1 = net.addPlace(1.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(P1, "P1" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    T0 = net.addTransition(1, parser.parseTable(T0_tableStr));
    nameStore.addTransitionName(T0, "T0" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P1, oT0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromTransitionToPlace(T0, P1);
  }

  private void putInitialMarking(){
  }

}