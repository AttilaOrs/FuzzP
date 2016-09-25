package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

import core.FuzzyPetriLogic.FuzzyToken;

public abstract class AbstarctTokenMovment implements IFullRecorderEvent {

  public int place;
  public int transition;
  public FuzzyToken token;

  public AbstarctTokenMovment() {
    this.place = -1;
    this.transition = -1;
    this.token = null;
  }

  public AbstarctTokenMovment(int place, int transition, FuzzyToken token) {
    this.place = place;
    this.transition = transition;
    this.token = token;
  }

  @Override
  public String makeString() {
    return starterString() + ";" + place + ";" + transition + ";" + token.shortString();
  }

  abstract String starterString();

  @Override
  public void buildFromString(String str) {
    String[] splitted = str.replaceAll(starterString(), "").split(";");
    this.place = Integer.parseInt(splitted[1].trim());
    this.transition = Integer.parseInt(splitted[2].trim());
    this.token = FuzzyToken.buildFromString(splitted[3].trim());

  }

  @Override
  public boolean isMyString(String str) {
    return str.startsWith(starterString());
  }
}
