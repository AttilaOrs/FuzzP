package core.common.recoder.fullrecorderevents;

import java.util.function.Function;

import core.common.recoder.FullRecordable;

public class OuputTransitionFired<TokenType extends FullRecordable<TokenType>> implements IFullRecorderEvent {

  private static final String DELIMITER = "@";

  private static final String STARTER = "---";

  Integer transitionId;
  TokenType token;

  private Function<String, TokenType> myFactory;

  public OuputTransitionFired(Function<String, TokenType> tokenFactory) {
    transitionId = null;
    token = null;
    this.myFactory = tokenFactory;
  }

  public OuputTransitionFired(Integer trId, TokenType tk) {
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
    token = myFactory.apply(splittted[1]);
  }

  @Override
  public boolean isMyString(String str) {
    return str.startsWith(STARTER);
  }
}
