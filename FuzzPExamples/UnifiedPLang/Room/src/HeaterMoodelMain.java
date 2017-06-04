import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.UnifiedPetriLogic.UnifiedToken;

public class HeaterMoodelMain {

  public static void main(String args[]) {
    HeaterModelUnifiedPetriMaker maker = new HeaterModelUnifiedPetriMaker();
    GeneralRun.runNet(maker.net, createInputs(), maker.nameStore);
  }

  public static List<Map<Integer, UnifiedToken>> createInputs() {
    List<Map<Integer, UnifiedToken>> toRet = new ArrayList<>();
    for (int i = 1; i < 60 * 60; i++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      toRet.add(inp);
    }
    return toRet;
  }

}
