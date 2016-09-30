package core.FuzzyPetriLogic.Controller;

import java.util.HashMap;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class FuzzyPetriNetAsyncThreadedController {
	private Map<Integer, FuzzyDriver> inpuDrivers;
	private AsyncronRunnableExecutor simulator;
	private Thread holdingThread;

	public FuzzyPetriNetAsyncThreadedController(Map<Integer, FuzzyDriver> inputDrivers,
	    FuzzyPetriNet net, long period) {
    this.inpuDrivers = inputDrivers;
		this.simulator = new AsyncronRunnableExecutor(net, period);
    simulator.resetSimulator();
  }
	
	public void start() {
		holdingThread = new Thread(simulator);
		holdingThread.start();
	}

	public void stopAndReset() {
		simulator.stop();
		holdingThread = null;
		simulator.resetSimulator();
	}

	public void putValueToInput(int placeId, Double dd) {
		HashMap<Integer, FuzzyToken> inp = new HashMap<>();
		inp.put(placeId, inpuDrivers.get(placeId).fuzzifie(dd));
		simulator.putTokenInInputPlace(inp);
	}
}
