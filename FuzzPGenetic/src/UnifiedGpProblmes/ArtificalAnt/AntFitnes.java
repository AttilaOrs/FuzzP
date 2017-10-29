package UnifiedGpProblmes.ArtificalAnt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.recoder.FullRecorder;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;
import core.common.recoder.MultiRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class AntFitnes extends AbstactFitness {

  static int MAX_MOOVES = 600;

  public AntFitnes() {
    super(problemSpecification());
    tableSup = () -> new MutableState(GridReader.copyGrid());

  }

  private int moove = -1;
  protected MutableState table;
  protected Supplier<MutableState> tableSup;
  public PetriConversationResult originalRez;
  public FiredTranitionRecorder<UnifiedToken> rec;
  public FullRecorder<UnifiedToken> recc;

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    int size = creature.getSizes().size();
    double multi = sizeMulti(size);
    if(multi == 0.0){
      return 0.0;
    }
    rec = new FiredTranitionRecorder<>();
    // recc = new FullRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(rec /*
                                                                                  * ,
                                                                                  * recc
                                                                                  */));
    originalRez = calcFitnes(creature, multiRec);
    String originalStr = creature.getRoot().toString();
    int inital = table.getFoodEaten();
    super.updateCreatureWithSimplification(creature, originalRez, rec);

    /*
     * calcFitnes(creature, new FiredTranitionRecorder<>()); int newMooves =
     * table.getFoodEaten(); String newStr = creature.getRoot().toString(); if
     * (newMooves != inital) { System.err.println("we have a problem sir " +
     * inital + " " + newMooves + "\n>" + originalStr + "\n>" + newStr + "\n>" +
     * originalStr.equals(newStr)); }
     */

    double multi2 = fireCountMulti(rec, MAX_MOOVES);
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
    int moveUnitl = MAX_MOOVES;
    for (int i = 0; i < MAX_MOOVES; i++) {
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

  public static ProblemSpecification problemSpecification() {
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 1.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 1.0);
    outScale.put(1, 1.0);
    outScale.put(2, 1.0);

    return new ProblemSpecificationImpl(20.0, 5, inpScale, outScale);

  }

}
