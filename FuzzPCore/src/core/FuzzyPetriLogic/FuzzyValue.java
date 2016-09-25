package core.FuzzyPetriLogic;

import java.util.Arrays;
import java.util.List;
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
}
