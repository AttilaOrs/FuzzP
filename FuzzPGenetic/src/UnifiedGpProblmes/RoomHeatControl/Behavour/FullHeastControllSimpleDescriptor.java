package UnifiedGpProblmes.RoomHeatControl.Behavour;

import static UnifiedGpProblmes.RoomHeatControl.FullControllMultiScenarioFitness.getProblemSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import UnifiedGp.AbstactFitness;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController.Control;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullSimulator;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullSimulator.Rezult;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.behaviour.IBeahviourDescriptor;

public class FullHeastControllSimpleDescriptor extends AbstactFitness
    implements
      IBeahviourDescriptor<FullHeatControllSimpleDescription, UnifiedGpIndi> {

  private List<RoomScenario> scenarios;

  public FullHeastControllSimpleDescriptor(List<RoomScenario> scenarios) {
    super(getProblemSpecification());
    this.scenarios = scenarios;
  }

  FullHeatController.ControllEvent lastEvent = FullHeatController.ControllEvent.None;
  double gasCmd = 0.0;
  @Override
  public FullHeatControllSimpleDescription evaluate(UnifiedGpIndi creature) {
    int size = creature.getSizes().size();
    double multi = sizeMulti(size);
    if (multi == 0.0) {
      return new FullHeatControllSimpleDescription(0, 0, 1000, 0.0, size);
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
    int nrOfIncorrectState = 0;
    int waterOffLimit = 0;
    int allTick = 0;

    for (RoomScenario scenario : scenarios) {
      FullSimulator sim = new FullSimulator(scenario);
      exec.resetSimulator();
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
      nrOfIncorrectState += simRez.roomIncorrectState;
      waterOffLimit += simRez.waterOffLimit;
      allTick += scenario.getScenarioLength();
    }

    super.updateCreatureWithSimplification(creature, rez, rec);
    double multi2 = super.fireCountMulti(rec, allTick);
    return new FullHeatControllSimpleDescription(nrOfIncorrectState, waterOffLimit, allTick, multi2, size);
  }

}
