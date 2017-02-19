package View.DataModelForTables;

import javax.swing.table.AbstractTableModel;

import View.TableView;
import config.IConfigurator;
import core.FuzzyPetriLogic.FuzzyValue;
import core.common.generaltable.IGeneralOneXOne;

public class TableModelForOneXOne extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
  private IGeneralOneXOne myTable;
  private IConfigurator<?, ?, ?, ?> iConfigurator;
	private static String[] header = new String[] { "inp", "out" };

  public TableModelForOneXOne(IGeneralOneXOne myTable, IConfigurator<?, ?, ?, ?> iConfigurator) {
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
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return header[columnIndex];
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
    FuzzyValue val = myTable.getTable().get(indexVal);

    return iConfigurator.getFuzzyToString().apply(val);

	}

}
