package UnifiedGpProblmes.RoomHeatControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AlgoImpl.IterationLogger;
import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController.Control;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController.ControllEvent;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullSimulator;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullSimulator.Rezult;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class FullControllMultiScenarioFitness extends AbstactFitness implements ICreatureFitnes<UnifiedGpIndi> {

  private List<RoomScenario> scenarios;
  private boolean saveBehave = false;
  private List<IterationLogger> loggers;

  public FullControllMultiScenarioFitness(List<RoomScenario> scenarios) {
    super(getProblemSpecification());
    this.scenarios = scenarios;
  }

  public void setSaveBehaviour(boolean save) {
    this.saveBehave = save;
  }
  public List<IterationLogger> getLoggers() {
    return this.loggers;
  }

  FullHeatController.ControllEvent lastEvent = FullHeatController.ControllEvent.None;
  double gasCmd = 0.0;
  @Override
  public double evaluate(UnifiedGpIndi creature) {
    double multi = sizeMulti(creature.getSizes().size());
    if (multi == 0.0) {
      return 0.0;
    }

    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    rez = super.convert(creature);
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net, () -> new TokenCacheDisabling<>(5)), false, true);
    exec.setRecorder(rec);
    rez.addActionIfPossible(0, c -> lastEvent = FullHeatController.ControllEvent.StartHeating);
    rez.addActionIfPossible(1, c -> lastEvent = FullHeatController.ControllEvent.StopHeating);
    rez.addActionIfPossible(2, c -> gasCmd = c.getValue());

    Map<Integer, UnifiedToken> inp = new HashMap<>();
    int nrOfInGoodState = 0;
    int waterOffLimit = 0;
    int allTick = 0;
    if (saveBehave) {
      loggers = new ArrayList<>();
    }

    for (RoomScenario scenario : scenarios) {
      FullSimulator sim = new FullSimulator(scenario);
      exec.resetSimulator();
      if (saveBehave) {
        IterationLogger logger = new IterationLogger();
        loggers.add(logger);
        sim.setIterationLogger(logger);
      }
      Rezult simRez = sim.simulate((sensorReading, roomTempRef, waterTemp, waterRef) -> {
        lastEvent = FullHeatController.ControllEvent.None;
        gasCmd = 0.0;
        inp.clear();
        rez.addToInpIfPossible(inp, 0, new UnifiedToken(sensorReading));
        rez.addToInpIfPossible(inp, 1, new UnifiedToken(roomTempRef));
        rez.addToInpIfPossible(inp, 2, new UnifiedToken(waterTemp));
        rez.addToInpIfPossible(inp, 3, new UnifiedToken(waterRef));
        exec.runTick(inp);
        
        return new Control(lastEvent, gasCmd);
      });
      nrOfInGoodState += simRez.roomInGoodState;
      waterOffLimit += simRez.waterOffLimit;
      allTick += scenario.getScenarioLength();
    }

    super.updateCreatureWithSimplification(creature, rez, rec);
    double multi2 = super.fireCountMulti(rec, allTick);
    return (nrOfInGoodState + (allTick - waterOffLimit)) * multi * multi2;
  }

  public FullControllMultiScenarioFitness(ProblemSpecification ps, List<RoomScenario> scenarios,
      ControllEvent lastEvent, double gasCmd) {
    super(ps);
    this.scenarios = scenarios;
    this.lastEvent = lastEvent;
    this.gasCmd = gasCmd;
  }

  public static ProblemSpecification getProblemSpecification() {

    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 30.0);
    inpScale.put(1, 30.0);
    inpScale.put(2, 60.0);
    inpScale.put(3, 60.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 1.0);
    outScale.put(1, 1.0);
    outScale.put(2, 1.0);
    return new ProblemSpecificationImpl(10.0, 4, inpScale, outScale);
  }

}
