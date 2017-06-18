package UnifiedGp.Tree.Visitors;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import UnifiedGp.ScaleProvider;
import UnifiedGp.Tree.BreadthFirstVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.SubnodeTypeMarker;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class ToPetriNet {
  private static final UnifiedTableParser parser = new UnifiedTableParser(true);

  public final static String inputTransmitterTwoXOneStr = "{" +
      "[<-2><-2><-2><-2><-2><-2>]" + //
      "[<-1><-1><-1><-1><-1><-1>]" + //
      "[< 0>< 0>< 0>< 0>< 0>< 0>]" + //
      "[< 1>< 1>< 1>< 1>< 1>< 1>]" + //
      "[< 2>< 2>< 2>< 2>< 2>< 2>]" + //
      "[<FF><FF><FF><FF><FF><FF>]" + //
      "}";
  public final static String inputTransmitterTwoXTwoStr = "{" +
      "[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><-2,-2>]" + //
      "[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><-1,-1>]" + //
      "[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0>]" + //
      "[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1>]" + //
      "[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>]" + //
      "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
      "}";


  private static final UnifiedTwoXTwoTable inputTransmitterTwoXTwo = parser
      .parseTwoXTwoTable(inputTransmitterTwoXTwoStr);
  private static final UnifiedTwoXOneTable inputTransmitterTwoXOne = parser
      .parseTwoXOneTable(inputTransmitterTwoXOneStr);

  private VisitorCostumizer<NodeType, SubnodeTypeMarker> cosutimzer;
  private BreadthFirstVisitor<NodeType, SubnodeTypeMarker> visitor;
  private UnifiedPetriNet netToMake;
  private Queue<int[]> placesBetween;
  private ScaleProvider scaleProvider;
  private Map<Integer, List<Integer>> placesWaitingForInputs;
  private Map<Integer, Integer> inpNameInpPlace;
  

  public ToPetriNet(ScaleProvider scaleProvider) {
    this.scaleProvider = scaleProvider;
    cosutimzer = new VisitorCostumizer<>();
    cosutimzer.registerOperatorConsumer(NodeType.Seq, this::seqVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Loop, this::loopVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Selc, this::selcVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Conc, this::concVisit);
    cosutimzer.registerLeafConsumer(NodeType.Delay, this::delayVisit);
    cosutimzer.registerLeafConsumer(NodeType.Inp, this::inpVisit);
    visitor = new BreadthFirstVisitor<>(cosutimzer);
  }

  public UnifiedPetriNet toNet(IInnerNode<NodeType> type) {
    netToMake = new UnifiedPetriNet();
    placesBetween = new ArrayDeque<>();
    placesWaitingForInputs = new HashMap<>();
    int firstPlace = netToMake.addPlace(scaleProvider.defaultScale());
    int lastPlace = netToMake.addPlace(scaleProvider.defaultScale());
    placesBetween.add(new int[] { firstPlace, lastPlace });
    visitor.visit(type);
    resolveInputs();
    return netToMake;
  }

  private void resolveInputs() {
    inpNameInpPlace = new HashMap<>();
    for (Integer inputNr : placesWaitingForInputs.keySet()) {
      int realInput = netToMake.addInputPlace(scaleProvider.getScaleForInp(inputNr));
      inpNameInpPlace.put(inputNr, realInput);
      int lastPlace = realInput;
      Iterator<Integer> it = placesWaitingForInputs.get(inputNr).iterator();
      while (it.hasNext()) {
        Integer placeToPut = it.next();
        if (it.hasNext()) {
          int newLastPlace = netToMake.addPlace(scaleProvider.getScaleForInp(inputNr));
          int tr = netToMake.addTransition(0, inputTransmitterTwoXTwo);
          netToMake.addArcFromPlaceToTransition(lastPlace, tr);
          netToMake.addArcFromPlaceToTransition(placeToPut, tr);
          netToMake.addArcFromTransitionToPlace(tr, placeToPut);
          netToMake.addArcFromTransitionToPlace(tr, newLastPlace);
          lastPlace = newLastPlace;
        } else {
          int tr = netToMake.addTransition(0, inputTransmitterTwoXOne);
          netToMake.addArcFromPlaceToTransition(lastPlace, tr);
          netToMake.addArcFromPlaceToTransition(placeToPut, tr);
          netToMake.addArcFromTransitionToPlace(tr, placeToPut);
        }
      }


    }

  }

  private Boolean seqVisit(INode<NodeType> seqNode) {
    int[] between = placesBetween.poll();
    int innerPlace = netToMake.addPlace(scaleProvider.defaultScale());
    int[] first = new int[] { between[0], innerPlace };
    int[] second = new int[] { innerPlace, between[1] };
    placesBetween.add(first);
    placesBetween.add(second);
    return Boolean.TRUE;
  }

  private Boolean loopVisit(INode<NodeType> loopNode) {
    int[] between = placesBetween.poll();
    int[] betweenSecond = new int[] { between[1], between[0] };
    placesBetween.add(between);
    placesBetween.add(betweenSecond);
    return Boolean.TRUE;
  }

  private Boolean selcVisit(INode<NodeType> loopNode) {
    int[] between = placesBetween.poll();
    placesBetween.add(between);
    placesBetween.add(between);
    return Boolean.TRUE;
  }

  private Boolean concVisit(INode<NodeType> loopNode) {
    int[] between = placesBetween.poll();
    int enterTr = netToMake.addTransition(0, UnifiedOneXTwoTable.defaultTable());
    netToMake.addArcFromPlaceToTransition(between[0], enterTr);
    int enterPlaceOne = netToMake.addPlace(scaleProvider.defaultScale());
    int enterPlaceTwo = netToMake.addPlace(scaleProvider.defaultScale());
    netToMake.addArcFromTransitionToPlace(enterTr, enterPlaceOne);
    netToMake.addArcFromTransitionToPlace(enterTr, enterPlaceTwo);

    int exitPlaceOne = netToMake.addPlace(scaleProvider.defaultScale());
    int exitPlaceTwo = netToMake.addPlace(scaleProvider.defaultScale());
    int exitTr = netToMake.addTransition(0, UnifiedTwoXOneTable.defaultTable());
    netToMake.addArcFromTransitionToPlace(exitTr, between[1]);
    netToMake.addArcFromPlaceToTransition(exitPlaceOne, exitTr);
    netToMake.addArcFromPlaceToTransition(exitPlaceTwo, exitTr);
    placesBetween.add(new int[] { enterPlaceOne, exitPlaceOne });
    placesBetween.add(new int[] { enterPlaceTwo, exitPlaceTwo });
    return Boolean.TRUE;
  }

  private Boolean delayVisit(INode<NodeType> ss) {
    DelayLeaf delayLeaf = (DelayLeaf) ss;
    int[] between = placesBetween.poll();
    Integer delayTr = netToMake.addTransition(delayLeaf.getDelay(), UnifiedOneXOneTable.defaultTable());
    netToMake.addArcFromPlaceToTransition(between[0], delayTr);
    netToMake.addArcFromTransitionToPlace(delayTr, between[1]);
    return Boolean.TRUE;
  }

  private Boolean inpVisit(INode<NodeType> ss) {
    InputLeaf inputLeaf = (InputLeaf) ss;
    int[] between = placesBetween.poll();
    Integer inpTr = netToMake.addTransition(0, inputLeaf.getSubtype().table.myClone());
    int inpPlace = netToMake.addPlace(scaleProvider.getScaleForInp(inputLeaf.inpNr()));

    netToMake.addArcFromPlaceToTransition(inpPlace, inpTr);
    netToMake.addArcFromPlaceToTransition(between[0], inpTr);
    netToMake.addArcFromTransitionToPlace(inpTr, between[1]);

    registerInpPlace(inputLeaf.inpNr(), inpPlace);
    return Boolean.TRUE;
  }

  private void registerInpPlace(int inpNr, int inpPlace) {
    placesWaitingForInputs.putIfAbsent(inpNr, new ArrayList<>());
    placesWaitingForInputs.get(inpNr).add(inpPlace);
  }

  public Map<Integer, Integer> getInpNrToINpPlace() {
    return inpNameInpPlace;

  }

}
