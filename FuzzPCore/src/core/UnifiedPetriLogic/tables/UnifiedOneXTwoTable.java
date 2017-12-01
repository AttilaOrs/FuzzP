package core.UnifiedPetriLogic.tables;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

  @Override
  public List<Map<FuzzyValue, FuzzyValue>> getTables() {
    return table.getTables();
  }

  public static UnifiedOneXTwoTable defaultTable() {
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

  @Override
  public boolean maybeExecutable(boolean[] ar) {
    return table.maybeExecutable(ar);
  }

  @Override
  public Stream<FuzzyValue> getValues() {
    return table.cellsOneByOne();
  }

  @Override
  public IUnifiedTable newTableBasedOnValues(Iterator<FuzzyValue> vals) {
    Map<FuzzyValue, FuzzyValue> valTable1 = new EnumMap<>(FuzzyValue.class);
    Map<FuzzyValue, FuzzyValue> valTable2 = new EnumMap<>(FuzzyValue.class);
    for (FuzzyValue key : FuzzyValue.inOrder) {
      if (getTables().get(0).get(key).isPhi()) {
        valTable1.put(key, FuzzyValue.FF);
      } else {
        valTable1.put(key, vals.next());
      }
    }
    for (FuzzyValue key : FuzzyValue.inOrder) {
      if (getTables().get(1).get(key).isPhi()) {
        valTable2.put(key, FuzzyValue.FF);
      } else {
        valTable2.put(key, vals.next());
      }
    }

    return new UnifiedOneXTwoTable(valTable1, valTable2);
  }

}
