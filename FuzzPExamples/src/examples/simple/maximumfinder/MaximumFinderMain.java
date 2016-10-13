package examples.simple.maximumfinder;

import java.util.HashMap;
import java.util.Map;

import Main.FuzzyPVizualzer;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;

public class MaximumFinderMain {
	public static void main(String[] args) {
		FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-1, 1);
		MaximumFinderFuzzyPetriMaker maker = new MaximumFinderFuzzyPetriMaker();
		SynchronousFuzzyPetriExecutor executor =  new SynchronousFuzzyPetriExecutor(maker.net);
		FullRecorder rec = new FullRecorder();
		executor.setRecorder(rec);
		for(int i =0; i < 500; i++){
			Map<Integer, FuzzyToken> inpMap  = new HashMap<>();
			inpMap.put(maker.iP0,driver.fuzzifie(Math.sin(i/100.0)));
			inpMap.put(maker.iP1,driver.fuzzifie(Math.cos(i/100.0)));
			inpMap.put(maker.iP2,driver.fuzzifie(Math.cos((i+50)/100.0  )));
			executor.runTick(inpMap);
		}
		FuzzyPVizualzer.visualize(maker.net, rec);
		
		
	}

}
