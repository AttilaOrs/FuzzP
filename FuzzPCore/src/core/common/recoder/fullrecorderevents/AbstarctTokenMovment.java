package core.common.recoder.fullrecorderevents;

import java.util.function.Function;

import core.common.recoder.FullRecordable;

public abstract class AbstarctTokenMovment<TokenType extends FullRecordable<TokenType>> implements IFullRecorderEvent {

  public int place;
  public int transition;
  public TokenType token;

  private Function<String, TokenType> myFactory;

  public AbstarctTokenMovment(Function<String, TokenType> tokenFactory) {
    this.place = -1;
    this.transition = -1;
    this.token = null;
    myFactory = tokenFactory;
  }

  public AbstarctTokenMovment(int place, int transition, TokenType token) {
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
    this.token = myFactory.apply(splitted[3].trim());

  }

  @Override
  public boolean isMyString(String str) {
    return str.startsWith(starterString());
  }
}
