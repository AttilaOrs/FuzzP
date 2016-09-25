package View.DataModelForTables;

import javax.swing.table.AbstractTableModel;

import View.TableView;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class TableModelForTwoXTwo extends AbstractTableModel {

  private TwoXTwoTable myTable;
  private static String header = "inp";
  private static String out_ch1 = " (out1)";
  private static String out_ch2 = " (out2)";

  public TableModelForTwoXTwo(TwoXTwoTable myTable) {
    this.myTable = myTable;
  }

  @Override
  public int getRowCount() {
		if (TableView.PHI_COLUMS_DISPLAY_ENABLED)
      return 6;
    return 5;
  }

  @Override
  public int getColumnCount() {
		return 1 + (5 + ((TableView.PHI_COLUMS_DISPLAY_ENABLED) ? 1 : 0)) * 2;
  }

  @Override
  public String getColumnName(int columnIndex) {
    if (columnIndex == 0) {
      return header;
    }

    return FuzzyValue.values()[(columnIndex - 1) / 2].name() + (((columnIndex - 1) % 2 == 0) ? out_ch1 : out_ch2);
  }

  @Override
  public Class getColumnClass(int columnIndex) {
    return String.class;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  @Override
  public String getValueAt(int rowIndex, int columnIndex) {

    FuzzyValue indexVal = FuzzyValue.values()[rowIndex];
    int mapIndex = (columnIndex - 1) % 2;
    if (columnIndex == 0) {
      return indexVal.name();
    }
    FuzzyValue indexVal2 = FuzzyValue.values()[(columnIndex - 1) / 2];
    return myTable.getTables().get(mapIndex).get(indexVal).get(indexVal2).name();
  }
}
