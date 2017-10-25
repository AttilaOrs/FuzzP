package UnifiedGpProblmes.Robo.Simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;

public class FiveSensorLineFollowerRobot implements IRobo {

  private final RoboMovmentSimulator moovmen;
  private final List<Point> visitedPoints;
  private final List<LineSensorsimulator> sensors;

  public FiveSensorLineFollowerRobot(ISegmentProvider p) {
    moovmen = new RoboMovmentSimulator();
    visitedPoints = new ArrayList<>();
    sensors = new ArrayList<>();
    sensors.add(new LineSensorsimulator(0.00, 0.13, moovmen, p));
    sensors.add(new LineSensorsimulator(0.02, 0.12, moovmen, p));
    sensors.add(new LineSensorsimulator(-0.02, 0.12, moovmen, p));
    sensors.add(new LineSensorsimulator(0.04, 0.11, moovmen, p));
    sensors.add(new LineSensorsimulator(-0.04, 0.11, moovmen, p));
  }

  @Override
  public List<Optional<Double>> simulate(double commandR, double commandL) {
    moovmen.setLeftCommand(commandL);
    moovmen.setRightCommand(commandR);
    moovmen.simulateTimeUnit();
    visitedPoints.add(new Point(moovmen.getX(), moovmen.getY()));
    return sensors.stream().map(FiveSensorLineFollowerRobot::mm)
        .collect(Collectors.toList());
  }

  private static Optional<Double> mm(LineSensorsimulator s) {
    return s.isSomthingThere() ? Optional.of(1.0) : Optional.empty();

  }

  @Override
  public RoboMovmentSimulator getRoboMoovmentSim() {
    return this.moovmen;
  }

  @Override
  public List<LineSensorsimulator> getLineSensors() {
    return sensors;
  }

  @Override
  public List<Point> getVisitedPoints() {
    return visitedPoints;
  }

  @Override
  public List<InfraredSensorSimulator> getInfraredSesors() {
    return Collections.EMPTY_LIST;
  }

  public boolean touchedTheWalls() {
    return false;
  }

}
