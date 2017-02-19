package core.common.recoder.fullrecorderevents;

import java.util.function.Function;

import core.common.recoder.FullRecordable;

public class TokenFromPlaceToTransition<TokenType extends FullRecordable<TokenType>>
    extends AbstarctTokenMovment<TokenType>
{
  public static final String starterString = "-->";

  public TokenFromPlaceToTransition(int place, int transition, TokenType myClone) {
    super(place, transition, myClone);
  }

  public TokenFromPlaceToTransition(Function<String, TokenType> tokenFactory) {
    super(tokenFactory);
  }

  @Override
  String starterString() {
    return starterString;
  }

}
