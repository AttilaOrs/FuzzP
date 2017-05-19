package core.common.tokencache;

import java.util.Arrays;

class TokenCacheKey<TokenType> {

  final TokenType[] inp;

  public TokenCacheKey(TokenType[] inp) {
    this.inp = inp;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TokenCacheKey)) {
    return false;
    }
    @SuppressWarnings("rawtypes")
    TokenCacheKey other = (TokenCacheKey) o;
    return Arrays.equals(inp, other.inp);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(inp);
  }


}
