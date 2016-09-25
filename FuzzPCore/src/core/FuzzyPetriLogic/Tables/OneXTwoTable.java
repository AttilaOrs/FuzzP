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

public class OneXTwoTable implements ITable {

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

	public static OneXTwoTable buildFromString(String from) {
		String[] splitted = from.replace('[', '\0').replace(']', '\0').split(";");
		EnumMap<FuzzyValue, FuzzyValue> table1 = new EnumMap<>(FuzzyValue.class);
		EnumMap<FuzzyValue, FuzzyValue> table2 = new EnumMap<>(FuzzyValue.class);
		for (int i = 0; i < FuzzyValue.inOrder.size(); i++) {
			table1.put(FuzzyValue.inOrder.get(i), FuzzyValue.fromString(splitted[i]));
			table2.put(FuzzyValue.inOrder.get(i), FuzzyValue.fromString(splitted[i + FuzzyValue.inOrder.size()]));
		}
		return new OneXTwoTable(table1, table2);
	}

	public String toShortString() {
		StringBuilder table1Str = new StringBuilder();
		StringBuilder table2Str = new StringBuilder();
		for (FuzzyValue ff : FuzzyValue.inOrder) {
			table1Str.append(valTable1.get(ff)).append(";");
			table2Str.append(valTable2.get(ff)).append(";");
		}
		table1Str.append(table2Str.toString());
		return table1Str.toString();
	}

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
}
