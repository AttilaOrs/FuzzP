package exampleNets;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public class SelectionLikeTwoBranchExample extends AbstarctPetriNetExampleBuilder {

  public SelectionLikeTwoBranchExample() {

    int P0 = exampleNet.addPlace();
    int P1_inp = exampleNet.addInputPlace();
    int P2_inp = exampleNet.addInputPlace();

    int t0 = exampleNet.addTransition(0, TwoXOneTable.defaultTable());
    int t1 = exampleNet.addTransition(0, TwoXOneTable.defaultTable());
    exampleNet.addArcFromPlaceToTransition(P0, t0, 1.0);
    exampleNet.addArcFromPlaceToTransition(P0, t1, 1.0);
    exampleNet.addArcFromPlaceToTransition(P1_inp, t0, 1.0);
    exampleNet.addArcFromPlaceToTransition(P2_inp, t1, 1.0);

    int P3 = exampleNet.addPlace();
    int P4 = exampleNet.addPlace();
    exampleNet.addArcFromTransitionToPlace(t0, P3);
    exampleNet.addArcFromTransitionToPlace(t1, P4);

    int t2 = exampleNet.addTransition(0, OneXTwoTable.defaultTable());
    int t3 = exampleNet.addTransition(0, OneXTwoTable.defaultTable());
    exampleNet.addArcFromPlaceToTransition(P3, t2, 1.0);
    exampleNet.addArcFromPlaceToTransition(P4, t3, 1.0);

    int P5 = exampleNet.addPlace();
    int P6 = exampleNet.addPlace();
    int P7 = exampleNet.addPlace();
    int P8 = exampleNet.addPlace();
    exampleNet.addArcFromTransitionToPlace(t2, P5);
    exampleNet.addArcFromTransitionToPlace(t2, P7);
    exampleNet.addArcFromTransitionToPlace(t3, P6);
    exampleNet.addArcFromTransitionToPlace(t3, P8);

    int t4_out = exampleNet.addOuputTransition(OneXOneTable.defaultTable());
    exampleNet.addActionForOuputTransition(t4_out, testAction(t4_out));
    int t5_out = exampleNet.addOuputTransition(OneXOneTable.defaultTable());
    exampleNet.addActionForOuputTransition(t5_out, testAction(t5_out));

    exampleNet.addArcFromPlaceToTransition(P5, t4_out, 1.0);
    exampleNet.addArcFromPlaceToTransition(P6, t5_out, 1.0);

    int t6 = exampleNet.addTransition(1, OneXOneTable.defaultTable());
    int t7 = exampleNet.addTransition(2, OneXOneTable.defaultTable());

    exampleNet.addArcFromPlaceToTransition(P7, t6, 1.0);
    exampleNet.addArcFromPlaceToTransition(P8, t7, 1.0);

    int P9 = exampleNet.addPlace();
    exampleNet.addArcFromTransitionToPlace(t6, P9);
    exampleNet.addArcFromTransitionToPlace(t7, P9);

    int t8 = exampleNet.addTransition(0, OneXOneTable.defaultTable());
    exampleNet.addArcFromTransitionToPlace(t8, P0);
    exampleNet.addArcFromPlaceToTransition(P9, t8, 1.0);

    exampleNet.setInitialMarkingForPlace(0, FuzzyToken.zeroToken());
  }

}
