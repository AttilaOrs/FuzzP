package core.common.recoder;

import java.util.Arrays;
import java.util.List;

public class MultiRecorder<TokenType> implements IGeneralPetriBehavoiurRecorder<TokenType> {

  List<IGeneralPetriBehavoiurRecorder<TokenType>> recoredrs;

  public MultiRecorder(IGeneralPetriBehavoiurRecorder<TokenType> recorder) {
    this(Arrays.asList(recorder));
  }

  public MultiRecorder(List<IGeneralPetriBehavoiurRecorder<TokenType>> recorders) {
    this.recoredrs = recorders;
  }

  @Override
  public void tokenTakenOutFromPlaceToTransition(int place, int transition, TokenType token) {
    recoredrs.forEach(r -> r.tokenTakenOutFromPlaceToTransition(place, transition, token));
  };

  @Override
  public void tokenPuttedInPlaceFromTransition(int place, int transition, TokenType token) {
    recoredrs.forEach(r -> r.tokenPuttedInPlaceFromTransition(place, transition, token));
  };

  @Override
  public void tickFinished(List<Integer> delayStateOfTransition,
      List<TokenType> placeState) {
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
  public void ouputTransitionFired(int trId, TokenType tk) {
    recoredrs.forEach(r -> r.ouputTransitionFired(trId, tk));
  }

  @Override
  public void inputPuttedInPlace(int placeId, TokenType tk) {
    recoredrs.forEach(r -> r.inputPuttedInPlace(placeId, tk));
  }
}
