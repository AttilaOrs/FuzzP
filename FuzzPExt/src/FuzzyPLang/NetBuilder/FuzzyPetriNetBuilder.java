package FuzzyPLang.NetBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNetChecker;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class FuzzyPetriNetBuilder implements FuzzyPLangNetBuilder {
	private ArrayList<String[]> tokensAdded;
	private ArrayList<String> places;
	private ArrayList<String> inpPlaces;
	private ArrayList<String> transitions;
	private ArrayList<String> outTransitions;
	private ArrayList<String[]> unweigthedArc;
	private ArrayList<String[]> weigthedArcs;
	private ArrayList<Double> weigthsForArc;
	private Map<String, Integer> delayMap;
	private Map<String, String> transitionTableName;
	private Map<String, ITable> tableMap;
	private boolean stict;
	private boolean correct;
	private StringBuilder errors;
	private TransitionPlaceNameStore store;

	public FuzzyPetriNetBuilder() {
		this(false);
	}

	public FuzzyPetriNetBuilder(boolean strictBuiding) {
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
		tokensAdded = new ArrayList<>();
		this.stict = strictBuiding;
	}

	@Override
	public void addPlace(String str) {
		if (!places.contains(str)) {
			places.add(str);
		}
	}

	@Override
	public void addInpPlace(String str) {
		if (!inpPlaces.contains(str)) {
			inpPlaces.add(str);
		}
	}

	@Override
	public void addTransition(String trName) {
		if (!transitions.contains(trName)) {
			transitions.add(trName);
		}
	}

	@Override
	public void addOutTransition(String trName) {
		if (!outTransitions.contains(trName)) {
			outTransitions.add(trName);
		}
	}

	@Override
	public void addArc(String firsNodeName, String secondNodeName, double weigth) {
		weigthedArcs.add(new String[] { firsNodeName, secondNodeName });
		weigthsForArc.add(weigth);
	}

	@Override
	public void addArc(String firsNodeName, String secondNodeName) {
		unweigthedArc.add(new String[] { firsNodeName, secondNodeName });
	}

	@Override
	public void setDelayForTransition(String trName, int delay) {
		delayMap.put(trName, delay);

	}

	@Override
	public void setNamedTableForTransition(String trName, String nameOfTable) {
		transitionTableName.put(trName, nameOfTable);
	}

	@Override
	public void addTableWithName(String nameOfTable, ITable table) {
		tableMap.put(nameOfTable, table);
	}

	@Override
	public void addInitialTokenInPlace(String prevNode, String token) {
		tokensAdded.add(new String[] { prevNode, token });
	}

	public TransitionPlaceNameStore getNameStore() {
		return store;
	}

	public String getErrors() {
		return errors.toString();
	}

	public FuzzyPetriNet build() {
		store = new TransitionPlaceNameStore();
		errors = new StringBuilder();
		correct = true;
		FuzzyPetriNet petriNet = new FuzzyPetriNet();
    for (String placeName : places) {
      int placeId = petriNet.addPlace();
      store.addPlaceName(placeId, placeName);
    }
    for (String transitionName : transitions) {
      ITable table = getTableForTransition(transitionName);
      int delay = getDelay(transitionName);
      int trId = petriNet.addTransition(delay, table);
      store.addTransitionName(trId, transitionName);
    }
		for (String inpPlaceName : inpPlaces) {
			int placeId = petriNet.addInputPlace();
			store.addPlaceName(placeId, inpPlaceName);
		}

		for (String outTransitionName : outTransitions) {
			OneXOneTable table = getTableForOutTransition(outTransitionName);
			int trId = petriNet.addOuputTransition(table);
			store.addTransitionName(trId, outTransitionName);
		}
		for (String[] arc : unweigthedArc) {
			if (store.isTransitionName(arc[0]) && store.isPlaceName(arc[1])) {
				petriNet.addArcFromTransitionToPlace(store.getTransitionId(arc[0]), store.getPlaceId(arc[1]));
			} else if (store.isPlaceName(arc[0]) && store.isTransitionName(arc[1])) {
				petriNet.addArcFromPlaceToTransition(store.getPlaceId(arc[0]), store.getTransitionId(arc[1]), 1.0);
			} else {
				incorrectNet("Cannot add arc between `" + arc[0] + "` and `" + arc[1] + "`\n");
			}
		}
		int cntr = 0;
		for (String[] arc : weigthedArcs) {
			if ((store.isPlaceName(arc[0]) && store.isTransitionName(arc[1]))) {
				petriNet.addArcFromPlaceToTransition(store.getPlaceId(arc[0]), store.getTransitionId(arc[1]),
						weigthsForArc.get(cntr));
			} else {
				incorrectNet("Cannot add WEIGHTED  arc between `" + arc[0] + "` and `" + arc[1] + "`\n"
						+ " weigthed arc are between places and transitions\n");
			}
		}

		for (String tokenAdd[] : tokensAdded) {
			if (!store.isPlaceName(tokenAdd[0])) {
				incorrectNet("Cannot set token `" + tokenAdd[1] + "` to place: `" + tokenAdd[0] + "`\n"
						+ " because place is not defined \n");
			} else {
				petriNet.setInitialMarkingForPlace(store.getPlaceId(tokenAdd[0]),
						FuzzyToken.buildFromString(tokenAdd[1]));
			}
		}
		FuzzyPetriNetChecker checker = new FuzzyPetriNetChecker();
		boolean sucees = checker.checkPetriNet(petriNet);
		if (!sucees) {
			errors.append(checker.getErrorMsg());
			correct = false;
		}
		if (stict && !correct) {
			throw new RuntimeException(errors.toString());
		}

		return petriNet;
	}

	protected void incorrectNet(String err) {
		correct = false;
		errors.append(err);
	}

	private int getDelay(String transitionName) {
		if (!delayMap.containsKey(transitionName)) {
			return 0;
		}
		return delayMap.get(transitionName);
	}

	private ITable getTableForTransition(String transitionName) {
		ITable table = getNamedTableForTranition(transitionName);
		if (table == null) {
			long inpArcs = weigthedArcs.stream().filter(arc -> arc[0].equals(transitionName)).count();
			inpArcs += unweigthedArc.stream().filter(arc -> arc[0].equals(transitionName)).count();
			long outArcs = weigthedArcs.stream().filter(arc -> arc[1].equals(transitionName)).count();
			outArcs += unweigthedArc.stream().filter(arc -> arc[1].equals(transitionName)).count();
			if (inpArcs > 2 && inpArcs < 0) {
				incorrectNet("transition with more than 2 or less than 0 input arc are not suported: " + transitionName
						+ "\n");
				return null;
			}
			if (outArcs > 2 && outArcs < 0) {
				incorrectNet("transition with more than 2 or less than 0 out arc are not suported: " + transitionName
						+ "\n");
				return null;
			}
			if (inpArcs == 1) {
				if (outArcs == 1) {
					table = OneXOneTable.defaultTable();
				} else {
					table = TwoXOneTable.defaultTable();
				}
			} else {
				if (outArcs == 1) {
					table = OneXTwoTable.defaultTable();
				} else {
					table = TwoXTwoTable.defaultTable();
				}
			}
		}
		return table;
	}

	private ITable getNamedTableForTranition(String transitionName) {
		if (transitionTableName.containsKey(transitionName)) {
			String tableName = transitionTableName.get(transitionName);
			if (!tableMap.containsKey(tableName)) {
				incorrectNet(" table with name `" + tableName + "` is used for transition `" + transitionName
						+ "` but not defined \n");
				return null;
			}
			return tableMap.get(tableName);
		}
		return null;
	}

	private OneXOneTable getTableForOutTransition(String outTransitionName) {
		ITable table = getNamedTableForTranition(outTransitionName);
		if (table != null) {
			if (!(table instanceof OneXOneTable)) {
				incorrectNet(" table with name `" + transitionTableName.get(outTransitionName)
						+ "` is used for output transition `" + outTransitionName
						+ "` but not OneXOneTable. FuzzyP suport only OneXOneTables for ouput transitions \n");
				table = OneXOneTable.defaultTable();
			}
		} else {
			table = OneXOneTable.defaultTable();
		}
		return (OneXOneTable) table;
	}
}
