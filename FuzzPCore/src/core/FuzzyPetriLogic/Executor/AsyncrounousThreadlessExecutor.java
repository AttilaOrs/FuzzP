package core.FuzzyPetriLogic.Executor;

import java.util.Map;

import core.FuzzyPetriLogic.ExecutableFuzzyPetriNet;
import core.FuzzyPetriLogic.FuzzyToken;

public class AsyncrounousThreadlessExecutor extends AbstractExecutor {

	private boolean initalTick = true;;

	public AsyncrounousThreadlessExecutor(ExecutableFuzzyPetriNet net) {
		super(net);
	}

	public AsyncrounousThreadlessExecutor(ExecutableFuzzyPetriNet net, boolean checkEnabled) {
		super(net, checkEnabled);
	}

	public void startNewTick() {
		if (!initalTick) {
			recorder.tickFinished(delayStateOfTransitions, stateOfPlaces);
		} else {
			initalTick = false;
		}
		updateDelayStateOfTransitionsAllreadyInFire();
		executeFirableTransitions();
	}

	public void putTokenInInputPlace(Map<Integer, FuzzyToken> token) {
		setInputPlacesWithToken(token);
		executeFirableTransitions();
	}

}
