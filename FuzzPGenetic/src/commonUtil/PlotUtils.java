package commonUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.FileTerminal;

public class PlotUtils {

	private static PlotColor[] usedColors = new PlotColor[]{
			NamedPlotColor.BLACK, NamedPlotColor.RED, NamedPlotColor.BLUE,
			NamedPlotColor.GREEN, NamedPlotColor.YELLOW, NamedPlotColor.GRAY,
			NamedPlotColor.DARK_ORANGE, NamedPlotColor.PINK,
			NamedPlotColor.MIDNIGHT_BLUE, NamedPlotColor.BROWN,
			NamedPlotColor.VIOLET};

  public static void plot(Map<String, List<Double>> what, String fileName) {
		try {
			JavaPlot p = null;

			if (System.getProperty("os.name").contains("Windows")) {
				p = new JavaPlot(
						"c:\\Program Files\\gnuplot\\bin\\gnuplot.exe", false);
			} else {
				p = new JavaPlot();
			}
			//JavaPlot.getDebugger().setLevel(Debug.QUIET);
      FileTerminal svg = new FileTerminal("svg", fileName);
			// String kacs = svg.getOutputFile();
			svg.set("size", "800, 600");
			p.setTerminal(svg);
			p.setTitle("Results");
			int cntr = 0;
			for (String title : what.keySet()) {
        List<Double> curentList = what.get(title);
				double[][] temp = new double[curentList.size()][2];
				for (int q = 0; q < curentList.size(); q++) {
					temp[q][0] = q;
					temp[q][1] = curentList.get(q);
				}
				DataSetPlot dt = new DataSetPlot(temp);
				p.addPlot(dt);
				((AbstractPlot) p.getPlots().get(cntr)).setTitle(title);

				PlotStyle stl = ((AbstractPlot) p.getPlots().get(cntr))
						.getPlotStyle();
				stl.setStyle(Style.LINES);
				stl.setLineType(usedColors[cntr % usedColors.length]);
				stl.setLineWidth(1);
				cntr++;
			}
			// p.setKey(JavaPlot.Key.TOP_LEFT);
			p.setKey(JavaPlot.Key.OUTSIDE);
			p.plot();

		} catch (RuntimeException ex) {
			System.err
					.println("!! probaby you do not have the plotting libabry insatlled ");
			PlotUtils.writeToFile(fileName + ".content", String.valueOf(what));
			Gson gson = (new GsonBuilder()).create();
			String jjson = gson.toJson(what);
			PlotUtils.writeToFile(fileName + "_indexPlot.json", jjson);

		}
	}

  public static void plot2(Map<String, Map<Double, Double>> what,
			String fileName) {

		JavaPlot p = new JavaPlot();
		//JavaPlot.getDebugger().setLevel(Debug.QUIET);
    // SVGTerminal svg = new SVGTerminal(fileName);
    FileTerminal svg = new FileTerminal("svg", fileName);
		// String kacs = svg.getOutputFile();
		p.setTerminal(svg);
		p.setTitle("Results");
		int cntr = 0;
		for (String title : what.keySet()) {
			System.out.println(title);
      Map<Double, Double> curentList = what.get(title);
			double[][] temp = new double[curentList.size()][2];
			ArrayList<Double> cigi = new ArrayList<Double>(curentList.keySet());
			/*
			 * cigi.sort(new Comparator<Double>() {
			 * 
			 * @Override public int compare(Double o1, Double o2) { double delta
			 * = (o1 - o2); if (delta < 0.0) { return 1; } if (delta > 0.0) {
			 * return -1; } return 0; } });
			 */

			for (int index = 0; index < cigi.size(); index++) {
				Double key = cigi.get(index);
				Double val = curentList.get(key);
				temp[index][0] = key;
				temp[index][1] = val;
				index++;
			}
			DataSetPlot dt = new DataSetPlot(temp);
			p.addPlot(dt);
			((AbstractPlot) p.getPlots().get(cntr)).setTitle(title);

			PlotStyle stl = ((AbstractPlot) p.getPlots().get(cntr))
					.getPlotStyle();
			stl.setStyle(Style.POINTS);
			cntr++;
		}
		p.plot();
	}

	public static void writeToFile(String fileName, String content) {
		try {
			File file = new File(fileName);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(content);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
