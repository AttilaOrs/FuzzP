package View;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Controller.IGlobalController;
import Model.FuzzyPVizualModel;
import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Label;
import de.erichseifert.gral.graphics.Location;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class PlotView implements IView {
  private static final double ellipseRaduis = 2.5;
  public static final Shape pointShape;
  public static final List<Color> pointColors;
  public static final List<Color> lineColors;
  double insetsTop = 20.0, insetsLeft = 40.0, insetsBottom = 20.0, insetsRight = 100.0;
  static {
    pointShape = new Ellipse2D.Double(-ellipseRaduis, -ellipseRaduis, 2 * ellipseRaduis, 2 * ellipseRaduis);
    pointColors = new ArrayList<>();
    lineColors = new ArrayList<>();
    pointColors.add(Color.RED);
    pointColors.add(Color.BLUE);
    pointColors.add(Color.GREEN);
    pointColors.add(Color.BLACK);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.MAGENTA);
		pointColors.add(Color.DARK_GRAY);
    for (int i = 0; i < pointColors.size(); i++) {
      Color ll = pointColors.get(i);
      lineColors.add(new Color(ll.getRed(), ll.getGreen(), ll.getBlue(), 100));
    }

  }

  private InteractivePanel interativePane;
  private FuzzyPVizualModel model;
  private XYPlot plot;
  int cntr;
  HashMap<Integer, DataSeries> placeSeries;
  HashMap<Integer, DataSeries> inputSeries;
  HashMap<Integer, DataSeries> outSeries;
  private IGlobalController controller;

  public PlotView(FuzzyPVizualModel mm) {
    model = mm;
    placeSeries = new HashMap<>();
    inputSeries = new HashMap<>();
    outSeries = new HashMap<>();
    cntr = 0;
    intializeGui();
  }

  private void intializeGui() {
    plot = new XYPlot();
    // adding demo data
    DataTable tt = new DataTable(Double.class, Double.class);
    for (double d = 0.0; d < model.getTickNr(); d++) {
      tt.add(d, Math.sin(d));
    }
    DataSeries dd = new DataSeries("demo data", tt, 0, 1);
    plot.add(dd);

    plot.setInsets(new Insets2D.Double(insetsTop, insetsLeft, insetsBottom, insetsRight));
    plot.getAxisRenderer(XYPlot.AXIS_X).setLabel(new Label("time"));
    Label r = new Label("value");
    r.setRotation(90);
    plot.getAxisRenderer(XYPlot.AXIS_Y).setLabel(r);
    interativePane = new InteractivePanel(plot);
    plot.remove(dd); // removing demo data
    plot.setLegendVisible(true);
    plot.setLegendLocation(Location.EAST);
    plot.getLegend().setBorderStroke(null);
    plot.getLegend().setBackground(null);
  }

  public InteractivePanel getInteractivePanel() {
    return interativePane;
  }

  @Override
  public void placeSelected(int plId) {
    if (placeSeries.containsKey(plId)) {
      return; // place is already ploted
    }
    String name = model.getNameStore().getPlaceName(plId);
    DataTable data = model.getDataForPlace(plId);
    DataSeries ss = addToPlotWithName(name, data);

    placeSeries.put(plId, ss);

  }

  private DataSeries addToPlotWithName(String name, DataTable data) {
    DataSeries ss = new DataSeries(name, data, 0, 1);
    plot.add(ss);
    LineRenderer ll = new DefaultLineRenderer2D();
    plot.setLineRenderers(ss, ll);
    plot.getPointRenderers(ss).get(0).setColor(getPointColor());
    plot.getPointRenderers(ss).get(0).setShape(pointShape);

    plot.getLineRenderers(ss).get(0).setColor(getLineColor());
    interativePane.repaint();
    cntr++;
    return ss;
  }

  private Color getPointColor() {
    return pointColors.get(cntr % pointColors.size());
  }

  private Color getLineColor() {
    return lineColors.get(cntr % lineColors.size());
  }

  @Override
  public void placeDeselect(int plId) {
    if (placeSeries.containsKey(plId)) {
      plot.remove(placeSeries.get(plId));
      interativePane.repaint();
      placeSeries.remove(plId);
    }

  }


  @Override
  public void setController(IGlobalController controller) {
    this.controller = controller;
  }

  @Override
  public void reset() {
    for (Integer plId : placeSeries.keySet()) {
      plot.remove(placeSeries.get(plId));
      placeSeries.remove(plId);
    }
    for (Integer inpID : inputSeries.keySet()) {
      plot.remove(inputSeries.get(inpID));
      inputSeries.remove(inpID);
    }
    for (Integer outId : outSeries.keySet()) {
      plot.remove(outSeries.get(outId));
      outSeries.remove(outId);
    }
    interativePane.repaint();

  }

}
