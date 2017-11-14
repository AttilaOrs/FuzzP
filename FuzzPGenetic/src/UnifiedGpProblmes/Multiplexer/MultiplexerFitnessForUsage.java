package UnifiedGpProblmes.Multiplexer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import UnifiedGp.GpIndi.UsageStats.AbstactFitnessWithUsage;
import UnifiedGp.GpIndi.UsageStats.UnifiedGpIndiWithUsageStats;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FireCounterRecorder;

public class MultiplexerFitnessForUsage extends AbstactFitnessWithUsage {

  private Boolean lastRez;
  private boolean full= false;

  public MultiplexerFitnessForUsage() {
    super(MultiplexerFitness.problemSpecification());
  }

  @Override
  public double evaluate(UnifiedGpIndiWithUsageStats creature) {
    PetriConversationResult rez = convert(creature);
    boolean hasFirst = rez.addActionIfPossible(0, d -> lastRez = Boolean.TRUE);
    boolean hasSceond = rez.addActionIfPossible(1, d -> lastRez = Boolean.FALSE);
    if (hasFirst || hasSceond) {
      FireCounterRecorder<UnifiedToken> rec = new FireCounterRecorder<>();
      SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
          UnifiedPetrinetCacheTableResultWrapper.defaultWrap(rez.net), false, true);
      exec.setRecorder(rec);
      Problem pr = new Problem();
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      int hits  = -1;
      if(full){
        hits = pr.fullHits(doTheMacarean(rez, exec, inp));
      } else {
        hits = pr.hits(doTheMacarean(rez, exec, inp));
      }

      updateCreatureWithSimplification(creature, rez, rec);
      return hits;
    }
    return 0;
  }

  private Function<boolean[], Boolean> doTheMacarean(PetriConversationResult rez, SyncronousUnifiedPetriExecutor exec,
      Map<Integer, UnifiedToken> inp) {
    return bl -> {
      lastRez = null;
      inp.clear();
      for (int i = 0; i < 11; i++) {
        rez.addToInpIfPossible(inp, i, bl[i] ? new UnifiedToken(1.0) : new UnifiedToken());
      }
      exec.runTick(inp);
      return lastRez;

    };
  }
}
