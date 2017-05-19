package core.UnifiedPetriLogic.executor;

import java.util.Map;

import core.UnifiedPetriLogic.ReadableUnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;

public class AsyncrounousThreadlessUnifiedPetriExecutor extends UnifiedAbstactExecutor {

  private boolean initalTick = true;

  public AsyncrounousThreadlessUnifiedPetriExecutor(UnifiedPetriNet net) {
    super(net);
  }

  public AsyncrounousThreadlessUnifiedPetriExecutor(ReadableUnifiedPetriNet net, boolean checkEnabled) {
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

  public void putTokenInInputPlace(Map<Integer, UnifiedToken> token) {
    setInputPlacesWithToken(token);
    executeFirableTransitions();
  }

}
