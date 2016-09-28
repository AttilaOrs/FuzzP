package examples.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import Main.Plotter;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.OneXOneTable;

public class InvertorExample extends JFrame {

  public InvertorExample() {

    Map<FuzzyValue, FuzzyValue> ruleMap = new HashMap<>();
    ruleMap.put(FuzzyValue.NL, FuzzyValue.PL);
    ruleMap.put(FuzzyValue.NM, FuzzyValue.PM);
    ruleMap.put(FuzzyValue.ZR, FuzzyValue.ZR);
    ruleMap.put(FuzzyValue.PM, FuzzyValue.NM);
    ruleMap.put(FuzzyValue.PL, FuzzyValue.NL);

    OneXOneTable oneXoneTable = new OneXOneTable(ruleMap);

    FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

    ArrayList<Double> savedInputs = new ArrayList<>();
    ArrayList<Double> savedOuputs = new ArrayList<>();

    for (int i = 0; i < 50; i++) {
      Double inpValue = Math.sin(i / 10.0);
      FuzzyToken inpToken = driver.fuzzifie(inpValue);
      FuzzyToken[] rezult = oneXoneTable.execute(new FuzzyToken[] { inpToken });
      Double outValue = driver.defuzzify(rezult[0]);
      savedInputs.add(inpValue);
      savedOuputs.add(outValue);
    }

    Map<String, List<Double>> mapForPlotter = new HashMap<>();
    mapForPlotter.put("out", savedOuputs);
    mapForPlotter.put("inp", savedInputs);
    Plotter plotter = new Plotter(mapForPlotter);

    this.getContentPane().add(plotter.makeInteractivePlot());

  }

  public static void main(String[] atgs) {

    JFrame frame = new InvertorExample();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(true);
    frame.setVisible(true);
		frame.setSize(800, 600);
  }

}
