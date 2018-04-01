package UnifiedGpProblmes.ArtificalAnt;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UsageStats.AbstactFitnessWithUsage;
import UnifiedGp.GpIndi.UsageStats.UnifiedGpIndiWithUsageStats;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FireCounterRecorder;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class ArtificalAntFitnesForUsage extends AbstactFitnessWithUsage {

  private int moove;
  protected Supplier<MutableState> tableSup;
  private MutableState table;

  public ArtificalAntFitnesForUsage() {
    super(AntFitnes.problemSpecification());
    tableSup = () -> new MutableState(GridReader.copyGrid());
  }

  public void setMutableStateSuplier(Supplier<MutableState> sup) {
    this.tableSup = sup;
  }

  @Override
  public double evaluate(UnifiedGpIndiWithUsageStats creature) {
    int size = creature.getSizes().size();
    double multi = sizeMulti(size);
    if (multi == 0.0) {
      return 0.0;
    }
    FireCounterRecorder<UnifiedToken> rec = new FireCounterRecorder<>();
    PetriConversationResult m = calcFitnes(creature, rec);

    int inital = table.getFoodEaten();
    super.updateCreatureWithSimplification(creature, m, rec);
    double multi2 = fireCountMulti(rec, AntFitnes.MAX_MOOVES);
    return inital * multi * multi2;
  }

  private PetriConversationResult calcFitnes(UnifiedGpIndi creature, IGeneralPetriBehavoiurRecorder<UnifiedToken> rec) {
    PetriConversationResult rez = super.convert(creature);
    rez.addActionIfPossible(0, d -> {
      if (moove > 0)
        moove = 0;
    });
    rez.addActionIfPossible(1, d -> {
      if (moove > 1)
        moove = 1;
    });
    rez.addActionIfPossible(2, d -> {
      if (moove > 2)
        moove = 2;
    });
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(rec);

    table = tableSup.get();

    Map<Integer, UnifiedToken> inp = new HashMap<>();
    int moveUnitl = AntFitnes.MAX_MOOVES;
    for (int i = 0; i < AntFitnes.MAX_MOOVES; i++) {
      inp.clear();
      rez.addToInpIfPossible(inp, 0, table.isFoodAhead() ? new UnifiedToken(1.0) : new UnifiedToken());
      moove = 10;
      exec.runTick(inp);
      switch (moove) {
      case 0:
        table.forward();
        break;
      case 1:
        table.left();
        break;
      case 2:
        table.right();
        break;
      default:
        break;
      }
      if (table.getFoodEaten() >= GridReader.getNumberOfFoodCells()) {
        moveUnitl = i;
        break;
      }
    }
    return rez;
  }

}
