package core;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class TableParser {

	private boolean phiIsRequired;

	public TableParser() {
		this(false);
	}

	public TableParser(boolean phiIsRequired) {
		this.phiIsRequired = phiIsRequired;
	}

	public ITable parseTable(String strToParse) {
		List<String> ls = splitToCellsContent(strToParse);
		if (!ls.get(0).contains(",")) {
			if(ls.size()< 10){
				return createOneXOneTableFromBareCells(ls);
			}{
				return createTwoXOneTableFromCells(ls);
			}
		} else {
			if (ls.size() < 10) {
				return createOneXTwoTableFromCells(ls);
			} else {
				return createTwoXTwoTableFromCells(ls);
			}

		}
	}

	public OneXOneTable parseOneXOneTable(String strToParse) {
		List<String> ls = splitToCellsContent(strToParse);
		return createOneXOneTableFromBareCells(ls);
	}

	public OneXTwoTable parseOneXTwoTable(String strToParse) {
		List<String> ls = splitToCellsContent(strToParse);
		return createOneXTwoTableFromCells(ls);
	}

	public TwoXOneTable parseTwoXOneTable(String strToParse) {
		List<String> ls = splitToCellsContent(strToParse);
		return createTwoXOneTableFromCells(ls);
	}

	public TwoXTwoTable parseTwoXTwoTable(String strToParse) {
		List<String> ls = splitToCellsContent(strToParse);
		return createTwoXTwoTableFromCells(ls);
	}

	private TwoXTwoTable createTwoXTwoTableFromCells(List<String> ls) {
		checkCellNrForBigTable(ls);
		Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firtsTable = new EnumMap<>(FuzzyValue.class);
		Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable = new EnumMap<>(FuzzyValue.class);
		int cntr = 0;
		for (FuzzyValue indexLine : fuzzyValsInOrder()) {
			Map<FuzzyValue, FuzzyValue> firtsTablelineMap = new EnumMap<>(FuzzyValue.class);
			Map<FuzzyValue, FuzzyValue> secondTableLineMap = new EnumMap<>(FuzzyValue.class);
			for (FuzzyValue indexColumn : fuzzyValsInOrder()) {
				FuzzyValue[] parsedVals = parseBareCellWithTwoValues(ls.get(cntr++));
				firtsTablelineMap.put(indexColumn, parsedVals[0]);
				secondTableLineMap.put(indexColumn, parsedVals[1]);
			}
			firtsTable.put(indexLine, firtsTablelineMap);
			secondTable.put(indexLine, secondTableLineMap);
		}
		return new TwoXTwoTable(firtsTable, secondTable);
	}

	private TwoXOneTable createTwoXOneTableFromCells(List<String> ls) {
		checkCellNrForBigTable(ls);
		Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
		int cntr = 0;
		for (FuzzyValue indexLine : fuzzyValsInOrder()) {
			Map<FuzzyValue, FuzzyValue> lineMap = new EnumMap<>(FuzzyValue.class);
			for (FuzzyValue indexColumn : fuzzyValsInOrder()) {
				lineMap.put(indexColumn, parseSingleValue(ls.get(cntr++)));
			}
			table.put(indexLine, lineMap);
		}
		return new TwoXOneTable(table);
	}

	private void checkCellNrForBigTable(List<String> ls) {
		if ((ls.size() != 25 && !phiIsRequired) || (ls.size() != 36 && phiIsRequired)) {
			throw new TablePareserExcetion(
			    "Table type string should containt " + ((phiIsRequired) ? "36" : "25") + " cells");
		}
	}

	private OneXTwoTable createOneXTwoTableFromCells(List<String> ls) {
		if ((ls.size() != 5 && !phiIsRequired) || (ls.size() != 6 && phiIsRequired)) {
			throw new TablePareserExcetion("OneXTwoTable string should containt " + ((phiIsRequired) ? "6" : "5") + " cells");
		}

		Map<FuzzyValue, FuzzyValue> table1 = new EnumMap<>(FuzzyValue.class);
		Map<FuzzyValue, FuzzyValue> table2 = new EnumMap<>(FuzzyValue.class);
		int cntr = 0;
		for (FuzzyValue iterIndex : fuzzyValsInOrder()) {
			FuzzyValue[] parsedVals = parseBareCellWithTwoValues(ls.get(cntr++));
			table1.put(iterIndex, parsedVals[0]);
			table2.put(iterIndex, parsedVals[1]);
		}
		return new OneXTwoTable(table1, table2);
	}

	private FuzzyValue[] parseBareCellWithTwoValues(String bareCellString) {
		String splitted[] = bareCellString.split(",");
		if (splitted.length != 2) {
			throw new TablePareserExcetion(
			    "A cell of OneXTwoTable should containt two fuzzy values separted with comma (',')");
		}
		FuzzyValue val1 = FuzzyValue.fromString(splitted[0]);
		FuzzyValue val2 = FuzzyValue.fromString(splitted[1]);
		if ((val1 == null) || (val2 == null)) {
			if (val1 == null) {
				throw new TablePareserExcetion("The String :'" + splitted[0] + "' is an incorrect fuzzy value string");
			} else {
				throw new TablePareserExcetion("The String :'" + splitted[1] + "' is an incorrect fuzzy value string");
			}
		}
		FuzzyValue[] parsedVals = new FuzzyValue[] { val1, val2 };
		return parsedVals;
	}

	private List<FuzzyValue> fuzzyValsInOrder() {
		return (phiIsRequired) ? FuzzyValue.inOrder : FuzzyValue.inOrderWithoutPhi;
	}

	private OneXOneTable createOneXOneTableFromBareCells(List<String> ls) {
		if ((ls.size() != 5 && !phiIsRequired) || (ls.size() != 6 && phiIsRequired)) {
			throw new TablePareserExcetion("OneXOneTabe string should containt " + ((phiIsRequired) ? "6" : "5") + " cells");
		}
		Map<FuzzyValue, FuzzyValue> table = new EnumMap<>(FuzzyValue.class);
		int cntr = 0;
		for (FuzzyValue iterIndex : fuzzyValsInOrder()) {
			table.put(iterIndex, parseSingleValue(ls.get(cntr++)));
		}
		return new OneXOneTable(table);
	}

	private FuzzyValue parseSingleValue(String valsTr) {
		FuzzyValue val = FuzzyValue.fromString(valsTr);
		if (val == null) {
			throw new TablePareserExcetion("The String :'" + valsTr + "' is an incorrect fuzzy value string");
		}
		return val;
	}

	private List<String> splitToCellsContent(String strToParse) {
		if (!(strToParse.startsWith("{") && strToParse.endsWith("}"))) {
			throw new TablePareserExcetion("Table not defined:: a table string should start with { and should and with }");
		}
		String bareaTable = strToParse.replace("{", "").replace("}", "");
		String[] lines = bareaTable.split("\\[");
		List<String> bareCellStrings = new ArrayList<>();
		for (int i = 1; i < lines.length; i++) {
			if (!lines[i].contains("]")) {
				throw new TablePareserExcetion(
				    "Table not defined:: lines in the table should start with [ and should end with ]");
			}
			int lastIndex = lines[i].indexOf("]");
			String bareLine = lines[i].substring(0, lastIndex);
			String cells[] = bareLine.split("<");
			for (int j = 1; j < cells.length; j++) {
				if (!cells[j].contains(">")) {
					throw new TablePareserExcetion(
					    "Table not defined:: cless in the table should start with < and should end with >");
				}
				int lastIndexCels = cells[j].indexOf(">");
				String bareCell = cells[j].substring(0, lastIndexCels);
				bareCellStrings.add(bareCell);
			}
		}
		return bareCellStrings;
	}

	public class TablePareserExcetion extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public TablePareserExcetion(String string) {
			super(string);
		}

	}

}
