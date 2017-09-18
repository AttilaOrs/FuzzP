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

  public LineFallowerFitnes(ISegmentProvider segmentProv) {
    super(getProblemSpecification());
    this.segmentProvider = segmentProv;
  }
  
  double commonCmd = 0.0;
  double diffCmd = 0.0;

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    PetriConversationResult rez = super.convert(creature);
    
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(rec);
    
    rez.addActionIfPossible(0, i -> commonCmd = i.getValue());
    rez.addActionIfPossible(1 , i -> diffCmd = i.getValue());
    TwoSensorLineFallowerRobot robo = new TwoSensorLineFallowerRobot(segmentProvider);
    boolean[] sensorsOut = new boolean[]{false,false};
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    for(int i = 0; i < 2000; i++){
      inp.clear();
      rez.addToInpIfPossible(inp, 0, sensorsOut[0] ? new UnifiedToken(1.0) : new UnifiedToken());
      rez.addToInpIfPossible(inp, 0, sensorsOut[1] ? new UnifiedToken(1.0) : new UnifiedToken());
      exec.runTick(inp);
      
      double commandR = commonCmd + diffCmd/2.0;
      double commandL = commonCmd - diffCmd/2.0;
      sensorsOut = robo.simulate(commandR, commandL);
      commonCmd = 0.0;
      diffCmd = 0.0;
    }
    int segs = segmentProvider.segmentsTouchedByPoints(robo.getVisitedPoints());
    
    super.updateCreatureWithSimplification(creature, rez, rec);
    return segs / 1.0;
  }
  
  public static ProblemSpecification getProblemSpecification(){
    
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 0.0);
    inpScale.put(1, 0.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 10.0);
    outScale.put(1, 10.0);
    return new ProblemSpecificationImpl(10.0, 4, inpScale, outScale);
    
  }

}
