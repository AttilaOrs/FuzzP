package core.FuzzyPetriLogic.Controller;

import java.util.List;
import java.util.stream.Collectors;

import core.FuzzyPetriLogic.IDefuzzifier;
import core.FuzzyPetriLogic.IFuzzyfier;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import structure.IOperatorFactory;

public class ControllerBuilder {

  private List<IOperatorFactory<IDefuzzifier>> defuzzFactories;
  private List<IOperatorFactory<IFuzzyfier>> fuzzifierFacctories;
  private IControllerRecorder recorder;

  public void withDefuzzicators(List<IOperatorFactory<IDefuzzifier>> defuzzFacories) {
    this.defuzzFactories = defuzzFacories;
  }

  public void withFuzzificators(List<IOperatorFactory<IFuzzyfier>> fuzzifierFacctories) {
    this.fuzzifierFacctories = fuzzifierFacctories;
  }

  public void withRecorder(IControllerRecorder ll) {
    this.recorder = ll;
  }

  public FuzzyPetriNetSyncornousController build(SynchronousFuzzyPetriExecutor sim) {
    List<IFuzzyfier> fuzz = fuzzifierFacctories.stream().map(factory -> factory.generate())
        .collect(Collectors.toList());
    List<IDefuzzifier> defuzz = defuzzFactories.stream().map(factory -> factory.generate())
        .collect(Collectors.toList());
    /*
     * FuzzySyncornousController toRet = new FuzzySyncornousController(defuzz,
     * fuzz, sim); if (this.recorder != null) {
     * toRet.setRecorder(this.recorder); }
     */
    return null;

  }

}
