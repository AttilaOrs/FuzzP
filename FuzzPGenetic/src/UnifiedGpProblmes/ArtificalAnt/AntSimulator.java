package UnifiedGpProblmes.ArtificalAnt;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;
import core.common.tokencache.TokenCacheDisabling;

public class AntSimulator {

  private static final int MAX_MOOVES = 600;
  int move = 0;
  protected Supplier<MutableState> tableSup;

  public AntSimulator() {
    tableSup = () -> new MutableState(GridReader.copyGrid());

  }

  public int simulate(int outForward, int outLeft, int outRight, int inpIsFood, UnifiedPetriNet net,
      IGeneralPetriBehavoiurRecorder<UnifiedToken> rec) {
    addOuputs(outForward, outLeft, outRight, net);
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(net,
            () -> new TokenCacheDisabling<>(5)),
        false, true);
    exec.setRecorder(rec);

    MutableState table = tableSup.get();

    Map<Integer, UnifiedToken> inp = new HashMap<>();
    for (int i = 0; i < MAX_MOOVES; i++) {
      inp.clear();
      if (inpIsFood >= 0) {
        inp.put(inpIsFood, table.isFoodAhead() ? new UnifiedToken(1.0) : new UnifiedToken());
      }
      move = 10;
      exec.runTick(inp);
      switch (move) {
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
        break;
      }
    }
    return table.getFoodEaten();
  }

  private void addOuputs(int outForward, int outLeft, int outRight, UnifiedPetriNet net) {
    if (outForward != -1) {
      net.addActionForOuputTransition(outForward, t -> move = 0);
    }
    if (outLeft != -1) {
      net.addActionForOuputTransition(outLeft, t ->{
        if (move > 1) {
          move = 1;
        }
        ;
      });
    }
    if (outRight != -1) {
      net.addActionForOuputTransition(outRight, t -> {
        if (move > 2) {
          move = 2;
        }
        ;
      });
    }
  }

}
