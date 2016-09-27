package View.DataModelForTables;

import javax.swing.table.AbstractTableModel;

import View.TableView;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;

public class TableModelForOneXTwo extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private OneXTwoTable myTable;
	private static String[] header = new String[] { "inp", "out1", "out2" };

	public TableModelForOneXTwo(OneXTwoTable myTable) {
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
		return 3;
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
		return myTable.getTables().get(columnIndex - 1).get(indexVal).name();
	}
}
