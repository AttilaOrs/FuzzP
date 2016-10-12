package FuzzyPLang.NetBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import core.FuzzyPetriLogic.ITable;

public class HierachicalBuilder {
	
	
	Map<String, List<StaticScope>>  declarationScopesName;
	Map<StaticScope, HiearchicalIntermediateNet> declearions;
	
	Map<String, Map<StaticScope, ITable>>  tableDeclaration;
	
	Map<NodeRef, StaticScope> staticScopeOfPlaces;
	List<NodeRef> realInputPlaces;
	Map<NodeRef, String> tokensPuttedInPlace;
	
	Map<NodeRef, StaticScope> staticScopeOfTransitions;
	List<NodeRef> realOuputTrans;
	
	
	private HiearchicalIntermediateNet interNet;
	
	public HierachicalBuilder(HiearchicalIntermediateNet net){
		this.interNet = net;
		
		
		generateAlllDecalrationRefs();
		generateAllTableDeclarations();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		declarationScopesName.entrySet().forEach(ent -> System.out.println(ent.getKey() + " >><< " + ent.getValue() ) );
		generateAllPlaceRefs();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(tableDeclaration);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		staticScopeOfPlaces.entrySet().forEach(ent -> System.out.println(ent.getKey() + " >><< " + ent.getValue() ) );
		System.out.println(">inps:");
		realInputPlaces.forEach(ff -> System.out.println(ff));
		System.out.println(">tokens:");
		tokensPuttedInPlace.entrySet().forEach(ent -> System.out.println(ent.getKey() + " <= " + ent.getValue() ) );
		generateAllTransRefs();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		staticScopeOfTransitions.entrySet().forEach(ent -> System.out.println(ent.getKey() + " >><< " + ent.getValue() ) );
		System.out.println(">outs:");
		realOuputTrans.forEach(ff -> System.out.println(ff));
		
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
