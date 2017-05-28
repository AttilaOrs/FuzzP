import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.UnifiedPetriLogic.UnifiedToken;

public class HeaterStateMain {
  public static void main(String args[]) {
    HeaterStateUnifiedPetriMaker maker = new HeaterStateUnifiedPetriMaker();
    GeneralRun.runNet(maker.net, createInputs(), maker.nameStore);
  }

  public static List<Map<Integer, UnifiedToken>> createInputs() {
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    HeaterStateUnifiedPetriMaker maker = new HeaterStateUnifiedPetriMaker();
    for (int i = 1; i < 40; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      if (i % 3 == 0) {
        inp.put(maker.iP0, UnifiedToken.zero());
      }
      if (i % 5 == 0) {
        inp.put(maker.iP2, UnifiedToken.zero());
      }
      if (i % 7 == 0) {
        inp.put(maker.iP1, UnifiedToken.zero());
      }
      if (i % 10 == 0) {
        inp.put(maker.iP3, UnifiedToken.zero());
      }
      toRet.add(inp);
    }
    return toRet;
  }

}
