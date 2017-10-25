package UnifiedGpProblmes.Robo.Simulator;

import java.util.ArrayList;
import java.util.List;

import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;

public class InfraOnlyRobo extends AbstractRobo {

  public InfraOnlyRobo(Court c) {
    super(c);
    setLineSensors(new ArrayList<>());
    List<InfraredSensorSimulator> infras = new ArrayList<>();
    infras.add(InfraredSensorSimulator.createSmall(0.03, 0.06, (Math.PI / 2.0) / 9.0, moovmen, c.getWalls()));
    infras.add(InfraredSensorSimulator.createSmall(-0.03, 0.06, (Math.PI / 2.0) / -9.0, moovmen, c.getWalls()));
    infras.add(InfraredSensorSimulator.createSmall(0.05, 0.05, (Math.PI / 4.0), moovmen, c.getWalls()));
    infras.add(InfraredSensorSimulator.createSmall(-0.05, 0.05, (Math.PI / -4.0), moovmen, c.getWalls()));
    infras.add(InfraredSensorSimulator.createSmall(0, 0.07, 0, moovmen, c.getWalls()));
    setInfraredDistanceSensors(infras);
  }

}
