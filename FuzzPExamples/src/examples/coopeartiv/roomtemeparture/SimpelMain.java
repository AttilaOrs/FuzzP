package examples.coopeartiv.roomtemeparture;

import java.util.DoubleSummaryStatistics;

import Main.FuzzyPVizualzer;
import Main.Plotter;
import View.MainView;
import examples.coopeartiv.roomtemeparture.components.TermostatComponent;
import examples.coopeartiv.roomtemeparture.components.WaterTankControllerComponent;
import examples.coopeartiv.roomtemeparture.model.Plant;
import examples.coopeartiv.roomtemeparture.model.Scenario;

public class SimpelMain {

  private static final int SIM_PERIOD = 10;

  public static void main(String[] args) {
    Scenario scenario = Scenario.extremeEvening();
    Plant plant = new Plant(SIM_PERIOD, scenario);
    WaterTankControllerComponent tankController = new WaterTankControllerComponent(plant, SIM_PERIOD);
    TermostatComponent termostat = new TermostatComponent(plant, SIM_PERIOD);

    termostat.start();
    tankController.start();
    plant.start();
    double waterRefTemp = 48.0;
    double roomTemperature = 24.0;

    for (int i = 0; i < scenario.getScenarioLength(); i++) {
      tankController.setWaterRefTemp(waterRefTemp);
      tankController.setTankWaterTemp(plant.getTankWaterTemperature());
      termostat.setInput(roomTemperature, plant.getRoomTemperature());
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    tankController.stop();
    termostat.stop();

    MainView windowTankController = FuzzyPVizualzer.visualize(tankController.getNet(),
        tankController.getRecorder());
    MainView windowTermostat = FuzzyPVizualzer.visualize(termostat.getNet(), termostat.getRecorder());
    Plotter plotterTemperatureLog = new Plotter(plant.getTemeartureLogs());
    Plotter plotterCommandLog = new Plotter(plant.getCommandLogs());
    windowTankController.addInteractivePanel("TempLogs", plotterTemperatureLog.makeInteractivePlot());
    windowTermostat.addInteractivePanel("TempLogs", plotterTemperatureLog.makeInteractivePlot());
    windowTankController.addInteractivePanel("ComandLogs", plotterCommandLog.makeInteractivePlot());
    windowTermostat.addInteractivePanel("ComandLogs", plotterCommandLog.makeInteractivePlot());

    DoubleSummaryStatistics tankStats = plant.getTemeartureLogs().get("tankTemp").stream().mapToDouble(d -> d)
        .summaryStatistics();
    DoubleSummaryStatistics roomStats = plant.getTemeartureLogs().get("roomTemp").stream().mapToDouble(d -> d)
        .summaryStatistics();
    System.out.println("max tank temp :" + tankStats.getMax());
    System.out.println("min tank temp :" + tankStats.getMin());
    System.out.println("avg tank temp :" + tankStats.getAverage());
    System.out.println("max room temp :" + roomStats.getMax());
    System.out.println("min room temp :" + roomStats.getMin());
    System.out.println("avg room temp :" + roomStats.getAverage());
    System.out.println("heater on ratio:" + plant.heatingOnRatio());
    System.out.println("max continous heating on:" + plant.maxContiniousHeaterOn());
    System.out.println("all consunption ::" + plant.gasConsumption());
    System.out.println("avg consunption in min ::" + plant.gasConsumption() / scenario.getScenarioLength());

  }

}
