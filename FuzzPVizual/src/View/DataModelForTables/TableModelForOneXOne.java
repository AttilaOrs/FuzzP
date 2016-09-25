package View.DataModelForTables;

import javax.swing.table.AbstractTableModel;

import View.TableView;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.OneXOneTable;

public class TableModelForOneXOne extends AbstractTableModel {

  private OneXOneTable myTable;
  private static String[] header = new String[] { "inp", "out" };

  public TableModelForOneXOne(OneXOneTable myTable) {
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
    return 2;
  }

  @Override
  public String getColumnName(int columnIndex) {
    return header[columnIndex];
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
    return myTable.getTable().get(indexVal).name();

  }

}
