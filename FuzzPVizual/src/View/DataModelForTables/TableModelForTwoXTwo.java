package View.DataModelForTables;

import javax.swing.table.AbstractTableModel;

import View.TableView;
import config.IConfigurator;
import core.FuzzyPetriLogic.FuzzyValue;
import core.common.generaltable.IGeneralTwoXTwoTable;

public class TableModelForTwoXTwo extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
  private IGeneralTwoXTwoTable myTable;
  private IConfigurator<?, ?, ?, ?> iConfigurator;
	private static String header = "inp";
	private static String out_ch1 = " (out1)";
	private static String out_ch2 = " (out2)";

  public TableModelForTwoXTwo(IGeneralTwoXTwoTable myTable, IConfigurator<?, ?, ?, ?> iConfigurator) {
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
		int mapIndex = (columnIndex - 1) % 2;
		if (columnIndex == 0) {
      return iConfigurator.getFuzzyToString().apply(indexVal);
		}
		FuzzyValue indexVal2 = FuzzyValue.values()[(columnIndex - 1) / 2];
    FuzzyValue val = myTable.getTables().get(mapIndex).get(indexVal).get(indexVal2);
    return iConfigurator.getFuzzyToString().apply(val);
	}
}
