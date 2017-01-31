package examples.async.basin;

import Main.FuzzyPVizualzer;
import Main.Plotter;
import View.MainView;

public class Main {
  private static final int SIM_PERIOD = 10;

  public static void main(String[] args) throws InterruptedException {
    Basin bs = new Basin();
    Scenario sc = Scenario.generateScenario(200);

    BasinController controller = new BasinController(bs, SIM_PERIOD);
    controller.start();
    
    for (int tick = 0; tick < sc.getScenarioLength(); tick++) {
      controller.setWater(bs.getWater());
      bs.updateSystem(sc.getDisturbanceForTick(tick));
      Thread.sleep(SIM_PERIOD);
    }
    controller.stop();

    MainView window = FuzzyPVizualzer.visualize(controller.getNet(), controller.getRecorder());
    Plotter plotterWater = new Plotter(bs.getSavedLogs());
    window.addInteractivePanel("Water", plotterWater.makeInteractivePlot());



  }

}
