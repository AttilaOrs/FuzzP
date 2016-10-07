package exampleNets;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class TwoLoopNet extends AbstarctPetriNetExampleBuilder {

	public int p4_inp;
	public int t2_out;
	public int t4_out;

	public TwoLoopNet() {
		int p0 = exampleNet.addPlace();
		exampleNet.setInitialMarkingForPlace(p0, FuzzyToken.zeroToken());

		int t0 = exampleNet.addTransition(0, OneXTwoTable.defaultTable());
		exampleNet.addArcFromPlaceToTransition(p0, t0, 1.0);

		int p1 = exampleNet.addPlace();
		exampleNet.addArcFromTransitionToPlace(t0, p1);
		int t1 = exampleNet.addTransition(1, OneXTwoTable.defaultTable());
		exampleNet.addArcFromPlaceToTransition(p1, t1, 1.0);
		exampleNet.addArcFromTransitionToPlace(t1, p1);

		int p2 = exampleNet.addPlace();
		exampleNet.addArcFromTransitionToPlace(t1, p2);
		t2_out = exampleNet.addOuputTransition(OneXOneTable.defaultTable());
		exampleNet.addArcFromPlaceToTransition(p2, t2_out, 1.0);
		exampleNet.addActionForOuputTransition(t2_out, testAction(t2_out));

		int p3 = exampleNet.addPlace();
		exampleNet.addArcFromTransitionToPlace(t0, p3);
		p4_inp = exampleNet.addInputPlace();
		int t3 = exampleNet.addTransition(0, TwoXTwoTable.defaultTable());
		exampleNet.addArcFromPlaceToTransition(p3, t3, 1.0);
		exampleNet.addArcFromPlaceToTransition(p4_inp, t3, 1.0);

		int p5 = exampleNet.addPlace();
		exampleNet.addArcFromTransitionToPlace(t3, p5);
		t4_out = exampleNet.addOuputTransition(OneXOneTable.defaultTable());
		exampleNet.addArcFromPlaceToTransition(p5, t4_out, 1.0);
		exampleNet.addActionForOuputTransition(t4_out, testAction(t4_out));

		exampleNet.addArcFromTransitionToPlace(t3, p3);
	}

}
