package core.common.recoder.fullrecorderevents;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.common.recoder.FullRecordable;

public class TickFinsihed<TokenType extends FullRecordable<TokenType>> implements IFullRecorderEvent {
  private static final String INNER_DELIMITER = ";";
  private static final String OUTER_DELIMITER = "@";

  private static final String STARTER = "### ";

  public List<TokenType> placeState;
  public List<Integer> delayState;
  private Function<String, TokenType> myFactory;

  public TickFinsihed(List<TokenType> placeState,
      List<Integer> delayState) {
    this.placeState = placeState;
    this.delayState = delayState;
  }

  public TickFinsihed(Function<String, TokenType> tokenFactory) {
    this.placeState = null;
    this.delayState = null;
    this.myFactory = tokenFactory;
  }

  @Override
  public String makeString() {
    return STARTER + makeStr(placeState)
        + OUTER_DELIMITER + String.valueOf(delayState);
  }

  private String makeStr(List<TokenType> ff) {
    return ff.stream().map(ft -> ft.shortString()).collect(Collectors.joining(INNER_DELIMITER));
  }

  private List<TokenType> fromString(String from) {
    return Arrays.stream(from.split(INNER_DELIMITER)).map(st -> myFactory.apply(st))
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

  public List<TokenType> getPlaceState() {
    return placeState;
  }

  public List<Integer> getDelayState() {
    return delayState;
  }
}
