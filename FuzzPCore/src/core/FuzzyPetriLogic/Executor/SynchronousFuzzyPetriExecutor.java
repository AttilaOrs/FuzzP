package core.FuzzyPetriLogic.Executor;

import java.util.Map;

import core.FuzzyPetriLogic.ExecutableFuzzyPetriNet;
import core.FuzzyPetriLogic.FuzzyToken;

public class SynchronousFuzzyPetriExecutor extends AbstractExecutor {

	public SynchronousFuzzyPetriExecutor(ExecutableFuzzyPetriNet net) {
		super(net);
	}

	public SynchronousFuzzyPetriExecutor(ExecutableFuzzyPetriNet net, boolean enablechecking) {
		super(net, enablechecking);
	}

	public void runTick(Map<Integer, FuzzyToken> inputs) {
		setInputPlacesWithToken(inputs);
		updateDelayStateOfTransitionsAllreadyInFire();
		executeFirableTransitions();
		recorder.tickFinished(delayStateOfTransitions, stateOfPlaces);
	}
}
