package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

import core.FuzzyPetriLogic.FuzzyToken;

public class TokenFromTransitionToPlace extends AbstarctTokenMovment {
  public static final String starterString = "<--";

  public TokenFromTransitionToPlace(int place, int transition, FuzzyToken myClone) {
    super(place, transition, myClone);
  }

  public TokenFromTransitionToPlace() {
    super();
  }

  @Override
  String starterString() {
    return starterString;
  }

}
