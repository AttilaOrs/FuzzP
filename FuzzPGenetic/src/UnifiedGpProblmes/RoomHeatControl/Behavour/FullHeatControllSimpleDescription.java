package UnifiedGpProblmes.RoomHeatControl.Behavour;

public class FullHeatControllSimpleDescription {
  final int roomInWrongState;
  final int tankOffLimit;
  final int totalTick;
  final double sizeMulti;
  final int size;
  final double waterErrot;
  final double roomTempError;
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
        + tankOffLimit
        + ", totalTick=" + totalTick + ", sizeMulti=" + sizeMulti + ", size=" + size + ", waterErrot=" + waterErrot
        + ", roomTempError=" + roomTempError + "]";
  }

}
