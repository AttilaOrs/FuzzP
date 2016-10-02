package examples.coopeartiv.roomtemeparture.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scenario {
  List<Double> outsideTemperature;
  List<Boolean> windowOpen;

  public Scenario(List<Double> outsideTempearture, List<Boolean> windowOpen) {
    this.outsideTemperature = outsideTempearture;
    this.windowOpen = windowOpen;
  }

  public boolean getWindowOpen(int tick) {
    return windowOpen.get(tick);
  }

  public double getOutSideTemepratue(int tick) {
    return outsideTemperature.get(tick);
  }

  public int getScenarioLength() {
    return outsideTemperature.size();
  }

  private static Scenario scenarioBuilder(double startingTempInHour[], double windowChance[]) {

    List<Double> outsideTemperature = new ArrayList<>();
    List<Boolean> windowOpen = new ArrayList<>();
    Random rnd = new Random();
    for (int hour = 0; hour < startingTempInHour.length - 1; hour++) {
      double startTemp = startingTempInHour[hour];
      double endTemp = startingTempInHour[(hour + 1)];
      for (int minute = 0; minute < 60; minute++) {
        double temp = startTemp + ((endTemp - startTemp) * minute) / 60.0 + rnd.nextDouble() * 0.1;
        outsideTemperature.add(temp);
        windowOpen.add(rnd.nextDouble() < windowChance[hour]);
      }
    }
    return new Scenario(outsideTemperature, windowOpen);
  }


  public static Scenario winterDay() {
    double startingTempInHour[] = new double[] { -12.5, -15.0, -17.0, -20.0, -21.0, -19.0, -17.0, -15.0,
        -12.0, -8.0, -7.0, -5.0, -4.0, -3.5, -5.0, -4.0, -5.0,
        -6.0, -7.5, -8.5, -9.0, -11.0, -11.5, -12.0, -12.0 };
    double windowChance[] = new double[] { 0.02, 0.01, 0.01, 0.01, 0.01, 0.01, 0.02, 0.02,
        0.08, 0.08, 0.1, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05,
        0.05, 0.05, 0.02, 0.02, 0.01, 0.01, 0.01 };
    return scenarioBuilder(startingTempInHour, windowChance);
  }

  public static Scenario winterMorning() {
    double startingTempInHour[] = new double[] { -19.0, -17.0, -15.0, -12.0 };
    double windowChance[] = new double[] { 0.08, 0.04, 0.01, };
    return scenarioBuilder(startingTempInHour, windowChance);
  }

  public static Scenario extremeEvening() {
    double startingTempInHour[] = new double[] { -5.0, -18.0, -22.0, -27.0 };
    double windowChance[] = new double[] { 0.06, 0.03, 0.05, };
    return scenarioBuilder(startingTempInHour, windowChance);

  }

}
