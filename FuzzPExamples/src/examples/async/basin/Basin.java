package examples.async.basin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basin {
  private static final double INIT_WATER = 50.0;
  private static final double VALVE_OPEN = 1.0;

  private double water;
  private boolean valveOpen;
  private ArrayList<Double> savedState;

  public Basin() {
    water = INIT_WATER;
    valveOpen = false;
    savedState = new ArrayList<>();
  }

  public void setValveOn() {
    this.valveOpen = true;
  }

  public void setValveOff() {
    this.valveOpen = false;
  }

  public double getWater() {
    return water;
  }

  public Map<String, List<Double>> getSavedLogs() {
    HashMap<String, List<Double>> toRet = new HashMap<>();
    toRet.put("water ", savedState);
    return toRet;
  }

  public void updateSystem(double disturbance) {
    water -= (valveOpen) ? VALVE_OPEN : 0.0;
    water += disturbance;
    savedState.add(water);
  }

}
