package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

import core.FuzzyPetriLogic.FuzzyToken;

public class OuputTransitionFired implements IFullRecorderEvent {

  private static final String DELIMITER = "@";

  private static final String STARTER = "---";

  Integer transitionId;
  FuzzyToken token;

  public OuputTransitionFired() {
    transitionId = null;
    token = null;
  }

  public OuputTransitionFired(Integer trId, FuzzyToken tk) {
    transitionId = trId;
    token = tk;
  }

  @Override
  public String makeString() {
    return STARTER + transitionId.toString() + DELIMITER + token.shortString();
  }

  @Override
  public void buildFromString(String str) {
    String[] splittted = str.replace(STARTER, "").split(DELIMITER);
    transitionId = Integer.parseInt(splittted[0]);
    token = FuzzyToken.buildFromString(splittted[1]);
  }

  @Override
  public boolean isMyString(String str) {
    return str.startsWith(STARTER);
  }
}
