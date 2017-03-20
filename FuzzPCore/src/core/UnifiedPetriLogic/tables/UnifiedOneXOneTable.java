package core.UnifiedPetriLogic.tables;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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

}
