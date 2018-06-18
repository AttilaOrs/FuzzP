package UnifiedGpProblmes.Robo.Behaviour;


import java.nio.file.Watchable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import UnifiedGp.Behaviour.BinaryDescritor;
import UnifiedGp.Behaviour.InputOutputRecorder;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGpProblmes.Robo.Fitnesses.MazeFitnes;
import UnifiedGpProblmes.Robo.Simulator.InfraOnlyRobo;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider.PathResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.recoder.MultiRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.behaviour.IBeahviourDescriptor;

public class RoboBehaviourBinaryDescriptor extends BinaryDescritor<RoboBihaviourBinaryDescription, UnifiedGpIndi> implements IBeahviourDescriptor<RoboBihaviourBinaryDescription, UnifiedGpIndi>{

  private static final double dist = 0.4;

  private Court maze;

  private double commonCmd;
  private double diffCmd;
  private int allRun;

  private InputOutputRecorder recc;
  public RoboBehaviourBinaryDescriptor(Court maze) {
    super(MazeFitnes.getProblemSpecification(), 3, RoboBihaviourBinaryDescription::new);
    this.maze = maze;
  }

  @Override
  public RoboBihaviourBinaryDescription evaluate(UnifiedGpIndi creature) {
    allRun = 0;
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    recc = new InputOutputRecorder();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(rec, recc));
    rez = super.convert(creature);
    int size = creature.getSizes().size();
    double multi = sizeMulti(size);
    if (multi == 0.0) {
      RoboBihaviourBinaryDescription w = super.createDescprition(InputOutputRecorder.fakeRecorder(2000));
      w.setSegmentTouchedData(-1, -1, -1);
      w.setSize(size);
      return w;
    }
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(multiRec);
    rez.addActionIfPossible(0, i -> commonCmd = i.getValue());
    rez.addActionIfPossible(1, i -> diffCmd = i.getValue());
    RoboBihaviourBinaryDescription desc =  evalSimple(maze, exec);
    desc.setSize(size);
    super.updateCreatureWithSimplification(creature, rez, rec);
    return desc;
  }

  private RoboBihaviourBinaryDescription evalSimple(Court c, SyncronousUnifiedPetriExecutor exec) {
    exec.resetSimulator();

    commonCmd = 0.0;
    diffCmd = 0.0;
    InfraOnlyRobo robo = new InfraOnlyRobo(c);
    List<Optional<Double>> sensorsOut = Collections.nCopies(ps.getInputCount(), Optional.empty());
    Map<Integer, UnifiedToken> inp = new HashMap<Integer, UnifiedToken>();
    int i;
    int finalTickNr = 301;
    int touchedTheWallsCntr = 0;
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
        touchedTheWallsCntr += 1;
      }

      commonCmd = 0.0;
      diffCmd = 0.0;
      if (i == 300) {
        PathResult smallRez = c.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(), dist);
        if (smallRez.touchedInOrder > 30) {
          finalTickNr += 700;
        }
      }
      if (i == 1000) {
        PathResult smallRez = c.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(), dist);
        if (smallRez.touchedInOrder > 80) {
          finalTickNr += 1000;
        }
      }
    }
    PathResult pathRez = c.getLines().smallSegmentsTouchedByPoints(robo.getVisitedPoints(), dist);
    allRun += i;
    InputOutputRecorder.fakeTicks(recc, 2000);
    RoboBihaviourBinaryDescription toRet = super.createDescprition(recc);
    toRet.setSegmentTouchedData(pathRez.touchedAtAll, pathRez.touchedInOrder, touchedTheWallsCntr);
    return toRet;
  }

}
