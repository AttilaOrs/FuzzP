package UnifiedGpProblmes.Robo.Simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;

public class TwoSensorLineFallowerRobot {
  
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
  
  
  public boolean[] simulate(double commandR, double commandL) {
    moovmen.setLeftCommand(commandL);
    moovmen.setRightCommand(commandR);
    moovmen.simulateTimeUnit();
    visitedPoints.add(new Point(moovmen.getX(), moovmen.getY()));
    return new boolean[] { fi.isSomthingThere(), se.isSomthingThere() };
  }
  
  public RoboMovmentSimulator getRoboMoovmentSim() {
    return this.moovmen;
  }

  public List<LineSensorsimulator> getSensors() {
    return Arrays.asList(fi, se);
  }
  
  public List<Point> getVisitedPoints(){
    return visitedPoints;
  }

}
