import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class BlinkUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int ii;
  /*ordinary places */
  public int P0, P1, P2, P3, P4, P5 ;
  /* output transitions */
  public int oT0, oT1 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T2, T3 ;
  /* table for delay transitions */
  String T0_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String T2_tableStr = "{[<-2,-2><-1,-1>< 0, 0>< 1, 1>< 2, 2><FF,FF>]}";
  String T3_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public BlinkUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    P0 = net.addPlace(0.0);
    P1 = net.addPlace(0.0);
    P2 = net.addPlace(0.0);
    P3 = net.addPlace(0.0);
    P4 = net.addPlace(0.0);
    P5 = net.addPlace(0.0);
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P5, "P5" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    oT1 =  net.addOuputTransition(parser.parseOneXOneTable(oT1_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    nameStore.addTransitionName(oT1, "oT1" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(10, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    T3 = net.addTransition(10, parser.parseTable(T3_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
    nameStore.addTransitionName(T3, "T3" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P1, oT0);
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(P4, oT1);
    net.addArcFromPlaceToTransition(P2, T1);
    net.addArcFromPlaceToTransition(P3, T2);
    net.addArcFromPlaceToTransition(P5, T3);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T0, P2);
    net.addArcFromTransitionToPlace(T1, P3);
    net.addArcFromTransitionToPlace(T2, P4);
    net.addArcFromTransitionToPlace(T2, P5);
    net.addArcFromTransitionToPlace(T3, P0);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}