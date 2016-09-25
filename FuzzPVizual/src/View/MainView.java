package View;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import com.mxgraph.swing.mxGraphComponent;

import Controller.IGlobalController;
import Model.FuzzyPVizualModel;
import de.erichseifert.gral.ui.InteractivePanel;

public class MainView extends JFrame {

  private static boolean SaveOpenEnabled = false;

  private FuzzyPVizualModel model;
  private GraphView graphView;
  private PlotView plotView;
  private IGlobalController controller;

  private JTabbedPane tabbedPane;

  public MainView(FuzzyPVizualModel model, IGlobalController controller) {
    super("Fuzzy Petri Visualizer");
    this.model = model;
    graphView = new GraphView(model);
    plotView = new PlotView(model);
    this.controller = controller;
    mxGraphComponent graphComponent = graphView.createGraphComponent();

    GridLayout ll = new GridLayout(2, 0);
    getContentPane().setLayout(ll);
    getContentPane().add(graphComponent, 0);
    tabbedPane = new JTabbedPane();
    getContentPane().add(tabbedPane, 1);
    JPanel jp2 = new JPanel();
    InteractivePanel plotViewPanel = plotView.getInteractivePanel();
    plotViewPanel.setBackground(Color.WHITE);
    tabbedPane.addTab("Plots", plotViewPanel);
    tabbedPane.addTab("Fuzzy Tables", jp2);
    tabbedPane.setBackground(Color.WHITE);
    TableView tableView = new TableView(model, jp2);

    controller.addView(graphView);
    controller.addView(plotView);
    controller.addView(tableView);
    graphView.setController(controller);
    plotView.setController(controller);
    tableView.setController(controller);
    addMenu();
  }

  public void addInteractivePanel(String tabName, InteractivePanel panel) {
    tabbedPane.add(tabName, panel);

  }

  private void addMenu() {
    JFileChooser choose = new JFileChooser();
    JMenuBar bar = new JMenuBar();
    this.setJMenuBar(bar);

    if (SaveOpenEnabled) {
      JMenu fileMenu = new JMenu("File");
      JMenuItem saveItem = new JMenuItem("Save");
      saveItem.addActionListener(ac -> {
        int rez = choose.showSaveDialog(this);
        if (rez == JFileChooser.APPROVE_OPTION) {
          model.save(choose.getSelectedFile());
        }
      });
      KeyStroke keyStrokeToSave = KeyStroke.getKeyStroke("shift S");
      saveItem.setAccelerator(keyStrokeToSave);
      JMenuItem openItem = new JMenuItem("Open");
      openItem.addActionListener(ac -> {
        int rez = choose.showOpenDialog(this);
        if (rez == JFileChooser.APPROVE_OPTION) {
          model.load(choose.getSelectedFile());
          controller.globalModelUpdate();
        }
      });
      KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke("shift O");
      openItem.setAccelerator(keyStrokeToOpen);

      fileMenu.add(saveItem);
      fileMenu.add(openItem);
      bar.add(fileMenu);
    }
    JMenu zoomMenu = new JMenu("Zoom");
    bar.add(zoomMenu);
    JMenuItem zoomInMenuItem = new JMenuItem("Zoom in");
    zoomInMenuItem.addActionListener(a -> graphView.zoomIn());
    KeyStroke keyStrokeToZoomIn = KeyStroke.getKeyStroke(',', java.awt.event.InputEvent.SHIFT_MASK );
    zoomInMenuItem.setAccelerator(keyStrokeToZoomIn);

    JMenuItem zoomOutMenuItem = new JMenuItem("Zoom out");
    zoomOutMenuItem.addActionListener(a -> graphView.zoomOut());
    KeyStroke keyStrokeToZoomOut = KeyStroke.getKeyStroke('.', java.awt.event.InputEvent.SHIFT_MASK );
    zoomOutMenuItem.setAccelerator(keyStrokeToZoomOut);

    zoomMenu.add(zoomInMenuItem);
    zoomMenu.add(zoomOutMenuItem);
  }

}
