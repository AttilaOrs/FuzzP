package subs;

import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;

public class EventStarterUnifiedPetriMaker {
  UnifiedPetriNet net ;
  /*input places */
  public int iP0 ;
  /*ordinary places */
  public int P12, P1, P2, P3, P4, P10 ;
  /* output transitions */
  public int oT0 ;
  /* table for out transitions */
  String oT0_tableStr = "{[<-2><-1>< 0>< 1>< 2><FF>]}";
  /* simple delay transitions */
  public int T10, T11, T0, T1, T2 ;
  /* table for delay transitions */
  String T10_tableStr = "{"+
  	"[<-2><-2><-1><-1>< 0><FF>]"+//
  	"[<-2><-1><-1>< 0>< 1><FF>]"+//
  	"[<-1><-1>< 0>< 1>< 1><FF>]"+//
  	"[<-1>< 0>< 1>< 1>< 2><FF>]"+//
  	"[< 0>< 1>< 1>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T11_tableStr = "{[< 0>< 0>< 0>< 0>< 0>< 0>]}";
  String T0_tableStr = "@+@{"+
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  String T1_tableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  String T2_tableStr = "{[< 0>< 0><FF><FF><FF><FF>]}";

  public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();
  UnifiedTableParser parser = new UnifiedTableParser(true);

  public EventStarterUnifiedPetriMaker(){
    this.net = new UnifiedPetriNet(); 
    addPlaces();
    addTransitions();
    addArcs();
    putInitialMarking();
  }

  private void addPlaces(){
    iP0 = net.addInputPlace(100.0);
    P12 = net.addPlace(100.0);
    P1 = net.addPlace(100.0);
    P2 = net.addPlace(100.0);
    P3 = net.addPlace(100.0);
    P4 = net.addPlace(100.0);
    P10 = net.addPlace(100.0);
    nameStore.addPlaceName(iP0, "iP0" );
    nameStore.addPlaceName(P12, "P12" );
    nameStore.addPlaceName(P1, "P1" );
    nameStore.addPlaceName(P2, "P2" );
    nameStore.addPlaceName(P3, "P3" );
    nameStore.addPlaceName(P4, "P4" );
    nameStore.addPlaceName(P10, "P10" );
  }

  private void addTransitions(){
    oT0 =  net.addOuputTransition(parser.parseOneXOneTable(oT0_tableStr));
    nameStore.addTransitionName(oT0, "oT0" );
    T10 = net.addTransition(0, parser.parseTable(T10_tableStr));
    T11 = net.addTransition(1, parser.parseTable(T11_tableStr));
    T0 = net.addTransition(0, parser.parseTable(T0_tableStr));
    T1 = net.addTransition(0, parser.parseTable(T1_tableStr));
    T2 = net.addTransition(0, parser.parseTable(T2_tableStr));
    nameStore.addTransitionName(T10, "T10" );
    nameStore.addTransitionName(T11, "T11" );
    nameStore.addTransitionName(T0, "T0" );
    nameStore.addTransitionName(T1, "T1" );
    nameStore.addTransitionName(T2, "T2" );
  }

  private void addArcs(){
    net.addArcFromPlaceToTransition(iP0, T10);
    net.addArcFromPlaceToTransition(P12, T10);
    net.addArcFromPlaceToTransition(P3, T11);
    net.addArcFromPlaceToTransition(P4, oT0);
    net.addArcFromPlaceToTransition(P2, T0);
    net.addArcFromPlaceToTransition(P10, T0);
    net.addArcFromPlaceToTransition(P1, T1);
    net.addArcFromPlaceToTransition(P2, T1);
    net.addArcFromPlaceToTransition(P3, T2);
    net.addArcFromTransitionToPlace(T10, P10);
    net.addArcFromTransitionToPlace(T11, P12);
    net.addArcFromTransitionToPlace(T0, P3);
    net.addArcFromTransitionToPlace(T1, P2);
    net.addArcFromTransitionToPlace(T1, P1);
    net.addArcFromTransitionToPlace(T2, P4);
  }

  private void putInitialMarking(){
    net.setInitialMarkingForPlace(P1, new UnifiedToken(10.0));
    net.setInitialMarkingForPlace(P3, new UnifiedToken(0.0));
  }

}