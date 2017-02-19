package View;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;

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
import core.common.AbstractPetriNet;
import core.common.recoder.FullRecordable;
import de.erichseifert.gral.ui.InteractivePanel;

public class MainView<TTokenType extends FullRecordable<TTokenType>, TTableType, TOuTableType extends TTableType, TPetriNetType extends AbstractPetriNet<TTokenType, TTableType, TOuTableType>>
    extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final boolean DefalutSaveOpenEnabled = false;
  private static final boolean DefailtDuzzyPLangEnabled = false;

  private boolean saveOpenEnabled;
  private boolean fuzzyPLangEnabled;

  private FuzzyPVizualModel<TTokenType, TTableType, TOuTableType, TPetriNetType> model;
	private GraphView graphView;
	private PlotView plotView;
	private IGlobalController controller;

	private JTabbedPane tabbedPane;
	private File lastFile;

  public MainView(FuzzyPVizualModel<TTokenType, TTableType, TOuTableType, TPetriNetType> model,
      IGlobalController controller) {
    this(model, controller, DefalutSaveOpenEnabled, DefailtDuzzyPLangEnabled);
  }

  public MainView(FuzzyPVizualModel<TTokenType, TTableType, TOuTableType, TPetriNetType> model,
      IGlobalController controller, boolean saveOpenEnabled,
      boolean fuzzyPLangEnabled) {
		super("Fuzzy Petri Visualizer");
    this.saveOpenEnabled = saveOpenEnabled;
    this.fuzzyPLangEnabled = fuzzyPLangEnabled;
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
    TableView<TTableType> tableView = new TableView<TTableType>(model, jp2);
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

		if (saveOpenEnabled) {
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
			

      JMenuItem savePetriJson = new JMenuItem("Save Petri Json only");
      savePetriJson.addActionListener(ac -> {
        int rez = choose.showSaveDialog(this);
        if (rez == JFileChooser.APPROVE_OPTION) {
          model.savePetriJsonOnly(choose.getSelectedFile());
        }
      });
      KeyStroke keyStrokeToSaveJson = KeyStroke.getKeyStroke("shift J");
      savePetriJson.setAccelerator(keyStrokeToSaveJson);

			fileMenu.add(saveItem);
			fileMenu.add(openItem);
      fileMenu.add(savePetriJson);
			bar.add(fileMenu);
		}

		if (fuzzyPLangEnabled) {
			JMenu fileMenu = new JMenu("FuzzyPLang");
			JMenuItem refresh = new JMenuItem("Refresh");
			refresh.addActionListener(ac -> {
				model.loadFuzzyPLang(lastFile);
				controller.globalModelUpdate();
			});
			KeyStroke keyStrokeToRefrash = KeyStroke.getKeyStroke("shift R");
			refresh.setAccelerator(keyStrokeToRefrash);

			JMenuItem openFzp = new JMenuItem("Open FuzzyPLang");
			openFzp.addActionListener(ac -> {
				int rez = choose.showOpenDialog(this);
				if (rez == JFileChooser.APPROVE_OPTION) {
					lastFile = choose.getSelectedFile();
					model.loadFuzzyPLang(lastFile);
					controller.globalModelUpdate();
				}
			});
			KeyStroke keyStrokeToOpenFz = KeyStroke.getKeyStroke("shift F");
			openFzp.setAccelerator(keyStrokeToOpenFz);

			JMenuItem saveJava = new JMenuItem("Save Java");
			saveJava.addActionListener(ac -> {
        if (lastFile != null) {
          model.saveToJava(lastFile);
        } else {
          int rez = choose.showSaveDialog(this);
          if (rez == JFileChooser.APPROVE_OPTION) {
            model.saveToJava(choose.getSelectedFile());
          }
        }
			});
			KeyStroke keyStrokeToSavejava = KeyStroke.getKeyStroke("shift J");
			saveJava.setAccelerator(keyStrokeToSavejava);

			fileMenu.add(saveJava);
			fileMenu.add(openFzp);
			fileMenu.add(refresh);
			bar.add(fileMenu);
		}

		JMenu zoomMenu = new JMenu("Zoom");
		bar.add(zoomMenu);
		JMenuItem zoomInMenuItem = new JMenuItem("Zoom in");
		zoomInMenuItem.addActionListener(a -> graphView.zoomIn());
		KeyStroke keyStrokeToZoomIn = KeyStroke.getKeyStroke(',', java.awt.event.InputEvent.SHIFT_MASK);
		zoomInMenuItem.setAccelerator(keyStrokeToZoomIn);

		JMenuItem zoomOutMenuItem = new JMenuItem("Zoom out");
		zoomOutMenuItem.addActionListener(a -> graphView.zoomOut());
		KeyStroke keyStrokeToZoomOut = KeyStroke.getKeyStroke('.', java.awt.event.InputEvent.SHIFT_MASK);
		zoomOutMenuItem.setAccelerator(keyStrokeToZoomOut);

		zoomMenu.add(zoomInMenuItem);
		zoomMenu.add(zoomOutMenuItem);
	}

}
