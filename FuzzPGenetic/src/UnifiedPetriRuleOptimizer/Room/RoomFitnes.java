package UnifiedPetriRuleOptimizer.Room;

import java.util.HashMap;
import java.util.Map;

import AlgoImpl.IterationLogger;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomController;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomSimulator;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomSimulator.Rezult;
import UnifiedPetriRuleOptimizer.BitIndi;
import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class RoomFitnes implements ICreatureFitnes<BitIndi> {

  private BitIndiDecoder decoder;
  private int[] inpPlaceIds;
  private int[] outTransitionIds;
  private RoomScenario scecario;
  private IterationLogger logger = null;

  public RoomFitnes(BitIndiDecoder decoder, int inps[], int outs[], RoomScenario sc) {
    this.inpPlaceIds = inps;
    this.outTransitionIds = outs;
    this.decoder = decoder;
    this.scecario = sc;
    assert (inps.length == 2);
    assert (outs.length == 2);
  }

  RoomController.ControllEvent lastEvent = RoomController.ControllEvent.None;

  @Override
  public double evaluate(BitIndi creature) {
    UnifiedPetriNet net = decoder.modified(creature);
    Rezult simRez = simulateNet(net);


    double f = 0.7 / (1.0 + simRez.incorrectState) + 0.3 / (1.0 + simRez.offLimit);

    return f;
  }

  public Rezult simulateNet(UnifiedPetriNet net) {
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);

    net.addActionForOuputTransition(outTransitionIds[0], t -> lastEvent = RoomController.ControllEvent.StartHeating);
    net.addActionForOuputTransition(outTransitionIds[1], t -> lastEvent = RoomController.ControllEvent.StopHeating);
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    RoomSimulator sim = new RoomSimulator(scecario);
    sim.setIterationLogger(logger);

    Rezult simRez = sim.simulate((temp, ref) -> {
      lastEvent = RoomController.ControllEvent.None;
      inp.clear();
      inp.put(inpPlaceIds[0], new UnifiedToken(temp));
      inp.put(inpPlaceIds[1], new UnifiedToken(ref));
      exec.runTick(inp);
      return lastEvent;
    });
    return simRez;
  }

  public void setLogger(IterationLogger logger) {
    this.logger = logger;
  }

}
