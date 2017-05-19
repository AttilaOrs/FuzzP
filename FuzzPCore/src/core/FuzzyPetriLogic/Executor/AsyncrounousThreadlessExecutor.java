package core.FuzzyPetriLogic.Executor;

import java.util.Map;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ReadableFuzzyPetriNet;

public class AsyncrounousThreadlessExecutor extends AbstractFuzzyExecutor {

  private boolean initalTick = true;

  public AsyncrounousThreadlessExecutor(ReadableFuzzyPetriNet net) {
		super(net);
	}

  public AsyncrounousThreadlessExecutor(ReadableFuzzyPetriNet net, boolean checkEnabled) {
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
