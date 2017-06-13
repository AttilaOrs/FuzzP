package subs;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class DifferUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0, iP1 ;
  /*ordinary places */
  public int P0, P1, P2, P3 ;
  /* output transitions */
  public int oT0 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T0, T1, T2 ;
  /* table for delay transitions */
  String T0_tableStr = "{"+
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<-2><-1>< 0>< 1>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T1_tableStr = "@-@ {"+
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2><FF,FF>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T2_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public DifferUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(100.0);
    iP1 = net.addInputPlace(100.0);
    P0 = net.addPlace(100.0);
    P1 = net.addPlace(100.0);
    P2 = net.addPlace(100.0);
    P3 = net.addPlace(100.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(iP1, "iP1" );
    nameStore.addPlaceName(P0, "P0" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(1, parser.parseTable(T2_tableStr));
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(P2, oT0);
    net.addArcFromPlaceToTransition(P0, T0);
    net.addArcFromPlaceToTransition(iP0, T0);
    net.addArcFromPlaceToTransition(P1, T1);
    net.addArcFromPlaceToTransition(iP1, T1);
    net.addArcFromPlaceToTransition(P3, T2);
    net.addArcFromTransitionToPlace(T0, P1);
    net.addArcFromTransitionToPlace(T1, P2);
    net.addArcFromTransitionToPlace(T1, P3);
    net.addArcFromTransitionToPlace(T2, P0);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, new UnifiedToken(0.0));
  }

}