package core.FuzzyPetriLogic.PetriNet.Recorders;

import java.util.Arrays;
import java.util.List;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.IFuzzyPetriBehaviourRecorder;

public class MultiRecorder implements IFuzzyPetriBehaviourRecorder {

  List<IFuzzyPetriBehaviourRecorder> recoredrs;

  public MultiRecorder(IFuzzyPetriBehaviourRecorder recorder) {
    this(Arrays.asList(recorder));
  }

  public MultiRecorder(List<IFuzzyPetriBehaviourRecorder> recorders) {
    this.recoredrs = recorders;
  }

  @Override
  public void fuzzyTokenTakenOutFromPlaceToTransition(int place, int transition, FuzzyToken token) {
    recoredrs.forEach(r -> r.fuzzyTokenTakenOutFromPlaceToTransition(place, transition, token));
  };

  @Override
  public void fuzzyTokenPuttedInPlaceFromTransition(int place, int transition, FuzzyToken token) {
    recoredrs.forEach(r -> r.fuzzyTokenPuttedInPlaceFromTransition(place, transition, token));
  };

  @Override
  public void tickFinished(List<Integer> delayStateOfTransition,
      List<FuzzyToken> placeState) {
    recoredrs.forEach(r -> r.tickFinished(delayStateOfTransition, placeState));
  };

  @Override
  public void transitionFiredStarted(int transition) {
    recoredrs.forEach(r -> r.transitionFiredStarted(transition));
  };

  @Override
  public void transitionFiredEnded(int transition) {
    recoredrs.forEach(r -> r.transitionFiredEnded(transition));
  };

  @Override
  public void reset() {
    recoredrs.forEach(r -> r.reset());
  };

  @Override
  public void ouputTransitionFired(int trId, FuzzyToken tk) {
    recoredrs.forEach(r -> ouputTransitionFired(trId, tk));
  }

  @Override
  public void inputPuttedInPlace(int placeId, FuzzyToken tk) {
    recoredrs.forEach(r -> inputPuttedInPlace(placeId, tk));
  }
}
