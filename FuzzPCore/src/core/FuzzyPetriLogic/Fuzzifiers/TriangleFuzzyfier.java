package core.FuzzyPetriLogic.Fuzzifiers;

import java.util.EnumMap;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;

public class TriangleFuzzyfier implements FuzzyDriver {

  EnumMap<FuzzyValue, Double[]> vals;

  public TriangleFuzzyfier(Double[] NL, Double[] NM, Double[] ZR, Double[] PM, Double[] PL) {
    vals = new EnumMap<>(FuzzyValue.class);
    vals.put(FuzzyValue.NL, NL);
    vals.put(FuzzyValue.NM, NM);
    vals.put(FuzzyValue.ZR, ZR);
    vals.put(FuzzyValue.PM, PM);
    vals.put(FuzzyValue.PL, PL);
  }

  @Override
  public FuzzyToken fuzzifie(Double val) {
    if (val == null) {
      return new FuzzyToken();
    }
    FuzzyToken toRet = new FuzzyToken();

    if (vals.get(FuzzyValue.NL)[1] > val) {
      toRet.addToFuzzyValue(FuzzyValue.NL, 1.0);
    } else if ((vals.get(FuzzyValue.NL)[1] <= val) && (val <= vals.get(FuzzyValue.NL)[2])) {
      toRet.addToFuzzyValue(FuzzyValue.NL, calcWithRigth(vals.get(FuzzyValue.NL)[1], vals.get(FuzzyValue.NL)[2], val));
    }

    toRet.addToFuzzyValue(FuzzyValue.NM, calcInMidle(vals.get(FuzzyValue.NM), val));
    toRet.addToFuzzyValue(FuzzyValue.ZR, calcInMidle(vals.get(FuzzyValue.ZR), val));
    toRet.addToFuzzyValue(FuzzyValue.PM, calcInMidle(vals.get(FuzzyValue.PM), val));

    if (val <= vals.get(FuzzyValue.PL)[0]) {
      toRet.addToFuzzyValue(FuzzyValue.PL, 0.0);
    } else if (val < vals.get(FuzzyValue.PL)[1]) {
      toRet.addToFuzzyValue(FuzzyValue.PL, calcWithLeft(vals.get(FuzzyValue.PL)[1], vals.get(FuzzyValue.PL)[0], val));
    } else {
      toRet.addToFuzzyValue(FuzzyValue.PL, 1.0);
    }

    return toRet;

  }

  double weighSum = 0.0;
  double allSum = 0.0;

  @Override
  public Double defuzzify(FuzzyToken tk) {
    if (tk == null) {
      return null;
    }
    if (tk.isPhi()) {
      return null;
    }
    weighSum = 0.0;
    allSum = 0.0;
    tk.getNonZeroValues().forEach(fv -> {
      weighSum += vals.get(fv)[1] * tk.getFuzzyValue(fv);
      allSum += tk.getFuzzyValue(fv);
    });
		return weighSum / allSum;
  }

  private Double calcInMidle(Double[] defuzValues, Double vale) {

    if ((defuzValues[0] >= vale) || (defuzValues[2] <= vale)) {
      return 0.0;
    }
    if (vale == defuzValues[1]) {
      return 1.0;
    } else if (vale < defuzValues[1]) {
      return calcWithLeft(defuzValues[1], defuzValues[0], vale);
    } else {
      return calcWithRigth(defuzValues[1], defuzValues[2], vale);
    }
  }

  private Double calcWithRigth(Double center, Double rigthLimit, Double value) {
    return (rigthLimit - value) / (rigthLimit - center);
  }

  private Double calcWithLeft(Double center, Double leftLimit, Double value) {
    return (value - leftLimit) / (center - leftLimit);
  }

  public static TriangleFuzzyfier withBorderVales(double d, double e, double f, double g, double h) {
    return new TriangleFuzzyfier(new Double[] { null, d, e }, new Double[] { d, e, f }, new Double[] { e, f, g },
        new Double[] { f, g, h }, new Double[] { g, h, null });
  }

  public static TriangleFuzzyfier defaultFuzzyfier() {
    return TriangleFuzzyfier.withBorderVales(-1.0, -0.5, 0.0, 0.5, 1.0);
  }

}
