package UnifiedGpProblmes.ArtificalAnt;

import java.util.HashMap;
import java.util.Map;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FiredTranitionRecorder;

public class AntFitnes extends AbstactFitness {
  int MAX_MOOVES = 600;

  public AntFitnes() {
    super(problemSpecification());
  }

  private int moove = -1;
  protected MutableState table;

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    PetriConversationResult rez = super.convert(creature);
    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    rez.addActionIfPossible(0, d -> moove = 0);
    rez.addActionIfPossible(1, d -> moove = 1);
    rez.addActionIfPossible(2, d -> moove = 2);
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    exec.setRecorder(rec);
    
    table = new MutableState(GridReader.copyGrid());
    
    Map<Integer, UnifiedToken> inp = new HashMap<>();
    int moveUnitl = MAX_MOOVES;
    for (int i = 0; i < MAX_MOOVES; i++) {
      inp.clear();
      rez.addToInpIfPossible(inp, 0, table.isFoodAhead() ? new UnifiedToken(1.0) : new UnifiedToken());
      moove = -1;
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
    super.updateCreatureWithSimplification(creature, rez, rec);

    return table.getFoodEaten();
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
