package UnifiedGpProblmes.RoomHeatControl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import UnifiedGp.AbstactFitness;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomController;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomSimulator;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomSimulator.Rezult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class RoomFitnessMultiScenario extends AbstactFitness implements ICreatureFitnes<UnifiedGpIndi> {

  private List<RoomScenario> scenarios;

  public RoomFitnessMultiScenario(List<RoomScenario> scenarios) {
    super(RoomFitnes.getProblemSpecification());
    this.scenarios = scenarios;
  }

  RoomController.ControllEvent lastEvent = RoomController.ControllEvent.None;
  @Override
  public double evaluate(UnifiedGpIndi creature) {
    double multi = sizeMulti(creature.getSizes().size());
    if (multi == 0.0) {
      return 0.0;
    }
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    rez = super.convert(creature);
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(rec);

    rez.addActionIfPossible(0, c -> lastEvent = RoomController.ControllEvent.StartHeating);
    rez.addActionIfPossible(1, c -> lastEvent = RoomController.ControllEvent.StopHeating);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    int nrOfIncorrectState = 0;
    int allTick = 0;
    
    for (RoomScenario scenario : scenarios) {
      RoomSimulator sim = new RoomSimulator(scenario);
      exec.resetSimulator();
      Rezult simRez = sim.simulate((temp, ref) -> {
        lastEvent = RoomController.ControllEvent.None;
        inp.clear();
        rez.addToInpIfPossible(inp, 0, new UnifiedToken(temp));
        rez.addToInpIfPossible(inp, 1, new UnifiedToken(ref));
        exec.runTick(inp);
        return lastEvent;
      });
      nrOfIncorrectState += simRez.incorrectState;
      allTick += scenario.getScenarioLength();
    }

    super.updateCreatureWithSimplification(creature, rez, rec);
    double multi2 = super.fireCountMulti(rec, allTick);
    return (allTick - nrOfIncorrectState) * multi * multi2;
  }

}
