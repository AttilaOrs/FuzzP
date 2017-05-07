package Model;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import core.common.recoder.FullRecordable;
import core.common.recoder.FullRecorder;
import core.common.recoder.fullrecorderevents.AbstarctTokenMovment;
import core.common.recoder.fullrecorderevents.IFullRecorderEvent;
import core.common.recoder.fullrecorderevents.TickFinsihed;
import core.common.recoder.fullrecorderevents.TokenFromPlaceToTransition;
import core.common.recoder.fullrecorderevents.TokenFromTransitionToPlace;
import de.erichseifert.gral.data.DataTable;

public class FuzzyPetrinetBehaviourModel<TTokenType extends FullRecordable<TTokenType>> {

  FullRecorder<TTokenType> recorder;
	List<List<IFullRecorderEvent>> eventsGroupedByTick;
	HashMap<Integer, DataTable> placeDataChache;
  HashMap<Integer, double[]> maxMinForPlace;
  private Function<TTokenType, Double> converter;

  FuzzyPetrinetBehaviourModel(FullRecorder<TTokenType> recorder, Function<TTokenType, Double> converter) {
		this.recorder = recorder;
    eventsGroupedByTick = recorder.eventGroupedByTicks();
		placeDataChache = new HashMap<>();
    maxMinForPlace = new HashMap<>();
    this.converter = converter;
	}

  public double getMinForPlace(Integer placeId) {
    if (!maxMinForPlace.containsKey(placeId)) {
      buildChacheForPlace(placeId);
    }
    return maxMinForPlace.get(placeId)[1];
  }

  public double getMaxForPlace(Integer placeId) {
    if (!maxMinForPlace.containsKey(placeId)) {
      buildChacheForPlace(placeId);
    }
    return maxMinForPlace.get(placeId)[0];
  }



	public DataTable getDataForPlace(int placeId) {
		if (!placeDataChache.containsKey(placeId)) {
			buildChacheForPlace(placeId);
		}
		return placeDataChache.get(placeId);
	}

  private void buildChacheForPlace(Integer placeId) {
		DataTable table = new DataTable(Double.class, Double.class);
    double max = Double.MIN_VALUE;
    double min = Double.MAX_VALUE;
		for (int tickNr = 0; tickNr < eventsGroupedByTick.size(); tickNr++) {
			for (int eventIndex = 0; eventIndex < eventsGroupedByTick.get(tickNr).size() - 1; eventIndex++) {
				if (eventsGroupedByTick.get(tickNr).get(eventIndex) instanceof TokenFromTransitionToPlace
						|| (eventsGroupedByTick.get(tickNr).get(eventIndex) instanceof TokenFromPlaceToTransition)) {
          AbstarctTokenMovment<TTokenType> event = (AbstarctTokenMovment<TTokenType>) (eventsGroupedByTick.get(tickNr)
							.get(eventIndex));
					if (event.place == placeId) {
						if (!event.token.isPhi()) {
							double inTickPlace = ((double) eventIndex) / (eventsGroupedByTick.get(tickNr).size());
              Double actualValue = converter.apply(event.token);
              table.add(tickNr + inTickPlace, actualValue);
              max = (max < actualValue) ? actualValue : max;
              min = (min > actualValue) ? actualValue : min;
						}
					}
				}
			}
			int lastIndex = eventsGroupedByTick.get(tickNr).size() - 1;
      TickFinsihed<TTokenType> tickFinished = (TickFinsihed<TTokenType>) eventsGroupedByTick.get(tickNr).get(lastIndex);
      TTokenType tt = tickFinished.placeState.get(placeId);
			if (!tt.isPhi()) {
        table.add(tickNr + 1.0, converter.apply(tt));
			}

		}
		placeDataChache.put(placeId, table);
    maxMinForPlace.put(placeId,
        new double[] { (max != Double.MIN_VALUE) ? max : 0.0, (min != Double.MAX_VALUE) ? min : 0.0 });
	}

	public int getTickNr() {
		return this.eventsGroupedByTick.size();
	}
}
