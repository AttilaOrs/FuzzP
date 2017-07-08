package UnifiedGpProblmes.CartCentering;

import java.util.HashMap;
import java.util.Map;

import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FiredTranitionRecorder;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;

public class UnifiedPetriController implements CartController {

  private PetriConversationResult rez;
  private command c = command.Non;
  private IGeneralPetriBehavoiurRecorder<UnifiedToken> firedRec;
  private SyncronousUnifiedPetriExecutor exec;
  private Map<Integer, UnifiedToken> inp;

  public UnifiedPetriController(PetriConversationResult rez) {
    this.rez = rez;
    if (rez.outNrToOutTr.containsKey(0)) {
      rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), d -> c = command.Pos);
    }
    if (rez.outNrToOutTr.containsKey(1)) {
      rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(1), d -> c = command.Neg);
    }
    firedRec = new FiredTranitionRecorder<>();
    exec = new SyncronousUnifiedPetriExecutor(rez.net);
    exec.setRecorder(firedRec);
    rez.net.setInitialMarkingForPlace(0, new UnifiedToken(0.0));
    inp = new HashMap<>();

  }


  @Override
  public command control(double[] st) {
    inp.clear();
    c = command.Non;
    if (rez.inpNrToInpPlace.containsKey(0)) {
      inp.put(rez.inpNrToInpPlace.get(0), new UnifiedToken(st[0]));
    }
    if (rez.inpNrToInpPlace.containsKey(1)) {
      inp.put(rez.inpNrToInpPlace.get(1), new UnifiedToken(st[1]));
    }
    exec.runTick(inp);
    return c;

  }

  public IGeneralPetriBehavoiurRecorder<UnifiedToken> getFiredRec() {
    return firedRec;
  }

  public void setFiredRec(IGeneralPetriBehavoiurRecorder<UnifiedToken> ks) {
    exec.setRecorder(ks);
    firedRec = ks;
  }

  public static ProblemSpecification create() {
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 20.0);
    inpScale.put(1, 20.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 20.0);
    outScale.put(1, 20.0);

    return new ProblemSpecificationImpl(20.0, 5, inpScale, outScale);

  }


}
