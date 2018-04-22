package UnifiedGpProblmes.RoomHeatControl.Behavour;

public class FullHeatControllSimpleDescription {
  final int roomInCorrentSate;
  final int tankInCorrentTemp;
  final int totalTick;
  final double sizeMulti;
  final int size;
  public FullHeatControllSimpleDescription(int roomInCorrentSate, int tankCorrentTemp, int totalScenarios,
      double sizeMulti, int size) {
    this.roomInCorrentSate = roomInCorrentSate;
    this.tankInCorrentTemp = tankCorrentTemp;
    this.totalTick = totalScenarios;
    this.sizeMulti = sizeMulti;
    this.size = size;
  }

  @Override
  public String toString() {
    return "FullHeatControllSimpleDescription [roomInCorrentSate=" + roomInCorrentSate + ", tankInCorrentTemp="
        + tankInCorrentTemp + ", totalTick=" + totalTick + ", sizeMulti=" + sizeMulti + ", size=" + size + "]";
  }
}
