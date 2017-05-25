package core.FuzzyPetriLogic.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.ITable;
import core.common.generaltable.IGeneralTwoXTwoTable;

public class TwoXTwoTable implements ITable, IGeneralTwoXTwoTable {

	Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable1;
	Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable2;

	public TwoXTwoTable(Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable1,
	    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable2) {
		this.ruleTable1 = ruleTable1;
		this.ruleTable2 = ruleTable2;
		TwoXOneTable.putPhiIfNecesary(ruleTable1);
		TwoXOneTable.putPhiIfNecesary(ruleTable2);
	}

	@Override
	public FuzzyToken[] execute(FuzzyToken[] inps) {
		ITable.inpCheck(inps, 2);
		FuzzyToken toRet = FuzzyToken.emptyToken();
		FuzzyToken toRet2 = FuzzyToken.emptyToken();
		FuzzyToken inp1 = inps[0];
		FuzzyToken inp2 = inps[1];
		for (int colIndex = 0; colIndex < FuzzyValue.inOrder.size(); colIndex++) {
			FuzzyValue col = FuzzyValue.inOrder.get(colIndex);
			for (int rowInd = 0; rowInd < FuzzyValue.inOrder.size(); rowInd++) {
				FuzzyValue row = FuzzyValue.inOrder.get(rowInd);
				double val = inp1.getFuzzyValue(col) * inp2.getFuzzyValue(row);
				toRet.addToFuzzyValue(ruleTable1.get(col).get(row), val);
				toRet2.addToFuzzyValue(ruleTable2.get(col).get(row), val);
			}
		}
		toRet.normalize();
		toRet2.normalize();

		return new FuzzyToken[] { toRet, toRet2 };
	}

	@Override
	public boolean executable(FuzzyToken[] inps) {
		ITable.inpCheck(inps, 2);
		FuzzyToken inp1 = inps[0];
		FuzzyToken inp2 = inps[1];
		Optional<?> rez = inp1.getNonZeroValues()
		    .flatMap(fv -> inp2.getNonZeroValues().map(fv2 -> new FuzzyValue[] { fv, fv2 }))
		    .map(fvAr -> (ruleTable1.get((fvAr[0])).get(fvAr[1]) != FuzzyValue.FF)
		        ? ruleTable1.get(fvAr[0]).get(fvAr[1])
		        : ruleTable2.get(fvAr[0]).get(fvAr[1]))
		    .filter(fv -> fv != FuzzyValue.FF).findAny();
		return rez.isPresent();
	}

	public static TwoXTwoTable defaultTable() {
		Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable = new EnumMap<>(FuzzyValue.class);
		for (int i = 0; i < FuzzyValue.inOrder.size(); i++) {
			EnumMap<FuzzyValue, FuzzyValue> tempRulaTable = new EnumMap<>(FuzzyValue.class);
			for (int q = 0; q < FuzzyValue.inOrder.size(); q++) {
				tempRulaTable.put(FuzzyValue.inOrder.get(q), FuzzyValue.inOrder.get((i + q + ((i + q >= 5) ? 1 : 0)) / 2));
			}
			ruleTable.put(FuzzyValue.inOrder.get(i), tempRulaTable);
		}
		for (FuzzyValue fv : FuzzyValue.inOrder) {
			ruleTable.get(fv).put(FuzzyValue.FF, FuzzyValue.FF);
			ruleTable.get(FuzzyValue.FF).put(fv, FuzzyValue.FF);
		}
		return new TwoXTwoTable(ruleTable, ruleTable);
	}

  @Override
  public List<Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>>> getTables() {
		ArrayList<Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>>> toRet = new ArrayList<>();
		toRet.add(ruleTable1);
		toRet.add(ruleTable2);
		return toRet;
	}

	@Override
	public Stream<FuzzyValue> cellsOneByOne() {
		return FuzzyValue.getFuzzyValuePairsForindexing()
		    .flatMap(ls -> Arrays.asList(ruleTable1.get(ls[0]).get(ls[1]), ruleTable2.get(ls[0]).get(ls[1])).stream());
	}

  @Override
  public boolean maybeExecutable(boolean[] inps) {
    ITable.inpCheck(inps, 2);
    boolean inp1 = inps[0];
    boolean inp2 = inps[1];
    if (!inp1 && inp1 == inp2) {
      return ruleTable1.get(FuzzyValue.FF).get(FuzzyValue.FF) != FuzzyValue.FF
          || ruleTable2.get(FuzzyValue.FF).get(FuzzyValue.FF) != FuzzyValue.FF;
    }
    if (!inp1) {
      for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
        if (ruleTable1.get(FuzzyValue.FF).get(index) != FuzzyValue.FF
            || ruleTable2.get(FuzzyValue.FF).get(index) != FuzzyValue.FF) {
          return true;
        }
      }
      return false;
    }
    if (!inp2) {
      for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
        if (ruleTable1.get(index).get(FuzzyValue.FF) != FuzzyValue.FF
            || ruleTable2.get(index).get(FuzzyValue.FF) != FuzzyValue.FF) {
          return true;
        }
      }
      return false;
    }
    for (FuzzyValue col : FuzzyValue.inOrderWithoutPhi) {
      for (FuzzyValue row : FuzzyValue.inOrderWithoutPhi) {
        if (ruleTable1.get(col).get(row) != FuzzyValue.FF || ruleTable2.get(col).get(row) != FuzzyValue.FF) {
          return true;
        }
      }
    }

    return false;
  }

  public TwoXTwoTable myClone() {
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> temp = new EnumMap<>(FuzzyValue.class);
    for (FuzzyValue outIndex : ruleTable1.keySet()) {
      EnumMap<FuzzyValue, FuzzyValue> inner = new EnumMap<>(FuzzyValue.class);
      for (FuzzyValue inertIndex : ruleTable1.get(outIndex).keySet()) {
        inner.put(inertIndex, ruleTable1.get(outIndex).get(inertIndex));
      }
      temp.put(outIndex, inner);
    }
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> temp2 = new EnumMap<>(FuzzyValue.class);
    for (FuzzyValue outIndex : ruleTable2.keySet()) {
      EnumMap<FuzzyValue, FuzzyValue> inner = new EnumMap<>(FuzzyValue.class);
      for (FuzzyValue inertIndex : ruleTable2.get(outIndex).keySet()) {
        inner.put(inertIndex, ruleTable2.get(outIndex).get(inertIndex));
      }
      temp2.put(outIndex, inner);
    }

    return new TwoXTwoTable(temp, temp2);
  }
}
