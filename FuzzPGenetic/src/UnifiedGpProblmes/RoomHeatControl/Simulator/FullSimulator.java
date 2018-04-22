package UnifiedGpProblmes.RoomHeatControl.Simulator;

import static java.lang.Math.abs;

import AlgoImpl.IterationLogger;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController.Control;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController.ControllEvent;

public class FullSimulator {

  public static final double deltaAllowedRoom = 0.5;
  public static final double waterRef = 65.0;
  public static final double deltaAllowedWater = 10.0;

  public static final String ROOM_TEMP = " room temp";
  public static final String OUTSIDE_TEMP = " out temp";
  public static final String WINDOW_OPEN = "window";
  public static final String HEATER_ON = " heater";
  private static final String REF_TEMP = " ref temp";
  private static final String WATER_TEMP = " water temp";

  private RoomScenario scenario;
  private IterationLogger logger;

  public FullSimulator(RoomScenario scenario) {
    this.scenario = scenario;
    this.logger = null;
  }

  public void setIterationLogger(IterationLogger logger) {
    this.logger = logger;
  }

  public Rezult simulate(FullHeatController controller) {
    RoomModel roomModel = new RoomModel();
    HeaterModel heaterModel = new HeaterModel();
    int heaterOn = 0;
    int offLimit = 0;
    double error = 0.0;
    int incorrectState = 0;
    int waterOffLimit = 0;
    double waterError = 0.0;

    for (int tickNr = 0; tickNr < scenario.getScenarioLength(); tickNr++) {
      Control rez = controller.control(roomModel.getSensorReading(), scenario.getReference(tickNr),
          heaterModel.getHotWaterTemeprature(), waterRef);
      if (rez.event.equals(ControllEvent.StartHeating)) {
        roomModel.setHeaterOnOff(true);
      } else if (rez.event.equals(ControllEvent.StopHeating)) {
        roomModel.setHeaterOnOff(false);
      }
      roomModel.setWindowOpen(scenario.getWindowOpen(tickNr));
      roomModel.setOutisdeTemp(scenario.getOutSideTemepratue(tickNr));
      heaterModel.updateSystem(roomModel.getHeaterOn(), rez.gasCmd);
      roomModel.setHeaterWaterTemp(heaterModel.getHotWaterTemeprature());
      roomModel.updateModel();

      if (logger != null) {
        logger.addLogToTopic(ROOM_TEMP, roomModel.getCurrentTemperature());
        logger.addLogToTopic(OUTSIDE_TEMP, scenario.getOutSideTemepratue(tickNr));
        logger.addLogToTopic(REF_TEMP, scenario.getReference(tickNr));
        logger.addLogToTopic(WINDOW_OPEN, roomModel.getWindowOpen() ? 2.0 : 1.0);
        logger.addLogToTopic(HEATER_ON, roomModel.getHeaterOn() ? 4.0 : 3.0);
        logger.addLogToTopic(WATER_TEMP, heaterModel.getHotWaterTemeprature());
      }

      double lower_Limit_acc = scenario.getReference(tickNr) - deltaAllowedRoom;
      double upper_Limit_acc = scenario.getReference(tickNr) + deltaAllowedRoom;
      double lower_Limit_water = waterRef - deltaAllowedWater;
      double upper_Limit_water = waterRef + deltaAllowedWater;
      if (roomModel.getCurrentTemperature() < lower_Limit_acc) {
        error += abs(roomModel.getCurrentTemperature() - lower_Limit_acc);
        offLimit++;
        if (!roomModel.getHeaterOn()) {
          incorrectState++;
        }
      }

      if (roomModel.getCurrentTemperature() > upper_Limit_acc) {
        error += abs(upper_Limit_acc - roomModel.getCurrentTemperature());
        offLimit++;
        if (roomModel.getHeaterOn()) {
          incorrectState++;
        }
      }
      heaterOn += roomModel.getHeaterOn() ? 1 : 0;
      if (heaterModel.getHotWaterTemeprature() < lower_Limit_water) {
        waterOffLimit += 1;
        waterError += abs(heaterModel.getHotWaterTemeprature() - lower_Limit_water);
      }
      if (heaterModel.getHotWaterTemeprature() > upper_Limit_water) {
        waterOffLimit += 1;
        waterError += abs(heaterModel.getHotWaterTemeprature() - upper_Limit_water);
      }

    }
    return new Rezult(error, heaterOn, offLimit, incorrectState, waterError, waterOffLimit);
  }

  public static class Rezult {

    public final double roomError;
    public final int heatrerOn;
    public final int roomOfLimit;
    public final int roomInGoodState;
    public final double waterError;
    public final int waterOffLimit;

    public Rezult(double roomError, int heatrerOn, int roomOfLimit, int roomIncorrectState, double waterError,
        int waterOffLimit) {
      this.roomError = roomError;
      this.heatrerOn = heatrerOn;
      this.roomOfLimit = roomOfLimit;
      this.roomInGoodState = roomIncorrectState;
      this.waterError = waterError;
      this.waterOffLimit = waterOffLimit;
    }

    @Override
    public String toString() {
      return "Rezult [roomError=" + roomError + ", heatrerOn=" + heatrerOn + ", roomOfLimit=" + roomOfLimit
          + ", roomInGoodState=" + roomInGoodState + ", waterError=" + waterError + ", waterOffLimit=" + waterOffLimit
          + "]";
    }



  }
}
