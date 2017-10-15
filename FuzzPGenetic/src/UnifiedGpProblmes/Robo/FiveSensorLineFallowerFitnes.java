package UnifiedGpProblmes.Robo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGpProblmes.Robo.Simulator.FiveSensorLineFollowerRobot;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider.PathResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class FiveSensorLineFallowerFitnes extends AbstactFitness {

  private ISegmentProvider segmentProvider;
  private static final int TICK_NR = 201;

  public FiveSensorLineFallowerFitnes(ISegmentProvider segmentProv) {
    super(getProblemSpecification());
    this.segmentProvider = segmentProv;
  }

  double commonCmd = 0.0;
  double diffCmd = 0.0;
  private PetriConversationResult rez;

  static int cntr = 0;
  final static List<Integer> checkpoints = Arrays.asList(0, 5, 15, 20, 40);

  @Override
  public double evaluate(UnifiedGpIndi creature) {
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
    FiveSensorLineFollowerRobot robo = new FiveSensorLineFollowerRobot(segmentProvider);
    List<Optional<Double>> sensorsOut = Arrays.asList(Optional.empty(), Optional.empty(), Optional.empty(),
        Optional.empty(), Optional.empty());
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    int finalTickNr = TICK_NR;
    boolean chance = true;
    for (int i = 0; i < finalTickNr; i++) {
      inp.clear();
      for (int ii = 0; ii < sensorsOut.size(); ii++) {
        rez.addToInpIfPossible(inp, ii, UnifiedToken.fromOptional(sensorsOut.get(ii)));
      }
      try {
        exec.runTick(inp);
      } catch (Throwable t) {
        System.out.println(creature.getRoot());
        throw t;
      }

      double commandR = commonCmd + diffCmd / 2.0;
      double commandL = commonCmd - diffCmd / 2.0;
      sensorsOut = robo.simulate(commandR, commandL);
      commonCmd = 0.0;
      diffCmd = 0.0;
      if (i % 200 == 0) {
        PathResult l = segmentProvider.smallSegmentsTouchedByPoints(robo.getVisitedPoints());
        int ii = (i / 200);
        if (ii < checkpoints.size()) {
          if (l.touchedAtAll >= checkpoints.get(ii)) {
            finalTickNr += 200;
            chance = true;
          } else if (chance) {
            chance = false;
            finalTickNr += 200;
          }
        }
      }
    }
    PathResult pathRez = segmentProvider.smallSegmentsTouchedByPoints(robo.getVisitedPoints());

    super.updateCreatureWithSimplification(creature, rez, rec);
    double multi2 = super.fireCountMulti(rec, finalTickNr);
    return calcBasicFitness(pathRez) * multi * multi2;
  }

  private double calcBasicFitness(PathResult pathRez) {
    return pathRez.touchedInOrder + 1.0 / (pathRez.lastIndex + 1);
  }

  public static ProblemSpecification getProblemSpecification() {

    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 1.0);
    inpScale.put(1, 1.0);
    inpScale.put(2, 1.0);
    inpScale.put(3, 1.0);
    inpScale.put(4, 1.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 10.0);
    outScale.put(1, 20.0);
    return new ProblemSpecificationImpl(10.0, 4, inpScale, outScale);

  }

  @Override
  public PetriConversationResult getRez() {
    return rez;
  }

}
