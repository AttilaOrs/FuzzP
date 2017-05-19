package core.FuzzyPetriLogic.Executor;

import java.util.Map;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ReadableFuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class SynchronousFuzzyPetriExecutor extends AbstractFuzzyExecutor {

  public SynchronousFuzzyPetriExecutor(FuzzyPetriNet net) {
		super(net);
	}

  public SynchronousFuzzyPetriExecutor(ReadableFuzzyPetriNet net, boolean enablechecking) {
		super(net, enablechecking);
	}

	public void runTick(Map<Integer, FuzzyToken> inputs) {
		setInputPlacesWithToken(inputs);
		updateDelayStateOfTransitionsAllreadyInFire();
		executeFirableTransitions();
		recorder.tickFinished(delayStateOfTransitions, stateOfPlaces);
	}
}
