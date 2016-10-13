package FuzzyPLang.NetBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.Tables.OneXOneTable;

public class HiearchicalIntermediateNet {
    private ArrayList<String> places;
    private ArrayList<String> inpPlaces;
    private ArrayList<String> transitions;
    private ArrayList<String> outTransitions;
    private ArrayList<NodeRef[]> unweigthedArc;
    private ArrayList<NodeRef[]> weigthedArcs;
    private ArrayList<Double> weigthsForArc;
    private Map<String, Integer> delayMap;
    private Map<String, String> transitionTableName;
    private Map<String, ITable> tableMap;
    private Map<String, HiearchicalIntermediateNet> declarations;
    private Map<String, String> instances;
    private Map<String, String> tokensAdded;

    
    public HiearchicalIntermediateNet(){
        places = new ArrayList<>();
        inpPlaces = new ArrayList<>();
        transitions = new ArrayList<>();
        outTransitions = new ArrayList<>();
        unweigthedArc = new ArrayList<>();
        weigthedArcs = new ArrayList<>();
        weigthsForArc = new ArrayList<>();
        delayMap = new HashMap<>();
        tableMap = new HashMap<>();
        transitionTableName = new HashMap<>();
        tokensAdded = new HashMap<>();
        declarations = new HashMap<>();
        instances = new HashMap<>();
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

    public void addInpPlace(StaticScope sub, String str) {
        if (sub.current()) {
            if (!inpPlaces.contains(str)) {
                inpPlaces.add(str);
            }
        } else {
            String subName = sub.removeFirstSub();
            declarations.get(subName).addInpPlace(sub, str);
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

    public void addArc(StaticScope sub, NodeRef firsNodeName, NodeRef secondNodeName, double weigth) {
        if (sub.current()) {
            weigthedArcs.add(new NodeRef[] { firsNodeName, secondNodeName });
            weigthsForArc.add(weigth);
        } else {
            String subName = sub.removeFirstSub();
            declarations.get(subName).addArc(sub, firsNodeName, secondNodeName, weigth);
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

    public void addTableWithName(StaticScope sub, String tableName, ITable table) {
        if (sub.current()) {
            tableMap.put(tableName, table);
        } else {
            String subName = sub.removeFirstSub();
            declarations.get(subName).addTableWithName(sub, tableName, table);
        }
    }

    public void addInitialTokenInPlace(StaticScope sub, String placeName, String token) {
        if (sub.current()) {
            tokensAdded.put(placeName, token );
        } else {
            String subName = sub.removeFirstSub();
            declarations.get(subName).addInitialTokenInPlace(sub, placeName, token);
        }
    }

    public void makeDeclaration(StaticScope sub, String newSubName) {
        if (sub.current()) {
            declarations.put(newSubName, new HiearchicalIntermediateNet());
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

	public ArrayList<NodeRef[]> getWeigthedArcs() {
		return weigthedArcs;
	}

	public ArrayList<Double> getWeigthsForArc() {
		return weigthsForArc;
	}

	public Map<String, Integer> getDelayMap() {
		return delayMap;
	}

	public Map<String, String> getTransitionTableName() {
		return transitionTableName;
	}

	public Map<String, ITable> getTableMap() {
		return tableMap;
	}

	public Map<String, HiearchicalIntermediateNet> getDeclarations() {
		return declarations;
	}

	public Map<String, String> getInstances() {
		return instances;
	}

}
