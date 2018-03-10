package UnifiedGpProblmes.RoomHeatControl.Simulator;

public interface RoomController {

  public static enum ControllEvent {
    StartHeating, StopHeating, None
  }

  ControllEvent control(double sensorReading, double reference);


}
