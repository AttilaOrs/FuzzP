package UnifiedPetriRuleOptimizer.PiFitness;

import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider.Result;
import UnifiedPetriRuleOptimizer.BitIndi;
import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import core.UnifiedPetriLogic.UnifiedPetriNet;
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
    PiSimulator sim = new PiSimulator(outTr, inp1, inp2, false);
    Result result = sim.simulate(net);


    /*
     * ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> scenarioSaver = new
     * ScenarioSaverLoader<>( UnifiedPetriNet.class);
     * scenarioSaver.setPetriNet(net); scenarioSaver.setFullRec(rec);
     * scenarioSaver.save(new File("testPi.json"));
     */

    /* prov.calcError(sys.getEvolution()) */

    double fi = 100.0 / (1.0 + 0.5 * result.error + 0.4 * result.changeSum + 0.1 * result.steadyStateError);

    return fi;
  }

}
