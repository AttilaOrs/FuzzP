package core.FuzzyPetriLogic.PetriNet.Recorders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.IFuzzyPetriBehaviourRecorder;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.IFullRecorderEvent;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.InputPuttedInPlace;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.OuputTransitionFired;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.TickFinsihed;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.TokenFromPlaceToTransition;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.TokenFromTransitionToPlace;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.TransitionFireEnded;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.TransitionFireStarted;

public class FullRecorder implements IFuzzyPetriBehaviourRecorder {

  List<IFullRecorderEvent> eventList;

  public FullRecorder() {
    eventList = new ArrayList<>();
  }

  public List<IFullRecorderEvent> getEvents() {
    return eventList;
  }

  @Override
  public void fuzzyTokenTakenOutFromPlaceToTransition(int place, int transition, FuzzyToken token) {
    eventList.add(new TokenFromPlaceToTransition(place, transition, token.myClone()));
  };

  @Override
  public void fuzzyTokenPuttedInPlaceFromTransition(int place, int transition, FuzzyToken token) {
    eventList.add(new TokenFromTransitionToPlace(place, transition, token.myClone()));
  };

  @Override
  public void tickFinished(List<Integer> delayStateOfTransition,
      List<FuzzyToken> placeState) {
    List<FuzzyToken> placeStateClone = placeState.stream().map(ft -> ft.myClone()).collect(Collectors.toList());
    List<Integer> delayStateClone = new ArrayList<>(delayStateOfTransition);

    eventList.add(new TickFinsihed(placeStateClone, delayStateClone));
  };

  @Override
  public void ouputTransitionFired(int trId, FuzzyToken tk) {
    eventList.add(new OuputTransitionFired(trId, tk.myClone()));
  };

  @Override
  public void inputPuttedInPlace(int placeId, FuzzyToken tk) {
    eventList.add(new InputPuttedInPlace(placeId, tk.myClone()));
  };

  @Override
  public void transitionFiredStarted(int transition) {
    eventList.add(new TransitionFireStarted(transition));
  };

  @Override
  public void transitionFiredEnded(int transition) {
    eventList.add(new TransitionFireEnded(transition));
  };

  @Override
  public void reset() {
    eventList = new ArrayList<>();
  }

  public String makeStr() {
    return eventList.stream().map(ev -> ev.makeString()).collect(Collectors.joining("\n"));
  }

  public void buildFromStr(String from) {
    TickFinsihed tckf = new TickFinsihed();
    TransitionFireEnded tre = new TransitionFireEnded();
    TransitionFireStarted trs = new TransitionFireStarted();
    TokenFromPlaceToTransition tkPT = new TokenFromPlaceToTransition();
    TokenFromTransitionToPlace tkTR = new TokenFromTransitionToPlace();
    String[] splittec = from.split("\n");
    for (int i = 0; i < splittec.length; i++) {
      String ss = splittec[i];
      if (tckf.isMyString(ss)) {
        tckf.buildFromString(ss);
        eventList.add(tckf);
        tckf = new TickFinsihed();
        continue;
      }
      if (tre.isMyString(ss)) {
        tre.buildFromString(ss);
        eventList.add(tre);
        tre = new TransitionFireEnded();
        continue;
      }

      if (trs.isMyString(ss)) {
        trs.buildFromString(ss);
        eventList.add(trs);
        trs = new TransitionFireStarted();
        continue;
      }

      if (tkPT.isMyString(ss)) {
        tkPT.buildFromString(ss);
        eventList.add(tkPT);
        tkPT = new TokenFromPlaceToTransition();
        continue;
      }

      if (tkTR.isMyString(ss)) {
        tkTR.buildFromString(ss);
        eventList.add(tkTR);
        tkTR = new TokenFromTransitionToPlace();
        continue;
      }

    }

  }

}
