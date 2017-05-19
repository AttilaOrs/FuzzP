package core.common.tokencache;

import java.util.Comparator;

class TokenCacheEntry<TokenType> {


  private int timeCntr;
  private final TokenType[] out;
  private final TokenCacheKey<TokenType> key;
  private int allCalled;


  public TokenCacheEntry(final int timeCntr, TokenType[] out, TokenCacheKey<TokenType> key) {
    this.timeCntr = timeCntr;
    allCalled = 1;
    this.out = out;
    this.key = key;
  }

  public TokenType[] getContent(int callCntr) {
    this.timeCntr = callCntr;
    allCalled++;
    return out;
  }

  public TokenCacheKey<TokenType> getKey() {
    return key;
  }



  static class DefaultComparator implements Comparator<TokenCacheEntry<?>> {

    @Override
    public int compare(TokenCacheEntry<?> o1, TokenCacheEntry<?> o2) {
      if (o1.allCalled == o2.allCalled) {
        return o1.timeCntr - o2.timeCntr;
      }
      return o1.allCalled - o2.allCalled;
    }

  }

  @Override
  public String toString() {
    return "TokenCacheEntry :relaiveTime " + this.allCalled + " used: " + this.allCalled;
  }

  public int getAllCalled() {
    return allCalled;
  }



}
