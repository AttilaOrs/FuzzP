package core.common.recoder.fullrecorderevents;

import java.util.function.Function;

import core.common.recoder.FullRecordable;

public class InputPuttedInPlace<TokenType extends FullRecordable<TokenType>> implements IFullRecorderEvent {

  private static final String DELIMITER = "@";

  private static final String STARTER = "+++";

  public Integer placeId;
  public TokenType token;

  private Function<String, TokenType> myFactory;

  public InputPuttedInPlace(Function<String, TokenType> tokenFactory) {
    placeId = null;
    token = null;
    this.myFactory = tokenFactory;
  }

  public InputPuttedInPlace(Integer plId, TokenType tk) {
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
    token = myFactory.apply(splittted[1]);
  }

  @Override
  public boolean isMyString(String str) {
    return str.startsWith(STARTER);
  }

}
