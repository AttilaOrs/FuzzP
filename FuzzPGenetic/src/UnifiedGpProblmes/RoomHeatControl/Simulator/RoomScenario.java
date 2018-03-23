package UnifiedGpProblmes.RoomHeatControl.Simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomScenario {
  List<Double> outsideTemperature;
  List<Boolean> windowOpen;
  List<Double> referenceTemp;

  public RoomScenario(List<Double> outsideTempearture, List<Boolean> windowOpen, List<Double> referenceTemp) {
    this.outsideTemperature = outsideTempearture;
    this.windowOpen = windowOpen;
    this.referenceTemp = referenceTemp;
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

  private static RoomScenario scenarioBuilder(double startingTempInHour[], double windowChance[], double[] refsHourly) {

    List<Double> outsideTemperature = new ArrayList<>();
    List<Double> refs = new ArrayList<>();
    List<Boolean> windowOpen = new ArrayList<>();
    Random rnd = new Random();
    for (int hour = 0; hour < startingTempInHour.length - 1; hour++) {
      double startTemp = startingTempInHour[hour];
      double endTemp = startingTempInHour[(hour + 1)];
      for (int minute = 0; minute < 60; minute++) {
        double temp = startTemp + ((endTemp - startTemp) * minute) / 60.0 + rnd.nextDouble() * 0.1;
        outsideTemperature.add(temp);
        windowOpen.add(rnd.nextDouble() < windowChance[hour]);
        refs.add(refsHourly[hour]);
      }
    }
    return new RoomScenario(outsideTemperature, windowOpen, refs);
  }


  public static RoomScenario winterDay() {
    double startingTempInHour[] = new double[] { -12.5, -15.0, -17.0, -20.0, -21.0, -19.0, -17.0, -15.0,
        -12.0, -8.0, -7.0, -5.0, -4.0, -3.5, -5.0, -4.0, -5.0,
        -6.0, -7.5, -8.5, -9.0, -11.0, -11.5, -12.0, -12.0 };
    double windowChance[] = new double[] { 0.02, 0.01, 0.01, 0.01, 0.01, 0.01, 0.02, 0.02,
        0.08, 0.08, 0.1, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05,
        0.05, 0.05, 0.02, 0.02, 0.01, 0.01, 0.01 };

    double refsHourly[] = new double[] { 17, 17, 21, 20, 23, 21, 18, 17,
        22, 22, 17, 22, 19, 18, 17, 21, 22,
        21, 16, 17, 17, 17, 17, 17 };

    return scenarioBuilder(startingTempInHour, windowChance, refsHourly);
  }

  public static RoomScenario winterMorning() {
    double startingTempInHour[] = new double[] { -19.0, -17.0, -15.0, -12.0 };
    double windowChance[] = new double[] { 0.08, 0.04, 0.01, };
    double refsHourly[] = new double[] { 18, 22, 21 };
    return scenarioBuilder(startingTempInHour, windowChance, refsHourly);
  }

  public static RoomScenario extremeEvening() {
    double startingTempInHour[] = new double[] { -5.0, -18.0, -22.0, -27.0 };
    double windowChance[] = new double[] { 0.06, 0.03, 0.05, };
    double refsHourly[] = new double[] { 22, 22, 17 };
    return scenarioBuilder(startingTempInHour, windowChance, refsHourly);
  }

  public static RoomScenario fitnessScenario() {
    double startingTempInHour[] = new double[] { -19.0, -17.0, -22.0, -15.0 };
    double windowChance[] = new double[] { 0.15, 0.01, 0.08, };
    double refsHourly[] = new double[] { 22, 17, 21 };
    return scenarioBuilder(startingTempInHour, windowChance, refsHourly);
  }

  public static RoomScenario presentationScenario() {
    double startingTempInHour[] = new double[] { -19.0, -17.0, -22.0, -15.0 };
    double windowChance[] = new double[] { 0.12, 0.20, 0.10, };
    double refsHourly[] = new double[] { 22, 20, 21 };
    return scenarioBuilder(startingTempInHour, windowChance, refsHourly);
  }

  public RoomScenario myClone() {
    return new RoomScenario(new ArrayList<>(outsideTemperature), new ArrayList<>(windowOpen),
        new ArrayList<>(referenceTemp));
  }

  public double getReference(int tickNr) {
    return this.referenceTemp.get(tickNr);
  }

}
