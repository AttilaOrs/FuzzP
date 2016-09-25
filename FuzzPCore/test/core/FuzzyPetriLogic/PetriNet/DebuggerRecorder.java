package core.FuzzyPetriLogic.PetriNet;

import java.util.List;
import java.util.stream.Collectors;

import core.FuzzyPetriLogic.FuzzyToken;

public class DebuggerRecorder implements IFuzzyPetriBehaviourRecorder {

  @Override
  public void fuzzyTokenTakenOutFromPlaceToTransition(int place, int transition, FuzzyToken token) {
    System.out.println("<-- take out from pl " + place + " tr " + transition + " token " + token.shortString());

  };

  @Override
  public void fuzzyTokenPuttedInPlaceFromTransition(int place, int transition, FuzzyToken token) {
    System.out.println("--> puttin in pl " + place + " tr " + transition + " token " + token.shortString());
  };

  @Override
  public void tickFinished(List<Integer> delayStateOfTransition,
      List<FuzzyToken> placeState) {
    System.out.println(">>tick finished");
    String res;
    res = makeString(placeState);
    System.out.println("\tplace state " + res);
    System.out.println("\tdelay state " + delayStateOfTransition);
    System.out.println("<<tick finished");

  };

  @Override
  public void transitionFiredStarted(int transition) {
    System.out.println("<< transition Fire Started " + transition);
  };

  @Override
  public void transitionFiredEnded(int transition) {
    System.out.println(">> transition Fire Ended " + transition);
  };

  public static String makeString(List<FuzzyToken> out) {
    return out.stream().map(ft -> ft.shortString()).collect(Collectors.joining(";"));
  }

  @Override
  public void ouputTransitionFired(int trId, FuzzyToken tk) {
    System.out.println("--- ouput Transition Fired T" + trId + " token: " + tk.shortString());
  };

  @Override
  public void inputPuttedInPlace(int placeId, FuzzyToken tk) {
    System.out.println("+++ input putted in place P" + placeId + " token: " + tk.shortString());
  };

}