package UnifiedPetriRuleOptimizer.Robo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.InfraOnlyRobo;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider.PathResult;
import UnifiedPetriRuleOptimizer.BitIndi;
import UnifiedPetriRuleOptimizer.BitIndiDecoder;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class MazeFitnes implements ICreatureFitnes<BitIndi> {

  private BitIndiDecoder decoder;
  private int[] inpPlaceIds;
  private int[] outTransitionIds;
  private Court maze;
  private Double commonCmd;
  private Double diffCmd;
  private static final double dist = 0.04;

  public MazeFitnes(BitIndiDecoder decoder, int[] inpPlaceIds, int[] outTransitionIds, Court maze) {
    this.decoder = decoder;
    this.inpPlaceIds = inpPlaceIds;
    this.outTransitionIds = outTransitionIds;
    this.maze = maze;
    assert (inpPlaceIds.length == 5);
    assert (outTransitionIds.length == 2);
  }

  @Override
  public double evaluate(BitIndi creature) {

    int allRun = 0;
    UnifiedPetriNet net = decoder.modified(creature);
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    net.addActionForOuputTransition(outTransitionIds[0], t -> commonCmd = t.getValue());
    net.addActionForOuputTransition(outTransitionIds[1], t -> diffCmd = t.getValue());
    InfraOnlyRobo robo = new InfraOnlyRobo(maze);
    List<Optional<Double>> sensorsOut = Collections.nCopies(5, Optional.empty());
    Map<Integer, UnifiedToken> inp = new HashMap<>();

    int i;
    int finalTickNr = 301;
    int pain = 0;
    for (i = 0; i < finalTickNr; i++) {
      inp.clear();
      for (int ii = 0; ii < sensorsOut.size(); ii++) {
        if (inpPlaceIds[ii] != -1) {
          inp.put(inpPlaceIds[ii], UnifiedToken.fromOptional(sensorsOut.get(ii)));
        }

      }
      exec.runTick(inp);

      double commandR = commonCmd + diffCmd / 2.0;
      double commandL = commonCmd - diffCmd / 2.0;
      sensorsOut = robo.simulate(commandR, commandL);
      if (robo.touchedTheWalls()) {
        pain += 10;
      }

      commonCmd = 0.0;
      diffCmd = 0.0;
      if (i == 300) {
        PathResult smallRez = maze.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(), dist);
        if (smallRez.touchedInOrder > 30) {
          finalTickNr += 700;
        }
      }
      if (i == 1000) {
        PathResult smallRez = maze.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(), dist);
        if (smallRez.touchedInOrder > 80) {
          finalTickNr += 1000;
        }
      }
    }
    PathResult pathRez = maze.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(), dist);
    allRun += i;
    int toRet = pathRez.touchedInOrder - pain;
    if (toRet > 0) {
      return toRet + 2.0;
    } else if (pathRez.touchedInOrder > 0) {
      return 2.0 - 1.0 / pathRez.touchedInOrder;
    } else if (pathRez.touchedAtAll > 0) {
      return 1.0 - 1.0 / pathRez.touchedAtAll;
    }
    return 0.0;

  }

}
