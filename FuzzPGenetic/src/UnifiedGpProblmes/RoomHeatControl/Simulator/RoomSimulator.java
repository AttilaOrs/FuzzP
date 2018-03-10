package UnifiedGpProblmes.RoomHeatControl.Simulator;

import static java.lang.Math.abs;

import AlgoImpl.IterationLogger;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomController.ControllEvent;

public class RoomSimulator {
  
  public static final double deltaAllowed = 0.5;

  public static final String ROOM_TEMP = " room temp";
  public static final String OUTSIDE_TEMP = " out temp";
  public static final String WINDOW_OPEN = "window";
  public static final String HEATER_ON = " heater";
  private static final String REF_TEMP = " ref temp";

  private RoomScenario scenario;
  private IterationLogger logger;

  public RoomSimulator(RoomScenario scenario) {
    this.scenario = scenario;
    this.logger = null;
  }

  public void setIterationLogger(IterationLogger logger) {
    this.logger = logger;
  }


  public Rezult simulate(RoomController controller) {
    RoomModel model = new RoomModel();
    int heaterOn = 0;
    int offLimit = 0;
    double error = 0.0;
    int incorrectState = 0;

    for (int tickNr = 0; tickNr < scenario.getScenarioLength(); tickNr++) {
      ControllEvent rez = controller.control(model.getSensorReading(), scenario.getReference(tickNr));
      if (rez.equals(ControllEvent.StartHeating)) {
        model.setHeaterOnOff(true);
      } else if (rez.equals(ControllEvent.StopHeating)) {
        model.setHeaterOnOff(false);
      }
      model.setWindowOpen(scenario.getWindowOpen(tickNr));
      model.setOutisdeTemp(scenario.getOutSideTemepratue(tickNr));
      model.updateModel();


      if (logger != null) {
        logger.addLogToTopic(ROOM_TEMP, model.getCurrentTemperature());
        logger.addLogToTopic(OUTSIDE_TEMP, scenario.getOutSideTemepratue(tickNr));
        logger.addLogToTopic(REF_TEMP, scenario.getReference(tickNr));
        logger.addLogToTopic(WINDOW_OPEN, model.getWindowOpen() ? 2.0 : 1.0);
        logger.addLogToTopic(HEATER_ON, model.getHeaterOn() ? 4.0 : 3.0);
      }

      double lower_Limit_acc = scenario.getReference(tickNr) - deltaAllowed;
      double upper_Limit_acc = scenario.getReference(tickNr) + deltaAllowed;
      if (model.getCurrentTemperature() < lower_Limit_acc) {
        error += abs(model.getCurrentTemperature() - lower_Limit_acc);
        offLimit++;
        if (!model.getHeaterOn()) {
          incorrectState++;
        }
      }
      
      if (model.getCurrentTemperature() > upper_Limit_acc) {
        error += abs(upper_Limit_acc - model.getCurrentTemperature());
        offLimit++;
        if (model.getHeaterOn()) {
          incorrectState++;
        }
      }
      heaterOn += model.getHeaterOn() ? 1 : 0;

    }
    return new Rezult(error, heaterOn, offLimit, incorrectState);
  }

  public static class Rezult {
    public final double error;
    public final int heatrerOn;
    public final int offLimit;
    public final int incorrectState;

    public Rezult(double error, int heatrerOn, int offLimit, int incorrectState) {
      this.error = error;
      this.heatrerOn = heatrerOn;
      this.offLimit = offLimit;
      this.incorrectState = incorrectState;
    }

    @Override
    public String toString() {
      return "Error: " + error + " heaterOn: " + heatrerOn + " offLimit: " + offLimit + " incorrect state: "
          + incorrectState;
    }

  }


}
