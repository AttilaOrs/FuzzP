package UnifiedGpProblmes.CartCentering;

import UnifiedGp.AbstactFitness;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FiredTranitionRecorder;

public class CartFitnes extends AbstactFitness {

  static final int SIM_LENTH = 50;
  static final double INIT_POS = 10;
  private CartSimpluator sim;

  public CartFitnes() {
    super(UnifiedPetriController.create());
    sim = new CartSimpluator(SIM_LENTH, INIT_POS, false);
  }

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    PetriConversationResult rez = super.convert(creature);
    int c = 0;
    c += rez.inpNrToInpPlace.containsKey(0) ? 1 : 0;
    c += rez.inpNrToInpPlace.containsKey(1) ? 1 : 0;
    c += rez.outNrToOutTr.containsKey(0) ? 1 : 0;
    c += rez.outNrToOutTr.containsKey(1) ? 1 : 0;
    double r = 50 * 20;
    if (c > 2) {
      UnifiedPetriController controller = new UnifiedPetriController(rez);
      r = sim.sim(controller);
      super.updateCreatureWithSimplification(creature, rez,
          (FiredTranitionRecorder<UnifiedToken>) controller.getFiredRec());
    }
    return 1.0 / (1.0 + r);
  }

}
