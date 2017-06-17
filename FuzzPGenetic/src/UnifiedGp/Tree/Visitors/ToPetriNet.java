package UnifiedGp.Tree.Visitors;

import java.util.LinkedList;
import java.util.Queue;

import UnifiedGp.ScaleProvider;
import UnifiedGp.Tree.BreadthFirstVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.SubnodeTypeMarker;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;

public class ToPetriNet {

  private VisitorCostumizer<NodeType, SubnodeTypeMarker> cosutimzer;
  private BreadthFirstVisitor<NodeType, SubnodeTypeMarker> visitor;
  private UnifiedPetriNet netToMake;
  private Queue<int[]> placesBetween;
  private ScaleProvider scaleProvider;

  public ToPetriNet(ScaleProvider scaleProvider) {
    this.scaleProvider = scaleProvider;
    cosutimzer = new VisitorCostumizer<>();
    cosutimzer.registerOperatorConsumer(NodeType.Seq, this::seqVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Loop, this::loopVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Selc, this::selcVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Conc, this::concVisit);
    cosutimzer.registerLeafConsumer(NodeType.Delay, this::delayVisit);
    visitor = new BreadthFirstVisitor<>(cosutimzer);
  }

  public UnifiedPetriNet toNet(IInnerNode<NodeType> type) {
    netToMake = new UnifiedPetriNet();
    placesBetween = new LinkedList<>();
    int firstPlace = netToMake.addPlace(scaleProvider.defaultScale());
    int lastPlace = netToMake.addPlace(scaleProvider.defaultScale());
    placesBetween.add(new int[] { firstPlace, lastPlace });
    visitor.visit(type);
    return netToMake;
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



}
