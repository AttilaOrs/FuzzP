package FuzzyPLang.NetBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.Tables.OneXOneTable;

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
	
	public HierachicalBuilder(HiearchicalIntermediateNet net){
		this.interNet = net;
		
		
		generateAlllDecalrationRefs();
		generateAllTableDeclarations();
		generateAllPlaceRefs();
		generateAllTransRefs();
        collectArcs();
	}
	
    public FuzzyPetriNet buildPetriNet() {
        FuzzyPetriNet toRet = new FuzzyPetriNet();
        HashMap<NodeRef, Integer> nodeIds = new HashMap<>();
        HashMap<NodeRef, Integer> trIDs = new HashMap<>();
        for (NodeRef palceRef : staticScopeOfPlaces.keySet()) {
            if (realInputPlaces.contains(palceRef)) {
                nodeIds.put(palceRef, toRet.addInputPlace());
            } else {
                nodeIds.put(palceRef, toRet.addPlace());
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
        // TODO Auto-generated method stub
        return null;
    }

    private OneXOneTable getOutTransitionTable(NodeRef trRef) {
        HiearchicalIntermediateNet net = declearions.get(staticScopeOfTransitions.get(trRef));
        if (net.getTransitionTableName().containsKey(trRef.getNodeName())) {
            String tableName = net.getTransitionTableName().get(trRef.getNodeName());
            ITable table = findTable(tableName, staticScopeOfTransitions.get(trRef).cloneSubState());
            if (table != null && table instanceof OneXOneTable) {
                return (OneXOneTable) table;
            }
            
        }
        return OneXOneTable.defaultTable();
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
        System.err.println("No table with name `" + tableName + "` found in scope " + staticScope);

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
            collecArcsRecursivily(insatnceStaticScope, insatnceDynScope, net);

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
			generateRecursivlyTheDeclarationRefs(scope.cloneSubState(), curentNet.getDeclarations().get(decSimpleName));
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
				System.err.println("No declareation `"+instance.getValue() +"` find in current scope " + curStaticScope );
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
				System.err.println("No declareation `"+instance.getValue() +"` find in current scope " + curStaticScope );
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
	
	


}
