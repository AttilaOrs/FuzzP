package UnifiedGpProblmes.Multiplexer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;

public class MultiplexerFitness extends AbstactFitness {

  private boolean full;

  public MultiplexerFitness(boolean full) {
    super(problemSpecification());
    this.full = full;
  }

  public MultiplexerFitness() {
    this(false);
  }

  private Boolean lastRez;

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    PetriConversationResult rez = convert(creature);
    boolean hasFirst = rez.addActionIfPossible(0, d -> lastRez = Boolean.TRUE);
    boolean hasSceond = rez.addActionIfPossible(1, d -> lastRez = Boolean.FALSE);
    if (hasFirst || hasSceond) {
      FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
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

  public static ProblemSpecification problemSpecification() {
    Map<Integer, Double> inpScale = new HashMap<>();
    for (int i = 0; i < 11; i++) {
      inpScale.put(i, 1.0);
    }
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 1.0);
    outScale.put(1, 1.0);
    return new ProblemSpecificationImpl(5.0, 5, inpScale, outScale);
  }

  public static void main(String[] args) {
    Set<Integer> rands = new HashSet<>();
    Random rnd = new Random();
    while (rands.size() < 600) {
      rands.add(rnd.nextInt(2048));
    }
    System.out.println(rands);
  }

}
