package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

import core.FuzzyPetriLogic.FuzzyToken;

public class TokenFromPlaceToTransition extends AbstarctTokenMovment {
  public static final String starterString = "-->";

  public TokenFromPlaceToTransition(int place, int transition, FuzzyToken myClone) {
    super(place, transition, myClone);
  }

  public TokenFromPlaceToTransition() {
    super();
  }

  @Override
  String starterString() {
    return starterString;
  }

}
