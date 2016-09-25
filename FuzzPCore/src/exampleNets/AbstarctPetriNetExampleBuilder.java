package exampleNets;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public abstract class AbstarctPetriNetExampleBuilder {

  protected List<FuzzyToken> results;
  protected List<Integer> firedOuptTransition;
  protected FuzzyPetriNet exampleNet;

  public AbstarctPetriNetExampleBuilder() {
    results = new ArrayList<>();
    firedOuptTransition = new ArrayList<>();
    exampleNet = new FuzzyPetriNet();
  }

  public Consumer<FuzzyToken> testAction(int trId) {
    return (tk -> {
      results.add(tk);
      firedOuptTransition.add(trId);
    });
  }

  public FuzzyPetriNet getNet() {
    return exampleNet;
  }

  public List<Integer> getFiredOuputTransition() {
    return firedOuptTransition;
  }

  public List<FuzzyToken> getResults() {
    return results;
  }

}
