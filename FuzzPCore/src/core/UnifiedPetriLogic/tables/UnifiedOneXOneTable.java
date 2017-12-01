package core.UnifiedPetriLogic.tables;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.UnifiedPetriLogic.IContex;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.generaltable.IGeneralOneXOne;

public class UnifiedOneXOneTable implements IUnifiedTable, IGeneralOneXOne {
  final OneXOneTable table;

  public UnifiedOneXOneTable(Map<FuzzyValue, FuzzyValue> valtable) {
    table = new OneXOneTable(valtable);
  }

  @Override
  public UnifiedToken[] execute(UnifiedToken[] inputs, IContex ct) {
    FuzzyToken tk = ct.fuzzyfieFirstInp(inputs[0]);
    FuzzyToken[] fuzzyRez = table.execute(new FuzzyToken[] { tk });
    UnifiedToken utk = ct.defuzzyfieFirstOutput(fuzzyRez[0]);
    return new UnifiedToken[] { utk };
  }

  @Override
  public boolean executable(UnifiedToken[] inputs, IContex ct) {
    FuzzyToken tk = ct.fuzzyfieFirstInp(inputs[0]);
    return table.executable(new FuzzyToken[] { tk });
  }

  @Override
  public Map<FuzzyValue, FuzzyValue> getTable() {
    return table.getTable();
  }

  public static UnifiedOneXOneTable defaultTable() {
    return new UnifiedOneXOneTable(OneXOneTable.defaultTable().getTable());
  }

  @Override
  public IUnifiedTable myClone() {
    Map<FuzzyValue, FuzzyValue> valtable = new EnumMap<>(FuzzyValue.class);
    valtable.putAll(getTable());
    return new UnifiedOneXOneTable(valtable);
  }

  @Override
  public List<Double> deduceScale(List<Double> inpScales) {
    return inpScales;
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
    Map<FuzzyValue, FuzzyValue> newValTable = new EnumMap<>(FuzzyValue.class);
    for (FuzzyValue key : FuzzyValue.inOrder) {
      if (getTable().get(key).isPhi()) {
        newValTable.put(key, FuzzyValue.FF);
      } else {
        newValTable.put(key, vals.next());
      }
    }
    return new UnifiedOneXOneTable(newValTable);
  }

}
