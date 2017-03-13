package FuzzyPLang.NetBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;


import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedPetriNetChecker;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class UnifiedHierachicalBuilder extends AbstactHierachicalBuilder<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet, HiearchicalIntermediateUnifiedNet> {

	private UnifiedPetriNetChecker checker;
	private Map<NodeRef, Double> scaleForRealInp;

  public UnifiedHierachicalBuilder(HiearchicalIntermediateUnifiedNet interNet) {
		this(interNet, false);
	}
  
  public UnifiedHierachicalBuilder(HiearchicalIntermediateUnifiedNet interNet, boolean strict) {
		super(interNet, strict);
		checker = new UnifiedPetriNetChecker();
	}

  @Override
  protected void regisetrRealInput(HiearchicalIntermediateUnifiedNet curentInterNet, NodeRef rr, String inpPlaceName) {
    if(scaleForRealInp == null){
      scaleForRealInp = new HashMap<>();
    }
    scaleForRealInp.put(rr,curentInterNet.getInpScales(inpPlaceName));
  }
  
	@Override
	protected void addSimpleArcFromPlaceToTrans(Integer placeId, Integer transitionId, UnifiedPetriNet toRet) {
	  toRet.addArcFromPlaceToTransition(placeId, transitionId);
		
	}

	@Override
	protected void checkPetri(UnifiedPetriNet net) {
	 errorFound |=  checker.checkPetriNet(net);
   errorLog.append(checker.getErrorMsg());
	}

	@Override
	protected Integer addPlace(UnifiedPetriNet toRet, NodeRef palceRef) {
	  return toRet.addPlace(-1.0);
	}

	@Override
	protected void addSpecialArcs(UnifiedPetriNet toRet) {
	  //no special arcs
	}

	@Override
	protected UnifiedToken convertStrinToToken(String tokenAdded) {
		return UnifiedToken.buildFromString(tokenAdded);
	}

	@Override
	protected Integer addInputPlace(UnifiedPetriNet toRet, NodeRef palceRef) {
		return toRet.addInputPlace(scaleForRealInp.get(palceRef));
	}

	@Override
	protected UnifiedPetriNet createNewPetriNet() {
		return new UnifiedPetriNet();
	}

	@Override
	protected Class<UnifiedOneXOneTable> getOutTableClass() {
		return UnifiedOneXOneTable.class;
	}

	@Override
	protected long countSpecialArcsWhich(Predicate<NodeRef[]> outgoingArcsFilter) {
		return 0; //no special arcs
	}

	@Override
	protected IUnifiedTable defaultTwoXOne() {
		return UnifiedTwoXOneTable.defaultTable();
	}

	@Override
	protected IUnifiedTable defaultOneXTwo() {
		return UnifiedOneXTwoTable.defaultTable();
	}

	@Override
	protected UnifiedOneXOneTable defaultOneXOne() {
		return UnifiedOneXOneTable.defaultTable();
	}

	@Override
	protected IUnifiedTable defaultTwoXTwo() {
		return UnifiedTwoXTwoTable.defaultTable();
	}

	@Override
	protected void extactSpecailArcs(DynamicScope dinScope, HiearchicalIntermediateUnifiedNet currentNet) {
	  //no scpecail arcs
	}


}
