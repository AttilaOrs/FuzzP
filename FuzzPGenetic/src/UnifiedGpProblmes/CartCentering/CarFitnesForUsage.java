package UnifiedGpProblmes.CartCentering;

import UnifiedGp.ProblemSpecification;
import UnifiedGp.GpIndi.UsageStats.AbstactFitnessWithUsage;
import UnifiedGp.GpIndi.UsageStats.UnifiedGpIndiWithUsageStats;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FireCounterRecorder;

public class CarFitnesForUsage extends AbstactFitnessWithUsage{

  private CartSimpluator sim;

  public CarFitnesForUsage() {
    super(UnifiedPetriController.create());
    sim = new CartSimpluator(CartFitnes.SIM_LENTH, CartFitnes.INIT_POS, false);
  }

  @Override
  public double evaluate(UnifiedGpIndiWithUsageStats creature) {
    PetriConversationResult rez = convert(creature);
      FireCounterRecorder<UnifiedToken> rec = new FireCounterRecorder<>();
      SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
          UnifiedPetrinetCacheTableResultWrapper.defaultWrap(rez.net), false, true);
      exec.setRecorder(rec);
      
    int c = 0;
    c += rez.inpNrToInpPlace.containsKey(0) ? 1 : 0;
    c += rez.inpNrToInpPlace.containsKey(1) ? 1 : 0;
    c += rez.outNrToOutTr.containsKey(0) ? 1 : 0;
    c += rez.outNrToOutTr.containsKey(1) ? 1 : 0;
    double r = 50 * 20;
    if (c > 2) {
      UnifiedPetriController controller = new UnifiedPetriController(rez);
      controller.setFiredRec(rec);
      r = sim.sim(controller);
      super.updateCreatureWithSimplification(creature, rez,rec);
    }
    return 100.0 /(1.0 +r);
  }

}
