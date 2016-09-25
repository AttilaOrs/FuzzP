package core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import core.FuzzyPetriLogic.FuzzyToken;

public class TickFinsihed implements IFullRecorderEvent {
  private static final String INNER_DELIMITER = ";";
  private static final String OUTER_DELIMITER = "@";

  private static final String STARTER = "### ";

  public List<FuzzyToken> placeState;
  public List<Integer> delayState;

  public TickFinsihed(List<FuzzyToken> placeState,
      List<Integer> delayState) {
    this.placeState = placeState;
    this.delayState = delayState;
  }

  public TickFinsihed() {
    this.placeState = null;
    this.delayState = null;
  }

  @Override
  public String makeString() {
    return STARTER + makeStr(placeState)
        + OUTER_DELIMITER + String.valueOf(delayState);
  }

  private String makeStr(List<FuzzyToken> ff) {
    return ff.stream().map(ft -> ft.shortString()).collect(Collectors.joining(INNER_DELIMITER));
  }

  private List<FuzzyToken> fromString(String from) {
    return Arrays.stream(from.split(INNER_DELIMITER)).map(st -> FuzzyToken.buildFromString(st))
        .collect(Collectors.toList());
  }

  @Override
  public void buildFromString(String str) {
    String[] splitted = str.replace(STARTER, "").split(OUTER_DELIMITER);
    this.placeState = fromString(splitted[0]);
    this.delayState = Arrays.stream(splitted[1].replace("[", "").replace("]", "").split(","))
        .map(st -> Integer.parseInt(st.trim())).collect(Collectors.toList());

  }

  @Override
  public boolean isMyString(String str) {
    return str.startsWith(STARTER);
  }

  public List<FuzzyToken> getPlaceState() {
    return placeState;
  }

  public List<Integer> getDelayState() {
    return delayState;
  }
}
