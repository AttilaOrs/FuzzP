package UnifiedGpProblmes.RoomHeatControl.Simulator;

public interface FullHeatController {

  public static enum ControllEvent {
    StartHeating, StopHeating, None
  }
  public static class Control {

    final ControllEvent event;
    final double gasCmd;
    public Control(ControllEvent e, double gasCmd) {
      this.event = e;
      this.gasCmd = gasCmd;
    }

  }

  Control control(double sensorReading, double roomTempRef, double waterTemp, double waterRef);
}
