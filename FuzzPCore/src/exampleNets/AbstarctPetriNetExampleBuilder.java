package exampleNets;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public abstract class AbstarctPetriNetExampleBuilder {

	protected List<FireLog> results;
	protected FuzzyPetriNet exampleNet;

	public AbstarctPetriNetExampleBuilder() {
		results = new ArrayList<>();
		exampleNet = new FuzzyPetriNet();
	}

	public Consumer<FuzzyToken> testAction(int trId) {
		return (tk -> {
			results.add(new FireLog(trId, tk, System.currentTimeMillis()));
		});
	}

	public FuzzyPetriNet getNet() {
		return exampleNet;
	}

	public List<Integer> getFiredOuputTransition() {
		return results.stream().map(res -> res.trId).collect(toList());
	}

	public List<FuzzyToken> getTokens() {
		return results.stream().map(res -> res.token).collect(toList());
	}

	public List<Long> getTimeStamps() {
		return results.stream().map(res -> res.timeStamp).collect(toList());
	}

	public static class FireLog {
		Integer trId;
		FuzzyToken token;
		long timeStamp;

		public FireLog(Integer trId, FuzzyToken token, long timeStamp) {
			this.trId = trId;
			this.token = token;
			this.timeStamp = timeStamp;
		}

	}

}
