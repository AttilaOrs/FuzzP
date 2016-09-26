package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.Plotter;
import core.TableParser;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public class SummingExample {

	String tableStr = "" +
	    "{[<NL><NL><NL><NM><ZR>]" +
	    " [<NL><NL><NM><ZR><PM>]" +
	    " [<NL><NM><ZR><PM><PL>]" +
	    " [<NM><ZR><PM><PL><PL>]" +
	    " [<ZR><PM><PL><PL><PL>]}";

  public SummingExample() {
    // create a table from string: this is a shortcut
    // but you can do by construction by constructing the map,
		// but this way it is easier
		TableParser parser = new TableParser();

		TwoXOneTable table = parser.parseTwoXOneTable(tableStr);
    // print out the table to see if it is everything OK
		System.out.println(parser.createString(table));
    // create a driver
		FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-5, 5);
    // create list where you can store the inps and outs
    ArrayList<Double> inp1 = new ArrayList<>();
    ArrayList<Double> inp2 = new ArrayList<>();
    ArrayList<Double> out1 = new ArrayList<>();

    // executing the table
    for (int i = 0; i < 50; i++) {
			Double inpVal1 = i / 10.0;
			Double inpVal2 = i / -10.0;
      FuzzyToken inp1Token = driver.fuzzifie(inpVal1);
      FuzzyToken inp2Token = driver.fuzzifie(inpVal2);
      // note that here it takes to inpTokens
      FuzzyToken[] rez = table.execute(new FuzzyToken[] { inp1Token, inp2Token });
      Double oupVal = driver.defuzzify(rez[0]);
      inp1.add(inpVal1);
      inp2.add(inpVal2);
      out1.add(oupVal);
    }
    // crate a map for plotting
    Map<String, List<Double>> plotMap = new HashMap<>();
    plotMap.put("inp1", inp1);
    plotMap.put("inp2", inp2);
    plotMap.put("out", out1);

    Plotter plotter = new Plotter(plotMap);

    // this time you will see the file as a plot
    plotter.makeJpg("plot.jpg");
  }

  public static void main(String[] args) {
    new SummingExample();
  }

}
