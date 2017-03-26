package core.UnifiedPetriLogic;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;

public class TokenConverter {

  FuzzyDriver myDriver;
  Double scale;

  public static TokenConverter defaultConverter() {
    return new TokenConverter(1.0);
  }

  public TokenConverter(Double scale) {
    this.scale = scale;
    if (!zeroScale()) {
      myDriver = FuzzyDriver.createDriverFromMinMax(-1.0 * scale, scale);
    }
  }

  private boolean zeroScale() {
    return scale == 0.0;
  }

  public FuzzyToken conver(UnifiedToken utk) {
    if (utk.isPhi()) {
      return new FuzzyToken();
    } else if (zeroScale()) {
      return new FuzzyToken(0.0, 0.0, 1.0, 0.0, 0.0);
    }
    return myDriver.fuzzifie(utk.getValue());
  }

  public UnifiedToken convert(FuzzyToken tk) {
    if (tk.isPhi()) {
      return new UnifiedToken();
    } else if (zeroScale()) {
      return new UnifiedToken(0.0);
    }
    return new UnifiedToken(myDriver.defuzzify(tk));
  }

  public UnifiedToken createMaxedScale(Double d) {
    if (d > scale) {
      return new UnifiedToken(scale);
    } else if (d < -1.0 * scale) {
      return new UnifiedToken(-scale);
    }
    return new UnifiedToken(d);

  }


}
