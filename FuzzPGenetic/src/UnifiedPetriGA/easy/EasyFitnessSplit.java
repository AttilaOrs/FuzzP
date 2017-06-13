package UnifiedPetriGA.easy;

import java.util.HashMap;
import java.util.Map;

import UnifiedPetriGA.BendingCreature;
import UnifiedPetriGA.mapper.BendingCreatureUTPNMapper;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import structure.ICreatureFitnes;

public class EasyFitnessSplit implements ICreatureFitnes<BendingCreature> {

  private static final double Wone = 0.5;
  private static final double Wtwo = 0.5;
  private testUnifiedPetriMaker maker;
  private BendingCreatureUTPNMapper mapper;

  public EasyFitnessSplit() {
    this.maker = new testUnifiedPetriMaker();
    this.mapper = new BendingCreatureUTPNMapper(maker.net, true);
  }

  double oT0;
  double oT1;
  private FullRecorder<UnifiedToken> rec;

  public FullRecorder<UnifiedToken> getRec() {
    return rec;
  }

  public double maxError = 0.0;
  public double maxPercent = 0.0;
  public double maxCarLost = 0.0;

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
    maxError = 0.0;
    maxPercent = 0.0;
    maxCarLost = 0.0;

    for (double i = 1.0; i < 40; i += 5.0) {
      for (double qq = 1.0; qq <= 10.0; qq += 1.0) {
        Map<Integer, UnifiedToken> inp = new HashMap<>();
        inp.put(maker.iP0, new UnifiedToken(i));
        inp.put(maker.iP1, new UnifiedToken(qq));
        exec.runTick(inp);
        if (oT1 == 0.0) {
          oT1 = 0.001;
        }
        sum += Wone * (oT0 / oT1 - qq) * (oT0 / oT1 - qq) + Wtwo * (i - oT0 - oT1) * (i - oT0 - oT1);
        double mm = Math.abs((oT0 / oT1 - qq));
        maxError = (mm > maxError) ? mm : maxError;
        double percent = Math.abs(1.0 - (oT0 + oT1) / i) * 100;
        maxPercent = (maxPercent > percent) ? maxPercent : percent;
        double carLost = Math.abs(i - oT0 - oT1);
        maxCarLost = (maxCarLost > carLost) ? maxCarLost : carLost;

      }

    }

    Map<Integer, UnifiedToken> inp = new HashMap<>();
    inp.put(maker.iP0, new UnifiedToken(40.0));
    inp.put(maker.iP1, new UnifiedToken(1.0));
    exec.runTick(inp);

    System.out.println(">>> 1.0");
    System.out.println(">>> e1: " + oT0);
    System.out.println(">>> e2: " + oT1);
    inp = new HashMap<>();
    inp.put(maker.iP0, new UnifiedToken(40.0));
    inp.put(maker.iP1, new UnifiedToken(10.0));
    exec.runTick(inp);

    System.out.println(">>> 10.0");
    System.out.println(">>> e1: " + oT0);
    System.out.println(">>> e1: " + oT1);

    return 1.0 / (1.0 + sum);
  }
}
