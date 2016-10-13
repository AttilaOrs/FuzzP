package FuzzyPLang.NetBuilder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNetChecker;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class HierachicalBuilder {
	
	
	Map<String, List<StaticScope>>  declarationScopesName;
	Map<StaticScope, HiearchicalIntermediateNet> declearions;
	
	Map<String, Map<StaticScope, ITable>>  tableDeclaration;
	
	Map<NodeRef, StaticScope> staticScopeOfPlaces;
	List<NodeRef> realInputPlaces;
	Map<NodeRef, String> tokensPuttedInPlace;
	
	Map<NodeRef, StaticScope> staticScopeOfTransitions;
	List<NodeRef> realOuputTrans;
	
    List<NodeRef[]> unweigthedArcs;
    List<NodeRef[]> weigthedArsc;
    List<Double> weigths;
	
	private HiearchicalIntermediateNet interNet;
	private HashMap<NodeRef, Integer> placeIDs;
	private HashMap<NodeRef, Integer> trIDs;
	private StringBuilder errorLog;
	private boolean errorFound;
	private final boolean strict;
	private FuzzyPetriNetChecker checker;
	
	public HierachicalBuilder(HiearchicalIntermediateNet net){
		this(net, false);
	}
	
	
	public HierachicalBuilder(HiearchicalIntermediateNet net, boolean strict){
		this.interNet = net;
		errorLog = new StringBuilder();
		errorFound = false;
        checker = new FuzzyPetriNetChecker();
        this.strict = strict;
		generateAlllDecalrationRefs();
		generateAllTableDeclarations();
		generateAllPlaceRefs();
		generateAllTransRefs();
        collectArcs();
	}
	
	public TransitionPlaceNameStore createSimplifiedNameStore(){
		TransitionPlaceNameStore nametStore = new TransitionPlaceNameStore();
		for(Entry<NodeRef, Integer> placeEntry : placeIDs.entrySet()){
			if(placeEntry.getKey().getDynamicScope().current()){
				nametStore.addPlaceName(placeEntry.getValue(), placeEntry.getKey().getNodeName());
			} else {
				nametStore.addPlaceName(placeEntry.getValue(), "_P"+placeEntry.getValue());
			}
		}
		for(Entry<NodeRef, Integer> transitionEntry : trIDs.entrySet()){
			if(transitionEntry.getKey().getDynamicScope().current()){
				nametStore.addTransitionName(transitionEntry.getValue(), transitionEntry.getKey().getNodeName());
			} else {
				nametStore.addTransitionName(transitionEntry.getValue(), "_T"+transitionEntry.getValue());
			}
		}
		return nametStore;
	}
	
	public String getErrors(){
		return errorLog.toString();
	}
	
    public FuzzyPetriNet buildPetriNet() {
        FuzzyPetriNet toRet = new FuzzyPetriNet();
        placeIDs = new HashMap<>();
        trIDs = new HashMap<>();
        for (NodeRef palceRef : staticScopeOfPlaces.keySet()) {
            if (realInputPlaces.contains(palceRef)) {
                placeIDs.put(palceRef, toRet.addInputPlace());
            } else {
                placeIDs.put(palceRef, toRet.addPlace());
            }
        }
        for (NodeRef trRef : staticScopeOfTransitions.keySet()) {
            if (realOuputTrans.contains(trRef)) {
                OneXOneTable table = getOutTransitionTable(trRef);
                trIDs.put(trRef, toRet.addOuputTransition(table));
            } else {
                ITable table = getTransitionTable(trRef);
                trIDs.put(trRef, toRet.addTransition(getDelay(trRef), table));

            }
        }
        for(int index = 0 ; index < weigthedArsc.size() ; index++){
        	NodeRef[] arc = weigthedArsc.get(index);
        	Double weigth = weigths.get(index);
        	if(placeIDs.containsKey(arc[0]) && trIDs.containsKey(arc[1])){
        		toRet.addArcFromPlaceToTransition(placeIDs.get(arc[0]), trIDs.get(arc[1]), weigth);
        	} else if(trIDs.containsKey(arc[0]) && placeIDs.containsKey(arc[1])){
        		error("You cannot have WEIGTED arc between a transition ("+ arc[0] +") and place  (" +arc[1] + ") \n" );
        		toRet.addArcFromTransitionToPlace(trIDs.get(arc[0]), placeIDs.get(arc[1]));
        	} else {
        		error("You cannot have ARCs between a transition ("+ arc[0] +") and place  (" +arc[1] + ") \n" );
        	}
        }
        
        for(int index = 0 ; index < unweigthedArcs.size() ; index++){
        	NodeRef[] arc = unweigthedArcs.get(index);
        	if(placeIDs.containsKey(arc[0]) && trIDs.containsKey(arc[1])){
        		toRet.addArcFromPlaceToTransition(placeIDs.get(arc[0]), trIDs.get(arc[1]), 1.0);
        	} else if(trIDs.containsKey(arc[0]) && placeIDs.containsKey(arc[1])){
        		toRet.addArcFromTransitionToPlace(trIDs.get(arc[0]), placeIDs.get(arc[1]));
        	} else {
        		error("You cannot have ARCs between a transition ("+ arc[0] +") and place  (" +arc[1] + ") \n" );
        	}
        }
         errorFound |= checker.checkPetriNet(toRet);
         errorLog.append(checker.getErrorMsg());
         if(strict  && errorFound){
        	 throw new RuntimeException("Incorrect Petri net :" + errorLog.toString());
         }
        return toRet;
    }
	

    private int getDelay(NodeRef trRef) {
        HiearchicalIntermediateNet net = declearions.get(staticScopeOfTransitions.get(trRef));
        if (net.getDelayMap().containsKey(trRef.getNodeName())) {
            return net.getDelayMap().get(trRef.getNodeName());
        }
        return 0;
    }

    private ITable getTransitionTable(NodeRef trRef) {
    	ITable table = findNamedTableForTransition(trRef);
    	if(table != null){
    		return table;
    	}
    	
    	long outGoingArcs = countArcsWhich(arc -> arc[0].equals(trRef));
    	long incommingArcs = countArcsWhich(arc -> arc[1].equals(trRef));
    	if(outGoingArcs <1 || outGoingArcs > 2 || incommingArcs >2 || incommingArcs <1  ){
    		error(" the transition `" + trRef +"` has " + outGoingArcs + "` outgoing arc and " + incommingArcs + " incomming arcs \n");
    		return TwoXTwoTable.defaultTable();
    	}
    	if(incommingArcs == 1){
    		if(outGoingArcs == 1){
    			return OneXOneTable.defaultTable();
    		} else {
    			return OneXTwoTable.defaultTable();
    		}
    	} else {
    		if(outGoingArcs == 1){
    			return TwoXOneTable.defaultTable();
    		} else {
    			return TwoXTwoTable.defaultTable();
    		}
    		
    	}
    	
    }

	private long countArcsWhich(Predicate<NodeRef[]> outgoingArcsFilter) {
		return weigthedArsc.stream().filter(outgoingArcsFilter).count() 
    	 + unweigthedArcs.stream().filter(outgoingArcsFilter).count();
	}

    private OneXOneTable getOutTransitionTable(NodeRef trRef) {
        ITable table = findNamedTableForTransition(trRef);
        if(table != null){
        	if(table instanceof OneXOneTable){
        		return (OneXOneTable) table;
        	} else {
        		error(" the ouput transition `" + trRef.nodeName + "` should have OneXOne table incorporated \n");
        	}
        }
        return OneXOneTable.defaultTable();
    }
    
    private ITable findNamedTableForTransition(NodeRef trRef){
    	String tableName = findTableNameForTransition(trRef);
    	if(tableName != null ){
    		return findTable(tableName, staticScopeOfTransitions.get(trRef));
    	}
    	return null;
    }
    
    private String findTableNameForTransition(NodeRef trRef){
        HiearchicalIntermediateNet net = declearions.get(staticScopeOfTransitions.get(trRef));
        if(net.getTransitionTableName().containsKey(trRef.getNodeName())){
        	return net.getTransitionTableName().get(trRef.getNodeName() );
        }
        return null;
    }

    private ITable findTable(String tableName, StaticScope staticScope) {
        if (tableDeclaration.containsKey(tableName)) {

            Set<StaticScope> alternatives = tableDeclaration.get(tableName).keySet();
            do {
                for (StaticScope curentAlternative : alternatives) {
                    if (staticScope.sameFamily(curentAlternative)) {
                        return tableDeclaration.get(tableName).get(curentAlternative);
                    }
                }
            } while (staticScope.removeLastSub() != null);

        }
        error("No table with name `" + tableName + "` found in scope " + staticScope);

        return null;

    }

    private void collectArcs() {
        unweigthedArcs = new ArrayList<>();
        weigthedArsc = new ArrayList<>();
        weigths = new ArrayList<>();
        DynamicScope dinScope = new DynamicScope();
        StaticScope staticScope = new StaticScope();
        collecArcsRecursivily(staticScope, dinScope, interNet);

    }

    private void collecArcsRecursivily(StaticScope staticScope, DynamicScope dinScope,
            HiearchicalIntermediateNet currentNet) {
        for (NodeRef[] arc : currentNet.getUnweigthedArc()) {
            NodeRef ref1 = arc[0].copyNodeRef();
            ref1.updateToFullDynScope(dinScope.cloneSubState());
            NodeRef ref2 = arc[1].copyNodeRef();
            ref2.updateToFullDynScope(dinScope.cloneSubState());
            NodeRef[] fullArc = new NodeRef[] { ref1, ref2 };

            unweigthedArcs.add(fullArc);
        }
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
        for (Entry<String, String> instance : currentNet.getInstances().entrySet()) {

            DynamicScope insatnceDynScope = dinScope.cloneSubState();
            insatnceDynScope.addSub(instance.getKey());
            StaticScope insatnceStaticScope = staticScope.cloneSubState();
            insatnceStaticScope.addSub(instance.getValue());

            HiearchicalIntermediateNet net = findDeclaration(instance.getValue(), staticScope.cloneSubState());
            if(net != null){
				collecArcsRecursivily(insatnceStaticScope, insatnceDynScope, net);
            }
        }
    }


    private void generateAllTableDeclarations() {
		tableDeclaration = new HashMap<>();
		for(StaticScope sc : declearions.keySet()){
			HiearchicalIntermediateNet hin = declearions.get(sc);
			hin.getTableMap().forEach((name, table) -> {
				if(!tableDeclaration.containsKey(name)){
					tableDeclaration.put(name, new HashMap<>());
				}
				tableDeclaration.get(name).put(sc, table);
			}  );
		}
	}

	private void generateAlllDecalrationRefs() {
		declarationScopesName = new HashMap<>();
		declearions = new HashMap<>();
    	StaticScope rootStaticScope = new StaticScope();
		generateRecursivlyTheDeclarationRefs(rootStaticScope, interNet);
		declearions.put(new StaticScope(), interNet);
	}

	private void generateRecursivlyTheDeclarationRefs(StaticScope curentStaticScape,
			HiearchicalIntermediateNet curentNet) {
		for( String decSimpleName :curentNet.getDeclarations().keySet() ){
			StaticScope scope = curentStaticScape.cloneSubState() ;
			scope.addSub(decSimpleName);
			declearions.put(scope, curentNet.getDeclarations().get(decSimpleName));
			if(!declarationScopesName.containsKey(decSimpleName)){
				declarationScopesName.put(decSimpleName, new ArrayList<>());
			}
			declarationScopesName.get(decSimpleName).add(scope);
			HiearchicalIntermediateNet net = curentNet.getDeclarations().get(decSimpleName);
			if(net != null){
				generateRecursivlyTheDeclarationRefs(scope.cloneSubState(), net);
			}
		}
	}

	private void generateAllTransRefs() {
		StaticScope rootStaticScope = new StaticScope();
		DynamicScope  rootDynamicScope = new DynamicScope();
		realOuputTrans = new ArrayList<>();
		staticScopeOfTransitions = genTransRefs(rootStaticScope, rootDynamicScope, interNet);
		
	}
	
	
	private Map<NodeRef, StaticScope> genTransRefs(StaticScope curStaticScope, DynamicScope curentDynamicScope,
			HiearchicalIntermediateNet curentInterNet) {
		Map<NodeRef, StaticScope> toRet = new HashMap<>();
		for(String outTrName : curentInterNet.getOutTransitions()){
			NodeRef rr = new NodeRef(outTrName, curentDynamicScope.cloneSubState());
			toRet.put(rr, curStaticScope.cloneSubState());
			if(curStaticScope.current()){
				realOuputTrans.add(rr);
			}
		}
		for(String normalTr : curentInterNet.getTransitions()){
			NodeRef rr = new NodeRef(normalTr, curentDynamicScope.cloneSubState());
			toRet.put(rr, curStaticScope.cloneSubState());
		}
		for( Entry<String, String> instance : curentInterNet.getInstances().entrySet()){
			DynamicScope insatnceDynScope = curentDynamicScope.cloneSubState();
			insatnceDynScope.addSub(instance.getKey());
			StaticScope insatnceStaticScope = curStaticScope.cloneSubState();
			insatnceStaticScope.addSub(instance.getValue());
			
			HiearchicalIntermediateNet net = findDeclaration(instance.getValue(), curStaticScope.cloneSubState());
			if(net != null){
				Map<NodeRef, StaticScope> rez = genTransRefs(insatnceStaticScope, insatnceDynScope, net);
				toRet.putAll(rez);
			} else {
				error("No declareation `"+instance.getValue() +"` find in current scope " + curStaticScope );
			}
		}
		return toRet;
	}


	private void generateAllPlaceRefs(){
		StaticScope rootStaticScope = new StaticScope();
		DynamicScope  rootDynamicScope = new DynamicScope();
		realInputPlaces = new ArrayList<>();
		tokensPuttedInPlace = new HashMap<>();
		staticScopeOfPlaces = genPlaceRefs(rootStaticScope, rootDynamicScope, interNet);
		
	}

	private  Map<NodeRef, StaticScope> genPlaceRefs(StaticScope curStaticScope, DynamicScope curentDynamicScope,
			HiearchicalIntermediateNet curentInterNet) {
		Map<NodeRef, StaticScope> toRet = new HashMap<>();
		
		for(String inpPlaceName : curentInterNet.getInpPlaces()){
			NodeRef rr = new NodeRef(inpPlaceName, curentDynamicScope.cloneSubState());
			toRet.put(rr, curStaticScope.cloneSubState());
			if(curStaticScope.current()){
				realInputPlaces.add(rr);
			}
			if(curentInterNet.getTokensAdded().containsKey(inpPlaceName)){
				tokensPuttedInPlace.put(rr, curentInterNet.getTokensAdded().get(inpPlaceName));
			}
		}
		
		for(String normalPlace : curentInterNet.getPlaces()){
			NodeRef rr = new NodeRef(normalPlace, curentDynamicScope.cloneSubState());
			toRet.put(rr, curStaticScope.cloneSubState());
			if(curentInterNet.getTokensAdded().containsKey(normalPlace)){
				tokensPuttedInPlace.put(rr, curentInterNet.getTokensAdded().get(normalPlace));
			}
		}
		
		for( Entry<String, String> instance : curentInterNet.getInstances().entrySet()){
			DynamicScope insatnceDynScope = curentDynamicScope.cloneSubState();
			insatnceDynScope.addSub(instance.getKey());
			StaticScope insatnceStaticScope = curStaticScope.cloneSubState();
			insatnceStaticScope.addSub(instance.getValue());
			
			HiearchicalIntermediateNet net = findDeclaration(instance.getValue(), curStaticScope.cloneSubState());
			if(net != null){
				Map<NodeRef, StaticScope> rez = genPlaceRefs(insatnceStaticScope, insatnceDynScope, net);
				toRet.putAll(rez);
			} else {
				error("No declareation `"+instance.getValue() +"` find in current scope " + curStaticScope +"\n");
			}
		}
		return toRet;
	}

	private HiearchicalIntermediateNet findDeclaration(String value, StaticScope insatnceStaticScope) {
		if(declarationScopesName.containsKey(value)){
			List<StaticScope> alternatives = declarationScopesName.get(value);
			do {
				for(StaticScope curentAlternative:alternatives){
					if(insatnceStaticScope.sameFamily(curentAlternative)){
						return declearions.get(curentAlternative);
					}
				}
			} while(insatnceStaticScope.removeLastSub()!=null);
		}
		return null;
	}
	
	
   private void error(String error){
	   errorLog.append(error);
	   errorFound = true;
   }

}
