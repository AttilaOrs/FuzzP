package UnifiedGpProblmes.FirstOrderSystem;

import static java.lang.Math.abs;

import java.util.List;

public class ReferenceProvider {

  public int getRefSize() {
    return 50;
  }

  public double getReference(int tick) {
    if (tick < 25) {
      return 0.9;
    }
    return 0.6;
  }

  public double calcError(List<Double> evolution) {
    double sum = 0.0;
    for (int i = 0; i < evolution.size(); i++) {
      sum += abs(evolution.get(i) - getReference(i));
    }
    return sum;

  }

}
