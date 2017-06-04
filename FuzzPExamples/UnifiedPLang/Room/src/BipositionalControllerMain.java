import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import core.UnifiedPetriLogic.UnifiedToken;

public class BipositionalControllerMain {

  public static void main(String args[]) {
    BipositionalControllerUnifiedPetriMaker maker = new BipositionalControllerUnifiedPetriMaker();

    GeneralRun.runNet(maker.net, createInputs(), maker.nameStore);

  }

  public static List<Map<Integer, UnifiedToken>> createInputs() {
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    BipositionalControllerUnifiedPetriMaker maker = new BipositionalControllerUnifiedPetriMaker();
    Random rnd = new Random();

    for (int i = 1; i < 95; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(maker.iP0, new UnifiedToken(55.0 - rnd.nextDouble() * 30));
      inp.put(maker.iP1, new UnifiedToken(40.0));
      toRet.add(inp);
    }
    return toRet;
  }

}
