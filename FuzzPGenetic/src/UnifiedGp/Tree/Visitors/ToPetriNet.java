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
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.MemoryLeaf;
import UnifiedGp.Tree.Nodes.NegateLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutputLeaf;
import UnifiedGp.Tree.Nodes.SubnodeTypeMarker;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.tables.Operator;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;

public class ToPetriNet {

  private VisitorCostumizer<NodeType, SubnodeTypeMarker> cosutimzer;
  private BreadthFirstVisitor<NodeType, SubnodeTypeMarker> visitor;
  private UnifiedPetriNet netToMake;
  private Queue<int[]> placesBetween;
  private ScaleProvider scaleProvider;
  private Map<Integer, List<Integer>> placesWaitingForInputs;
  private Map<Integer, List<Integer>> placesWaitingForOuputs;
  private Map<Integer, Integer> inpNameInpPlace;
  private Map<Integer, Integer> outNrToOutTr;

  
  public ToPetriNet(ScaleProvider scaleProvider) {
    this.scaleProvider = scaleProvider;
    cosutimzer = new VisitorCostumizer<>();
    cosutimzer.registerOperatorConsumer(NodeType.Seq, this::seqVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Loop, this::loopVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Selc, this::selcVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Conc, this::concVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Add, this::concVisit);
    cosutimzer.registerOperatorConsumer(NodeType.Multiply, this::concVisit);
    cosutimzer.registerLeafConsumer(NodeType.Delay, this::delayVisit);
    cosutimzer.registerLeafConsumer(NodeType.Inp, this::inpVisit);
    cosutimzer.registerLeafConsumer(NodeType.Out, this::outVisit);
    cosutimzer.registerLeafConsumer(NodeType.Block, this::simpleVisit);
    cosutimzer.registerLeafConsumer(NodeType.Negate, this::simpleVisit);
    cosutimzer.registerLeafConsumer(NodeType.Memory, this::memoryVisit);
    cosutimzer.registerLeafConsumer(NodeType.Const, this::constantVisit);
    cosutimzer.registerLeafConsumer(NodeType.Inv, this::invVisit);
    visitor = new BreadthFirstVisitor<>(cosutimzer);
  }

  public PetriConversationResult toNet(IInnerNode<NodeType> type) {
    netToMake = new UnifiedPetriNet();
    placesBetween = new ArrayDeque<>();
    placesWaitingForInputs = new HashMap<>();
    placesWaitingForOuputs = new HashMap<>();
    int firstPlace = netToMake.addPlace(scaleProvider.defaultScale());
    int lastPlace = netToMake.addPlace(scaleProvider.defaultScale());
    placesBetween.add(new int[] { firstPlace, lastPlace });
    visitor.visit(type);
    resolveInputs();
    resolveOutputs();
    return new PetriConversationResult(netToMake, inpNameInpPlace, outNrToOutTr);
  }

  private void resolveOutputs() {
    outNrToOutTr = new HashMap<>();
    for (Integer outNr : placesWaitingForOuputs.keySet()) {
      int realOuputTransition = netToMake.addOuputTransition(UnifiedOneXOneTable.defaultTable());
      outNrToOutTr.put(outNr, realOuputTransition);
      List<Integer> places = placesWaitingForOuputs.get(outNr);
      for (int i = 0; i < places.size(); i++) {
        Integer currentPlace = places.get(i);
        if (i == places.size() - 1) {
          netToMake.addArcFromPlaceToTransition(currentPlace, realOuputTransition);
        } else {
          Integer nextPlace = places.get(i + 1);
          int intermedateTr = netToMake.addTransition(0, UnifiedOneXOneTable.defaultTable());
          netToMake.addArcFromPlaceToTransition(currentPlace, intermedateTr);
          netToMake.addArcFromTransitionToPlace(intermedateTr, nextPlace);
        }
      }

    }

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
          int tr = netToMake.addTransition(0, InputLeaf.inputTransmitterTwoXTwo);
          netToMake.addArcFromPlaceToTransition(lastPlace, tr);
          netToMake.addArcFromPlaceToTransition(placeToPut, tr);
          netToMake.addArcFromTransitionToPlace(tr, placeToPut);
          netToMake.addArcFromTransitionToPlace(tr, newLastPlace);
          lastPlace = newLastPlace;
        } else {
          int tr = netToMake.addTransition(0, InputLeaf.inputTransmitterTwoXOne);
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

  private Boolean concVisit(INode<NodeType> conc) {
    InnerNode op = (InnerNode) conc;
    int[] between = placesBetween.poll();
    int enterTr = netToMake.addTransition(0, UnifiedOneXTwoTable.defaultTable());
    netToMake.addArcFromPlaceToTransition(between[0], enterTr);
    int enterPlaceOne = netToMake.addPlace(scaleProvider.defaultScale());
    int enterPlaceTwo = netToMake.addPlace(scaleProvider.defaultScale());
    netToMake.addArcFromTransitionToPlace(enterTr, enterPlaceOne);
    netToMake.addArcFromTransitionToPlace(enterTr, enterPlaceTwo);

    int exitPlaceOne = netToMake.addPlace(scaleProvider.defaultScale());
    int exitPlaceTwo = netToMake.addPlace(scaleProvider.defaultScale());
    int exitTr = netToMake.addTransition(0, getEndTableFor(op));
    netToMake.addArcFromTransitionToPlace(exitTr, between[1]);
    netToMake.addArcFromPlaceToTransition(exitPlaceOne, exitTr);
    netToMake.addArcFromPlaceToTransition(exitPlaceTwo, exitTr);
    placesBetween.add(new int[] { enterPlaceOne, exitPlaceOne });
    placesBetween.add(new int[] { enterPlaceTwo, exitPlaceTwo });
    return Boolean.TRUE;
  }

  private UnifiedTwoXOneTable getEndTableFor(InnerNode op) {
    switch (op.getType()){
    case Conc:
      return UnifiedTwoXOneTable.defaultTable();
    case Add:
      return UnifiedTwoXOneTable.onlyOp(Operator.PLUS);
    case Multiply:
      return UnifiedTwoXOneTable.onlyOp(Operator.MULT);
    default:
      break;
    }
    throw new RuntimeException("Error at ToPetriNet");
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

  private Boolean outVisit(INode<NodeType> ss) {
    OutputLeaf outLeaf = (OutputLeaf) ss;
    int[] between = placesBetween.poll();
    int outTr = netToMake.addTransition(0, outLeaf.getSubtype().table.myClone());
    int outPlace = netToMake.addPlace(scaleProvider.getScaleForOut(outLeaf.outNr()));
    
    netToMake.addArcFromPlaceToTransition(between[0], outTr);
    netToMake.addArcFromTransitionToPlace(outTr, outPlace);
    netToMake.addArcFromTransitionToPlace(outTr, between[1]);
    regisetrOutPlace(outLeaf.outNr(), outPlace);
    return Boolean.TRUE;
  }

  private void regisetrOutPlace(int outNr, int outPlace) {
    placesWaitingForOuputs.putIfAbsent(outNr, new ArrayList<>());
    placesWaitingForOuputs.get(outNr).add(outPlace);
  }

  private Boolean simpleVisit(INode<NodeType> ss) {
    int[] between = placesBetween.poll();
    int blokcTransition = netToMake.addTransition(0, getTaleForSimpleNode(ss.getType()));
    netToMake.addArcFromPlaceToTransition(between[0], blokcTransition);
    netToMake.addArcFromTransitionToPlace(blokcTransition, between[1]);
    return Boolean.TRUE;
  }

  private IUnifiedTable getTaleForSimpleNode(NodeType nodeType) {
    switch(nodeType){
      case Block: return  BlockLeaf.table.myClone();
      case Negate : return NegateLeaf.table.myClone();
      default : break;
    }
    throw new RuntimeException(" there is a problem ToPetriNet");
  }
  
  private Boolean memoryVisit(INode<NodeType> ss) {
    MemoryLeaf mem = (MemoryLeaf) ss;
    int[] between = placesBetween.poll();
    if(mem.getMemNr() <= 0){
      int tr = netToMake.addTransition(0, UnifiedOneXOneTable.defaultTable());
      netToMake.addArcFromPlaceToTransition(between[0], tr);
      netToMake.addArcFromTransitionToPlace(tr, between[1]);
    } else {
      int lastPlace = between[0];
      for(int i= 0; i < mem.getMemNr();i++){
        int tr = netToMake.addTransition(1, UnifiedOneXOneTable.defaultTable());
        int newPlace = netToMake.addPlace(scaleProvider.defaultScale());
        netToMake.addArcFromPlaceToTransition(lastPlace, tr);
        netToMake.addArcFromTransitionToPlace(tr, newPlace);
        netToMake.setInitialMarkingForPlace(newPlace, new UnifiedToken(0.0));
        lastPlace = newPlace;
      }
      int tr = netToMake.addTransition(0, UnifiedOneXOneTable.defaultTable());
      netToMake.addArcFromPlaceToTransition(lastPlace, tr);
      netToMake.addArcFromTransitionToPlace(tr, between[1]);
      
    }
    return Boolean.TRUE;
  }
  
  private Boolean constantVisit(INode<NodeType> ss ){
    ConstantLeaf l = (ConstantLeaf) ss;
    int[] between = placesBetween.poll();
    int constPlace = netToMake.addPlace(scaleProvider.defaultScale());
    netToMake.setInitialMarkingForPlace(constPlace, new UnifiedToken(l.getConsValue()));
    int intermedaitePlace = netToMake.addPlace(scaleProvider.defaultScale());
    int copyTr = netToMake.addTransition(0, ConstantLeaf.copyTable.myClone());
    netToMake.addArcFromPlaceToTransition(constPlace,copyTr );
    netToMake.addArcFromPlaceToTransition(intermedaitePlace,copyTr );
    
    netToMake.addArcFromTransitionToPlace(copyTr, constPlace);
    netToMake.addArcFromTransitionToPlace(copyTr, intermedaitePlace);
    
    int recive = netToMake.addTransition(0, ConstantLeaf.recieveTable.myClone()) ;
    
    netToMake.addArcFromPlaceToTransition(intermedaitePlace, recive);
    netToMake.addArcFromPlaceToTransition(between[0], recive);
    netToMake.addArcFromTransitionToPlace(recive, between[1]);
    
    return Boolean.TRUE;
  }

  private Boolean invVisit(INode<NodeType> ss) {
    int[] between = placesBetween.poll();
    int enterTr = netToMake.addTransition(0, UnifiedOneXTwoTable.defaultTable());
    netToMake.addArcFromPlaceToTransition(between[0], enterTr);
    int enterPlaceOne = netToMake.addPlace(scaleProvider.defaultScale());
    int enterPlaceTwo = netToMake.addPlace(scaleProvider.defaultScale());
    netToMake.addArcFromTransitionToPlace(enterTr, enterPlaceOne);
    netToMake.addArcFromTransitionToPlace(enterTr, enterPlaceTwo);

    int exitPlaceOne = netToMake.addPlace(scaleProvider.defaultScale());
    int exitTr = netToMake.addTransition(0, UnifiedTwoXOneTable.onlyOp(Operator.DIV));
    netToMake.addArcFromTransitionToPlace(exitTr, between[1]);
    netToMake.addArcFromPlaceToTransition(exitPlaceOne, exitTr);
    netToMake.addArcFromPlaceToTransition(enterPlaceTwo, exitTr);
    placesBetween.add(new int[] { enterPlaceOne, exitPlaceOne });
    this.constantVisit(new ConstantLeaf(1.0));
    return Boolean.TRUE;
  }

}
