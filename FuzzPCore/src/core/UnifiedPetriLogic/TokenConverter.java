package core.UnifiedPetriLogic;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;

public class TokenConverter {

  FuzzyDriver myDriver;

  public static TokenConverter defaultConverter() {
    return new TokenConverter(1.0);
  }

  public TokenConverter(Double scale) {
    myDriver = FuzzyDriver.createDriverFromMinMax(-1.0 * scale, scale);
  }

  public FuzzyToken conver(UnifiedToken utk) {
    if (utk.isPhi()) {
      return new FuzzyToken();
    }
    return myDriver.fuzzifie(utk.getValue());
  }

  public UnifiedToken convert(FuzzyToken tk) {
    if (tk.isPhi()) {
      return new UnifiedToken();
    }
    return new UnifiedToken(myDriver.defuzzify(tk));
  }

}
