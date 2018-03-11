package UnifiedGpProblmes.RoomHeatControl.Simulator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class RoomModel {
  /* Room model for one minute */

  private static final double StartingTemperature = 24.0;

  private static final double StartingOutsideTemp = -3.0;

  /* initially was 30 */

  private static final double StartingHEatingWaterTemp = 40.0;

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
   * 
   * initaillt it was 0.01
   */
  private static final double windowConstant = 0.03;

  private static final int delayOfSensor = 3;

  double currentTemaprature;
  Queue<Double> pastTemperatures;
  boolean heatingOn;
  boolean windowOpen;
  double heaterWaterTemp;
  double outsideTemp;


  public RoomModel() {
    currentTemaprature = StartingTemperature;
    pastTemperatures = new LinkedList<>(Collections.nCopies(delayOfSensor, StartingTemperature));
    heatingOn = false;
    windowOpen = false;
    heaterWaterTemp = StartingHEatingWaterTemp;
    outsideTemp = StartingOutsideTemp;
  }

  private void updateModel(boolean heatingOn, double heaterWaterTemp, boolean windowOpen, double outSideTemp) {
    double delatHeater = (heatingOn) ? (heaterWaterTemp - currentTemaprature) : 0.0;
    double outsideDelta = currentTemaprature - outSideTemp;
    currentTemaprature += delatHeater * heaterConstant - outsideDelta * wallConstant -
        ((windowOpen) ? (outsideDelta * windowConstant) : 0.0);
    pastTemperatures.poll();
    pastTemperatures.add(currentTemaprature);
  }

  public double getCurrentTemperature() {
    return currentTemaprature;
  }

  public boolean getWindowOpen() {
    return this.windowOpen;
  }

  public boolean getHeaterOn() {
    return this.heatingOn;
  }

  public void setHeaterOnOff(boolean heatingOn) {
    this.heatingOn = heatingOn;
  }

  public void setHeaterWaterTemp(double heaterTemp) {
    this.heaterWaterTemp = heaterTemp;
  }

  public void setWindowOpen(boolean windowOpen) {
    this.windowOpen = windowOpen;
  }

  public void setOutisdeTemp(double outisdeTemp) {
    this.outsideTemp = outisdeTemp;
  }


  public double getSensorReading() {
    return pastTemperatures.peek();
  }

  public void updateModel() {
    this.updateModel(heatingOn, heaterWaterTemp, windowOpen, outsideTemp);
  }

}
