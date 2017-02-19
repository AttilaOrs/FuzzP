package View;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import Controller.IGlobalController;
import Model.FuzzyPVizualModel;
import View.DataModelForTables.TableModelForOneXOne;
import View.DataModelForTables.TableModelForOneXTwo;
import View.DataModelForTables.TableModelForTwoXOne;
import View.DataModelForTables.TableModelForTwoXTwo;
import core.common.generaltable.IGeneralOneXOne;
import core.common.generaltable.IGeneralOneXTwoTable;
import core.common.generaltable.IGeneralTwoXOneTable;
import core.common.generaltable.IGeneralTwoXTwoTable;

public class TableView<TTableType> implements IView {

  public static final boolean PHI_COLUMS_DISPLAY_ENABLED = true;

  private static final String no_selection = "Nothing is selected";
  private static final String TR_SELECT_PTTRN = "TTR_NR is selectd";
  private static final String TR_NR = "TR_NR";

  private FuzzyPVizualModel<?, TTableType, ?, ?> model;
  @SuppressWarnings("unused") // follows the pattern... maybe used in future
  private IGlobalController controller;
  private JPanel myPanel;

  int currenltyDisplayedTransition = -1;

  private JLabel messageLabel;

  private JScrollPane scrollPane;


  public TableView(FuzzyPVizualModel<?, TTableType, ?, ?> model, JPanel myPanel) {
    this.model = model;
    this.myPanel = myPanel;
    myPanel.setLayout(new BorderLayout());
    messageLabel = new JLabel();
    myPanel.add(messageLabel, BorderLayout.NORTH);
    messageLabel.setText(no_selection);
  }

  @Override
  public void transitionSelected(int trId) {
    System.out.println("real_id " + trId);
    TTableType tableToDisplay = model.getTableForTranition(trId);
    currenltyDisplayedTransition = trId;
    messageLabel.setText(TR_SELECT_PTTRN.replace(TR_NR, Integer.toString(trId)) +
        model.getConfigurator().transitionCommentSupply().apply(trId));
    displayFuzzyLogicTable(tableToDisplay);

  }

  private void displayFuzzyLogicTable(TTableType tableToDisplay) {
    TableModel table = null;
    if (tableToDisplay instanceof IGeneralOneXOne) {
      table = new TableModelForOneXOne((IGeneralOneXOne) tableToDisplay, model.getConfigurator());
    }
    if (tableToDisplay instanceof IGeneralOneXTwoTable) {
      table = new TableModelForOneXTwo((IGeneralOneXTwoTable) tableToDisplay, model.getConfigurator());
    }
    if (tableToDisplay instanceof IGeneralTwoXOneTable) {
      table = new TableModelForTwoXOne((IGeneralTwoXOneTable) tableToDisplay, model.getConfigurator());
    }
    if (tableToDisplay instanceof IGeneralTwoXTwoTable) {
      table = new TableModelForTwoXTwo((IGeneralTwoXTwoTable) tableToDisplay, model.getConfigurator());
    }
    JTable tableToDisp = new JTable(table);
    tableToDisp.setFillsViewportHeight(true);
    for (int i = 0; i < tableToDisp.getColumnModel().getColumnCount(); i++) {
      tableToDisp.getColumnModel().getColumn(i).setPreferredWidth(75);
      tableToDisp.getColumnModel().getColumn(i).setMaxWidth(100);
    }
    scrollPane = new JScrollPane(tableToDisp);
    myPanel.add(scrollPane, BorderLayout.CENTER);
  }

  @Override
  public void transitionDeselected(int currentlySelectedTransition) {
    if (currenltyDisplayedTransition != -1) {
      currenltyDisplayedTransition = -1;
      myPanel.remove(scrollPane);
      messageLabel.setText(no_selection);
      myPanel.repaint();
    }
  }

  @Override
  public void setController(IGlobalController controller) {
    this.controller = controller;
  }

  @Override
  public void reset() {
    transitionDeselected(-1);
  }

}
