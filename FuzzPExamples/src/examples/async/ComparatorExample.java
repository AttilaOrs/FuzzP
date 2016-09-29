package examples.async;

import java.util.HashMap;
import java.util.Map;

import Main.FuzzyPVizualzer;
import core.TableParser;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public class ComparatorExample {
	String differentiator = "" + //
			"{[<ZR><NM><NL><NL><NL>]" + //
			" [<PM><ZR><NM><NL><NL>]" + //
			" [<PL><PM><ZR><NM><NL>]" + //
			" [<PL><PL><PM><ZR><NM>]" + //
			" [<PL><PL><PL><PM><ZR>]}";

	String separator = "{[<NM,FF><NM,FF><NM,PM><FF,PM><FF,PM>]}";

	public ComparatorExample() {
		TableParser parser = new TableParser();
		FuzzyPetriNet petriNet = new FuzzyPetriNet();

		int p0Inp = petriNet.addInputPlace();
		int p1Inp = petriNet.addInputPlace();
		TwoXOneTable diffTable = parser.parseTwoXOneTable(differentiator);
		int t0 = petriNet.addTransition(0, diffTable);
		petriNet.addArcFromPlaceToTransition(p0Inp, t0, 1.0);
		petriNet.addArcFromPlaceToTransition(p1Inp, t0, 1.0);

		int p2 = petriNet.addPlace();
		petriNet.addArcFromTransitionToPlace(t0, p2);

		int t1 = petriNet.addTransition(0, parser.parseOneXTwoTable(separator));
		petriNet.addArcFromPlaceToTransition(p2, t1, 1.0);

		int p3 = petriNet.addPlace();
		petriNet.addArcFromTransitionToPlace(t1, p3);
		int p4 = petriNet.addPlace();
		petriNet.addArcFromTransitionToPlace(t1, p4);

		int t2 = petriNet.addOuputTransition(OneXOneTable.defaultTable());
		petriNet.addArcFromPlaceToTransition(p3, t2, 1.0);
		int t3 = petriNet.addOuputTransition(OneXOneTable.defaultTable());
		petriNet.addArcFromPlaceToTransition(p4, t3, 1.0);

		AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(petriNet, 20);
		FullRecorder recorder = new FullRecorder();
		executor.setRecorder(recorder);

		FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

		(new Thread(executor)).start();

		for (double d = 0.0; d < 10.0; d += 0.25) {
			Map<Integer, FuzzyToken> inps = new HashMap<>();
			inps.put(p0Inp, driver.fuzzifie(Math.sin(d)));
			inps.put(p1Inp, driver.fuzzifie(Math.cos(d)));
			executor.putTokenInInputPlace(inps);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		executor.stop();
		FuzzyPVizualzer.visualize(petriNet, recorder);

	}

	public static void main(String[] main) {
		new ComparatorExample();

	}

}
