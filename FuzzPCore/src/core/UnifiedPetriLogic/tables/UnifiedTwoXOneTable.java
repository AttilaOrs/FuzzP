package core.UnifiedPetriLogic.tables;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.UnifiedPetriLogic.IContex;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.generaltable.IGeneralTwoXOneTable;

public class UnifiedTwoXOneTable extends AbstractTable implements IGeneralTwoXOneTable {

  private final TwoXOneTable table;
  private final Operator op;

  public UnifiedTwoXOneTable(Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> rulaTabel, Operator op) {
    this.op = op;
    table = new TwoXOneTable(rulaTabel);
  }

  @Override
  public UnifiedToken[] execute(UnifiedToken[] inputs, IContex ct) {
    FuzzyToken tk1 = ct.fuzzyfieFirstInp(inputs[0]);
    FuzzyToken tk2 = ct.fuzzyfieSecondInp(inputs[1]);

    FuzzyToken[] fuzzyRez = table.execute(new FuzzyToken[] { tk1, tk2 });
    Double rez = null;
    if (op != Operator.None) {
      rez = clac(inputs, Operator.opMap.get(op));
    }
    UnifiedToken utk = null;
    if (rez == null) {
      // can be null becaouse it was None, or becaouse of the two Phis
      utk = ct.defuzzyfieFirstOutput(fuzzyRez[0]);
    } else {
      utk = ct.createScaleMamiximexForFirstOutput(defaultDriver().defuzzify(fuzzyRez[0]) * rez);
    }
    return new UnifiedToken[] { utk };
  }

  @Override
  public boolean executable(UnifiedToken[] inputs, IContex ct) {
    FuzzyToken tk1 = ct.fuzzyfieFirstInp(inputs[0]);
    FuzzyToken tk2 = ct.fuzzyfieSecondInp(inputs[1]);
    return table.executable(new FuzzyToken[] { tk1, tk2 });
  }

  private Double clac(UnifiedToken[] tk, BiFunction<Double, Double, Double> f) {
    if ((!tk[0].isPhi()) && (!tk[1].isPhi())) {
      return f.apply(tk[0].getValue(), tk[1].getValue());
    }
    if (!tk[0].isPhi()) {
      return tk[0].getValue();
    }
    if (!tk[1].isPhi()) {
      return tk[1].getValue();
    }
    return null;

  }

  @Override
  public Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> getTable() {
    return table.getTable();
  }

  public Operator getOpertaor() {
    return op;
  }
  
  public static UnifiedTwoXOneTable defaultTable(){
    return new UnifiedTwoXOneTable(TwoXOneTable.defaultTable().getTable(), Operator.None);
  }

  public static UnifiedTwoXOneTable defaultTableWithOp(Operator op) {
    return new UnifiedTwoXOneTable(TwoXOneTable.defaultTable().getTable(), op);
  }

  @Override
  public IUnifiedTable myClone() {
    TwoXOneTable cloneTable = table.myClone();
    return new UnifiedTwoXOneTable(cloneTable.getTable(), op);
  }

  @Override
  public List<Double> deduceScale(List<Double> inpScales) {
    Double rr = op.deduceScale(inpScales);
    return Arrays.asList(rr);
  }

  @Override
  public boolean maybeExecutable(boolean[] ar) {
    return table.maybeExecutable(ar);
  }

}
