package core.UnifiedPetriLogic;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyValue;
import core.UnifiedPetriLogic.tables.Operator;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class UnifiedTableParser {

  private boolean phiIsRequired;

  public UnifiedTableParser() {
		this(false);
	}

  public IUnifiedTable parseTable(String strToParse) {
    List<String> ls = splitToCellsContent(deleteOperatorStr(strToParse));
    if (!ls.get(0).contains(",")) {
      if (ls.size() < 10) {
        return parseOneXOneTable(strToParse);
      }
      {
        return parseTwoXOneTable(strToParse);
      }
    } else {
      if (ls.size() < 10) {
        return parseOneXTwoTable(strToParse);
      } else {
        return parseTwoXTwoTable(strToParse);
      }

    }
  }

  public UnifiedTableParser(boolean phiIsRequired) {
		this.phiIsRequired = phiIsRequired;
	}

  public String createString(IUnifiedTable table) {
    if (table instanceof UnifiedOneXOneTable) {
      return createStrForOneXOneTable((UnifiedOneXOneTable) table);
    } else if (table instanceof UnifiedOneXTwoTable) {
      return createStrForOneXTwoTable((UnifiedOneXTwoTable) table);
    } else if (table instanceof UnifiedTwoXOneTable) {
      return createStrForTwoXOneTable((UnifiedTwoXOneTable) table);
    } else if (table instanceof UnifiedTwoXTwoTable) {
      return createStrForTwoXTwoTable((UnifiedTwoXTwoTable) table);
    }

    return "";
  }

  private String createStrForTwoXTwoTable(UnifiedTwoXTwoTable table) {
    ArrayList<String> lines = new ArrayList<>();
    for (FuzzyValue index : fuzzyValsInOrder()) {
      lines.add(createLineWithDoubleCells(table.getTables().get(0).get(index),
          table.getTables().get(1).get(index)));
    }

    String temp = createTabeStrFromLines(lines);
    if (table.getOpertaor() != Operator.None) {
      temp = "@" + table.getOpertaor().getSign() + "@ " + temp;
    }
    return temp;

  }

  private String createStrForTwoXOneTable(UnifiedTwoXOneTable table) {
    ArrayList<String> lines = new ArrayList<>();
    for (FuzzyValue index : fuzzyValsInOrder()) {
      lines.add(createLineWithSinleCelss(table.getTable().get(index)));
    }
    String temp = createTabeStrFromLines(lines);
    if (table.getOpertaor() != Operator.None) {
      temp = "@" + table.getOpertaor().getSign() + "@\n" + temp;
    }
    return temp;
  }

  private String createStrForOneXTwoTable(UnifiedOneXTwoTable table) {
    String lineStr = createLineWithDoubleCells(table.getTables().get(0), table.getTables().get(1));
    return createTableFromASingleLine(lineStr);
  }

  private String createStrForOneXOneTable(UnifiedOneXOneTable table) {
    String lineStr = createLineWithSinleCelss(table.getTable());
    return createTableFromASingleLine(lineStr);
  }

  private String createTableFromASingleLine(String lineStr) {
    return "{" + lineStr + "}";
  }

  private String createTabeStrFromLines(List<String> lines) {
    StringBuilder bld = new StringBuilder("{");
    for (String line : lines) {
      bld.append(line).append("\n");
    }
    bld.append("}");
    return bld.toString();
  }

  private String createLineWithSinleCelss(Map<FuzzyValue, FuzzyValue> table) {
    StringBuilder bld = new StringBuilder("[");
    for (FuzzyValue fv : fuzzyValsInOrder()) {
      bld.append("<").append(table.get(fv).unifiedStr()).append(">");
    }
    bld.append("]");
    return bld.toString();
  }

  private String createLineWithDoubleCells(Map<FuzzyValue, FuzzyValue> firstTable,
      Map<FuzzyValue, FuzzyValue> secondTable) {
    StringBuilder bld = new StringBuilder("[");
    for (FuzzyValue fv : fuzzyValsInOrder()) {
      bld.append("<").append(firstTable.get(fv).unifiedStr()).append(",").append(secondTable.get(fv).unifiedStr())
          .append(">");
    }
    bld.append("]");
    return bld.toString();
  }

  public UnifiedOneXOneTable parseOneXOneTable(String strToParse) {
    List<String> ls = splitToCellsContent(strToParse);
    return createOneXOneTableFromBareCells(ls);
  }

  public UnifiedOneXTwoTable parseOneXTwoTable(String strToParse) {
    List<String> ls = splitToCellsContent(strToParse);
    return createOneXTwoTableFromCells(ls);
  }

  public UnifiedTwoXOneTable parseTwoXOneTable(String strToParse) {
    Operator op = extractOperator(strToParse);
    String remains = deleteOperatorStr(strToParse);
    List<String> ls = splitToCellsContent(remains);
    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = createTwoXOneTableFromCells(ls);
    return new UnifiedTwoXOneTable(table, op);
  }

  private String deleteOperatorStr(String strToParse) {

    if (!strToParse.contains("@")) {
      return strToParse;
    }
    String rez = strToParse.substring(strToParse.indexOf('@'), strToParse.lastIndexOf('@'));

    return strToParse.replace(rez, "").replaceAll("@", "");
  }

  private Operator extractOperator(String strToParse) {
    if (!strToParse.contains("@")) {
      return Operator.None;
    }
    String rez = strToParse.substring(strToParse.indexOf('@'), strToParse.lastIndexOf('@'));
    rez = rez.replaceAll("@", "");
    return Operator.parse(rez);
  }

  public UnifiedTwoXTwoTable parseTwoXTwoTable(String strToParse) {
    Operator op = extractOperator(strToParse);
    String remains = deleteOperatorStr(strToParse);
    List<String> ls = splitToCellsContent(remains);
    return createTwoXTwoTableFromCells(ls, op);
  }

  private UnifiedTwoXTwoTable createTwoXTwoTableFromCells(List<String> ls, Operator op) {
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
    return new UnifiedTwoXTwoTable(firtsTable, secondTable, op);
  }

  private Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> createTwoXOneTableFromCells(List<String> ls) {
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
    return table;
  }

  private void checkCellNrForBigTable(List<String> ls) {
    if ((ls.size() != 25 && !phiIsRequired) || (ls.size() != 36 && phiIsRequired)) {
      throw new TablePareserExcetion(
          "Table type string should containt " + ((phiIsRequired) ? "36" : "25") + " cells");
    }
  }

  private UnifiedOneXTwoTable createOneXTwoTableFromCells(List<String> ls) {
    if ((ls.size() != 5 && !phiIsRequired) || (ls.size() != 6 && phiIsRequired)) {
      throw new TablePareserExcetion(
          "OneXTwoTable string should containt " + ((phiIsRequired) ? "6" : "5") + " cells");
    }

    Map<FuzzyValue, FuzzyValue> table1 = new EnumMap<>(FuzzyValue.class);
    Map<FuzzyValue, FuzzyValue> table2 = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue iterIndex : fuzzyValsInOrder()) {
      FuzzyValue[] parsedVals = parseBareCellWithTwoValues(ls.get(cntr++));
      table1.put(iterIndex, parsedVals[0]);
      table2.put(iterIndex, parsedVals[1]);
    }
    return new UnifiedOneXTwoTable(table1, table2);
  }

  private FuzzyValue[] parseBareCellWithTwoValues(String bareCellString) {
    String splitted[] = bareCellString.split(",");
    if (splitted.length != 2) {
      if (!bareCellString.contains(",")) {
        throw new TablePareserExcetion(
            "A cell of OneXTwoTable should containt two fuzzy values separted with comma (',')");
      } else {
        throw new TablePareserExcetion("Cells with two values has to contain TWO values, separated with comma");
      }
    }
    FuzzyValue val1 = FuzzyValue.parseUnifiedStr(splitted[0]);
    FuzzyValue val2 = FuzzyValue.parseUnifiedStr(splitted[1]);
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

  private UnifiedOneXOneTable createOneXOneTableFromBareCells(List<String> ls) {
    if ((ls.size() != 5 && !phiIsRequired) || (ls.size() != 6 && phiIsRequired)) {
      throw new TablePareserExcetion(
          "UnifiedOneXOneTable string should containt " + ((phiIsRequired) ? "6" : "5") + " cells");
    }
    Map<FuzzyValue, FuzzyValue> table = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue iterIndex : fuzzyValsInOrder()) {
      table.put(iterIndex, parseSingleValue(ls.get(cntr++)));
    }
    return new UnifiedOneXOneTable(table);
  }

  private FuzzyValue parseSingleValue(String valsTr) {
    FuzzyValue val = FuzzyValue.parseUnifiedStr(valsTr);
    if (val == null) {
      throw new TablePareserExcetion("The String :'" + valsTr + "' is an incorrect fuzzy value string");
    }
    return val;
  }

  private List<String> splitToCellsContent(String strToParse) {
    strToParse = strToParse.trim();
    if (!(strToParse.startsWith("{") && strToParse.endsWith("}"))) {
      throw new TablePareserExcetion(
          "Table not defined:: a table string should start with { and should and with } " + strToParse);
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
