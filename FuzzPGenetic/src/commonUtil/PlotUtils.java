package commonUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.DrawingContext;
import de.erichseifert.gral.io.plots.DrawableWriter;
import de.erichseifert.gral.io.plots.DrawableWriterFactory;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;

public class PlotUtils {


  public static final Shape pointShape;
  public static final List<Color> colorsUSed;
  double insetsTop = 20.0, insetsLeft = 40.0, insetsBottom = 20.0, insetsRight = 100.0;
  static {
    double ellipseRaduis = 2.6;
    pointShape = new Ellipse2D.Double(-ellipseRaduis, -ellipseRaduis, 2 * ellipseRaduis, 2 * ellipseRaduis);
    colorsUSed = new ArrayList<>();
    colorsUSed.add(Color.RED);
    colorsUSed.add(Color.BLUE);
    colorsUSed.add(Color.GREEN);
    colorsUSed.add(Color.BLACK);
    colorsUSed.add(Color.ORANGE);
    colorsUSed.add(Color.MAGENTA);
    colorsUSed.add(Color.DARK_GRAY);
    colorsUSed.add(Color.CYAN);
    colorsUSed.add(Color.PINK);
    colorsUSed.add(Color.YELLOW);


  }
  public static void plot(Map<String, List<Double>> what, String fileName) {

    XYPlot plot = new XYPlot();
    int cntr = 0;
    double maxKey = 0.0;
    double maxValue = 0.0;

    for(String name : what.keySet()){
      DataTable dataTable = new DataTable(Double.class, Double.class);
      List<Double> data = what.get(name);
      for (int i = 0; i < data.size(); i++) {
        dataTable.add(((double) i), data.get(i));
        maxValue = (data.get(i) > maxValue) ? data.get(i) : maxValue;
      }
      maxKey = (data.size() > maxKey) ? data.size() : maxKey;
      DataSeries series = new DataSeries(name, dataTable, 0, 1);
      plot.add(series);
      LineRenderer ll = new DefaultLineRenderer2D();
      plot.setLineRenderers(series, ll);
      plot.getPointRenderers(series).get(0).setColor(colorsUSed.get(cntr % colorsUSed.size()));
      plot.getPointRenderers(series).get(0).setShape(pointShape);

      plot.getLineRenderers(series).get(0).setColor(colorsUSed.get(cntr % colorsUSed.size()));
      cntr++;
    }

    plot.setLegendVisible(true);
    // plot.setLegendLocation(Location.EAST);

    writeOut(fileName, plot, maxKey, maxValue);

	}

  private static void writeOut(String fileName, XYPlot plot, double maxKey, double maxValue) {
    plot.getAxis(XYPlot.AXIS_X).setMin(maxKey / -10.0);
    plot.getAxis(XYPlot.AXIS_Y).setMin(maxValue / -10.0);
    plot.getAxis(XYPlot.AXIS_X).setMax(maxKey + maxKey / 25.0);
    plot.getAxis(XYPlot.AXIS_Y).setMax(maxValue + maxValue / 25.0);

    BufferedImage bImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = (Graphics2D) bImage.getGraphics();
    DrawingContext context = new DrawingContext(g2d);
    plot.draw(context);
    plot.draw(context);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DrawableWriter wr = DrawableWriterFactory.getInstance().get("image/jpeg");
    try {
      wr.write(plot, baos, 800, 600);
      baos.flush();
      OutputStream outputStream = new FileOutputStream(fileName + ".jpg");
      baos.writeTo(outputStream);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static double maxKey = 0.0;
  private static double maxValue = 0.0;
  public static void plot2(Map<String, Map<Double, Double>> what,
			String fileName) {
    XYPlot plot = new XYPlot();
    int cntr = 0;
    maxKey = 0.0;
    maxValue = 0.0;
    for (String title : what.keySet()) {
      DataTable dataTable = new DataTable(Double.class, Double.class);
      Map<Double, Double> curentMap = what.get(title);
      curentMap.keySet().stream().sorted().forEach(key -> {
        maxKey = (maxKey < key) ? key : maxKey;
        maxValue = (maxValue < curentMap.get(key)) ? curentMap.get(key) : maxKey;
        dataTable.add(key, curentMap.get(key));
      });
      DataSeries series = new DataSeries(title, dataTable, 0, 1);
      plot.add(series);
      plot.getPointRenderers(series).get(0).setColor(colorsUSed.get(cntr % colorsUSed.size()));
      plot.getPointRenderers(series).get(0).setShape(pointShape);
      cntr++;
    }
    plot.setLegendVisible(true);
    // plot.setLegendLocation(Location.EAST);

    writeOut(fileName, plot, maxKey, maxValue);

	}

  public static void hist(Map<String, Map<Integer, Integer>> what,
			String fileName) {

    XYPlot plot = new XYPlot();
    int cntr = 0;
    maxKey = 0.0;
    maxValue = 0.0;
    for (String title : what.keySet()) {
      DataTable dataTable = new DataTable(Double.class, Double.class);
      Map<Integer, Integer> curentMap = what.get(title);
      curentMap.keySet().stream().sorted().forEach(key -> {
        maxKey = (maxKey < key) ? key : maxKey;
        maxValue = (maxValue < curentMap.get(key)) ? curentMap.get(key) : maxKey;
        dataTable.add((double) key, (double) curentMap.get(key));
      });
      DataSeries series = new DataSeries(title, dataTable, 0, 1);
      plot.add(series);
      plot.getPointRenderers(series).get(0).setColor(colorsUSed.get(cntr % colorsUSed.size()));
      plot.getPointRenderers(series).get(0).setShape(pointShape);
      LineRenderer ll = new DefaultLineRenderer2D();
      plot.setLineRenderers(series, ll);
      plot.getLineRenderers(series).get(0).setColor(colorsUSed.get(cntr % colorsUSed.size()));
      cntr++;
    }
    plot.setLegendVisible(true);
    // plot.setLegendLocation(Location.EAST);

    writeOut(fileName, plot, maxKey, maxValue);
	}
	public static void writeToFile(String fileName, String content) {
		try {
			File file = new File(fileName);
      file.getParentFile().mkdirs();
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(content);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
