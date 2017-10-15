package UnifiedGpProblmes.Robo.Simulator;

import java.util.List;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;

public interface IRobo {

  List<Point> getVisitedPoints();

  List<LineSensorsimulator> getLineSensors();

  List<InfraredSensorSimulator> getInfraredSesors();

  RoboMovmentSimulator getRoboMoovmentSim();

  List<Optional<Double>> simulate(double commandR, double commandL);

}