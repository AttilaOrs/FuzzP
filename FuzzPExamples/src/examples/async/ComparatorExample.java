package examples.async;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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

  String separator = "{[<FF,PL><FF,PL><FF,FF><NL,FF><NL,FF>]}";

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

		int t2Out = petriNet.addOuputTransition(OneXOneTable.defaultTable());
		petriNet.addArcFromPlaceToTransition(p3, t2Out, 1.0);
		petriNet.addActionForOuputTransition(t2Out, new Consumer<FuzzyToken>() {
			@Override
			public void accept(FuzzyToken t) {
				System.out.println("Output From Transition 2: " + t.shortString());
			}
		});

		int t3Out = petriNet.addOuputTransition(OneXOneTable.defaultTable());
		petriNet.addArcFromPlaceToTransition(p4, t3Out, 1.0);
		petriNet.addActionForOuputTransition(t3Out, new Consumer<FuzzyToken>() {
			@Override
			public void accept(FuzzyToken t) {
				System.out.println("Output From Transition 3: " + t.shortString());
			}
		});

		AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(petriNet, 20);
		FullRecorder recorder = new FullRecorder();
		executor.setRecorder(recorder);

		FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

		(new Thread(executor)).start();

		for (int i = 0; i < 100; i++) {
			Map<Integer, FuzzyToken> inps = new HashMap<>();
      inps.put(p0Inp, driver.fuzzifie(Math.sin(i / 10.0)));
      inps.put(p1Inp, driver.fuzzifie(Math.cos(i / 10.0)));
			executor.putTokenInInputPlace(inps);
			try {
        Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		executor.stop();
		FuzzyPVizualzer.visualize(petriNet, recorder);

	}

  public static void main(String args[]) {
		new ComparatorExample();

	}

}
