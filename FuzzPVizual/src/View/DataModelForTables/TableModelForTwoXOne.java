package View.DataModelForTables;

import javax.swing.table.AbstractTableModel;

import View.TableView;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public class TableModelForTwoXOne extends AbstractTableModel {

  private TwoXOneTable myTable;
  private static String header = "inp";

  public TableModelForTwoXOne(TwoXOneTable myTable) {
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
		return 6 + ((TableView.PHI_COLUMS_DISPLAY_ENABLED) ? 1 : 0);
  }

  @Override
  public String getColumnName(int columnIndex) {
    if (columnIndex == 0) {
      return header;
    }
    return FuzzyValue.values()[columnIndex - 1].name();
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
    if (columnIndex == 0) {
      return indexVal.name();
    }
    FuzzyValue indexVal2 = FuzzyValue.values()[columnIndex - 1];
    return myTable.getTable().get(indexVal).get(indexVal2).name();
  }

}
