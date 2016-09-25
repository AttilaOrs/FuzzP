package exampleNets;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public class SimpleDelayPetriNetBuilder extends AbstarctPetriNetExampleBuilder {


  public SimpleDelayPetriNetBuilder() {
    super();
    exampleNet = new FuzzyPetriNet();

    int p0 = exampleNet.addPlace();
    int p1 = exampleNet.addPlace();
    int p2_inp = exampleNet.addInputPlace();
    int p3 = exampleNet.addPlace();
    int t0 = exampleNet.addTransition(0, TwoXOneTable.defaultTable());
    int t1 = exampleNet.addTransition(1, OneXTwoTable.defaultTable());
    int t2_out = exampleNet.addOuputTransition(OneXOneTable.defaultTable());
    exampleNet.addActionForOuputTransition(t2_out, testAction(t2_out));


    exampleNet.addArcFromPlaceToTransition(p2_inp, t0, 1.0);
    exampleNet.addArcFromPlaceToTransition(p0, t0, 1.0);
    exampleNet.addArcFromTransitionToPlace(t0, p1);
    exampleNet.addArcFromPlaceToTransition(p1, t1, 1.0);
    exampleNet.addArcFromTransitionToPlace(t1, p0);
    exampleNet.addArcFromTransitionToPlace(t1, p3);
    exampleNet.addArcFromPlaceToTransition(p3, t2_out, 1.0);
    exampleNet.setInitialMarkingForPlace(p0, FuzzyToken.zeroToken());
  }

}
