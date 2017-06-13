package subs;

import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;

public class SplitterUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0 ;
  /*ordinary places */
  public int P1, P2 ;
  /* output transitions */
  public int oT0, oT1 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  String oT1_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0 ;
  /* table for delay transitions */
  String T0_tableStr = "{[<-2,FF><-1,FF>< 0, 0><FF, 1><FF, 2><FF,FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public SplitterUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(100.0);
    P1 = net.addPlace(100.0);
    P2 = net.addPlace(100.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
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
    net.addArcFromPlaceToTransition(P1, oT0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(P2, oT1);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T0, P2);
  }

  private void putInitialMarking(){
  }

}