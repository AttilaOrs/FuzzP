package UnifiedGpProblmes.Robo;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGpProblmes.Robo.Simulator.BigRobo;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider.PathResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class MultiCourtFitness extends AbstactFitness {

  private static final List<Integer> simpleCheckpoints = Arrays.asList(0, 5, 10);
  private static final List<Integer> bigCheckpoint = Arrays.asList(0, 5, 10, 15, 20, 30, 40, 55, 70, 90, 110, 130);
  private Court first;
  private Court second;
  private Court third;
  private Court fourth;
  private Double commonCmd;
  private Double diffCmd;
  private int allRun;

  public MultiCourtFitness(Court first, Court second, Court third, Court fourth) {
    super(getProblemSpecification());
    this.first = first;
    this.second = second;
    this.third = third;
    this.fourth = fourth;
    this.allRun = 0;
  }

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    allRun = 0;
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    rez = super.convert(creature);
    int size = creature.getSizes().size();
    double multi = sizeMulti(size);
    if (multi == 0.0) {
      return 0.0;
    }
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(rec);
    rez.addActionIfPossible(0, i -> commonCmd = i.getValue());
    rez.addActionIfPossible(1, i -> diffCmd = i.getValue());
    int f = evalSimple(second, exec, simpleCheckpoints, false);
    int s = evalSimple(third, exec, simpleCheckpoints, false);
    int w = 0;
    int q = 0;
    if (f + s > 45) {
      w = evalSimple(fourth, exec, simpleCheckpoints, true);
    }
    if (f + s + w > 70) {
      q = evalSimple(first, exec, bigCheckpoint, true);
    }
    super.updateCreatureWithSimplification(creature, rez, rec);
    double multi2 = super.fireCountMulti(rec, allRun);
    return (f + w + s + q) * multi * multi2;

  }

  private int evalSimple(Court c, SyncronousUnifiedPetriExecutor exec, List<Integer> checkpoints, boolean chance) {
    exec.resetSimulator();

    commonCmd = 0.0;
    diffCmd = 0.0;
    BigRobo robo = new BigRobo(c);
    List<Optional<Double>> sensorsOut = Collections.nCopies(ps.getOuputCount(), Optional.empty());
        Map<Integer, UnifiedToken> inp = new HashMap<>();
    int i;
    int finalTickNr = 100;
    for (i = 0; i < finalTickNr; i++) {
      inp.clear();
      for (int ii = 0; ii < sensorsOut.size(); ii++) {
        rez.addToInpIfPossible(inp, ii, UnifiedToken.fromOptional(sensorsOut.get(ii)));
      }
      try {
        exec.runTick(inp);
      } catch (Throwable t) {
        throw t;
      }

      double commandR = commonCmd + diffCmd / 2.0;
      double commandL = commonCmd - diffCmd / 2.0;
      sensorsOut = robo.simulate(commandR, commandL);
      if (robo.touchedTheWalls()) {
        break;
      }

      commonCmd = 0.0;
      diffCmd = 0.0;
      if (i % 100 == 0) {
        PathResult l = c.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints());
        int ii = (i / 200);
        if (ii < checkpoints.size()) {
          if (l.touchedAtAll >= checkpoints.get(ii)) {
            finalTickNr += 100;
            chance = true;
          } else if (chance) {
            chance = false;
            finalTickNr += 200;
          }
        }
      }
    }
    PathResult pathRez = c.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(), 0.07);
    allRun += i;
    return pathRez.touchedInOrder;
  }


  public static ProblemSpecification getProblemSpecification() {

    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 1.0);
    inpScale.put(1, 1.0);
    inpScale.put(2, 1.0);
    inpScale.put(3, 1.0);
    inpScale.put(4, 1.0);
    inpScale.put(5, 5.0);
    inpScale.put(6, 5.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 10.0);
    outScale.put(1, 20.0);
    return new ProblemSpecificationImpl(10.0, 4, inpScale, outScale);
  }

}
