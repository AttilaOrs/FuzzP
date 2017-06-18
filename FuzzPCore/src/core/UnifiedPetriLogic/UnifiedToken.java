package core.UnifiedPetriLogic;

import core.common.recoder.FullRecordable;

public class UnifiedToken implements FullRecordable<UnifiedToken> {
  private final Double val;
  private final boolean isPhi;

  public UnifiedToken(Double d) {
    val = d;
    isPhi = false;
  }

  public UnifiedToken() {
    val = null;
    isPhi = true;
  }

  public Double getValue() {
    if (isPhi) {
      throw new RuntimeException("token is Phi");
    }
    return val;
  }

  @Override
  public boolean isPhi() {
    return isPhi;
  }

  @Override
  public String shortString() {
    if (isPhi) {
      return "<phi>";
    } else {
      return "<" + Double.toString(val) + ">";
    }
  }

  @Override
  public String toString() {
    return shortString();
  }

  @Override
  public UnifiedToken myClone() {
    if (isPhi) {
      return new UnifiedToken();
    } else {
      return new UnifiedToken(val);

    }
  }

  @Override
  public UnifiedToken unite(UnifiedToken unifiedToken) {
    if (this.isPhi && unifiedToken.isPhi) {
      return new UnifiedToken();
    }
    if ((!this.isPhi) && (!unifiedToken.isPhi())) {
      return new UnifiedToken((val + unifiedToken.getValue()) / 2.0);
    }
    if (this.isPhi) {
      return new UnifiedToken(unifiedToken.getValue());
    }
    if (unifiedToken.isPhi()) {
      return new UnifiedToken(val);

    }
    return new UnifiedToken();
  }
  
  public static UnifiedToken buildFromString(String gg) {
    String theoreticDouble = gg.replaceAll("<", "").replaceAll(">", "").trim();
    try{
      return new UnifiedToken(Double.valueOf(theoreticDouble));
    } catch ( NumberFormatException e) {
      return new UnifiedToken();
    }
    
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof UnifiedToken)) {
      return false;
    }
    UnifiedToken ut = (UnifiedToken) o;
    if (ut.isPhi() && this.isPhi()) {
      return true; // both of the, are phi
    }
    if (ut.isPhi() || this.isPhi()) {
      return false; // only one of them is phi
    }
    // non of them is phi
    return ut.getValue().equals(this.getValue());
  }

  @Override
  public int hashCode() {
    if (isPhi()) {
      return 1;
    }
    return 39 * getValue().hashCode();

  }

  public static UnifiedToken zero() {
    return new UnifiedToken(0.0);
  }



}
