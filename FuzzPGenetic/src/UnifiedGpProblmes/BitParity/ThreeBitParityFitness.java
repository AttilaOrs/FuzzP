package UnifiedGpProblmes.BitParity;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class ThreeBitParityFitness extends AbstactFitness implements ICreatureFitnes<UnifiedGpIndi> {
  
  protected static final int[] nums = new int[] {0,0, 1 ,7,0,2,2,3, 1,0, 4,0,5,6,7,3,2,0,1,5,7,6};
  private final boolean simplifcation;
  private final boolean killBySize;
  private final boolean sizeMultiplyer;
  private final List<InpOutForSystem> tests ;
  

  public ThreeBitParityFitness(boolean simplification, boolean killBySize, boolean  sizeMultiplyer ) {
    super(getProblemSpecification());
    this.simplifcation = simplification;
    this.killBySize = killBySize;
    this.sizeMultiplyer = sizeMultiplyer;
    tests =IntStream.of(nums).mapToObj(this::numberTwoBits).collect(toList());
    
  }
  
  private boolean out ;

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    double multi = sizeMulti(creature.getSizes().size());
    if(killBySize && (multi == 0.0)) {
      return 0.0;
    }
    
    rez = super.convert(creature);
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    
    exec.setRecorder(rec);
    int correctCntr = 0;
    
    rez.addActionIfPossible(0, i -> out = true);
    for( InpOutForSystem test : tests) {
      out = false;
      Map<Integer, UnifiedToken> w = rez.convertFromInputSpecificationToPlace(test.inp);
        exec.runTick(w);
      if(out == test.rez) {
        correctCntr++;
      }
    }
    if(simplifcation) {
      super.updateCreatureWithSimplification(creature, rez, rec);
    }
    if(sizeMultiplyer) {
      double multi2 = super.fireCountMulti(rec, tests.size());
      return multi*multi2*correctCntr;
    }
    
    
    return correctCntr;
  }
  
  private InpOutForSystem numberTwoBits(int nr){
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    int cntr = 0;
    for(int i = 0; i < 3; i++ ) {
      UnifiedToken t = new UnifiedToken(1.0);
      if(nr % 2 == 0 ) {
        t = new UnifiedToken();
        cntr++;
      }
      inp.put(i, t);
      nr /= 2;
    }
    return new InpOutForSystem(inp, cntr %2 !=0);
  }
  
  
  public static ProblemSpecification getProblemSpecification() {
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 1.0);
    inpScale.put(1, 1.0);
    inpScale.put(2, 1.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 1.0);
    ProblemSpecification ps = new ProblemSpecificationImpl(1.0,  0, inpScale,  outScale); 
    return ps;
  }
  
  private static class InpOutForSystem{
    public final  Map<Integer, UnifiedToken> inp;
    public final boolean rez;
    

    public InpOutForSystem(Map<Integer, UnifiedToken> inp, boolean rez) {
      this.inp = inp;
      this.rez = rez;
    }
    
    @Override
    public String toString() {
      return "InpOutForSystem [inp=" + inp + ", rez=" + rez + "]";
    }
  }

}
