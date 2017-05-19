package core.UnifiedPetriLogic;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import core.UnifiedPetriLogic.tables.Operator;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;

public class ScaleDeducerTest {

  @Test
  public void loopfree_deducalble() {
    UnifiedTwoXOneTable def = UnifiedTwoXOneTable.defaultTable();

    UnifiedPetriNet net = new UnifiedPetriNet();
    int iP0 = net.addInputPlace(2.0);
    int iP1 = net.addInputPlace(4.0);
    int t0 = net.addTransition(0, new UnifiedTwoXOneTable(def.getTable(), Operator.PLUS));
    net.addArcFromPlaceToTransition(iP0, t0);
    net.addArcFromPlaceToTransition(iP1, t0);

    int P2 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t0, P2);

    int P3 = net.addPlace(-1.0);
    net.setInitialMarkingForPlace(P3, new UnifiedToken(2.0));

    int t1 = net.addTransition(0, new UnifiedTwoXOneTable(def.getTable(), Operator.MULT));
    net.addArcFromPlaceToTransition(P2, t1);
    net.addArcFromPlaceToTransition(P3, t1);

    int p4 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t1, p4);


    int ot2 = net.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p4, ot2);

    UnifiedPetriNet newNet = ScaleDeducer.deduceScale(net);
    List<Integer> unscaled = ScaleDeducer.unscaledPlaces(newNet);
    assertTrue(unscaled.size() == 0);

  }


  @Test
  public void loopfree_undeducalble() {
    UnifiedTwoXOneTable def = UnifiedTwoXOneTable.defaultTable();

    UnifiedPetriNet net = new UnifiedPetriNet();
    int iP0 = net.addInputPlace(2.0);
    int iP1 = net.addInputPlace(4.0);
    int t0 = net.addTransition(0, new UnifiedTwoXOneTable(def.getTable(), Operator.PLUS));
    net.addArcFromPlaceToTransition(iP0, t0);
    net.addArcFromPlaceToTransition(iP1, t0);

    int P2 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t0, P2);

    int P3 = net.addPlace(-1.0);

    int t1 = net.addTransition(0, new UnifiedTwoXOneTable(def.getTable(), Operator.MULT));
    net.addArcFromPlaceToTransition(P2, t1);
    net.addArcFromPlaceToTransition(P3, t1);

    int p4 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t1, p4);

    int ot2 = net.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p4, ot2);

    UnifiedPetriNet newNet = ScaleDeducer.deduceScale(net);
    assertTrue(ScaleDeducer.unscaledPlaces(newNet).size() == 0);
  }

  @Test
  public void loop_deducable() {
    UnifiedPetriNet net = new UnifiedPetriNet();
    int iP0 = net.addInputPlace(2.0);
    int p1 = net.addPlace(-1.0);
    net.setInitialMarkingForPlace(p1, new UnifiedToken(2.0));
    int t0 = net.addTransition(0, UnifiedTwoXOneTable.defaultTableWithOp(Operator.PLUS));
    net.addArcFromPlaceToTransition(iP0, t0);
    net.addArcFromPlaceToTransition(p1, t0);
    int p2 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t0, p2);
    int t1 = net.addTransition(1, UnifiedOneXTwoTable.defaultTable());
    net.addArcFromPlaceToTransition(p2, t1);
    int p3 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t1, p3);
    int otr = net.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p3, otr);
    net.addArcFromTransitionToPlace(t1, p1);
    
    UnifiedPetriNet fixedScalesNet = ScaleDeducer.deduceScale(net);
    
    assertTrue(ScaleDeducer.unscaledPlaces(fixedScalesNet).size() == 0);
  }
  
  @Test
  public void loop_undeducable() {
    UnifiedPetriNet net = new UnifiedPetriNet();
    int iP0 = net.addInputPlace(2.0);
    int p1 = net.addPlace(-1.0);
    int t0 = net.addTransition(0, UnifiedTwoXOneTable.defaultTableWithOp(Operator.PLUS));
    net.addArcFromPlaceToTransition(iP0, t0);
    net.addArcFromPlaceToTransition(p1, t0);
    int p2 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t0, p2);
    int t1 = net.addTransition(1, UnifiedOneXTwoTable.defaultTable());
    net.addArcFromPlaceToTransition(p2, t1);
    int p3 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t1, p3);
    int otr = net.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p3, otr);
    net.addArcFromTransitionToPlace(t1, p1);

    UnifiedPetriNet fixedScalesNet = ScaleDeducer.deduceScale(net);

    assertTrue(ScaleDeducer.unscaledPlaces(fixedScalesNet).size() == 0);
  }
  
  @Test
  public void loop_userDefened() {
    UnifiedPetriNet net = new UnifiedPetriNet();
    int iP0 = net.addInputPlace(2.0);
    int p1 = net.addPlace(2.0);
    int t0 = net.addTransition(0, UnifiedTwoXOneTable.defaultTableWithOp(Operator.PLUS));
    net.addArcFromPlaceToTransition(iP0, t0);
    net.addArcFromPlaceToTransition(p1, t0);
    int p2 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t0, p2);
    int t1 = net.addTransition(1, UnifiedOneXTwoTable.defaultTable());
    net.addArcFromPlaceToTransition(p2, t1);
    int p3 = net.addPlace(-1.0);
    net.addArcFromTransitionToPlace(t1, p3);
    int otr = net.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    net.addArcFromPlaceToTransition(p3, otr);
    net.addArcFromTransitionToPlace(t1, p1);

    UnifiedPetriNet fixedScalesNet = ScaleDeducer.deduceScale(net);

    assertTrue(ScaleDeducer.unscaledPlaces(fixedScalesNet).size() == 0);

  }

}