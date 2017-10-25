package UnifiedGpProblmes.Robo.Simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;

public abstract class AbstractRobo implements IRobo {

  private static final double DEAD = 0.07;
  protected final RoboMovmentSimulator moovmen;
  protected final List<Point> visitedPoints;

  protected void setLineSensors(List<LineSensorsimulator> lineSensors) {
    this.lineSensors = lineSensors;
  }

  protected void setInfraredDistanceSensors(List<InfraredSensorSimulator> infraredDistanceSensors) {
    this.infraredDistanceSensors = infraredDistanceSensors;
  }

  protected List<LineSensorsimulator> lineSensors;
  protected List<InfraredSensorSimulator> infraredDistanceSensors;
  protected final Court court;

  public AbstractRobo(Court c) {
    this.court = c; 
    visitedPoints = new ArrayList<>();
    moovmen = new RoboMovmentSimulator();
  }

  @Override
  public boolean touchedTheWalls() {
    Point p = moovmen.position();
    for (Segment s : court.getWalls().getLineSegments()) {
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
      d.add(lineSens.isSomthingThere() ? Optional.of(1.0) : Optional.empty());
    }
    for (InfraredSensorSimulator infra : infraredDistanceSensors) {
      d.add(Optional.of(infra.currentValue()));
    }
    return d;

  }

  @Override
  public List<InfraredSensorSimulator> getInfraredSesors() {
    return infraredDistanceSensors;
  }
}
