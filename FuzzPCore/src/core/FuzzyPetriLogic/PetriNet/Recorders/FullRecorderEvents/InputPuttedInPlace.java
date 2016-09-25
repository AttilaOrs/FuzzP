package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

import core.FuzzyPetriLogic.FuzzyToken;

public class InputPuttedInPlace implements IFullRecorderEvent {

  private static final String DELIMITER = "@";

  private static final String STARTER = "+++";

  Integer placeId;
  FuzzyToken token;

  public InputPuttedInPlace() {
    placeId = null;
    token = null;
  }

  public InputPuttedInPlace(Integer plId, FuzzyToken tk) {
    placeId = plId;
    token = tk;
  }

  @Override
  public String makeString() {
    return STARTER + placeId.toString() + DELIMITER + token.shortString();
  }

  @Override
  public void buildFromString(String str) {
    String[] splittted = str.replace(STARTER, "").split(DELIMITER);
    placeId = Integer.parseInt(splittted[0]);
    token = FuzzyToken.buildFromString(splittted[1]);
  }

  @Override
  public boolean isMyString(String str) {
    return str.startsWith(STARTER);
  }

}
