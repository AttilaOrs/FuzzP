package UnifiedPetriGA.easy;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.HashMap;
import java.util.Map;

import UnifiedPetriGA.BendingCreature;
import UnifiedPetriGA.mapper.BendingCreatureUTPNMapper;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import structure.ICreatureFitnes;

public class EasyFitness implements ICreatureFitnes<BendingCreature> {

  private testUnifiedPetriMaker maker;
  private BendingCreatureUTPNMapper mapper;

  public EasyFitness() {
    this.maker = new testUnifiedPetriMaker();
    this.mapper = new BendingCreatureUTPNMapper(maker.net, false);
  }

  double oT0;
  double oT1;
  private FullRecorder<UnifiedToken> rec;

  public FullRecorder<UnifiedToken> getRec() {
    return rec;
  }

  @Override
  public double evaluate(BendingCreature creature) {
    oT0 = 0.0;
    oT1 = 0.0;

    UnifiedPetriNet net = mapper.decode(creature);
    net.addActionForOuputTransition(maker.oT0, d -> {
      oT0 = d.getValue();
    });
    net.addActionForOuputTransition(maker.oT1, d -> oT1 = d.getValue());
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(net);
    rec = new FullRecorder<>();
    exec.setRecorder(rec);
    double sum = 0.0;

    for (int i = 0; i < 50; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      double ii = 10.0 * sin(i / 10.0);
      double ii2 = 10.0 * cos(i / 10.0);
      inp.put(maker.iP0, new UnifiedToken(ii));
      inp.put(maker.iP1, new UnifiedToken(ii2));
      exec.runTick(inp);

      sum += abs(ii - oT0);
      sum += abs(ii2 - oT1);

    }
    return 1.0 / (1.0 + sum);
  }

}
