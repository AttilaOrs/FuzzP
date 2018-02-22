package UnifiedPetriRuleOptimizer.PiFitness;

import java.util.HashMap;
import java.util.Map;

import UnifiedGpProblmes.FirstOrderSystem.FirtsOrderSystem;
import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider;
import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider.Result;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FullRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class PiSimulator {

  private Integer outTr;
  private Integer inp1;
  private Integer inp2;
  private boolean saveScenario;
  private FullRecorder<UnifiedToken> rec;

  public PiSimulator(Integer outTr, Integer inp1, Integer inp2, boolean saveScenario) {
    this.outTr = outTr;
    this.inp1 = inp1;
    this.inp2 = inp2;
    this.saveScenario = saveScenario;
  }

  public Result simulate(UnifiedPetriNet net) {
    FirtsOrderSystem sys = new FirtsOrderSystem(0.67, 0.35, 0.87, 0.22);
    ReferenceProvider prov = new ReferenceProvider(3);

    net.addActionForOuputTransition(outTr, d -> {
      sys.setCommand(d.getValue());
    });

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    if (saveScenario) {
      rec = new FullRecorder<>();
      exec.setRecorder(rec);
    }

    Map<Integer, UnifiedToken> inp = new HashMap<>();
    for (int i = 0; i < prov.getRefSize(); i++) {
      inp.clear();
      inp.put(inp1, new UnifiedToken(sys.curentStatus()));
      inp.put(inp2, new UnifiedToken(prov.getReference(i)));
      exec.runTick(inp);
      sys.runTick();
    }

    return prov.calcResult(sys.getEvolution());
  }

  public FullRecorder<UnifiedToken> getRecorder() {
    if (rec == null) {
      System.err.println("The recording was disabled");
    }
    return rec;
  }


}
