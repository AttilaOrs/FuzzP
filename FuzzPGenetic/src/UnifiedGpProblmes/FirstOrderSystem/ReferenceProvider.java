package UnifiedGpProblmes.FirstOrderSystem;

import static java.lang.Math.abs;

import java.util.List;

public class ReferenceProvider {

  public int getRefSize() {
    return 80;
  }

  public double getReference(int tick) {
    if (tick <= 10) {
      return 0.9;
    } else if (tick <= 20) {
      return 0.4;
    } else if (tick <= 30) {
      return 0.7;
    } else if (tick <= 40) {
      return 0.2;
    } else if (tick <= 60) {
      return 0.5;
    } else if (tick <= 70) {
      return 0.8;
    }
    return 0.1;
  }

  public double calcError(List<Double> evolution) {
    double sum = 0.0;
    for (int i = 0; i < evolution.size(); i++) {
      sum += abs(evolution.get(i) - getReference(i));
    }
    return sum;

  }

}
