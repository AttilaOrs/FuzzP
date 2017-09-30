package UnifiedGpProblmes.Robo.Simulator;

import java.util.List;
import java.util.stream.Collectors;

import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;

public interface IRobo {

  List<Point> getVisitedPoints();

  List<LineSensorsimulator> getSensors();

  RoboMovmentSimulator getRoboMoovmentSim();

  List<Boolean> simulate(double commandR, double commandL);

}
