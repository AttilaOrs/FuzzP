package core.UnifiedPetriLogic.tables;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;
import core.UnifiedPetriLogic.IContex;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.generaltable.IGeneralTwoXTwoTable;

public class UnifiedTwoXTwoTable implements IUnifiedTable, IGeneralTwoXTwoTable {

  private final TwoXTwoTable table;
  private final Operator op;
  private transient final FuzzyDriver defaultDriver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

  public UnifiedTwoXTwoTable(Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable1,
      Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable2, Operator op) {
    table = new TwoXTwoTable(ruleTable1, ruleTable2);
    this.op = op;
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
    UnifiedToken utk1 = null;
    UnifiedToken utk2 = null;
    if (rez == null) {
      // can be null becaouse it was None, or becaouse of the two Phis
      utk1 = ct.defuzzyfieFirstOutput(fuzzyRez[0]);
      utk2 = ct.defuzzyfieSecondOuput(fuzzyRez[1]);
    } else {
      if (!fuzzyRez[0].isPhi()) {
        utk1 = ct.createScaleMamiximexForFirstOutput(defaultDriver.defuzzify(fuzzyRez[0]) * rez);
      } else {
        utk1 = new UnifiedToken();
      }
      if (!fuzzyRez[1].isPhi()) {
        utk2 = ct.createScaleMamiximexForSecondOuput(defaultDriver.defuzzify(fuzzyRez[1]) * rez);
      } else {
        utk2 = new UnifiedToken();
      }
    }
    return new UnifiedToken[] { utk1, utk2 };
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
  public List<Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>>> getTables() {
    return table.getTables();
  }

  public Operator getOpertaor() {
    return op;
  }

  public static UnifiedTwoXTwoTable defaultTable() {
    TwoXTwoTable defFuzzTable = TwoXTwoTable.defaultTable();
    return new UnifiedTwoXTwoTable(defFuzzTable.getTables().get(0), defFuzzTable.getTables().get(1), Operator.None);
  }

  @Override
  public IUnifiedTable myClone() {
    TwoXTwoTable tt = table.myClone();
    return new UnifiedTwoXTwoTable(tt.getTables().get(0), tt.getTables().get(1), op);
  }

  @Override
  public List<Double> deduceScale(List<Double> inpScales) {
    Double d = op.deduceScale(inpScales);
    return Arrays.asList(d, d);
  }

}
