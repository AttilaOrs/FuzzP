package UnifiedGpProblmes.SymbolicRegression;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.ICreatureFitnes;

public class SymbolicRegressionFitness extends AbstactFitness implements ICreatureFitnes<UnifiedGpIndi> {

  private Double lastRez;

  public SymbolicRegressionFitness() {
    super(problemsSpecification());
  }

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    int size = creature.getSizes().size();
    double multi = sizeMulti(size);
    if (multi == 0.0) {
      return 0.0;
    }
    PetriConversationResult rez = super.convert(creature);
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(rec);
    boolean b = rez.addActionIfPossible(0, d -> lastRez = d.getValue());
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    if (rez.hasInp(0) && b) {
      FuntionSimulator sim = new FuntionSimulator();
      Double sum = sim.compare(dodo(rez, exec, inp));

      double multi2 = fireCountMulti(rec, sim.getLength());

      return (1.0 / (1.0 + sum)) * multi * multi2;
    }
    return 0;
  }

  public Map<String, Map<Double, Double>> makeLog(UnifiedGpIndi creature) {
    PetriConversationResult rez = super.convert(creature);
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(rec);
    boolean b = rez.addActionIfPossible(0, d -> lastRez = d.getValue());
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    FuntionSimulator sim = new FuntionSimulator();
    return sim.sim(dodo(rez, exec, inp));
  }

  public Map<String, Map<Double, Double>> makeLog(UnifiedPetriNet net) {
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(rec);
    int outTr = IntStream.range(0, net.getNrOfTransition()).filter(i -> net.isOuputTransition(i)).findAny()
        .getAsInt();
    int inpPl = IntStream.range(0, net.getNrOfPlaces()).filter(i -> net.isInputPlace(i)).findAny()
        .getAsInt();

    net.addActionForOuputTransition(outTr, d -> lastRez = d.getValue());
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    FuntionSimulator sim = new FuntionSimulator();
    return sim.sim(d -> {
      lastRez = null;
      inp.clear();
      inp.put(inpPl, new UnifiedToken(d));
      exec.runTick(inp);
      return lastRez;
    });

  }

  private Function<Double, Double> dodo(PetriConversationResult rez, SyncronousUnifiedPetriExecutor exec,
      Map<Integer, UnifiedToken> inp) {
    return d -> {
      lastRez = null;
      inp.clear();
      rez.addToInpIfPossible(inp, 0, new UnifiedToken(d));
      exec.runTick(inp);
      return lastRez;
    };
  }

  public static ProblemSpecification problemsSpecification() {

    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 5.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 5.0);
    return new ProblemSpecificationImpl(5.0, 5, inpScale, outScale);
  }

}
