package examples.coopeartiv.roomtemeparture.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Plant {
  private volatile boolean heaterOn = false;
  private volatile double gasCmd = 0.0;
  private int tickCntr = 0;
  private long period;
  private RoomModel room;
  private Scenario scenario;
  private HeaterTank tank;
  /* for logs */
  ArrayList<Double> heaterWaterTempLog = new ArrayList<>();
  ArrayList<Double> roomTempLog = new ArrayList<>();

  ArrayList<Double> waterHetarCmdLog = new ArrayList<>();
  ArrayList<Double> heatOnCmdLog = new ArrayList<>();
  int heatOnCntr = 0;
  int continousHeatOnMax = 0;
  int continousHeatOnCurent = 0;
  double tankGasCommandSum = 0.0;

  public Plant(long simPeriod, Scenario scen) {
    this.period = simPeriod;
    room = new RoomModel();
    tank = new HeaterTank();
    scenario = scen;
  }

  public void setHeatingOn(boolean heaterOn) {
    this.heaterOn = heaterOn;
  }

  public void setHeaterGasCmd(double cmd) {
    gasCmd = cmd;
  }

  public double getRoomTemperature() {
    return room.getCurrentTemperature();
  }

  public double heatingOnRatio() {
    return ((double) heatOnCntr / (double) tickCntr);
  }

  public double gasConsumption() {
    return tankGasCommandSum;
  }

  public int maxContiniousHeaterOn() {
    return continousHeatOnMax;
  }

  public void start() {
    Timer myTimer = new Timer();
    TimerTask task = new TimerTask() {

      @Override
      public void run() {
        if (tickCntr < scenario.getScenarioLength()) {
          tank.updateSystem(heaterOn, gasCmd);
          room.updateModel(heaterOn, tank.getHotWaterTemeprature(), scenario.getWindowOpen(tickCntr),
              scenario.getOutSideTemepratue(tickCntr));
          makeLogs();
          tickCntr++;
        } else {
          myTimer.cancel();
          myTimer.purge();
        }

      }

    };
    myTimer.scheduleAtFixedRate(task, period, period);
  }

  private void makeLogs() {
    heaterWaterTempLog.add(tank.getHotWaterTemeprature());
    roomTempLog.add(room.getCurrentTemperature());
    waterHetarCmdLog.add(gasCmd);
    heatOnCmdLog.add(heaterOn ? 1.0 : 0.0);
    heatOnCntr += (heaterOn ? 1.0 : 0.0);
    if (heaterOn) {
      continousHeatOnCurent++;
    } else if (continousHeatOnCurent > 0) {
      if (continousHeatOnCurent > continousHeatOnMax) {
        continousHeatOnMax = continousHeatOnCurent;
      }
      continousHeatOnCurent = 0;
    }
    tankGasCommandSum += (gasCmd < 0.0) ? 0.0 : gasCmd;

  }

  public Double getTankWaterTemperature() {
    return tank.getHotWaterTemeprature();
  }

  public Map<String, List<Double>> getTemeartureLogs() {
    HashMap<String, List<Double>> logMap = new HashMap<>();
    logMap.put("tankTemp", heaterWaterTempLog);
    logMap.put("roomTemp", roomTempLog);
    return logMap;
  }

  public Map<String, List<Double>> getCommandLogs() {
    HashMap<String, List<Double>> logMap = new HashMap<>();
    logMap.put("waterCmd", waterHetarCmdLog);
    logMap.put("heaterOn", heatOnCmdLog);
    return logMap;
  }

}
