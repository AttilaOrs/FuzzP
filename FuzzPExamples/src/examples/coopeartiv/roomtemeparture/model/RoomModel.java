package examples.coopeartiv.roomtemeparture.model;

public class RoomModel {
  /* Room model for one minute */

  private static final double StartingTemperature = 24.0;

  /*
   * if the delta between heating water and room temperature is 1C than the room
   * temperature will go up <heaterConstant> in each minute
   */
  private static final double heaterConstant = 0.01;
  /*
   * if the delta between outside temperature and room temperature is 1C than
   * the room temperature will drop <wallConstant> in each minute
   */
  private static final double wallConstant = 0.00055;
  /*
   * if the delta between outside temperature and room temperature is 1C than
   * and the window is opened the room temperature will drop <windowConstant> in
   * each minute
   */
  private static final double windowConstant = 0.01;

  double currentTemaprature;

  public RoomModel() {
    currentTemaprature = StartingTemperature;
  }

  public void updateModel(boolean heatingOn, double heaterWaterTemp, boolean windowOpen, double outSideTemp) {
    double delatHeater = (heatingOn) ? (heaterWaterTemp - currentTemaprature) : 0.0;
    double outsideDelta = currentTemaprature - outSideTemp;
    currentTemaprature += delatHeater * heaterConstant - outsideDelta * wallConstant -
        ((windowOpen) ? (outsideDelta * windowConstant) : 0.0);
  }

  public double getCurrentTemperature() {
    return currentTemaprature;
  }
}
