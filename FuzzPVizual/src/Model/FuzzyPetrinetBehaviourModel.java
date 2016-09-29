package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Fuzzifiers.TriangleFuzzyfier;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.AbstarctTokenMovment;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.IFullRecorderEvent;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.TickFinsihed;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.TokenFromPlaceToTransition;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorderEvents.TokenFromTransitionToPlace;
import de.erichseifert.gral.data.DataTable;

public class FuzzyPetrinetBehaviourModel {
	private static TriangleFuzzyfier defultFuzz = TriangleFuzzyfier.defaultFuzzyfier();

	FullRecorder recorder;
	List<List<IFullRecorderEvent>> eventsGroupedByTick;
	HashMap<Integer, DataTable> placeDataChache;
	HashMap<Integer, DataTable> inputDataCache;
	HashMap<Integer, DataTable> outputDataCache;

	FuzzyPetrinetBehaviourModel(FullRecorder recorder) {
		this.recorder = recorder;
		makeTickGroups();
		placeDataChache = new HashMap<>();
		inputDataCache = new HashMap<>();
		outputDataCache = new HashMap<>();
	}

	private void makeTickGroups() {
		eventsGroupedByTick = new ArrayList<>();
		int firstIndexOfTikc = 0;
		for (int i = 0; i < recorder.getEvents().size(); i++) {
			if (recorder.getEvents().get(i) instanceof TickFinsihed) {
				eventsGroupedByTick.add(recorder.getEvents().subList(firstIndexOfTikc, i + 1));
				firstIndexOfTikc = i + 1;
			}
		}

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
					AbstarctTokenMovment event = (AbstarctTokenMovment) (eventsGroupedByTick.get(tickNr)
							.get(eventIndex));
					if (event.place == placeId) {
						if (!event.token.isPhi()) {
							double inTickPlace = ((double) eventIndex) / (eventsGroupedByTick.get(tickNr).size());
							table.add(tickNr + inTickPlace, defultFuzz.defuzzify(event.token));
						}
					}
				}
			}
			int lastIndex = eventsGroupedByTick.get(tickNr).size() - 1;
			TickFinsihed tickFinished = (TickFinsihed) eventsGroupedByTick.get(tickNr).get(lastIndex);
			FuzzyToken tt = tickFinished.placeState.get(placeId);
			if (!tt.isPhi()) {
				table.add(tickNr + 1.0, defultFuzz.defuzzify(tt));
			}

		}
		placeDataChache.put(placeId, table);
	}

	public int getTickNr() {
		return this.eventsGroupedByTick.size();
	}
}
