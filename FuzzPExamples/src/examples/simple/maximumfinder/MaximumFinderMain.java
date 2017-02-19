package examples.simple.maximumfinder;

import java.util.HashMap;
import java.util.Map;

import Main.FuzzyPVizualzer;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import core.common.recoder.FullRecorder;

public class MaximumFinderMain {
	public static void main(String[] args) {
		FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-1, 1);
		MaximumFinderFuzzyPetriMaker maker = new MaximumFinderFuzzyPetriMaker();
		SynchronousFuzzyPetriExecutor executor =  new SynchronousFuzzyPetriExecutor(maker.net);
    FullRecorder<FuzzyToken> rec = new FullRecorder<>();
		executor.setRecorder(rec);
			Map<Integer, FuzzyToken> inpMap  = new HashMap<>();
    inpMap.put(maker.iP0, driver.fuzzifie(-0.5));
    inpMap.put(maker.iP1, driver.fuzzifie(0.0));
    inpMap.put(maker.iP2, driver.fuzzifie(0.5));
			executor.runTick(inpMap);
		FuzzyPVizualzer.visualize(maker.net, rec);
    System.out.println(rec.makeStr());
		
		
	}

}
