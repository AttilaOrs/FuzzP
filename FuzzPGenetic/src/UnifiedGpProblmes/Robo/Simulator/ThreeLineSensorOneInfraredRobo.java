package UnifiedGpProblmes.Robo.Simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;

public class ThreeLineSensorOneInfraredRobo implements IRobo {

  private static final double DEAD = 0.15;
  private final RoboMovmentSimulator moovmen;
  private final List<Point> visitedPoints;
  private final List<LineSensorsimulator> lineSensors;
  private final InfraredSensorSimulator infraredDistance;
  private ISegmentProvider walls;

  public ThreeLineSensorOneInfraredRobo(ISegmentProvider lines, ISegmentProvider walls) {
    moovmen = new RoboMovmentSimulator();
    visitedPoints = new ArrayList<>();
    lineSensors = new ArrayList<>();
    lineSensors.add(new LineSensorsimulator(0.00, 0.13, moovmen, lines));
    lineSensors.add(new LineSensorsimulator(0.02, 0.12, moovmen, lines));
    lineSensors.add(new LineSensorsimulator(-0.02, 0.12, moovmen, lines));
    infraredDistance = InfraredSensorSimulator.createSmall(0, 0.06, 0, moovmen, walls);
    this.walls = walls;
  }

  public boolean touchedTheWalls() {
    Point p = moovmen.position();
    for (Segment s : walls.getLineSegments()) {
      if (s.dist(p) < DEAD) {
        return true;
      }
    }
    return false;

  }

  @Override
  public List<Point> getVisitedPoints() {
    return visitedPoints;
  }

  @Override
  public List<LineSensorsimulator> getLineSensors() {
    return lineSensors;
  }

  @Override
  public RoboMovmentSimulator getRoboMoovmentSim() {
    return moovmen;
  }

  @Override
  public List<Optional<Double>> simulate(double commandR, double commandL) {
    moovmen.setLeftCommand(commandL);
    moovmen.setRightCommand(commandR);
    moovmen.simulateTimeUnit();
    visitedPoints.add(new Point(moovmen.getX(), moovmen.getY()));
    ArrayList<Optional<Double>> d = new ArrayList<>();
    for (LineSensorsimulator lineSens : this.lineSensors) {
      d.add(lineSens.isSomthingThere()?Optional.of(1.0):Optional.empty());
    }
    d.add(Optional.of(infraredDistance.currentValue()));
    return d;

  }

  @Override
  public List<InfraredSensorSimulator> getInfraredSesors() {
    return Arrays.asList(infraredDistance);
  }
}
