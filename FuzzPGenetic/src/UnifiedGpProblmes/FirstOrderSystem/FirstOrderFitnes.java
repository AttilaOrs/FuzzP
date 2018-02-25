package UnifiedGpProblmes.FirstOrderSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.DynamicallySimplifiedPetriNetBuilder;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import UnifiedGpProblmes.FirstOrderSystem.ReferenceProvider.Result;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.recoder.FullRecorder;
import core.common.recoder.MultiRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class FirstOrderFitnes extends AbstactFitness {


  private ToPetriNet tp;
  private ProblemSpecification ps;
  private boolean useRecorder;
  private FullRecorder<UnifiedToken> recorder;
  private DynamicallySimplifiedPetriNetBuilder simplifier;


  public FirstOrderFitnes() {
    this(false);
  }

  public FirstOrderFitnes(boolean useRecorder) {
    super(createProblemSpecification());
    ps = createProblemSpecification();
    tp = new ToPetriNet(ps, true, true);
    this.useRecorder = useRecorder;
    simplifier = new DynamicallySimplifiedPetriNetBuilder();
  }


  static int cntr = 0;

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    int size = creature.getSizes().size();
    double sizeMulitlier = super.sizeMulti(size);
    if (sizeMulitlier == 0.0) {
      return 0.0;
    }

    PetriConversationResult rez = tp.toNet(creature.getRoot());
    if (rez.outNrToOutTr.containsKey(0)) {
      FirtsOrderSystem ll = new FirtsOrderSystem(0.67, 0.35, 0.87, 0.22);
      ReferenceProvider ref = new ReferenceProvider();
      rez.net.addActionForOuputTransition(rez.outNrToOutTr.get(0), d -> {
        ll.setCommand(d.getValue());
      });



      SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
          new UnifiedPetrinetCacheTableResultWrapper(rez.net,
              () -> new TokenCacheDisabling<>(5)),
          false, true);
      FiredTranitionRecorder<UnifiedToken> tk = new FiredTranitionRecorder<>();
      if (useRecorder) {
        recorder = new FullRecorder<>();
        MultiRecorder<UnifiedToken> multiRecorder = new MultiRecorder<>(Arrays.asList(recorder, tk));
        exec.setRecorder(multiRecorder);
      } else {
        exec.setRecorder(tk);
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


      updateCreatureWithSimplification(creature, rez, tk);
      Result result = ref.calcResult(ll.getEvolution());
      double fireMulitplier = super.fireCountMulti(tk, ref.getRefSize());


      double fitnes = 100.0 / (1.0 + 0.5 * result.error + 0.4 * result.changeSum + 0.1 * result.steadyStateError);
      return fireMulitplier * fitnes * sizeMulitlier;
    }
    return 0;
  }


  public FullRecorder<UnifiedToken> getRecorder() {
    return recorder;
  }

  public static ProblemSpecificationImpl createProblemSpecification() {
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 2.0);
    inpScale.put(1, 2.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 2.0);

    return new ProblemSpecificationImpl(2.0, 5, inpScale, outScale);
  }
}
