package FuzzyPLang.NetBuilder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstactHierachicalIntermediateNet<TTable, SELF extends AbstactHierachicalIntermediateNet<TTable, SELF>> {
  protected ArrayList<String> places;
  protected ArrayList<String> inpPlaces;
  protected ArrayList<String> transitions;
  protected ArrayList<String> outTransitions;
  protected ArrayList<NodeRef[]> unweigthedArc;
  protected Map<String, Integer> delayMap;
  protected Map<String, String> transitionTableName;
  protected Map<String, TTable> tableMap;
  protected Map<String, AbstactHierachicalIntermediateNet<TTable, SELF>> declarations;
  protected Map<String, String> instances;
  protected Map<String, String> tokensAdded;

  Supplier<AbstactHierachicalIntermediateNet<TTable, SELF>> selfFactory;

  public AbstactHierachicalIntermediateNet(Supplier<AbstactHierachicalIntermediateNet<TTable, SELF>> selfFactory) {
    places = new ArrayList<>();
    inpPlaces = new ArrayList<>();
    transitions = new ArrayList<>();
    outTransitions = new ArrayList<>();
    unweigthedArc = new ArrayList<>();
    delayMap = new HashMap<>();
    tableMap = new HashMap<>();
    transitionTableName = new HashMap<>();
    tokensAdded = new HashMap<>();
    declarations = new HashMap<>();
    instances = new HashMap<>();
    this.selfFactory = selfFactory;
  }

  public void addPlace(StaticScope sub, String str) {
    if (sub.current()) {
      if (!places.contains(str)) {
        places.add(str);
      }
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).addPlace(sub, str);
    }
  }

  public void addTransition(StaticScope sub, String tr) {
    if (sub.current()) {
      if (!transitions.contains(tr)) {
        transitions.add(tr);
      }
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).addTransition(sub, tr);
    }
  }

  public void addOutTransition(StaticScope sub, String tr) {
    if (sub.current()) {
      if (!outTransitions.contains(tr)) {
        outTransitions.add(tr);
      }
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).addOutTransition(sub, tr);
    }
  }

  public void addArc(StaticScope sub, NodeRef firsNodeName, NodeRef secondNodeName) {
    if (sub.current()) {
      unweigthedArc.add(new NodeRef[] { firsNodeName, secondNodeName });
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).addArc(sub, firsNodeName, secondNodeName);
    }
  }

  public void setDelayForTransition(StaticScope sub, String trName, int delay) {
    if (sub.current()) {
      delayMap.put(trName, delay);
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).setDelayForTransition(sub, trName, delay);
    }
  }

  public void setNamedTableForTransition(StaticScope sub, String trName, String nameOfTable) {
    if (sub.current()) {
      transitionTableName.put(trName, nameOfTable);
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).setNamedTableForTransition(sub, trName, nameOfTable);
    }
  }

  public void addTableWithName(StaticScope sub, String tableName, TTable table) {
    if (sub.current()) {
      tableMap.put(tableName, table);
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).addTableWithName(sub, tableName, table);
    }
  }

  public void addInitialTokenInPlace(StaticScope sub, String placeName, String token) {
    if (sub.current()) {
      tokensAdded.put(placeName, token);
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).addInitialTokenInPlace(sub, placeName, token);
    }
  }

  public void makeDeclaration(StaticScope sub, String newSubName) {
    if (sub.current()) {
      declarations.put(newSubName, selfFactory.get());
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).makeDeclaration(sub, newSubName);
    }
  }

  public void makeInstenciation(StaticScope sub, String varName, String declaredSubName) {
    if (sub.current()) {
      instances.put(varName, declaredSubName);
    } else {
      String subName = sub.removeFirstSub();
      declarations.get(subName).makeInstenciation(sub, varName, declaredSubName);
    }
  }

  public Map<String, String> getTokensAdded() {
    return tokensAdded;
  }

  public ArrayList<String> getPlaces() {
    return places;
  }

  public ArrayList<String> getInpPlaces() {
    return inpPlaces;
  }

  public ArrayList<String> getTransitions() {
    return transitions;
  }

  public ArrayList<String> getOutTransitions() {
    return outTransitions;
  }

  public ArrayList<NodeRef[]> getUnweigthedArc() {
    return unweigthedArc;
  }


  public Map<String, Integer> getDelayMap() {
    return delayMap;
  }

  public Map<String, String> getTransitionTableName() {
    return transitionTableName;
  }

  public Map<String, TTable> getTableMap() {
    return tableMap;
  }

  public Map<String, SELF> getDeclarations() {
    return declarations.entrySet().stream().map(en -> new AbstractMap.SimpleEntry<>(en.getKey(), (SELF) en.getValue()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  public Map<String, String> getInstances() {
    return instances;
  }

}
