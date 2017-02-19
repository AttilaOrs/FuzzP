package core.FuzzyPetriLogic;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.common.recoder.FullRecordable;

public class FuzzyToken implements FullRecordable<FuzzyToken> {
  private static final String SEPARATOR = ",";
  private static final String END = ">";
  private static final String STR = "<";
  private static final String PHI = "<phi>";

  public static FuzzyToken zeroToken() {
    return new FuzzyToken(0.0, 0.0, 1.0, 0.0, 0.0);
  }

  public static FuzzyToken emptyToken() {
    return new FuzzyToken(new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
  }

  private double[] fuzzyValues;
  private boolean phi;

  public FuzzyToken() {
    phi = true;
    fuzzyValues = null;
  }

  public FuzzyToken(Double NL, Double NM, Double ZR, Double PM, Double PL) {
    fuzzyValues = new double[] { NL, NM, ZR, PM, PL };
    phi = false;
    normalize();
  }

  public FuzzyToken(double[] ll) {
    this.fuzzyValues = ll;
    phi = false;
  }

  public void normalize() {
    if (!phi) {
      double sum = Arrays.stream(fuzzyValues).sum();
      if (sum != 0.0) {
        for (int q = 0; q < fuzzyValues.length; q++) {
          fuzzyValues[q] /= sum;
        }
      } else {
        phi = true;
        fuzzyValues = null;
      }
    }
  }

  public void addToFuzzyValue(FuzzyValue fuzzyVal, double val) {
    if (phi && val != 0.0) {
      phi = false;
    }
    if (fuzzyValues == null) {
      fuzzyValues = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
    }
    if (fuzzyVal != FuzzyValue.FF) {
      fuzzyValues[FuzzyValue.indexOf(fuzzyVal)] += val;
    }
  }

  public double getFuzzyValue(FuzzyValue val) {
    if (phi ^ (val == FuzzyValue.FF)) {
      return 0.0;
    }
    if (phi && (val == FuzzyValue.FF)) {
      return 1.0;
    }
    return fuzzyValues[FuzzyValue.indexOf(val)];
  }

  public boolean isPhi() {
    return this.phi;
  }

  public FuzzyToken unite(FuzzyToken ff) {
    if (isPhi() && ff.isPhi()) {
      return new FuzzyToken();
    }
    if (isPhi()) {
      return ff.myClone();
    }
    if (ff.isPhi()) {
      return myClone();
    }
    double[] ll = new double[fuzzyValues.length];
    for (int q = 0; q < fuzzyValues.length; q++) {
      ll[q] = fuzzyValues[q] + ff.fuzzyValues[q];
    }
    FuzzyToken toRet = new FuzzyToken(ll);
    toRet.normalize();
    return toRet;
  }

  public FuzzyToken myClone() {
    if (!phi) {
      double[] ll = Arrays.copyOf(fuzzyValues, fuzzyValues.length);
      return new FuzzyToken(ll);
    } else {
      return new FuzzyToken();
    }
  }

  static NumberFormat formatter = new DecimalFormat("#0.00");

  public String shortString() {
    if (isPhi()) {
      return PHI;
    } else {
      StringBuilder stb = new StringBuilder(STR);
      for (int q = 0; q < fuzzyValues.length; q++) {
        stb.append(formatter.format(fuzzyValues[q]));
        if (q != 4) {
          stb.append(SEPARATOR);
        }
      }
      stb.append(END);
      return stb.toString();

    }
  }

  public Stream<FuzzyValue> getNonZeroValues() {
    if (phi) {
      return Arrays.stream(new FuzzyValue[] { FuzzyValue.FF });
    }
    return IntStream.range(0, 5).filter(i -> fuzzyValues[i] != 0.0).mapToObj(i -> FuzzyValue.inOrder.get(i));
  }

  public static FuzzyToken buildFromString(String myStr) {
    if (myStr.contains(PHI)) {
      return new FuzzyToken();
    }
    String[] res = myStr.replace(STR, "").replace(END, "").split(SEPARATOR);
    double[] vals = new double[5];
    for (int i = 0; i < res.length; i++) {
      vals[i] = Double.parseDouble(res[i]);
    }

    return new FuzzyToken(vals);
  }
}
