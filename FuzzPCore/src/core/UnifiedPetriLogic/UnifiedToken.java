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
  public UnifiedToken myClone() {
    if (isPhi) {
      return new UnifiedToken();
    } else {
      return new UnifiedToken(val);

    }
  }

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



}
