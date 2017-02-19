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
	HashMap<Integer, DataTable> inputDataCache;
	HashMap<Integer, DataTable> outputDataCache;
  private Function<TTokenType, Double> converter;

  FuzzyPetrinetBehaviourModel(FullRecorder<TTokenType> recorder, Function<TTokenType, Double> converter) {
		this.recorder = recorder;
    eventsGroupedByTick = recorder.eventGroupedByTicks();
		placeDataChache = new HashMap<>();
		inputDataCache = new HashMap<>();
		outputDataCache = new HashMap<>();
    this.converter = converter;
	}


	public DataTable getDataForPlace(int placeId) {
		if (!placeDataChache.containsKey(placeId)) {
			buildChacheForPlace(placeId);
		}
		return placeDataChache.get(placeId);
	}

	private void buildChacheForPlace(int placeId) {
		DataTable table = new DataTable(Double.class, Double.class);
		for (int tickNr = 0; tickNr < eventsGroupedByTick.size(); tickNr++) {
			for (int eventIndex = 0; eventIndex < eventsGroupedByTick.get(tickNr).size() - 1; eventIndex++) {
				if (eventsGroupedByTick.get(tickNr).get(eventIndex) instanceof TokenFromTransitionToPlace
						|| (eventsGroupedByTick.get(tickNr).get(eventIndex) instanceof TokenFromPlaceToTransition)) {
          AbstarctTokenMovment<TTokenType> event = (AbstarctTokenMovment<TTokenType>) (eventsGroupedByTick.get(tickNr)
							.get(eventIndex));
					if (event.place == placeId) {
						if (!event.token.isPhi()) {
							double inTickPlace = ((double) eventIndex) / (eventsGroupedByTick.get(tickNr).size());
              table.add(tickNr + inTickPlace, converter.apply(event.token));
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
	}

	public int getTickNr() {
		return this.eventsGroupedByTick.size();
	}
}
