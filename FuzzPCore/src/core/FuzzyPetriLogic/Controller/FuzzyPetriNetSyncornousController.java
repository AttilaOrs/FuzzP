package core.FuzzyPetriLogic.Controller;

import java.util.HashMap;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.IDefuzzifier;
import core.FuzzyPetriLogic.IFuzzyfier;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import core.FuzzyPetriLogic.Fuzzifiers.TriangleFuzzyfier;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;

public class FuzzyPetriNetSyncornousController {
  private Map<Integer, FuzzyDriver> inpuDrivers;
  private SynchronousFuzzyPetriExecutor simulator;
  IControllerRecorder rec;
  private Map<Integer, FuzzyDriver> outDriver;
  private Map<Integer, FuzzyToken> recordedOuput;
  private TriangleFuzzyfier defaultDriver;


  public FuzzyPetriNetSyncornousController(Map<Integer, FuzzyDriver> inputDrivers, Map<Integer, FuzzyDriver> outputDriver,
      FuzzyPetriNet net) {
    this.inpuDrivers = inputDrivers;
    this.outDriver = outputDriver;
    rec = IControllerRecorder.empty();
    for (Integer trId : net.getOutputTransitions()) {
      net.addActionForOuputTransition(trId, tk -> recordedOuput.put(trId, tk));
    }
    this.simulator = new SynchronousFuzzyPetriExecutor(net);
    simulator.resetSimulator();
    defaultDriver = TriangleFuzzyfier.defaultFuzzyfier();
    recordedOuput = new HashMap<>();
  }

  public void reset() {
    simulator.resetSimulator();
  }

  public void setRecorderForExecutor(IGeneralPetriBehavoiurRecorder<FuzzyToken> recorder) {
    simulator.setRecorder(recorder);
  }

  public void setControllerRecorder(IControllerRecorder recToSet) {
    this.rec = recToSet;
  }

  public Map<Integer, Double> control(Map<Integer, Double> inps) {
    rec.addInputForController(inps);
    recordedOuput.clear();
    Map<Integer, FuzzyToken> fuzzyInps = new HashMap<>();
    if (inps != null) {
      for (Integer placeId : inps.keySet()) {
        FuzzyToken inp = getFuzzifier(placeId).fuzzifie(inps.get(placeId));
        fuzzyInps.put(placeId, inp);
      }
    }
    simulator.runTick(fuzzyInps);
    Map<Integer, Double> out = new HashMap<>();
    for (Integer trId : recordedOuput.keySet()) {
      out.put(trId, getDefuzzyfier(trId).defuzzify(recordedOuput.get(trId)));
    }
    rec.addOutOfController(out);
    return out;
  }

  private IFuzzyfier getFuzzifier(int placeId) {
    if (inpuDrivers.containsKey(placeId)) {
      return inpuDrivers.get(placeId);
    }
    return defaultDriver;
  }

  private IDefuzzifier getDefuzzyfier(int transitionID) {
    if (outDriver.containsKey(transitionID)) {
      return outDriver.get(transitionID);
    }
    return defaultDriver;
  }


  public SynchronousFuzzyPetriExecutor getSimulator() {
    return simulator;
  }

}
