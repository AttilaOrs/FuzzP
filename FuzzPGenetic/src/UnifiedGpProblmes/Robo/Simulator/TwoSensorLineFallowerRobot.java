package UnifiedGpProblmes.Robo.Simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;

public class TwoSensorLineFallowerRobot implements IRobo {
  
  private final RoboMovmentSimulator moovmen;
  private final LineSensorsimulator fi;
  private final LineSensorsimulator se;
  private final List<Point> visitedPoints;
  

  public TwoSensorLineFallowerRobot(ISegmentProvider p) {
    moovmen = new RoboMovmentSimulator();
    fi = new LineSensorsimulator(0.02, 0.12, moovmen, p);
    se = new LineSensorsimulator(-0.02, 0.12, moovmen, p);
    visitedPoints = new ArrayList<>();
  }
  
  
  @Override
  public List<Optional<Double>> simulate(double commandR, double commandL) {
    moovmen.setLeftCommand(commandL);
    moovmen.setRightCommand(commandR);
    moovmen.simulateTimeUnit();
    visitedPoints.add(new Point(moovmen.getX(), moovmen.getY()));
    return Arrays.asList(fi.isSomthingThere() ? Optional.of(1.0) : Optional.empty(),
        se.isSomthingThere() ? Optional.of(1.0) : Optional.empty());
  }
  
  @Override
  public RoboMovmentSimulator getRoboMoovmentSim() {
    return this.moovmen;
  }

  @Override
  public List<LineSensorsimulator> getLineSensors() {
    return Arrays.asList(fi, se);
  }
  
  @Override
  public List<Point> getVisitedPoints(){
    return visitedPoints;
  }

  @Override
  public List<InfraredSensorSimulator> getInfraredSesors() {
    return Collections.EMPTY_LIST;
  }

}
