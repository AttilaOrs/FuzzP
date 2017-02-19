package core.FuzzyPetriLogic.PetriNet;

import java.util.List;
import java.util.stream.Collectors;

import core.common.recoder.FullRecordable;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;

public class DebuggerRecorder<TokenType extends FullRecordable<TokenType>>
    implements IGeneralPetriBehavoiurRecorder<TokenType> {

  @Override
  public void tokenTakenOutFromPlaceToTransition(int place, int transition, TokenType token) {
    System.out.println("<-- take out from pl " + place + " tr " + transition + " token " + token.shortString());

  };

  @Override
  public void tokenPuttedInPlaceFromTransition(int place, int transition, TokenType token) {
    System.out.println("--> puttin in pl " + place + " tr " + transition + " token " + token.shortString());
  };

  @Override
  public void tickFinished(List<Integer> delayStateOfTransition,
      List<TokenType> placeState) {
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

  public String makeString(List<TokenType> out) {
    return out.stream().map(ft -> ft.shortString()).collect(Collectors.joining(";"));
  }

  @Override
  public void ouputTransitionFired(int trId, TokenType tk) {
    System.out.println("--- ouput Transition Fired T" + trId + " token: " + tk.shortString());
  };

  @Override
  public void inputPuttedInPlace(int placeId, TokenType tk) {
    System.out.println("+++ input putted in place P" + placeId + " token: " + tk.shortString());
  };

}