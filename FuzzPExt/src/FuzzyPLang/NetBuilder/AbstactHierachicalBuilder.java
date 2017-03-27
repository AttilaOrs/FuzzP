package FuzzyPLang.NetBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import core.Drawable.TransitionPlaceNameStore;
import core.common.AbstractPetriNet;

public abstract class AbstactHierachicalBuilder<TTokenType, TITable, TOutTable extends TITable, TPetriNet extends AbstractPetriNet<TTokenType, TITable, TOutTable>, TIntermediateNet extends AbstactHierachicalIntermediateNet<TITable, TIntermediateNet>> {

  protected TIntermediateNet interNet;
  protected boolean strict;
  protected StringBuilder errorLog;
  protected boolean errorFound;
  protected Map<String, List<StaticScope>> declarationScopesName;
  protected Map<StaticScope, TIntermediateNet> declearions;
  protected Map<String, Map<StaticScope, TITable>> tableDeclaration;
  protected List<NodeRef> realInputPlaces;
  protected Map<NodeRef, String> tokensPuttedInPlace;
  protected Map<NodeRef, StaticScope> staticScopeOfPlaces;
  protected List<NodeRef> realOuputTrans;
  protected Map<NodeRef, StaticScope> staticScopeOfTransitions;
  protected List<NodeRef[]> unweigthedArcs;

  protected HashMap<NodeRef, Integer> placeIDs;
  protected HashMap<NodeRef, Integer> trIDs;

  public AbstactHierachicalBuilder(TIntermediateNet interNet) {
    this(interNet, false);
  }

  public AbstactHierachicalBuilder(TIntermediateNet interNet, boolean strict) {
    this.interNet = interNet;
    this.strict = strict;
    errorLog = new StringBuilder();
    errorFound = false;
    generateAlllDecalrationRefs();
    generateAllTableDeclarations();
    generateAllTransRefs();
    generateAllPlaceRefs();
    collectArcs();

  }

  public TransitionPlaceNameStore createNameStoreTransitionFullName() {
    TransitionPlaceNameStore nametStore = new TransitionPlaceNameStore();
    for (Entry<NodeRef, Integer> placeEntry : placeIDs.entrySet()) {
      if (placeEntry.getKey().getDynamicScope().current()) {
        nametStore.addPlaceName(placeEntry.getValue(), placeEntry.getKey().getNodeName());
      } else {
        nametStore.addPlaceName(placeEntry.getValue(), "_P" + placeEntry.getValue());
      }
    }
    for (Entry<NodeRef, Integer> transitionEntry : trIDs.entrySet()) {
      if (transitionEntry.getKey().getDynamicScope().current()) {
        nametStore.addTransitionName(transitionEntry.getValue(), transitionEntry.getKey().getNodeName());
      } else {
        nametStore.addTransitionName(transitionEntry.getValue(),
            transitionEntry.getKey().toString().replaceAll("\\.", "_"));
      }
    }
    return nametStore;
  }

  private void generateAlllDecalrationRefs() {
    declarationScopesName = new HashMap<>();
    declearions = new HashMap<>();
    StaticScope rootStaticScope = new StaticScope();
    generateRecursivlyTheDeclarationRefs(rootStaticScope, interNet);
    declearions.put(new StaticScope(), interNet);
  }

  private void generateRecursivlyTheDeclarationRefs(StaticScope curentStaticScape, TIntermediateNet curentNet) {
    for (String decSimpleName : curentNet.getDeclarations().keySet()) {
      StaticScope scope = curentStaticScape.cloneSubState();
      scope.addSub(decSimpleName);
      declearions.put(scope, curentNet.getDeclarations().get(decSimpleName));
      if (!declarationScopesName.containsKey(decSimpleName)) {
        declarationScopesName.put(decSimpleName, new ArrayList<>());
      }
      declarationScopesName.get(decSimpleName).add(scope);
      TIntermediateNet net = curentNet.getDeclarations().get(decSimpleName);
      if (net != null) {
        generateRecursivlyTheDeclarationRefs(scope.cloneSubState(), net);
      }
    }
  }

  private void generateAllTableDeclarations() {
    tableDeclaration = new HashMap<>();
    for (StaticScope sc : declearions.keySet()) {
      TIntermediateNet hin = declearions.get(sc);
      hin.getTableMap().forEach((name, table) -> {
        if (!tableDeclaration.containsKey(name)) {
          tableDeclaration.put(name, new HashMap<>());
        }
        tableDeclaration.get(name).put(sc, table);
      });
    }
  }

  private void generateAllPlaceRefs() {
    StaticScope rootStaticScope = new StaticScope();
    DynamicScope rootDynamicScope = new DynamicScope();
    realInputPlaces = new ArrayList<>();
    tokensPuttedInPlace = new HashMap<>();
    staticScopeOfPlaces = genPlaceRefs(rootStaticScope, rootDynamicScope, interNet);

  }

  private Map<NodeRef, StaticScope> genPlaceRefs(StaticScope curStaticScope, DynamicScope curentDynamicScope,
      TIntermediateNet curentInterNet) {
    Map<NodeRef, StaticScope> toRet = new HashMap<>();

    for (String inpPlaceName : curentInterNet.getInpPlaces()) {
      NodeRef rr = new NodeRef(inpPlaceName, curentDynamicScope.cloneSubState());
      toRet.put(rr, curStaticScope.cloneSubState());
      if (curStaticScope.current()) {
        realInputPlaces.add(rr);
        regisetrRealInput(curentInterNet, rr, inpPlaceName);
      }
      if (curentInterNet.getTokensAdded().containsKey(inpPlaceName)) {
        tokensPuttedInPlace.put(rr, curentInterNet.getTokensAdded().get(inpPlaceName));
      }
    }

    for (String normalPlace : curentInterNet.getPlaces()) {
      NodeRef rr = new NodeRef(normalPlace, curentDynamicScope.cloneSubState());
      toRet.put(rr, curStaticScope.cloneSubState());
      registerNormalPlace(curentInterNet, rr, normalPlace);
      if (curentInterNet.getTokensAdded().containsKey(normalPlace)) {
        tokensPuttedInPlace.put(rr, curentInterNet.getTokensAdded().get(normalPlace));
      }
    }

    for (Entry<String, String> instance : curentInterNet.getInstances().entrySet()) {
      DynamicScope insatnceDynScope = curentDynamicScope.cloneSubState();
      insatnceDynScope.addSub(instance.getKey());
      StaticScope insatnceStaticScope = curStaticScope.cloneSubState();
      insatnceStaticScope.addSub(instance.getValue());

      TIntermediateNet net = findDeclaration(instance.getValue(), curStaticScope.cloneSubState());
      if (net != null) {
        Map<NodeRef, StaticScope> rez = genPlaceRefs(insatnceStaticScope, insatnceDynScope, net);
        toRet.putAll(rez);
      } else {
        error("No declareation `" + instance.getValue() + "` find in current scope " + curStaticScope + "\n");
      }
    }
    return toRet;
  }



  protected TIntermediateNet findDeclaration(String value, StaticScope insatnceStaticScope) {
    if (declarationScopesName.containsKey(value)) {
      List<StaticScope> alternatives = declarationScopesName.get(value);
      do {
        for (StaticScope curentAlternative : alternatives) {
          if (insatnceStaticScope.sameFamily(curentAlternative)) {
            return declearions.get(curentAlternative);
          }
        }
      } while (insatnceStaticScope.removeLastSub() != null);
    }
    return null;
  }

  protected void error(String error) {
    errorLog.append(error);
    errorFound = true;
  }

  private void generateAllTransRefs() {
    StaticScope rootStaticScope = new StaticScope();
    DynamicScope rootDynamicScope = new DynamicScope();
    realOuputTrans = new ArrayList<>();
    staticScopeOfTransitions = genTransRefs(rootStaticScope, rootDynamicScope, interNet);

  }

  private Map<NodeRef, StaticScope> genTransRefs(StaticScope curStaticScope, DynamicScope curentDynamicScope,
      TIntermediateNet curentInterNet) {
    Map<NodeRef, StaticScope> toRet = new HashMap<>();
    for (String outTrName : curentInterNet.getOutTransitions()) {
      NodeRef rr = new NodeRef(outTrName, curentDynamicScope.cloneSubState());
      toRet.put(rr, curStaticScope.cloneSubState());
      if (curStaticScope.current()) {
        realOuputTrans.add(rr);
      }
    }
    for (String normalTr : curentInterNet.getTransitions()) {
      NodeRef rr = new NodeRef(normalTr, curentDynamicScope.cloneSubState());
      toRet.put(rr, curStaticScope.cloneSubState());
    }
    for (Entry<String, String> instance : curentInterNet.getInstances().entrySet()) {
      DynamicScope insatnceDynScope = curentDynamicScope.cloneSubState();
      insatnceDynScope.addSub(instance.getKey());
      StaticScope insatnceStaticScope = curStaticScope.cloneSubState();
      insatnceStaticScope.addSub(instance.getValue());

      TIntermediateNet net = findDeclaration(instance.getValue(), curStaticScope.cloneSubState());
      if (net != null) {
        Map<NodeRef, StaticScope> rez = genTransRefs(insatnceStaticScope, insatnceDynScope, net);
        toRet.putAll(rez);
      } else {
        error("No declareation `" + instance.getValue() + "` find in current scope " + curStaticScope);
      }
    }
    return toRet;
  }

  private void collectArcs() {
    unweigthedArcs = new ArrayList<>();
    DynamicScope dinScope = new DynamicScope();
    StaticScope staticScope = new StaticScope();
    collecArcsRecursivily(staticScope, dinScope, interNet);

  }

  private void collecArcsRecursivily(StaticScope staticScope, DynamicScope dinScope, TIntermediateNet currentNet) {
    for (NodeRef[] arc : currentNet.getUnweigthedArc()) {
      NodeRef ref1 = arc[0].copyNodeRef();
      ref1.updateToFullDynScope(dinScope.cloneSubState());
      NodeRef ref2 = arc[1].copyNodeRef();
      ref2.updateToFullDynScope(dinScope.cloneSubState());
      NodeRef[] fullArc = new NodeRef[] { ref1, ref2 };

      unweigthedArcs.add(fullArc);
    }
    extactSpecailArcs(dinScope, currentNet);
    for (Entry<String, String> instance : currentNet.getInstances().entrySet()) {

      DynamicScope insatnceDynScope = dinScope.cloneSubState();
      insatnceDynScope.addSub(instance.getKey());
      StaticScope insatnceStaticScope = staticScope.cloneSubState();
      insatnceStaticScope.addSub(instance.getValue());

      TIntermediateNet net = findDeclaration(instance.getValue(), staticScope.cloneSubState());
      if (net != null) {
        collecArcsRecursivily(insatnceStaticScope, insatnceDynScope, net);
      }
    }
  }

  public TransitionPlaceNameStore createSimplifiedNameStore() {
    TransitionPlaceNameStore nametStore = new TransitionPlaceNameStore();
    for (Entry<NodeRef, Integer> placeEntry : placeIDs.entrySet()) {
      if (placeEntry.getKey().getDynamicScope().current()) {
        nametStore.addPlaceName(placeEntry.getValue(), placeEntry.getKey().getNodeName());
      } else {
        nametStore.addPlaceName(placeEntry.getValue(), "_P" + placeEntry.getValue());
      }
    }
    for (Entry<NodeRef, Integer> transitionEntry : trIDs.entrySet()) {
      if (transitionEntry.getKey().getDynamicScope().current()) {
        nametStore.addTransitionName(transitionEntry.getValue(), transitionEntry.getKey().getNodeName());
      } else {
        nametStore.addTransitionName(transitionEntry.getValue(), "_T" + transitionEntry.getValue());
      }
    }
    return nametStore;
  }

  public TPetriNet buildPetriNet() {
    TPetriNet toRet = createNewPetriNet();
    placeIDs = new HashMap<>();
    trIDs = new HashMap<>();
    for (NodeRef palceRef : staticScopeOfPlaces.keySet()) {
      if (realInputPlaces.contains(palceRef)) {
        placeIDs.put(palceRef, addInputPlace(toRet, palceRef));
      } else {
        placeIDs.put(palceRef, addPlace(toRet, palceRef));
        TIntermediateNet net = declearions.get(staticScopeOfPlaces.get(palceRef));
        String tokenAdded = net.getTokensAdded().get(palceRef.getNodeName());
        if (tokenAdded != null) {
          toRet.setInitialMarkingForPlace(placeIDs.get(palceRef), convertStrinToToken(tokenAdded));
        }
      }
    }
    for (NodeRef trRef : staticScopeOfTransitions.keySet()) {
      if (realOuputTrans.contains(trRef)) {
        TOutTable table = getOutTransitionTable(trRef);
        trIDs.put(trRef, toRet.addOuputTransition(table));
      } else {
        TITable table = getTransitionTable(trRef);
        trIDs.put(trRef, toRet.addTransition(getDelay(trRef), table));

      }
    }
    addSpecialArcs(toRet);

    for (int index = 0; index < unweigthedArcs.size(); index++) {
      NodeRef[] arc = unweigthedArcs.get(index);
      if (placeIDs.containsKey(arc[0]) && trIDs.containsKey(arc[1])) {
        // toRet.addArcFromPlaceToTransition(placeIDs.get(arc[0]),
        // trIDs.get(arc[1]), 1.0);
        addSimpleArcFromPlaceToTrans(placeIDs.get(arc[0]), trIDs.get(arc[1]), toRet);
      } else if (trIDs.containsKey(arc[0]) && placeIDs.containsKey(arc[1])) {
        toRet.addArcFromTransitionToPlace(trIDs.get(arc[0]), placeIDs.get(arc[1]));
      } else {
        boolean firstFound = trIDs.containsKey(arc[0]) || placeIDs.containsKey(arc[0]);
        boolean secondFounf = trIDs.containsKey(arc[1]) || placeIDs.containsKey(arc[1]);
        if(firstFound && secondFounf){
          error("You cannot have ARCs between a transition (" + arc[0] + ") and place  (" + arc[1] + ") \n");
        } else if ((!firstFound) && (!secondFounf)){
          error("You cannot have ARCs between a transition (" + arc[0] + ") and place  (" + arc[1] + ") \n"+
                arc[0] +" Not found, probaby mistyped \n"+
                arc[1] +" Not found, probaby mistyped \n");
        } else if(!firstFound){
          error("You cannot have ARCs between a transition (" + arc[0] + ") and place  (" + arc[1] + ") \n"+
                arc[0] +" Not found, probaby mistyped \n");
        } else {
          error("You cannot have ARCs between a transition (" + arc[0] + ") and place  (" + arc[1] + ") \n"+
                arc[1] +" Not found, probaby mistyped \n");
        }
        
      }
    }

    if (strict && errorFound) {
      throw new RuntimeException("Incorrect Petri net :" + errorLog.toString());
    }
    return toRet;
  }

  protected abstract void addSimpleArcFromPlaceToTrans(Integer integer, Integer integer2, TPetriNet toRet);

  protected abstract void checkPetri(TPetriNet net);

  protected abstract Integer addPlace(TPetriNet toRet, NodeRef palceRef);

  protected abstract void addSpecialArcs(TPetriNet toRet);

  protected abstract TTokenType convertStrinToToken(String tokenAdded);

  protected abstract Integer addInputPlace(TPetriNet toRet, NodeRef palceRef);

  protected abstract TPetriNet createNewPetriNet();
  
  protected abstract void regisetrRealInput(TIntermediateNet curentInterNet, NodeRef rr, String inpPlaceName);
  
  public boolean hasErrors(){
    return errorFound;
  }

  public String getErrors() {
    return errorLog.toString();
  }

  @SuppressWarnings("unchecked") // it is actually checked
  protected TOutTable getOutTransitionTable(NodeRef trRef) {
    TITable table = findNamedTableForTransition(trRef);
    if (table != null) {
      if (table.getClass().equals(getOutTableClass())) {
        return (TOutTable) table;
      } else {
        error(" the ouput transition `" + trRef.nodeName + "` should have OneXOne table incorporated \n");
      }
    }
    return defaultOneXOne();
  }

  protected int getDelay(NodeRef trRef) {
    TIntermediateNet net = declearions.get(staticScopeOfTransitions.get(trRef));
    if (net.getDelayMap().containsKey(trRef.getNodeName())) {
      return net.getDelayMap().get(trRef.getNodeName());
    }
    return 0;
  }

  protected TITable getTransitionTable(NodeRef trRef) {
    TITable table = findNamedTableForTransition(trRef);
    if (table != null) {
      return table;
    }

    long outGoingArcs = countArcsWhich(arc -> arc[0].equals(trRef));
    long incommingArcs = countArcsWhich(arc -> arc[1].equals(trRef));
    if (outGoingArcs < 1 || outGoingArcs > 2 || incommingArcs > 2 || incommingArcs < 1) {
      error(" the transition `" + trRef + "` has " + outGoingArcs + "` outgoing arc and " + incommingArcs
          + " incomming arcs \n");
      return defaultTwoXTwo();
    }
    if (incommingArcs == 1) {
      if (outGoingArcs == 1) {
        return defaultOneXOne();
      } else {
        return defaultOneXTwo();
      }
    } else {
      if (outGoingArcs == 1) {
        return defaultTwoXOne();
      } else {
        return defaultTwoXTwo();
      }

    }
  }

  private long countArcsWhich(Predicate<NodeRef[]> outgoingArcsFilter) {
    return countSpecialArcsWhich(outgoingArcsFilter) + unweigthedArcs.stream().filter(outgoingArcsFilter).count();
  }

  protected TITable findNamedTableForTransition(NodeRef trRef) {
    String tableName = findTableNameForTransition(trRef);
    if (tableName != null) {
      return findTable(tableName, staticScopeOfTransitions.get(trRef));
    }
    return null;
  }

  private String findTableNameForTransition(NodeRef trRef) {
    TIntermediateNet net = declearions.get(staticScopeOfTransitions.get(trRef));
    if (net.getTransitionTableName().containsKey(trRef.getNodeName())) {
      return net.getTransitionTableName().get(trRef.getNodeName());
    }
    return null;
  }

  private TITable findTable(String tableName, StaticScope staticScope) {
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

  protected abstract Class<TOutTable> getOutTableClass();

  protected abstract long countSpecialArcsWhich(Predicate<NodeRef[]> outgoingArcsFilter);

  protected abstract TITable defaultTwoXOne();

  protected abstract TITable defaultOneXTwo();

  protected abstract TOutTable defaultOneXOne();

  protected abstract TITable defaultTwoXTwo();

  protected abstract void extactSpecailArcs(DynamicScope dinScope, TIntermediateNet currentNet);

  protected abstract void registerNormalPlace(TIntermediateNet curentInterNet, NodeRef rr, String normalPlace);
}
