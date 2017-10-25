package UnifiedGpProblmes.Robo.Simulator;

import java.util.ArrayList;

import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segments;

public class BigRobo extends AbstractRobo {


  public BigRobo(Court c) {
    super(c);
    ArrayList<LineSensorsimulator> lineSensors = new ArrayList<>();
    lineSensors.add(new LineSensorsimulator(0.00, 0.13, moovmen, c.getLines()));
    lineSensors.add(new LineSensorsimulator(0.02, 0.12, moovmen, c.getLines()));
    lineSensors.add(new LineSensorsimulator(-0.02, 0.12, moovmen, c.getLines()));
    lineSensors.add(new LineSensorsimulator(0.04, 0.11, moovmen, c.getLines()));
    lineSensors.add(new LineSensorsimulator(-0.04, 0.11, moovmen, c.getLines()));
    ArrayList<InfraredSensorSimulator> infras = new ArrayList<>();
    infras.add(InfraredSensorSimulator.createSmall(0.03, 0.06, (Math.PI / 2.0) / 9.0, moovmen, c.getWalls()));
    infras.add(InfraredSensorSimulator.createSmall(-0.03, 0.06, (Math.PI / 2.0) / -9.0, moovmen, c.getWalls()));
    super.setInfraredDistanceSensors(infras);
    super.setLineSensors(lineSensors);
  }

  public BigRobo(Segments lines, Segments walls) {
    this(new Court(lines, walls));
  }


}
