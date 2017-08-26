import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.UnifiedPetriLogic.UnifiedToken;

public class BlinkMain {

  public static void main(String args[]) {
    BlinkUnifiedPetriMaker maker = new BlinkUnifiedPetriMaker();
    GeneralRun.runNet(maker.net, createInputs(), maker.nameStore);
  }

  public static List<Map<Integer, UnifiedToken>> createInputs() {
    LargeSmallerExampleMaker maker = new LargeSmallerExampleMaker();
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    for (int cntr = 0; cntr < 40; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      toRet.add(inp);
    }
    return toRet;
  }
}
