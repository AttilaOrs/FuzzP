package UnifiedGpProblmes.SymbolicRegression;

import java.util.Map;
import java.util.Map.Entry;

import commonUtil.PlotUtils;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;

public class MainForPlot {

  private static final String fileNmae = "";

  public static void main(String args[]) {
    SymbolicRegressionFitness fit = new SymbolicRegressionFitness();
    PetriNetJsonSaver<UnifiedPetriNet> loader = new PetriNetJsonSaver<>();

    UnifiedPetriNet net = loader.load(fileNmae, UnifiedPetriNet.class);

    Map<String, Map<Double, Double>> rez = fit.makeLog(net);
    for (Entry<String, Map<Double, Double>> entry : rez.entrySet()) {
      StringBuilder bld = new StringBuilder();
      for (Entry<Double, Double> innerEntry : entry.getValue().entrySet()) {
        bld.append(innerEntry.getKey()).append(" ").append(innerEntry.getValue()).append("\n");
      }
      PlotUtils.writeToFile(entry.getKey() + ".txt", bld.toString());

    }


  }

}
