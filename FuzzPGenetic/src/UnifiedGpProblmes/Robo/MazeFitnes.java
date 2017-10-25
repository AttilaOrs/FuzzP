package UnifiedGpProblmes.Robo;

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

public class MazeFitnes extends AbstactFitness {

  private Court maze;
  private double commonCmd;
  private double diffCmd;
  private int allRun;
  public MazeFitnes(Court maze) {
    super(getProblemSpecification());
    this.maze = maze;
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
    double f = evalSimple(maze, exec, false);
    super.updateCreatureWithSimplification(creature, rez, rec);
    double multi2 = super.fireCountMulti(rec, allRun);
    return f * multi * multi2;
  }

  private double evalSimple(Court c, SyncronousUnifiedPetriExecutor exec, boolean chance) {
    exec.resetSimulator();

    commonCmd = 0.0;
    diffCmd = 0.0;
    BigRobo robo = new BigRobo(c);
    List<Optional<Double>> sensorsOut = Collections.nCopies(ps.getOuputCount(), Optional.empty());
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    int i;
    int finalTickNr = 301;
    int pain = 0;
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
        pain += 10;
      }

      commonCmd = 0.0;
      diffCmd = 0.0;
      if (i == 300) {
        PathResult smallRez = c.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(),0.07);
        if (smallRez.touchedAtAll > 30) {
          finalTickNr += 700;
        }
      }
    }
    PathResult pathRez = c.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(), 0.07);
    allRun += i;
    int toRet = pathRez.touchedInOrder - pain;
    if (toRet > 0) {
      return toRet +2.0;
    } else if (pathRez.touchedInOrder > 0){
      return 2.0 - 1.0 / pathRez.touchedInOrder;
    } else if (pathRez.touchedAtAll > 0) {
      return 1.0 - 1.0 / pathRez.touchedAtAll;
    }
    return 0.0;
  }

  public static ProblemSpecification getProblemSpecification() {

    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 5.0);
    inpScale.put(1, 5.0);
    inpScale.put(2, 5.0);
    inpScale.put(3, 5.0);
    inpScale.put(4, 5.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 10.0);
    outScale.put(1, 20.0);
    return new ProblemSpecificationImpl(10.0, 4, inpScale, outScale);
  }
}
