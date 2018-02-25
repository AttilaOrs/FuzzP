package core.common.recoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import core.common.recoder.fullrecorderevents.AbstarctTokenMovment;
import core.common.recoder.fullrecorderevents.IFullRecorderEvent;
import core.common.recoder.fullrecorderevents.InputPuttedInPlace;
import core.common.recoder.fullrecorderevents.OuputTransitionFired;
import core.common.recoder.fullrecorderevents.TickFinsihed;
import core.common.recoder.fullrecorderevents.TokenFromPlaceToTransition;
import core.common.recoder.fullrecorderevents.TokenFromTransitionToPlace;
import core.common.recoder.fullrecorderevents.TransitionFireEnded;
import core.common.recoder.fullrecorderevents.TransitionFireStarted;

public class FullRecorder<TokenType extends FullRecordable<TokenType>>
    implements IGeneralPetriBehavoiurRecorder<TokenType> {

  List<IFullRecorderEvent> eventList;

  public FullRecorder() {
    eventList = new ArrayList<>();
  }

  public List<IFullRecorderEvent> getEvents() {
    return eventList;
  }

  @Override
  public void tokenTakenOutFromPlaceToTransition(int place, int transition, TokenType token) {
    eventList.add(new TokenFromPlaceToTransition<>(place, transition, token.myClone()));
  };

  @Override
  public void tokenPuttedInPlaceFromTransition(int place, int transition, TokenType token) {
    eventList.add(new TokenFromTransitionToPlace<>(place, transition, token.myClone()));
  };

  @Override
  public void tickFinished(List<Integer> delayStateOfTransition,
      List<TokenType> placeState) {
    List<TokenType> placeStateClone = placeState.stream().map(ft -> ft.myClone())
        .collect(Collectors.toList());
    List<Integer> delayStateClone = new ArrayList<>(delayStateOfTransition);

    eventList.add(new TickFinsihed<>(placeStateClone, delayStateClone));
  };

  @Override
  public void ouputTransitionFired(int trId, TokenType tk) {
    eventList.add(new OuputTransitionFired<>(trId, tk.myClone()));
  };

  @Override
  public void inputPuttedInPlace(int placeId, TokenType tk) {
    eventList.add(new InputPuttedInPlace<>(placeId, tk.myClone()));
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

  public void buildFromStr(String from, Function<String, TokenType> conv) {
    TickFinsihed<TokenType> tckf = new TickFinsihed<>(conv);
    TransitionFireEnded tre = new TransitionFireEnded();
    TransitionFireStarted trs = new TransitionFireStarted();
    TokenFromPlaceToTransition<TokenType> tkPT = new TokenFromPlaceToTransition<>(conv);
    TokenFromTransitionToPlace<TokenType> tkTR = new TokenFromTransitionToPlace<>(conv);
    String[] splittec = from.split("\n");
    for (int i = 0; i < splittec.length; i++) {
      String ss = splittec[i];
      if (tckf.isMyString(ss)) {
        tckf.buildFromString(ss);
        eventList.add(tckf);
        tckf = new TickFinsihed<>(conv);
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
        tkPT = new TokenFromPlaceToTransition<>(conv);
        continue;
      }

      if (tkTR.isMyString(ss)) {
        tkTR.buildFromString(ss);
        eventList.add(tkTR);
        tkTR = new TokenFromTransitionToPlace<>(conv);
        continue;
      }

    }

  }

  public List<Map<Integer, TokenType>> getInputs() {
    List<Map<Integer, TokenType>> toRet = new ArrayList<>();
    HashMap<Integer, TokenType> toPut = new HashMap<>();
    for (IFullRecorderEvent event : eventList) {
      if (event instanceof InputPuttedInPlace) {
        @SuppressWarnings("unchecked")
        InputPuttedInPlace<TokenType> casted = (InputPuttedInPlace<TokenType>) event;
        toPut.put(casted.placeId, casted.token.myClone());
      }
      if (event instanceof TickFinsihed) {
        toRet.add(toPut);
        toPut = new HashMap<>();
      }
    }
    return toRet;
  }

  public List<List<IFullRecorderEvent>> eventGroupedByTicks() {
    List<List<IFullRecorderEvent>> toRet = new ArrayList<>();
    List<IFullRecorderEvent> toPut = new ArrayList<>();
    for (IFullRecorderEvent event : eventList) {
      toPut.add(event);
      if (event instanceof TickFinsihed) {
        toRet.add(toPut);
        toPut = new ArrayList<>();
      }
    }
    return toRet;
  }

  public String evolutionOfPlaceDatFormatOnceInTick(int placeID, ToDoubleFunction<? super FullRecordable> conv) {
    StringBuilder bld = new StringBuilder();
    bld.append("#tick\t place").append(placeID).append("\n");
    int cntr = 0;
    for (List<IFullRecorderEvent> eventsInATick : eventGroupedByTicks()) {
      OptionalDouble avg = eventsInATick.stream()
          .filter(i -> (i instanceof AbstarctTokenMovment) && ((AbstarctTokenMovment) i).place == placeID)
          .map(i -> ((AbstarctTokenMovment) i).token)
          .mapToDouble(conv)
          .average();
      
      if(avg.isPresent()) {
        bld.append(cntr).append("\t").append(avg.getAsDouble()).append("\n");
      }
      cntr++;
    }
    return bld.toString();
  }


}
