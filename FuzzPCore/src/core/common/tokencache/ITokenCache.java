package core.common.tokencache;

import java.util.Optional;

public interface ITokenCache<TokenType> {

  public Optional<TokenType[]> getCached(TokenType[] inp);

  public void addRezultToCache(TokenType[] inp, TokenType[] out);

}
