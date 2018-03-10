package UnifiedGpProblmes.RoomHeatControl;

import java.util.HashMap;
import java.util.Map;

import AlgoImpl.IterationLogger;
import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
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

public class RoomFitnes extends AbstactFitness implements ICreatureFitnes<UnifiedGpIndi> {

  private RoomScenario scenario;
  private IterationLogger logger = null;

  public RoomFitnes(RoomScenario sc) {
    super(getProblemSpecification());
    this.scenario = sc;
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
    RoomSimulator sim = new RoomSimulator(scenario);
    sim.setIterationLogger(logger);
    Rezult simRez = sim.simulate((temp, ref) -> {
      lastEvent = RoomController.ControllEvent.None;
      inp.clear();
      rez.addToInpIfPossible(inp, 0, new UnifiedToken(temp));
      rez.addToInpIfPossible(inp, 1, new UnifiedToken(ref));
      exec.runTick(inp);
      return lastEvent;
    });

    double multi2 = super.fireCountMulti(rec, scenario.getScenarioLength());
    double f = 0.7 / (1.0 + simRez.incorrectState) + 0.3 / (1.0 + simRez.offLimit);
    return f * multi2 * multi;
  }

  public static ProblemSpecification getProblemSpecification() {

    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 30.0);
    inpScale.put(1, 30.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 1.0);
    outScale.put(1, 2.0);
    return new ProblemSpecificationImpl(10.0, 4, inpScale, outScale);
  }

  public void setLogger(IterationLogger logger) {
    this.logger = logger;
  }
}
