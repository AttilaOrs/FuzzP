package Main;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import View.PlotView;
import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.io.plots.DrawableWriter;
import de.erichseifert.gral.io.plots.DrawableWriterFactory;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class Plotter {

  private Map<String, List<Double>> toPlot;
  double minX, maxX, minY, maxY;

  public Plotter(Map<String, List<Double>> toPlot) {
    this.toPlot = toPlot;
    calcExtrems();
  }

  private void calcExtrems() {
    minX = 0;
    minY = 1000.0;
    maxX = maxY = 0.0;
    for (String key : toPlot.keySet()) {
      List<Double> temp = toPlot.get(key);
      if (temp.size() > maxX) {
        maxX = temp.size();
      }
      for (Double val : temp) {
        if (val > maxY) {
          maxY = val;
        }
        if (val < minY) {
          minY = val;
        }

      }

    }

  }

  public InteractivePanel makeInteractivePlot() {
    XYPlot plot = makePlot();
    InteractivePanel panel = new InteractivePanel(plot);
    return panel;
  }

  public void makeJpg(String filePath) {
    try {
      File file = new File(filePath);
      DrawableWriter writer = DrawableWriterFactory.getInstance().get("image/jpeg");
      writer.write(makePlot(), new FileOutputStream(file), 800, 600);
    } catch (IOException e) {
      System.err.println("FILE HANDELING PROBLEM!!!");
      e.printStackTrace();
    }

  }

  private XYPlot makePlot() {
    XYPlot plot = new XYPlot();
    int cntr = 0;
    for (String key : toPlot.keySet()) {
      DataSeries ds = convertToDataSeries(key);
      plot.add(ds);
      LineRenderer render = new DefaultLineRenderer2D();
      Color color = PlotView.lineColors.get(cntr % PlotView.lineColors.size());
      render.setColor(color);
      plot.setLineRenderers(ds, render);
      plot.getPointRenderers(ds).get(0).setColor(color);
      plot.getPointRenderers(ds).get(0).setShape(PlotView.pointShape);
      cntr++;

    }
    plot.setLegendVisible(true);
    plot.getAxis(XYPlot.AXIS_X).setMax(maxX);
    plot.getAxis(XYPlot.AXIS_X).setMin(minX - (maxX - minX) / 15);
    return plot;

  }

  private DataSeries convertToDataSeries(String key) {
    DataTable table = new DataTable(Double.class, Double.class);
    List<Double> userData = toPlot.get(key);
    for (int i = 0; i < userData.size(); i++) {
      if (userData.get(i) != null) {
        table.add(i + 0.0, userData.get(i));
      }
    }
    return new DataSeries(key, table, 0, 1);
  }

}
