package UnifiedPetriGA.controller;

import java.util.HashMap;
import java.util.Map;

import UnifiedPetriGA.BendingCreature;
import UnifiedPetriGA.mapper.BendingCreatureUTPNMapper;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import structure.ICreatureFitnes;

public class ControllerFitness implements ICreatureFitnes<BendingCreature> {

  private static final double W0 = 1.0;
  private static final double W1 = 2.0;
  private static final double W2 = 2.0;
  private static final double W3 = 4.0;
  private static final double T = 90;
  private static final double W5 = 3;
  private Scenario scenario;
  private ControllerUnifiedPetriMaker petriMaker;
  private BendingCreatureUTPNMapper mapper;
  private boolean record;
  private Satistics st;

  public ControllerFitness(Scenario scenario, BendingCreatureUTPNMapper mapper) {
    this(scenario, mapper, false);
  }

  public ControllerFitness(Scenario scenario, BendingCreatureUTPNMapper mapper, boolean record) {
    this.scenario = scenario;
    this.mapper = mapper;
    this.petriMaker = new ControllerUnifiedPetriMaker();
    this.record = record;
  }

  Double oT6, oT19;
  private FullRecorder<UnifiedToken> recorder;

  public FullRecorder<UnifiedToken> getRecorder() {
    return recorder;
  }

  public Satistics getStatistics() {
    return st;
  }

  @Override
  public double evaluate(BendingCreature creature) {
    UnifiedPetriNet net = mapper.decode(creature);
    net.addActionForOuputTransition(petriMaker.oT6, d -> oT6 = d.getValue());
    net.addActionForOuputTransition(petriMaker.oT19, d -> oT19 = d.getValue());
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(net);
    Double sum = 0.0;
    st = new Satistics(scenario.getLength());
    if (record) {
      this.recorder = new FullRecorder<>();
      exec.setRecorder(this.recorder);
    }
    for (int tickNr = 0; tickNr < scenario.getLength(); tickNr++) {
      double[] inpFromScenario = scenario.getValuesForTick(tickNr);
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(petriMaker.iP3, new UnifiedToken(inpFromScenario[0])); // d1
      inp.put(petriMaker.iP4, new UnifiedToken(inpFromScenario[1])); // d2
      inp.put(petriMaker.iP7, new UnifiedToken(inpFromScenario[2])); // o1
      inp.put(petriMaker.iP8, new UnifiedToken(inpFromScenario[3])); // o2
      inp.put(petriMaker.iP15, new UnifiedToken(0.0)); // demand
      exec.runTick(inp);
      sum += calcCurrentFitnes(inpFromScenario);
    }
    return sumTransformed(sum);

  }

  private double sumTransformed(Double sum) {
    if (sum > 0) {
      return sum + 1.0;
    }
    return 1.0 / (1.0 + Math.abs(sum));
  }

  private Double calcCurrentFitnes(double[] i) {
    double ratio = Math.abs((oT6 / oT19 - i[0] / i[1]));
    if (Double.isNaN(ratio)) {
      return -1000.0;
    }
    double timeOverlow = (oT6 + oT19 <= T) ? 0 : (oT6 + oT19 - T);
    double overflowOne = (oT6 - i[2] < 0) ? 0 : oT6 - i[2];
    double overflowTwo = (oT19 - i[3] < 0) ? 0 : oT19 - i[3];
    double allGreen = oT6 + oT19;
    st.allGreen(allGreen);
    st.owerflow(overflowOne, overflowTwo);
    st.ratio(ratio);
    st.timeOverflow(timeOverlow);
    return W0 * (allGreen) - W1 * (overflowOne) - W2 * (overflowTwo) - W3 * ratio - timeOverlow * W5;
  }

  public static class Satistics {
    double[] allGreen, overflow, absRatio, timeOverlow;
    int tickNr;

    public Satistics(int tickNr) {
      allGreen = new double[] { 0.0, 0.0 };
      overflow = new double[] { 0.0, 0.0 };
      absRatio = new double[] { 0.0, 0.0 };
      timeOverlow = new double[] { 0.0, 0.0 };
      this.tickNr = tickNr;
    }

    public void allGreen(double currentAllGreen) {
      calc(allGreen, currentAllGreen);
    }

    public void owerflow(double currentOwOne, double currentOwTwo) {
      calc(overflow, currentOwOne);
      calc(overflow, currentOwTwo);
    }

    public void ratio(double currentRatio) {
      calc(absRatio, currentRatio);
    }

    public void timeOverflow(double currentTimeOver) {
      calc(timeOverlow, currentTimeOver);
    }

    private static void calc(double[] arr, double val) {
      if (arr[0] < val) {
        arr[0] = val;
      }
      arr[1] += val;
    }

    public String toString() {
      StringBuilder bld = new StringBuilder();
      bld.append("            max    avg\n");
      bld.append("green     : ").append(allGreen[0]).append("; ").append(allGreen[1] / tickNr).append("\n");
      bld.append("overflow  : ").append(overflow[0]).append("; ").append(overflow[1] / (tickNr * 2)).append("\n");
      bld.append("absratio  : ").append(absRatio[0]).append("; ").append(absRatio[1] / tickNr).append("\n");
      bld.append("t. overlw : ").append(timeOverlow[0]).append("; ").append(timeOverlow[1] / tickNr).append("\n");
      return bld.toString();
    }

  }

}
