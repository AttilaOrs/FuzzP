package core.common.recoder;

import java.util.List;

public interface IGeneralPetriBehavoiurRecorder<TokenType> {

  default void tokenTakenOutFromPlaceToTransition(int place, int transition, TokenType token) {
  };

  default void tokenPuttedInPlaceFromTransition(int place, int transition, TokenType token) {
  };

  default void tickFinished(List<Integer> delayStateOfTransition,
      List<TokenType> placeState) {
  };

  default void ouputTransitionFired(int trId, TokenType tk) {
  };

  default void inputPuttedInPlace(int placeId, TokenType tk) {
  };

  default void transitionFiredStarted(int transition) {
  };

  default void transitionFiredEnded(int transition) {
  };

  default void reset() {
  };

  public static <T> IGeneralPetriBehavoiurRecorder<T> empty() {
    // In order to not to add an empty class and dependecy to the subpackage
    return new IGeneralPetriBehavoiurRecorder<T>() {
    };
  }
}
