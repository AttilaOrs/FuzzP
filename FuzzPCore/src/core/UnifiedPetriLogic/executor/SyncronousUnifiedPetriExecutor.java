package core.UnifiedPetriLogic.executor;

import java.util.Map;

import core.UnifiedPetriLogic.ReadableUnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;

public class SyncronousUnifiedPetriExecutor extends UnifiedAbstactExecutor {

  public SyncronousUnifiedPetriExecutor(ReadableUnifiedPetriNet net) {
    super(net);
  }

  public SyncronousUnifiedPetriExecutor(ReadableUnifiedPetriNet net, boolean enableChecking) {
    super(net, enableChecking);
  }


  public void runTick(Map<Integer, UnifiedToken> inputs) {
    setInputPlacesWithToken(inputs);
    updateDelayStateOfTransitionsAllreadyInFire();
    executeFirableTransitions();
    recorder.tickFinished(delayStateOfTransitions, stateOfPlaces);
  }
}
