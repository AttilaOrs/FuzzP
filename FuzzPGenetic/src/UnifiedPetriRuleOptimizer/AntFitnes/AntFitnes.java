package UnifiedPetriRuleOptimizer.AntFitnes;

import UnifiedGpProblmes.ArtificalAnt.AntSimulator;
import UnifiedPetriRuleOptimizer.BitIndi;
import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import structure.ICreatureFitnes;

public class AntFitnes implements ICreatureFitnes<BitIndi> {

  private BitIndiDecoder decoder;
  private Integer outForward;
  private Integer outLeft;
  private Integer outRight;
  private Integer inp;

  public AntFitnes(BitIndiDecoder decoder, Integer outForwad, Integer outLeft, Integer outRight, Integer inp) {
    this.decoder = decoder;
    this.outForward = outForwad;
    this.outLeft = outLeft;
    this.outRight = outRight;
    this.inp = inp;
  }


  @Override
  public double evaluate(BitIndi creature) {
    UnifiedPetriNet net = decoder.modified(creature);
    AntSimulator sim = new AntSimulator();
    int steps = sim.simulate(outForward, outLeft, outRight, inp, net, null);
    return steps;
  }

}
