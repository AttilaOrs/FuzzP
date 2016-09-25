package core.FuzzyPetriLogic.Tables;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.ITable;

public class OneXOneTable implements ITable {

	public static OneXOneTable defaultTable() {
		EnumMap<FuzzyValue, FuzzyValue> valTable = new EnumMap<>(FuzzyValue.class);
		FuzzyValue.inOrder.forEach(fv -> valTable.put(fv, fv));
		return new OneXOneTable(valTable);
	}

	public static OneXOneTable invertingTable() {
		EnumMap<FuzzyValue, FuzzyValue> valTable = new EnumMap<>(FuzzyValue.class);
		valTable.put(FuzzyValue.FF, FuzzyValue.FF);
		valTable.put(FuzzyValue.PL, FuzzyValue.NL);
		valTable.put(FuzzyValue.PM, FuzzyValue.NM);
		valTable.put(FuzzyValue.NL, FuzzyValue.PL);
		valTable.put(FuzzyValue.NM, FuzzyValue.PM);
		valTable.put(FuzzyValue.ZR, FuzzyValue.ZR);

		return new OneXOneTable(valTable);
	}

	Map<FuzzyValue, FuzzyValue> valTable;

	public OneXOneTable(Map<FuzzyValue, FuzzyValue> valTable) {
		this.valTable = valTable;
		putPhiIfNecessary(this.valTable);
	}

	protected static void putPhiIfNecessary(Map<FuzzyValue, FuzzyValue> valTable) {
		if (!valTable.containsKey(FuzzyValue.FF)) {
			valTable.put(FuzzyValue.FF, FuzzyValue.FF);
		}
	}

	public Map<FuzzyValue, FuzzyValue> getTable() {
		return valTable;
	}

	@Override
	public FuzzyToken[] execute(FuzzyToken[] inps) {
		ITable.inpCheck(inps, 1);
		FuzzyToken inp = inps[0];
		FuzzyToken out = FuzzyToken.emptyToken();

		for (FuzzyValue val : FuzzyValue.inOrder) {
			out.addToFuzzyValue(valTable.get(val), inp.getFuzzyValue(val));
		}
		out.normalize();
		return new FuzzyToken[] { out };
	}

	@Override
	public boolean executable(FuzzyToken[] inps) {
		ITable.inpCheck(inps, 1);
		FuzzyToken inp = inps[0];
		Optional<FuzzyValue> rez = inp.getNonZeroValues().map(fv -> valTable.get(fv)).filter(fv -> fv != FuzzyValue.FF)
		    .findAny();

		return rez.isPresent();
	}

	public static OneXOneTable buildFromString(String from) {
		String[] splitted = from.replace('[', '\0').replace(']', '\0').split(";");
		EnumMap<FuzzyValue, FuzzyValue> table = new EnumMap<>(FuzzyValue.class);
		for (int i = 0; i < FuzzyValue.inOrder.size(); i++) {
			table.put(FuzzyValue.inOrder.get(i), FuzzyValue.fromString(splitted[i]));
		}
		return new OneXOneTable(table);
	}

	@Override
	public Stream<FuzzyValue> cellsOneByOne() {
		return FuzzyValue.inOrder.stream().map(fv -> valTable.get(fv));
	}

}
