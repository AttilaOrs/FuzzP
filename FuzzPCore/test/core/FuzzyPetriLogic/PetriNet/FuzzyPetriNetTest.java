package core.FuzzyPetriLogic.PetriNet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public class FuzzyPetriNetTest {

  @Test
  public void firstTest() {
    FuzzyPetriNet underTest = new FuzzyPetriNet();
    int p0 = underTest.addPlace();
    int p1 = underTest.addPlace();
    int t0 = underTest.addTransition(0, OneXOneTable.defaultTable());
    int t1 = underTest.addTransition(12, OneXOneTable.defaultTable());
    underTest.addArcFromPlaceToTransition(p0, t0, 1.0);
    underTest.addArcFromTransitionToPlace(t0, p1);
    underTest.addArcFromPlaceToTransition(p1, t1, 1.0);
    underTest.addArcFromTransitionToPlace(t1, p0);

    assertFalse(underTest.isInputPlace(p0));
    assertFalse(underTest.isInputPlace(p1));
    assertTrue(underTest.getNrOfPlaces() == 2);
    assertTrue(underTest.getNrOfTransition() == 2);
    assertTrue(underTest.getDelayForTransition(t1) == 12);
    assertTrue(underTest.getOutputTransitions().size() == 0);

  }

  @Test
  public void secondTest() {
    FuzzyPetriNet underTest = new FuzzyPetriNet();
    int p0 = underTest.addPlace();
    int p1 = underTest.addPlace();
    int p3_inp = underTest.addInputPlace();
    int p4 = underTest.addPlace();
    int t0 = underTest.addTransition(0, TwoXOneTable.defaultTable());
    int t1 = underTest.addTransition(12, OneXTwoTable.defaultTable());
    int t2_out = underTest.addOuputTransition(OneXOneTable.defaultTable());

    underTest.addArcFromPlaceToTransition(p3_inp, t0, 2.0);
    underTest.addArcFromPlaceToTransition(p0, t0, 1.0);
    underTest.addArcFromTransitionToPlace(t0, p1);
    underTest.addArcFromPlaceToTransition(p1, t1, 1.0);
    underTest.addArcFromTransitionToPlace(t1, p0);
    underTest.addArcFromTransitionToPlace(t1, p4);
    underTest.addArcFromPlaceToTransition(p4, t2_out, 0.5);

    assertTrue(underTest.isInputPlace(p3_inp));
    assertTrue(underTest.getNrOfPlaces() == 4);
    assertTrue(underTest.getNrOfTransition() == 3);
    assertTrue(underTest.getDelayForTransition(t1) == 12);
    assertTrue(underTest.getOutputTransitions().size() == 1);

  }

}
