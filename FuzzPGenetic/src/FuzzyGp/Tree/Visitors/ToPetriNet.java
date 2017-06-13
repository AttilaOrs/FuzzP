package FuzzyGp.Tree.Visitors;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.DelayLeaf;
import FuzzyGp.Tree.Nodes.FullRuleLeaf;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InversionLeaf;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OutLeaf;
import FuzzyGp.Tree.Nodes.ZeroEventInput;
import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class ToPetriNet implements VisitorCustomizer {

  public static FuzzyPetriNet convertToFuzzyPetriNet(INode nn) {
    ToPetriNet converter = new ToPetriNet();
    return converter.convert(nn);
  }

  FuzzyPetriNet net;
  BreadthFirstVisitorRunner runner;
  ArrayDeque<int[]> stackOfBorderPlaces;
  boolean preservMapping;
  NodeToTransitionMapping mapping;
  Map<Integer, int[]> connectionOfOuts; // outNr:::[outTrId, outPlaceId]
  Map<Integer, Set<Integer>> slaveInputPlacesForInputs;
  private Map<Integer, Integer> inpPlaceForInps;

  public ToPetriNet() {
    this(false);
  }

  public ToPetriNet(boolean preservMapping) {
    runner = new BreadthFirstVisitorRunner(this);
    this.preservMapping = preservMapping;
    connectionOfOuts = new HashMap<>();
    slaveInputPlacesForInputs = new HashMap<>();
    intiMapping();

  }

  private void intiMapping() {
    if (preservMapping) {
      mapping = new NodeToTransitionMapping();
    }
  }

  public FuzzyPetriNet convert(INode node) {
    intiMapping();
    stackOfBorderPlaces = new ArrayDeque<>();
    net = new FuzzyPetriNet();
    int startPlace = net.addPlace();
    int endPlace = net.addPlace();
    stackOfBorderPlaces.push(new int[] { startPlace, endPlace });
    runner.visit(node);
    resolveInputs();
    net.setInitialMarkingForPlace(0, FuzzyToken.zeroToken());
    return net;
  }

  public Map<Integer, Integer> getInputPlacesForInps() {
    return inpPlaceForInps;
  }

  public Map<Integer, Integer> getOutputTransitionsForOuts() {
    return connectionOfOuts.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()[0]));
  }

  private void resolveInputs() {
    inpPlaceForInps = new HashMap<>();
    for (Integer inpNr : slaveInputPlacesForInputs.keySet()) {
      Set<Integer> connPlaces = slaveInputPlacesForInputs.get(inpNr);
      Iterator<Integer> it = connPlaces.iterator();
      int realInp = net.addInputPlace();
      inpPlaceForInps.put(inpNr, realInp);
      int lastAccPlace = realInp;
      while (it.hasNext()) {
        int cu = it.next();
        if (!it.hasNext()) {
          // last
          int trId = net.addTransition(0, OneXOneTable.defaultTable());
          net.addArcFromPlaceToTransition(lastAccPlace, trId, 1.0);
          net.addArcFromTransitionToPlace(trId, cu);
        } else {
          int trId = net.addTransition(0, OneXTwoTable.defaultTable());
          net.addArcFromPlaceToTransition(lastAccPlace, trId, 1.0);
          int nextAcc = net.addPlace();
          net.addArcFromTransitionToPlace(trId, nextAcc);
          net.addArcFromTransitionToPlace(trId, cu);
          lastAccPlace = nextAcc;
        }
      }

    }

  }

  @Override
  public void visitDelayLeaf(DelayLeaf node) {
    int tr = net.addTransition(node.getDelay(), OneXOneTable.defaultTable());
    putTrasnitionBetweenPlaces(tr);
    recordMapping(node, tr);
  }

  @Override
  public void visitOutLeaf(OutLeaf node) {
    int outNr = node.getOutNr();
    if (!outInitialized(outNr)) {
      initalizeOut(outNr);
    }
    int outPlaceId = getOutConnectionPlace(outNr);
    int tr = net.addTransition(0, node.getOutType().geTable());
    putTrasnitionBetweenPlaces(tr);
    net.addArcFromTransitionToPlace(tr, outPlaceId);
    recordMapping(node, tr);

  }

  private int getOutConnectionPlace(int outNr) {
    return connectionOfOuts.get(outNr)[1];
  }

  private void initalizeOut(int outNr) {
    int outTrId = net.addOuputTransition(OneXOneTable.defaultTable());
    int outPlaceId = net.addPlace();
    net.addArcFromPlaceToTransition(outPlaceId, outTrId, 1.0);
    connectionOfOuts.put(outNr, new int[] { outTrId, outPlaceId });
  }

  private boolean outInitialized(int outNr) {
    return connectionOfOuts.containsKey(outNr);
  }

  @Override
  public void visitInpLeaf(InputLeaf node) {
    int inpPlace = net.addPlace();
    registerPlaceForIput(node.getInpNr(), inpPlace);

    int tr = net.addTransition(0, node.getInputType().getTable());
    putTrasnitionBetweenPlaces(tr);
    net.addArcFromPlaceToTransition(inpPlace, tr, 1.0);
    recordMapping(node, tr);

  }

  private void registerPlaceForIput(int inpNr, int inpPlace) {
    if (!slaveInputPlacesForInputs.containsKey(inpNr)) {
      slaveInputPlacesForInputs.put(inpNr, new HashSet<>());
    }
    slaveInputPlacesForInputs.get(inpNr).add(inpPlace);
  }

  private static String blocker = "{[<FF><FF><FF><FF><FF>]}";
  private static FuzzyTableParser parser = new FuzzyTableParser();
  private static OneXOneTable blockingTable = parser.parseOneXOneTable(blocker);

  @Override
  public void visitZeroEventInput(ZeroEventInput node) {
    int tr = net.addTransition(0, blockingTable);
    putTrasnitionBetweenPlaces(tr);
    recordMapping(node, tr);
  }

  @Override
  public void visitInversionLeaf(InversionLeaf node) {

    int tr = net.addTransition(0, OneXOneTable.invertingTable());
    putTrasnitionBetweenPlaces(tr);
    recordMapping(node, tr);

  }

  @Override
  public void visitFullRuleLeaf(FullRuleLeaf node) {
    int outNr = node.getOut();
    if (!outInitialized(outNr)) {
      initalizeOut(outNr);
    }
    int outPlaceId = getOutConnectionPlace(outNr);

    int inpPlace = net.addPlace();
    registerPlaceForIput(node.getInp(), inpPlace);

    int tr = net.addTransition(0, TwoXTwoTable.defaultTable());
    putTrasnitionBetweenPlaces(tr);
    net.addArcFromTransitionToPlace(tr, outPlaceId);
    net.addArcFromPlaceToTransition(inpPlace, tr, 1.0);

    recordMapping(node, tr);

  }

  public void putTrasnitionBetweenPlaces(int tr) {
    int[] borderPlaces = stackOfBorderPlaces.pop();
    net.addArcFromPlaceToTransition(borderPlaces[0], tr, 1.0);
    net.addArcFromTransitionToPlace(tr, borderPlaces[1]);
  }

  @Override
  public void visitConc(Operator oo) {
    int[] boredrPlaces = stackOfBorderPlaces.pop();
    int tr_start = net.addTransition(0, OneXTwoTable.defaultTable());
    int pl_br1_start = net.addPlace();
    int pl_br2_start = net.addPlace();
    net.addArcFromPlaceToTransition(boredrPlaces[0], tr_start, 1.0);
    net.addArcFromTransitionToPlace(tr_start, pl_br1_start);
    net.addArcFromTransitionToPlace(tr_start, pl_br2_start);

    int tr_end = net.addTransition(0, TwoXOneTable.defaultTable());
    int pl_br1_end = net.addPlace();
    int pl_br2_end = net.addPlace();
    net.addArcFromPlaceToTransition(pl_br1_end, tr_end, 1.0);
    net.addArcFromPlaceToTransition(pl_br2_end, tr_end, 1.0);
    net.addArcFromTransitionToPlace(tr_end, boredrPlaces[1]);
    stackOfBorderPlaces.push(new int[] { pl_br2_start, pl_br2_end });
    stackOfBorderPlaces.push(new int[] { pl_br1_start, pl_br1_end });
    recordMapping(oo, tr_start, tr_end);
  }

  @Override
  public void visitSelect(Operator oo) {
    int[] myBorderPlaces = stackOfBorderPlaces.pop();
    stackOfBorderPlaces.push(myBorderPlaces);
    stackOfBorderPlaces.push(myBorderPlaces);
    recordEmptyMapping(oo);
  }

  @Override
  public void visitSeq(Operator oo) {
    int[] myBorderPlaces = stackOfBorderPlaces.pop();
    int midlePlace = net.addPlace();
    stackOfBorderPlaces.push(new int[] { midlePlace, myBorderPlaces[1] });
    stackOfBorderPlaces.push(new int[] { myBorderPlaces[0], midlePlace });
    recordEmptyMapping(oo);
  }

  @Override
  public void visitLoop(Operator oo) {
    int[] myBorderPlaces = stackOfBorderPlaces.pop();
    stackOfBorderPlaces.push(new int[] { myBorderPlaces[1], myBorderPlaces[0] });
    stackOfBorderPlaces.push(new int[] { myBorderPlaces[0], myBorderPlaces[1] });
    recordEmptyMapping(oo);
  }

  public NodeToTransitionMapping getMapping() {
    return mapping;
  }

  private void recordMapping(INode node, int... trs) {
    if (preservMapping) {
      mapping.addIdsToNode(node, trs);
    }
  }

  private void recordEmptyMapping(INode node) {
    if (preservMapping) {
      mapping.addEmptyMapping(node);
    }
  }

}
