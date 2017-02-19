package core.common.recoder.fullrecorderevents;

import java.util.function.Function;

import core.common.recoder.FullRecordable;

public class TokenFromTransitionToPlace<TokenType extends FullRecordable<TokenType>>
    extends AbstarctTokenMovment<TokenType> {
  public static final String starterString = "<--";

  public TokenFromTransitionToPlace(int place, int transition, TokenType myClone) {
    super(place, transition, myClone);
  }

  public TokenFromTransitionToPlace(Function<String, TokenType> tokenFactory) {
    super(tokenFactory);
  }

  @Override
  String starterString() {
    return starterString;
  }

}
