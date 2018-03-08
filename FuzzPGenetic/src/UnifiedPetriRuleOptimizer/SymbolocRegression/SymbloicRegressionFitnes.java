package UnifiedPetriRuleOptimizer.SymbolocRegression;

import java.util.HashMap;
import java.util.Map;

import UnifiedGpProblmes.SymbolicRegression.FuntionSimulator;
import UnifiedPetriRuleOptimizer.BitIndi;
import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class SymbloicRegressionFitnes implements ICreatureFitnes<BitIndi> {

  private BitIndiDecoder decoder;
  private Integer inpId;
  private Integer out;
  private Double lastRez;

  public SymbloicRegressionFitnes(BitIndiDecoder decoder, Integer inp, Integer out) {
    this.decoder = decoder;
    this.inpId = inp;
    this.out = out;
  }


  @Override
  public double evaluate(BitIndi creature) {
    UnifiedPetriNet net = decoder.modified(creature);

    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);

    net.addActionForOuputTransition(out, d -> this.lastRez = d.getValue());
    FuntionSimulator sim = new FuntionSimulator();
    Double sum = sim.compare(d -> {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(inpId, new UnifiedToken(d));
      exec.runTick(inp);
      return lastRez;
    });
    return 1.0 / (1.0 + sum);
  }

}
