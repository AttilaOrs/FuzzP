package FuzzyGp.Individual;

import java.util.ArrayList;

import FuzzyGp.ControllerBuilder;
import FuzzyGp.Tree.Visitors.NodeToTransitionMapping;
import FuzzyGp.Tree.Visitors.ToPetriNet;
import core.FuzzyPetriLogic.Controller.OutRecorder;
import core.common.recoder.FiredTranitionRecorder;
import structure.ICreatureFitnes;

public class TestFitnes implements ICreatureFitnes<TreeIndividual> {
  private static final double eventError = 2.0;
  public static String example = "(# (+ (* i1 o1 ) (* i0 o0 ) ) d1)";
  public static String example2 = "(# (& (* i1 o1 ) (+(* i0 o0 ) d0 ) ) d1)";
  ControllerBuilder builder;
  ArrayList<ArrayList<Double>> testSequence;
  private ToPetriNet petriBuilder;

  public TestFitnes(ControllerBuilder bl, String toCompareWith, ArrayList<ArrayList<Double>> testSequence) {
    this.testSequence = testSequence;
    this.builder = bl;
    petriBuilder = new ToPetriNet(true);
    // FuzzyController original = makeContoller(Parser.parse(toCompareWith));
    /*
     * orinignalRec = new OutRecorder(); ; original.setRecorder(orinignalRec);
     * original.reset(); for (int tickIndex = 0; tickIndex <
     * testSequence.size(); tickIndex++) { ArrayList<Double> currentTest =
     * testSequence.get(tickIndex); original.control(currentTest); }
     */

  }

  protected FiredTranitionRecorder rec;
  protected NodeToTransitionMapping mapping;
  private OutRecorder orinignalRec;
  /*
   * private FuzzyController makeContoller(INode node) { FuzzyPetriNet net =
   * petriBuilder.convert(node); mapping = petriBuilder.getMapping();
   * SynchronousFuzzyPetriExecutor sim = new SynchronousFuzzyPetriExecutor(net);
   * rec = new FiredTranitionRecorder(); sim.setRecorder(rec); return
   * builder.build(sim); }
   */

  @Override
  public double evaluate(TreeIndividual creature) {
    /*
     * FuzzyController underTest = makeContoller(creature.getRoot());
     * underTest.reset(); OutRecorder underTestRec = new OutRecorder();
     * underTest.setRecorder(underTestRec); for (int tickIndex = 0; tickIndex <
     * testSequence.size(); tickIndex++) { ArrayList<Double> currentTest =
     * testSequence.get(tickIndex); underTest.control(currentTest); }
     */

    /*
     * PlotUtils.plot( TestFitnesTest.makePlotable(orinignalRec.recorded,
     * "orig"), "orig--.svg"); PlotUtils.plot(
     * TestFitnesTest.makePlotable(underTestRec.recorded, "test"),
     * "test--.svg");
     * 
     * PlotUtils.plot(TestFitnesTest.makePlotable(testSequence, "test_inp"),
     * "inp--.svg");
     */
    double rez = 1.0;// = summOfDifferecnces(underTestRec, orinignalRec);
    rez = 1.0 / (1.0 + rez);
    return rez;

  }

  private double summOfDifferecnces(OutRecorder underTestRec, OutRecorder orinignalRec) {
    double sum = 0.0;
    for (int tickIndex = 0; tickIndex < orinignalRec.recorded.size(); tickIndex++) {
      ArrayList<Double> original = orinignalRec.recorded.get(tickIndex);
      ArrayList<Double> underTest = underTestRec.recorded.get(tickIndex);
      for (int inside = 0; inside < original.size(); inside++) {
        Double orig = original.get(inside);
        Double unde = (underTest.size() > inside) ? underTest.get(inside) : null;
        if ((orig == null) || unde == null) {
          if (orig != unde) {
            sum += eventError;
          }
        } else {
          sum += Math.abs(orig - unde);
        }
      }
    }
    return sum;
  }

}
