package View.DataModelForTables;

import javax.swing.table.AbstractTableModel;

import View.TableView;
import config.IConfigurator;
import core.FuzzyPetriLogic.FuzzyValue;
import core.common.generaltable.IGeneralTwoXOneTable;

public class TableModelForTwoXOne extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
  private IGeneralTwoXOneTable myTable;
	private static String header = "inp";
  private IConfigurator<?, ?, ?, ?> iConfigurator;

  public TableModelForTwoXOne(IGeneralTwoXOneTable myTable, IConfigurator<?, ?, ?, ?> iConfigurator) {
		this.myTable = myTable;
    this.iConfigurator = iConfigurator;
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
  public Class<String> getColumnClass(int columnIndex) {
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
      return iConfigurator.getFuzzyToString().apply(indexVal);
		}
		FuzzyValue indexVal2 = FuzzyValue.values()[columnIndex - 1];
    FuzzyValue val = myTable.getTable().get(indexVal).get(indexVal2);
    return iConfigurator.getFuzzyToString().apply(val);
	}

}
