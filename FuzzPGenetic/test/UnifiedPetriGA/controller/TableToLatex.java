package UnifiedPetriGA.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyValue;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class TableToLatex {

  public static void saveTableLatex(UnifiedTwoXTwoTable table, String fileName, boolean withPhi) {
    StringBuilder bld = new StringBuilder();
    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> first = table.getTables().get(0);
    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> second = table.getTables().get(1);
    for (FuzzyValue fvOne : (withPhi) ? FuzzyValue.inOrder : FuzzyValue.inOrderWithoutPhi) {
      for (FuzzyValue fvTwo : (withPhi) ? FuzzyValue.inOrder : FuzzyValue.inOrderWithoutPhi) {
        FuzzyValue ll1 = first.get(fvOne).get(fvTwo);
        FuzzyValue ll2 = second.get(fvOne).get(fvTwo);
        bld.append("& $ ").append(createStr(ll1)).append(",").append(createStr(ll2)).append(" $");

      }
      bld.append("\\\\ \\hline\n");
    }

    try {
      Files.write(Paths.get(fileName), bld.toString().getBytes());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private static String createStr(FuzzyValue v) {
    if (v == FuzzyValue.FF) {
      return "\\phi";
    } else {
      return "X_{" + ((FuzzyValue.indexOf(v)) - 2) + "}";
    }

  }
}
