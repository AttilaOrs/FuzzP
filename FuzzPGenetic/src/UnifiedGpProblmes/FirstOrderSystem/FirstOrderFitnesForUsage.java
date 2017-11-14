package UnifiedGpProblmes.FirstOrderSystem;

import java.util.HashMap;
import java.util.Map;

import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.UsageStats.AbstactFitnessWithUsage;
import UnifiedGp.GpIndi.UsageStats.UnifiedGpIndiWithUsageStats;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FireCounterRecorder;

public class FirstOrderFitnesForUsage extends AbstactFitnessWithUsage{

  public FirstOrderFitnesForUsage() {
    super(FirstOrderFitnes.createProblemSpecification());
  }

  @Override
  public double evaluate(UnifiedGpIndiWithUsageStats creature) {
    PetriConversationResult rez = convert(creature);
    FireCounterRecorder<UnifiedToken> rec = new FireCounterRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        UnifiedPetrinetCacheTableResultWrapper.defaultWrap(rez.net), false, true);
    exec.setRecorder(rec);
    
    if (rez.outNrToOutTr.containsKey(0)) {
      FirtsOrderSystem ll = new FirtsOrderSystem(0.5, 0.7, 0.2, 0.3);
      ReferenceProvider ref = new ReferenceProvider();
      rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), d -> {
        ll.setCommand(d.getValue());
      });



      Map<Integer, UnifiedToken> inp = new HashMap<>();
      for (int i = 0; i < ref.getRefSize(); i++) {
        inp.clear();
        if (rez.inpNrToInpPlace.containsKey(0)) {
          inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(ll.curentStatus()));
        }
        if (rez.inpNrToInpPlace.containsKey(1)) {
          inp.put(rez.inpNrToInpPlace.get(1), new UnifiedToken(ref.getReference(i)));
        }
        exec.runTick(inp);
        ll.runTick();
      }


      super.updateCreatureWithSimplification(creature, rez, rec);

      return 1.0 / (1.0 + ref.calcError(ll.getEvolution()));
    }
    return 0;
    
  }

}
