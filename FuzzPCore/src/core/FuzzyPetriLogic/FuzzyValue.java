package core.FuzzyPetriLogic;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

public enum FuzzyValue {

  NL("Negative Large"), NM("Negative Medium"), ZR("Zero"), PM("Pozitive Medium"), PL("Pozitive Large"), FF("Phi");

  private String fullName;

  private FuzzyValue(String fullName) {
    this.fullName = fullName;
  }

  @Override
  public String toString() {
    return fullName;
  }

  public boolean isPhi() {
    return this.equals(FF);
  }

  public static List<FuzzyValue> inOrder = Arrays.asList(FuzzyValue.NL, FuzzyValue.NM, FuzzyValue.ZR, FuzzyValue.PM,
      FuzzyValue.PL, FuzzyValue.FF);
  public static List<FuzzyValue> inOrderWithoutPhi = Arrays.asList(FuzzyValue.NL, FuzzyValue.NM, FuzzyValue.ZR,
      FuzzyValue.PM, FuzzyValue.PL);

  public static FuzzyValue fromString(String valueString) {
    for (FuzzyValue val : inOrder) {
			if (val.fullName.equals(valueString.trim()) || (val.name().equals(valueString.trim()))
			    || (val.name().equals(valueString.trim().toUpperCase()))) {
        return val;
      }
    }
    return null;
  }

  public static int indexOf(FuzzyValue fuzzyVal) {
    return inOrder.indexOf(fuzzyVal);
  }

  public static Stream<FuzzyValue[]> getFuzzyValuePairsForindexing() {
    return FuzzyValue.inOrder.stream().flatMap(
        outerIndex -> FuzzyValue.inOrder.stream().map(innerIndex -> new FuzzyValue[] { outerIndex, innerIndex }));
  }

  private static final EnumMap<FuzzyValue, String> unifiedStr = new EnumMap<>(FuzzyValue.class);
  static {
    unifiedStr.put(NL, "-2");
    unifiedStr.put(NM, "-1");
    unifiedStr.put(ZR, "0");
    unifiedStr.put(PM, "1");
    unifiedStr.put(PL, "2");
    unifiedStr.put(FF, "FF");

  }

  public String unifiedStr() {
    String toREt = unifiedStr.get(this);
    if(toREt.length() < 2) {
      return " "+ toREt;
    }
    return toREt;
  }

  public static FuzzyValue parseUnifiedStr(String str) {
    for (Entry<FuzzyValue, String> e : unifiedStr.entrySet()) {
      if (e.getValue().equals(str.trim())) {
        return e.getKey();
      }
    }
    return null;

  }

  public static FuzzyValue fromInt(int val) {
    switch (val) {
    case 0:
      return NL;
    case 1:
      return NM;
    case 2:
      return ZR;
    case 3:
      return PM;
    case 4:
      return PL;
    default:
      return FF;
    }
  }
}
