package core.FuzzyPetriLogic.Tables;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.ITable;
import core.common.generaltable.IGeneralTwoXOneTable;

public class TwoXOneTable implements ITable, IGeneralTwoXOneTable {

	public static TwoXOneTable defaultTable() {
		EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable = new EnumMap<>(FuzzyValue.class);
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
		return new TwoXOneTable(ruleTable);
	}

  public static TwoXOneTable onlyPositiveLarge() {
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable = new EnumMap<>(FuzzyValue.class);
    for (int i = 0; i < FuzzyValue.inOrder.size(); i++) {
      EnumMap<FuzzyValue, FuzzyValue> tempRulaTable = new EnumMap<>(FuzzyValue.class);
      for (int q = 0; q < FuzzyValue.inOrder.size(); q++) {
        tempRulaTable.put(FuzzyValue.inOrder.get(q), FuzzyValue.PL);
      }
      ruleTable.put(FuzzyValue.inOrder.get(i), tempRulaTable);
    }
    for (FuzzyValue fv : FuzzyValue.inOrder) {
      ruleTable.get(fv).put(FuzzyValue.FF, FuzzyValue.FF);
      ruleTable.get(FuzzyValue.FF).put(fv, FuzzyValue.FF);
    }
    return new TwoXOneTable(ruleTable);
  }

  protected Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable;

	public TwoXOneTable(Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> rulaTabel) {
		this.ruleTable = rulaTabel;
		putPhiIfNecesary(rulaTabel);
	}


	@Override
	public FuzzyToken[] execute(FuzzyToken[] inps) {
		ITable.inpCheck(inps, 2);
		FuzzyToken toRet = FuzzyToken.emptyToken();
		FuzzyToken inp1 = inps[0];
		FuzzyToken inp2 = inps[1];
		for (int colIndex = 0; colIndex < FuzzyValue.inOrder.size(); colIndex++) {
			FuzzyValue col = FuzzyValue.inOrder.get(colIndex);
			for (int rowInd = 0; rowInd < FuzzyValue.inOrder.size(); rowInd++) {
				FuzzyValue row = FuzzyValue.inOrder.get(rowInd);
				double val = inp1.getFuzzyValue(col) * inp2.getFuzzyValue(row);
				toRet.addToFuzzyValue(ruleTable.get(col).get(row), val);
			}
		}
		toRet.normalize();

		return new FuzzyToken[] { toRet };
	}

	@Override
	public boolean executable(FuzzyToken[] inps) {
		ITable.inpCheck(inps, 2);
		FuzzyToken inp1 = inps[0];
		FuzzyToken inp2 = inps[1];
		Optional<?> rez = inp1.getNonZeroValues()
		    .flatMap(fv -> inp2.getNonZeroValues().map(fv2 -> new FuzzyValue[] { fv, fv2 }))
		    .filter(fvAr -> ruleTable.get(fvAr[0]).get(fvAr[1]) != FuzzyValue.FF)
		    .findAny();
		return rez.isPresent();
	}

  @Override
	public Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> getTable() {
		return this.ruleTable;
	}

	public TwoXOneTable myClone() {
		EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> temp = new EnumMap<>(FuzzyValue.class);
		for (FuzzyValue outIndex : ruleTable.keySet()) {
			EnumMap<FuzzyValue, FuzzyValue> inner = new EnumMap<>(FuzzyValue.class);
			for (FuzzyValue inertIndex : ruleTable.get(outIndex).keySet()) {
				inner.put(inertIndex, ruleTable.get(outIndex).get(inertIndex));
			}
			temp.put(outIndex, inner);
		}
		return new TwoXOneTable(temp);
	}

	@Override
	public Stream<FuzzyValue> cellsOneByOne() {
		return FuzzyValue.getFuzzyValuePairsForindexing().map(ls -> ruleTable.get(ls[0]).get(ls[1]));
	}

	protected static void putPhiIfNecesary(Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable) {
		boolean putInLine = false;
		if (!ruleTable.containsKey(FuzzyValue.FF)) {
			ruleTable.put(FuzzyValue.FF, new HashMap<>());
			putInLine = true;
		}
		for (FuzzyValue fv : FuzzyValue.inOrder) {
			if (!ruleTable.get(fv).containsKey(FuzzyValue.FF))
				ruleTable.get(fv).put(FuzzyValue.FF, FuzzyValue.FF);
			if (putInLine) {
				ruleTable.get(FuzzyValue.FF).put(fv, FuzzyValue.FF);
			}
		}

	}

  @Override
  public boolean maybeExecutable(boolean[] inps) {
    ITable.inpCheck(inps, 2);
    boolean inp1 = inps[0];
    boolean inp2 = inps[1];
    if (!inp1 && inp1 == inp2) {
      return ruleTable.get(FuzzyValue.FF).get(FuzzyValue.FF) != FuzzyValue.FF;
    }
    if (!inp1) {
      for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
        if (ruleTable.get(FuzzyValue.FF).get(index) != FuzzyValue.FF) {
          return true;
        }
      }
      return false;

    }
    if (!inp2) {
      for(FuzzyValue index : FuzzyValue.inOrderWithoutPhi){
        if (ruleTable.get(index).get(FuzzyValue.FF) != FuzzyValue.FF) {
          return true;
        }
      }
      return false;
    }
    for (FuzzyValue col : FuzzyValue.inOrderWithoutPhi) {
      for (FuzzyValue row : FuzzyValue.inOrderWithoutPhi) {
        if (ruleTable.get(col).get(row) != FuzzyValue.FF) {
          return true;
        }
      }
    }

    return false;
  }
}
