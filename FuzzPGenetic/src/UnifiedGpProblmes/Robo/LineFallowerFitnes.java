package UnifiedGpProblmes.Robo;

import java.util.HashMap;
import java.util.Map;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGpProblmes.Robo.Simulator.TwoSensorLineFallowerRobot;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class LineFallowerFitnes extends AbstactFitness{

  private ISegmentProvider segmentProvider;
  private static final int TICK_NR = 600;

  public LineFallowerFitnes(ISegmentProvider segmentProv) {
    super(getProblemSpecification());
    this.segmentProvider = segmentProv;
  }
  
  double commonCmd = 0.0;
  double diffCmd = 0.0;
  private PetriConversationResult rez;

  static int cntr = 0;

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
    TwoSensorLineFallowerRobot robo = new TwoSensorLineFallowerRobot(segmentProvider);
    boolean[] sensorsOut = new boolean[]{false,false};
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    for (int i = 0; i < TICK_NR; i++) {
      inp.clear();
      rez.addToInpIfPossible(inp, 0, sensorsOut[0] ? new UnifiedToken(1.0) : new UnifiedToken());
      rez.addToInpIfPossible(inp, 1, sensorsOut[1] ? new UnifiedToken(1.0) : new UnifiedToken());
      try {
      exec.runTick(inp);
      } catch (Throwable t) {
        System.out.println(creature.getRoot());
        throw t;
      }
      
      double commandR = commonCmd + diffCmd/2.0;
      double commandL = commonCmd - diffCmd/2.0;
      sensorsOut = robo.simulate(commandR, commandL);
      commonCmd = 0.0;
      diffCmd = 0.0;
    }
    int segs = segmentProvider.segmentsTouchedByPoints(robo.getVisitedPoints());
    
    super.updateCreatureWithSimplification(creature, rez, rec);
    double multi2 = super.fireCountMulti(rec, TICK_NR);
    return segs * multi * multi2;
  }
  
  public static ProblemSpecification getProblemSpecification(){
    
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 1.0);
    inpScale.put(1, 1.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 10.0);
    outScale.put(1, 10.0);
    return new ProblemSpecificationImpl(10.0, 4, inpScale, outScale);
    
  }

  public PetriConversationResult getRez() {
    return rez;
  }

}
