package UnifiedPetriRuleOptimizer.PiFitness;

import java.util.HashMap;
import java.util.Map;

import UnifiedGpProblmes.FirstOrderSystem.FirtsOrderSystem;
import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider;
import UnifiedPetriRuleOptimizer.BitIndi;
import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FullRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class FirstOrderFitnes implements ICreatureFitnes<BitIndi> {

  private BitIndiDecoder decoder;
  private Integer outTr;
  private Integer inp1;
  private Integer inp2;

  public FirstOrderFitnes(BitIndiDecoder decoder, Integer outTr, Integer inp1, Integer inp2) {
    this.decoder = decoder;
    this.outTr = outTr;
    this.inp1 = inp1;
    this.inp2 = inp2;
  }

  @Override
  public double evaluate(BitIndi creature) {
    UnifiedPetriNet net = decoder.modified(creature);
    FirtsOrderSystem sys = new FirtsOrderSystem(0.67, 0.35, 0.87, 0.22);
    ReferenceProvider prov = new ReferenceProvider(3);

    net.addActionForOuputTransition(outTr, d -> {
      sys.setCommand(d.getValue());
    });

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    exec.setRecorder(rec);

    Map<Integer, UnifiedToken> inp = new HashMap<>();
    for (int i = 0; i < prov.getRefSize(); i++) {
      inp.clear();
      inp.put(inp1, new UnifiedToken(sys.curentStatus()));
      inp.put(inp2, new UnifiedToken(prov.getReference(i)));
      exec.runTick(inp);
      sys.runTick();
    }


    /*
     * ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> scenarioSaver = new
     * ScenarioSaverLoader<>( UnifiedPetriNet.class);
     * scenarioSaver.setPetriNet(net); scenarioSaver.setFullRec(rec);
     * scenarioSaver.save(new File("testPi.json"));
     */


    return 100.0 / (1.0 + prov.calcError(sys.getEvolution()));
  }

}
