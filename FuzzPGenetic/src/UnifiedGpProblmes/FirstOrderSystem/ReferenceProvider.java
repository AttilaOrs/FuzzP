package UnifiedGpProblmes.FirstOrderSystem;

import static java.lang.Math.abs;

import java.util.List;

public class ReferenceProvider {

  private int multi;

  public ReferenceProvider() {
    this(1);
  }

  public ReferenceProvider(int timeMulitplier) {
    this.multi = timeMulitplier;

  }

  public int getRefSize() {
    return 80 * multi;
  }

  private static final int limits[] = new int[] { 10, 20, 30, 40, 60, 70 };
  private static final double refs[] = new double[] { 0.9, 0.4, 0.7, 0.2, 0.5, 0.8 };

  public double getReference(int tick) {
    for (int i = limits.length - 1; i > 1; i--) {
      if (tick <= getLimit(i) && tick > getLimit(i - 1)) {
        return refs[i];
      }
    }
    return refs[0];
  }

  private int getLimit(int i) {
    return limits[i] * multi;
  }


  double calcError(List<Double> evolution) {
    double sum = 0.0;
    for (int i = 0; i < evolution.size(); i++) {
      sum += abs(evolution.get(i) - getReference(i));
    }
    return sum;
  }

  double getSteadyStateErrorSum(List<Double> evolution) {
    double sum = 0.0;
    for (int i = 0; i < limits.length; i++) {
      sum += abs(evolution.get(getLimit(i)) - refs[i]);

    }
    return sum;
  }

  double getChangeSum(List<Double> evolution) {
    double changeSum = 0.0;
    for (int i = 0; i < evolution.size() - 1; i++) {
      changeSum += abs(evolution.get(i) - evolution.get(i + 1));
    }
    return changeSum;

  }

  public Result calcResult(List<Double> evolution) {
    double error = calcError(evolution);
    double steady = getSteadyStateErrorSum(evolution);
    double change = getChangeSum(evolution);
    return new Result(error, steady, change);
  }


  public static class Result {
    public final double error;
    public final double steadyStateError;
    public final double changeSum;

    public Result(double error, double steadyStateError, double change) {
      this.error = error;
      this.steadyStateError = steadyStateError;
      this.changeSum = change;
    }

  }

}
