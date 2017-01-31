package examples.async.basin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scenario {
  List<Double> disturbance;

  private Scenario(List<Double> disturbance) {
    this.disturbance = disturbance;
  }

  public int getScenarioLength() {
    return disturbance.size();
  }

  public double getDisturbanceForTick(int t) {
    return disturbance.get(t);
  }
  
  public static Scenario generateScenario(int length) {
    ArrayList<Double> scenarioList = new ArrayList<>();
    Random rnd = new Random();
    for (int i = 0; i < length; i++) {
      double mult = (rnd.nextInt(3) % 3 == 0) ? -1.2 : 1.2;
      double sin = Math.sin(i / 10.0) * 1.5;
      scenarioList.add(sin + rnd.nextDouble() * mult);
    }
    return new Scenario(scenarioList);
    
  }

}
