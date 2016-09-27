package core.FuzzyPetriLogic.Executor;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.FuzzyPetriLogic.ExecutableFuzzyPetriNet;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Fuzzifiers.TriangleFuzzyfier;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNetChecker;
import core.FuzzyPetriLogic.PetriNet.IFuzzyPetriBehaviourRecorder;

public class AbstractExecutor {

	private TriangleFuzzyfier defultFuzzificator = TriangleFuzzyfier.withBorderVales(-1.0, -0.5, 0.0, 0.5, 1.0);

	private static final int MAX_LOOP_STEP = 40;
	private ExecutableFuzzyPetriNet myNet;
	protected List<FuzzyToken> stateOfPlaces;
	protected List<Integer> delayStateOfTransitions;
	protected List<FuzzyToken[]> valueHoldInTransitions;
	protected List<Integer> orderOfTransition;

	protected IFuzzyPetriBehaviourRecorder recorder;

	public AbstractExecutor(ExecutableFuzzyPetriNet net) {
		this(net, true);
	}

	public AbstractExecutor(ExecutableFuzzyPetriNet net, boolean enablechecking) {
		this.myNet = net;
		intilazeOrder();
		resetSimulator();
		recorder = IFuzzyPetriBehaviourRecorder.empty();
		if (enablechecking) {
			FuzzyPetriNetChecker ch = new FuzzyPetriNetChecker();
			if (!ch.checkPetriNet(net)) {
				throw new RuntimeException(ch.getErrorMsg());
			}
		}

	}

	public ExecutableFuzzyPetriNet getNet() {
		return myNet;
	}

	public void setRecorder(IFuzzyPetriBehaviourRecorder rec) {
		this.recorder = rec;
	}

	public void resetSimulator() {

		stateOfPlaces = IntStream.range(0, myNet.getNrOfPlaces())
				.mapToObj(plId -> myNet.getInitialMarkingForPlace(plId).myClone()).collect(toList());
		delayStateOfTransitions = Stream.generate(() -> 0).limit(myNet.getNrOfTransition()).collect(toList());
		valueHoldInTransitions = Stream.generate(() -> new FuzzyToken[] {}).limit(myNet.getNrOfTransition())
				.collect(toList());

	}

	private void intilazeOrder() {
		orderOfTransition = new ArrayList<>();
		List<Integer> trWithInputs = IntStream.range(0, myNet.getNrOfTransition())
				.filter(trID -> myNet.getPlacesNeededForTransition(trID).stream()
						.filter(plId -> myNet.isInputPlace(plId)).findAny().isPresent())
				.boxed().collect(toList());

		orderOfTransition.addAll(trWithInputs);

		List<Integer> trWithOutputs = myNet.getOutputTransitions().stream()
				.filter(trId -> !orderOfTransition.contains(trId)).collect(toList());
		orderOfTransition.addAll(trWithOutputs);

		List<Integer> trWithoutDelays = IntStream.range(0, myNet.getNrOfTransition())
				.filter(trId -> !orderOfTransition.contains(trId))
				.filter(trId -> myNet.getDelayForTransition(trId) == 0).boxed().collect(toList());
		orderOfTransition.addAll(trWithoutDelays);

		List<Integer> trWithDelays = IntStream.range(0, myNet.getNrOfTransition())
				.filter(trId -> !orderOfTransition.contains(trId))
				.filter(trId -> myNet.getDelayForTransition(trId) != 0).boxed().sorted((trId, trId2) -> Integer
						.compare(myNet.getDelayForTransition(trId), myNet.getDelayForTransition(trId2)))
				.collect(toList());
		orderOfTransition.addAll(trWithDelays);

	}

	protected void executeFirableTransitions() {
		int loopCntr = 0; // overcome infinite loop in the system
		boolean happendSomthing = true;
		while (happendSomthing && loopCntr < MAX_LOOP_STEP) {
			loopCntr++;
			for (int trListIndex = 0; trListIndex < orderOfTransition.size(); trListIndex++) {
				int currentTrans = orderOfTransition.get(trListIndex);
				if (fireable(currentTrans)) {
					happendSomthing = true;
					startFire(currentTrans);
					break;
				}
			}
		}
	}

	protected void setInputPlacesWithToken(Map<Integer, FuzzyToken> inputs) {
		if (inputs != null) {
			for (Integer inpPlaceId : inputs.keySet()) {
				if (!myNet.isInputPlace(inpPlaceId)) {
					throw new RuntimeException("Non input place: P" + inpPlaceId + " cannot be setted runtime");
				}
				FuzzyToken current = stateOfPlaces.get(inpPlaceId);
				FuzzyToken newToken = current.unite(inputs.get(inpPlaceId));
				recorder.inputPuttedInPlace(inpPlaceId, newToken);
				stateOfPlaces.set(inpPlaceId, newToken);
			}
		}

	}

	private void startFire(int trId) {

		ITable tt = myNet.getTableForTransition(trId);
		FuzzyToken[] inpsForTrs = getInpsFromTr(trId, true);
		FuzzyToken[] res = tt.execute(inpsForTrs);
		valueHoldInTransitions.set(trId, res);
		recorder.transitionFiredStarted(trId);
		if (myNet.getDelayForTransition(trId) != 0) {
			delayStateOfTransitions.set(trId, myNet.getDelayForTransition(trId));
		} else {
			finsihFire(trId);
		}

	}

	private FuzzyToken[] getInpsFromTr(int trId, boolean replaceWithPhi) {
		List<Integer> places = myNet.getPlacesNeededForTransition(trId);
		FuzzyToken[] toRet = new FuzzyToken[places.size()];
		for (int i = 0; i < places.size(); i++) {
			int placeId = places.get(i);
			FuzzyToken currentTokenInPlace = stateOfPlaces.get(placeId);

			if (!currentTokenInPlace.isPhi()) {
				Double val = (defultFuzzificator.defuzzify(currentTokenInPlace)) * myNet.getWeigth(placeId, trId);
				FuzzyToken transformed = defultFuzzificator.fuzzifie(val);
				toRet[i] = transformed;
			} else {
				toRet[i] = new FuzzyToken();
			}
			if (replaceWithPhi) {
				stateOfPlaces.set(placeId, new FuzzyToken());
				recorder.fuzzyTokenTakenOutFromPlaceToTransition(placeId, trId, currentTokenInPlace);
			}
		}
		return toRet;

	}

	private boolean fireable(int trId) {
		if (delayStateOfTransitions.get(trId) != 0) {
			return false;
		}
		FuzzyToken[] inps = getInpsFromTr(trId, false);
		return myNet.getTableForTransition(trId).executable(inps);
	}

	protected void updateDelayStateOfTransitionsAllreadyInFire() {

		for (int trId = 0; trId < myNet.getNrOfTransition(); trId++) {
			Integer state = delayStateOfTransitions.get(trId);
			if (state > 0) {
				if (state == 1) {
					finsihFire(trId);
				}
				delayStateOfTransitions.set(trId, state - 1);
			}
		}

	}

	private void finsihFire(int trId) {

		FuzzyToken[] res = valueHoldInTransitions.get(trId);
		valueHoldInTransitions.set(trId, new FuzzyToken[] {});
		int nrOfNeededOut = myNet.getOutputPlacesForTransition(trId).size() + ((myNet.isOuputTransition(trId)) ? 1 : 0);
		if (nrOfNeededOut != res.length) {
			throw new RuntimeException("Ucorrect Petri Structure at trastition " + trId + ", ouputs needed"
					+ nrOfNeededOut + " and exists " + res.length);
		}

		int tokenIndex = 0;
		if (myNet.isOuputTransition(trId)) {
			myNet.getActionsForOuputTransition(trId).forEach(c -> c.accept(res[0].myClone()));
			recorder.ouputTransitionFired(trId, res[0]);
			tokenIndex = 1;
		}
		for (int plID : myNet.getOutputPlacesForTransition(trId)) {
			putInPlace(plID, res[tokenIndex++], trId);
		}

		recorder.transitionFiredEnded(trId);

	}

	private void putInPlace(int placeNr, FuzzyToken fuzzyToken, int trNr) {
		FuzzyToken united = stateOfPlaces.get(placeNr).unite(fuzzyToken);
		stateOfPlaces.set(placeNr, united);
		recorder.fuzzyTokenPuttedInPlaceFromTransition(placeNr, trNr, fuzzyToken);
	}

}
