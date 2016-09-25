package exampleNets;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class ConcurentPetriNetBuilder extends AbstarctPetriNetExampleBuilder {

  public ConcurentPetriNetBuilder() {
    int p0 = exampleNet.addPlace();
    int t0 = exampleNet.addTransition(0, OneXTwoTable.defaultTable());
    exampleNet.addArcFromPlaceToTransition(p0, t0, 1.0);

    int p1 = exampleNet.addPlace();
    int p2 = exampleNet.addPlace();
    exampleNet.addArcFromTransitionToPlace(t0, p1);
    exampleNet.addArcFromTransitionToPlace(t0, p2);

    int p3 = exampleNet.addInputPlace();
    int t1 = exampleNet.addTransition(0, TwoXTwoTable.defaultTable());
    exampleNet.addArcFromPlaceToTransition(p1, t1, 1.0);
    exampleNet.addArcFromPlaceToTransition(p3, t1, 1.0);

    int p4 = exampleNet.addPlace();
    int p5 = exampleNet.addPlace();
    exampleNet.addArcFromTransitionToPlace(t1, p4);
    exampleNet.addArcFromTransitionToPlace(t1, p5);

    int t2 = exampleNet.addOuputTransition(OneXOneTable.defaultTable());
    exampleNet.addArcFromPlaceToTransition(p4, t2, 1.0);
    exampleNet.addActionForOuputTransition(t2, testAction(t2));

    int t3 = exampleNet.addTransition(1, OneXOneTable.defaultTable());
    exampleNet.addArcFromPlaceToTransition(p5, t3, 1.0);

    int p6 = exampleNet.addPlace();
    exampleNet.addArcFromTransitionToPlace(t3, p6);

    int t4 = exampleNet.addTransition(0, TwoXOneTable.defaultTable());
    exampleNet.addArcFromPlaceToTransition(p6, t4, 1.0);
    exampleNet.addArcFromPlaceToTransition(p2, t4, 1.0);
    exampleNet.addArcFromTransitionToPlace(t4, p0);

    exampleNet.setInitialMarkingForPlace(p0, FuzzyToken.zeroToken());



  }



}
