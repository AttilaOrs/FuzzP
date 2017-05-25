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
import core.common.generaltable.IGeneralOneXTwoTable;

public class OneXTwoTable implements ITable, IGeneralOneXTwoTable {

	public static OneXTwoTable defaultTable() {
		EnumMap<FuzzyValue, FuzzyValue> valTable1 = new EnumMap<>(FuzzyValue.class);
		EnumMap<FuzzyValue, FuzzyValue> valTable2 = new EnumMap<>(FuzzyValue.class);
		FuzzyValue.inOrder.forEach(fv -> {
			valTable1.put(fv, fv);
			valTable2.put(fv, fv);
		});
		return new OneXTwoTable(valTable1, valTable2);
	}

	Map<FuzzyValue, FuzzyValue> valTable1;
	Map<FuzzyValue, FuzzyValue> valTable2;

	public OneXTwoTable(Map<FuzzyValue, FuzzyValue> valTable1, Map<FuzzyValue, FuzzyValue> valTable2) {
		this.valTable1 = valTable1;
		this.valTable2 = valTable2;
		OneXOneTable.putPhiIfNecessary(valTable1);
		OneXOneTable.putPhiIfNecessary(valTable2);
	}

	@Override
	public FuzzyToken[] execute(FuzzyToken[] inps) {
		ITable.inpCheck(inps, 1);
		FuzzyToken inp = inps[0];
		FuzzyToken out1 = FuzzyToken.emptyToken();
		FuzzyToken out2 = FuzzyToken.emptyToken();
		for (FuzzyValue val : FuzzyValue.inOrder) {
			out1.addToFuzzyValue(valTable1.get(val), inp.getFuzzyValue(val));
			out2.addToFuzzyValue(valTable2.get(val), inp.getFuzzyValue(val));
		}
		out1.normalize();
		out2.normalize();
		return new FuzzyToken[] { out1, out2 };
	}

	@Override
	public boolean executable(FuzzyToken[] inps) {
		ITable.inpCheck(inps, 1);
		FuzzyToken inp = inps[0];

		Optional<FuzzyValue> rez = inp.getNonZeroValues()
		    .map(fv -> (valTable1.get(fv) != FuzzyValue.FF) ? valTable1.get(fv) : valTable2.get(fv))
		    .filter(fv -> fv != FuzzyValue.FF).findAny();

		return rez.isPresent();
	}

  @Override
	public List<Map<FuzzyValue, FuzzyValue>> getTables() {
		List<Map<FuzzyValue, FuzzyValue>> toRet = new ArrayList<>();
		toRet.add(valTable1);
		toRet.add(valTable2);
		return toRet;
	}

	@Override
	public Stream<FuzzyValue> cellsOneByOne() {
		return FuzzyValue.inOrder.stream().flatMap(fv -> Arrays.asList(valTable1.get(fv), valTable2.get(fv)).stream());
	}

  @Override
  public boolean maybeExecutable(boolean[] inps) {
    ITable.inpCheck(inps, 1);
    if (!inps[0]) {
      return valTable1.get(FuzzyValue.FF) != FuzzyValue.FF || valTable2.get(FuzzyValue.FF) != FuzzyValue.FF;
    }

    for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
      if (valTable1.get(index) != FuzzyValue.FF || valTable2.get(index) != FuzzyValue.FF) {
        return true;
      }
    }
    return false;
  }
}
