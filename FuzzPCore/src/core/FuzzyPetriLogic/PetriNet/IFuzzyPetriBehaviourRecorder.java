package core.FuzzyPetriLogic.PetriNet;

import java.util.List;

import core.FuzzyPetriLogic.FuzzyToken;

public interface IFuzzyPetriBehaviourRecorder {

  default void fuzzyTokenTakenOutFromPlaceToTransition(int place, int transition, FuzzyToken token) {
  };

  default void fuzzyTokenPuttedInPlaceFromTransition(int place, int transition, FuzzyToken token) {
  };

  default void tickFinished(List<Integer> delayStateOfTransition,
      List<FuzzyToken> placeState) {
  };

  default void ouputTransitionFired(int trId, FuzzyToken tk) {
  };

  default void inputPuttedInPlace(int placeId, FuzzyToken tk) {
  };

  default void transitionFiredStarted(int transition) {
  };

  default void transitionFiredEnded(int transition) {
  };

  default void reset() {
  };

  public static IFuzzyPetriBehaviourRecorder empty() {
    // In order to not to add an empty class and dependecy to the subpackage
    return new IFuzzyPetriBehaviourRecorder() {
    };
  }

}
