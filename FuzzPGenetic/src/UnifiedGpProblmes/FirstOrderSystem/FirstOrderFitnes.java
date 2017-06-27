package UnifiedGpProblmes.FirstOrderSystem;

import java.util.HashMap;
import java.util.Map;

import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FullRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class FirstOrderFitnes implements ICreatureFitnes<UnifiedGpIndi> {


  private ToPetriNet tp;
  private ProblemSpecification ps;
  private boolean useRecorder;
  private FullRecorder<UnifiedToken> recorder;


  public FirstOrderFitnes() {
    this(false);
  }

  public FirstOrderFitnes(boolean useRecorder) {
    ps = createProblemSpecification();
    tp = new ToPetriNet(ps);
    this.useRecorder = useRecorder;
  }

  public static ProblemSpecificationImpl createProblemSpecification() {
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 2.0);
    inpScale.put(1, 2.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 2.0);

    return new ProblemSpecificationImpl(2.0, 5, inpScale, outScale);
  }

  static int cntr = 0;

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    PetriConversationResult rez = tp.toNet(creature.root);

    if (rez.outNrToOutTr.containsKey(0)) {
      FirtsOrderSystem ll = new FirtsOrderSystem(0.5, 0.7, 0.2, 0.3);
      ReferenceProvider ref = new ReferenceProvider();
      rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), d -> {
        ll.setCommand(d.getValue());
      });


      SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
          new UnifiedPetrinetCacheTableResultWrapper(rez.net,
              () -> new TokenCacheDisabling<>(5)),
          false, true);
      if (useRecorder) {
        recorder = new FullRecorder<>();
        exec.setRecorder(recorder);
      }
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
      return 1.0 / (1.0 + ref.calcError(ll.getEvolution()));
    }
    return 0;
  }

  public FullRecorder<UnifiedToken> getRecorder() {
    return recorder;
  }

}
