package core.FuzzyPetriLogic.PetriNet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.FuzzyPetriLogic.Tables.OneXTwoTable;

public class FuzzyPetriNetCheckerTest {
	
	FuzzyPetriNetChecker checker = new FuzzyPetriNetChecker();
	
	
	@Test
	public void not_connected() {

		
		FuzzyPetriNet net = new FuzzyPetriNet();
    net.addTransition(0, null);
    net.addTransition(0, null);
		
		boolean rez = checker.checkPetriNet(net);
		
		assertFalse(rez);
		assertTrue(checker.getErrorMsg().split("\n").length >= 4);
	}
	

	@Test
	public void multiple_inputPlaces() {
		FuzzyPetriNet net = new FuzzyPetriNet();
		int plId1= net.addPlace();
		int plId2= net.addPlace();
		int plId3= net.addPlace();
		
    int trId = net.addTransition(0, null);
		
    net.addArcFromPlaceToTransition(plId1, trId, 1.0);
    net.addArcFromPlaceToTransition(plId2, trId, 1.0);
    net.addArcFromPlaceToTransition(plId3, trId, 1.0);
		
		boolean rez = checker.checkPetriNet(net);
		
		assertFalse(rez);
    assertTrue(checker.getErrorMsg().split("\n").length >= 1);
	}
	
	
	@Test
	public void wrongTableType(){
		FuzzyPetriNet net = new FuzzyPetriNet();
		int pl1 = net.addPlace();
		int pl2 = net.addPlace();
    int pl3 = net.addInputPlace();
		
    int trId = net.addTransition(0, OneXTwoTable.defaultTable());
		
    net.addArcFromPlaceToTransition(pl1, trId, 1.0);
    net.addArcFromPlaceToTransition(pl3, trId, 1.0);

		net.addArcFromTransitionToPlace(trId, pl2);		
		
		boolean rez = checker.checkPetriNet(net);
		
		assertFalse(rez);
		assertTrue(checker.getErrorMsg().split("\n").length >= 1);
		
	}
	
	
	

}
