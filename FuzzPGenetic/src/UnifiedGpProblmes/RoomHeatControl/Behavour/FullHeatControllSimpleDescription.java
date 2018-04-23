package UnifiedGpProblmes.RoomHeatControl.Behavour;

public class FullHeatControllSimpleDescription {
  public final int roomInWrongState;
  public final int tankOffLimit;
  public final int totalTick;
  public final double sizeMulti;
  public final int size;
  public final double waterErrot;
  public final double roomTempError;

  public FullHeatControllSimpleDescription(int roomInGoodState, double roomTempError, int tankOffLimit,
      double waterError,
      int totalScenarios, double sizeMulti, int size) {
    this.roomInWrongState = roomInGoodState;
    this.tankOffLimit = tankOffLimit;
    this.totalTick = totalScenarios;
    this.sizeMulti = sizeMulti;
    this.size = size;
    this.waterErrot = waterError;
    this.roomTempError = roomTempError;
  }
  @Override
  public String toString() {
    return "FullHeatControllSimpleDescription [roomInWrongState=" + roomInWrongState + ", tankOffLimit="
        + tankOffLimit + ", totalTick=" + totalTick + ", sizeMulti=" + sizeMulti + ", size=" + size + ", waterErrot="
        + waterErrot + ", roomTempError=" + roomTempError + "]";
  }

}
