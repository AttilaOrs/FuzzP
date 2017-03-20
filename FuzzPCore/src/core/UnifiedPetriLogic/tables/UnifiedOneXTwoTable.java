package core.UnifiedPetriLogic.tables;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.UnifiedPetriLogic.IContex;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.generaltable.IGeneralOneXTwoTable;

public class UnifiedOneXTwoTable implements IUnifiedTable, IGeneralOneXTwoTable {

  private final OneXTwoTable table;

  public UnifiedOneXTwoTable(Map<FuzzyValue, FuzzyValue> valTable1, Map<FuzzyValue, FuzzyValue> valTable2) {
    table = new OneXTwoTable(valTable1, valTable2);
  }

  @Override
  public UnifiedToken[] execute(UnifiedToken[] inputs, IContex ct) {
    FuzzyToken tk1 = ct.fuzzyfieFirstInp(inputs[0]);

    FuzzyToken[] fuzzyRez = table.execute(new FuzzyToken[] { tk1 });
    UnifiedToken utk1 = ct.defuzzyfieFirstOutput(fuzzyRez[0]);
    UnifiedToken utk2 = ct.defuzzyfieSecondOuput(fuzzyRez[1]);
    return new UnifiedToken[] { utk1, utk2 };
  }

  @Override
  public boolean executable(UnifiedToken[] inputs, IContex ct) {
    FuzzyToken tk1 = ct.fuzzyfieFirstInp(inputs[0]);
    return table.executable(new FuzzyToken[] { tk1 });
  }

  public List<Map<FuzzyValue, FuzzyValue>> getTables() {
    return table.getTables();
  }

  public static IUnifiedTable defaultTable() {
    OneXTwoTable rr = OneXTwoTable.defaultTable();
    return new UnifiedOneXTwoTable(rr.getTables().get(0), rr.getTables().get(1));
  }

  @Override
  public IUnifiedTable myClone() {
    Map<FuzzyValue, FuzzyValue> tableOne = new EnumMap<>(FuzzyValue.class);
    tableOne.putAll(getTables().get(0));
    Map<FuzzyValue, FuzzyValue> tableTwo = new EnumMap<>(FuzzyValue.class);
    tableTwo.putAll(getTables().get(1));
    return new UnifiedOneXTwoTable(tableOne, tableTwo);
  }

  @Override
  public List<Double> deduceScale(List<Double> inpScales) {
    return Arrays.asList(inpScales.get(0), inpScales.get(0));
  }

}
