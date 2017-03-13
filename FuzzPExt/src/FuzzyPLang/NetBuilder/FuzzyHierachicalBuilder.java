package FuzzyPLang.NetBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNetChecker;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class FuzzyHierachicalBuilder extends
    AbstactHierachicalBuilder<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet, HiearchicalIntermediateFuzzyNet> {

  List<NodeRef[]> weigthedArsc;
  List<Double> weigths;

  private FuzzyPetriNetChecker checker;

  public FuzzyHierachicalBuilder(HiearchicalIntermediateFuzzyNet net) {
    this(net, false);
  }

  public FuzzyHierachicalBuilder(HiearchicalIntermediateFuzzyNet interNet, boolean strict) {
    super(interNet, strict);
    checker = new FuzzyPetriNetChecker();
    weigthedArsc = new ArrayList<>();
    weigths = new ArrayList<>();
  }




  @Override
  protected void addSpecialArcs(FuzzyPetriNet toRet) {
    for (int index = 0; index < weigthedArsc.size(); index++) {
      NodeRef[] arc = weigthedArsc.get(index);
      Double weigth = weigths.get(index);
      if (placeIDs.containsKey(arc[0]) && trIDs.containsKey(arc[1])) {
        toRet.addArcFromPlaceToTransition(placeIDs.get(arc[0]), trIDs.get(arc[1]), weigth);
      } else if (trIDs.containsKey(arc[0]) && placeIDs.containsKey(arc[1])) {
        error("You cannot have WEIGTED arc between a transition (" + arc[0] + ") and place  (" + arc[1] + ") \n");
        toRet.addArcFromTransitionToPlace(trIDs.get(arc[0]), placeIDs.get(arc[1]));
      } else {
        error("You cannot have ARCs between a transition (" + arc[0] + ") and place  (" + arc[1] + ") \n");
      }
    }
  }

  @Override
  protected Integer addPlace(FuzzyPetriNet toRet, NodeRef ref) {
    return toRet.addPlace();
  }

  @Override
  protected Integer addInputPlace(FuzzyPetriNet toRet, NodeRef ref) {
    return toRet.addInputPlace();
  }

  @Override
  protected FuzzyPetriNet createNewPetriNet() {
    FuzzyPetriNet toRet = new FuzzyPetriNet();
    return toRet;
  }

  @Override
  protected FuzzyToken convertStrinToToken(String tokenAdded) {
    return FuzzyToken.buildFromString(tokenAdded);
  }

  @Override
  protected ITable defaultTwoXOne() {
    return TwoXOneTable.defaultTable();
  }

  @Override
  protected ITable defaultOneXTwo() {
    return OneXTwoTable.defaultTable();
  }

  @Override
  protected OneXOneTable defaultOneXOne() {
    return OneXOneTable.defaultTable();
  }

  @Override
  protected ITable defaultTwoXTwo() {
    return TwoXTwoTable.defaultTable();
  }

  @Override
  protected long countSpecialArcsWhich(Predicate<NodeRef[]> outgoingArcsFilter) {
    return weigthedArsc.stream().filter(outgoingArcsFilter).count();
  }







  @Override
  protected void extactSpecailArcs(DynamicScope dinScope, HiearchicalIntermediateFuzzyNet currentNet) {
    for (int i = 0; i < currentNet.getWeigthedArcs().size(); i++) {
      NodeRef[] arc = currentNet.getWeigthedArcs().get(i);
      NodeRef ref1 = arc[0].copyNodeRef();
      ref1.updateToFullDynScope(dinScope.cloneSubState());
      NodeRef ref2 = arc[1].copyNodeRef();
      ref2.updateToFullDynScope(dinScope.cloneSubState());
      NodeRef[] fullArc = new NodeRef[] { ref1, ref2 };
      weigthedArsc.add(fullArc);
      weigths.add(currentNet.getWeigthsForArc().get(i));
    }
  }

  @Override
  protected Class<OneXOneTable> getOutTableClass() {
    return OneXOneTable.class;
  }

  @Override
  protected void checkPetri(FuzzyPetriNet net) {
    errorFound |= checker.checkPetriNet(net);
    errorLog.append(checker.getErrorMsg());
  }

  @Override
  protected void addSimpleArcFromPlaceToTrans(Integer placeId, Integer trId, FuzzyPetriNet toRet) {
    toRet.addArcFromPlaceToTransition(placeId, trId, 1.0);
  }

  @Override
  protected void regisetrRealInput(HiearchicalIntermediateFuzzyNet curentInterNet, NodeRef rr, String inpPlaceName) {
    //does nothing
  }

}
