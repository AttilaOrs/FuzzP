package UnifiedGp.Behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import structure.IGPGreature;

public class BinaryDescritorTest {

  private ProblemSpecificationImpl ps;
  private InputOutputRecorder rec;
  private PetriConversationResult testRez;
  private int placeIdInpOne;
  private int placeIdInpTwo;
  private int outOne;

  public class TestDescriptor extends BinaryDescritor<BinaryDescrition, IGPGreature> {

    public TestDescriptor(ProblemSpecification ps, int bitsForChannel,
        BiFunction<BitSet, Integer, BinaryDescrition> constructor) {
      super(ps, bitsForChannel, constructor);
    }

    @Override
    public BinaryDescrition evaluate(IGPGreature cr) {
      super.rez = testRez;
      return null;
    }

  }

  @Before
  public void setup() {
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 2.0);
    inpScale.put(1, 10.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 5.0);
    ps = new ProblemSpecificationImpl(2, 2, inpScale, outScale);

    rec = new InputOutputRecorder();

    placeIdInpOne = 99;
    placeIdInpTwo = 142;
    outOne = 356;
    rec.inputPuttedInPlace(placeIdInpOne, new UnifiedToken(2.0));
    rec.inputPuttedInPlace(placeIdInpTwo, new UnifiedToken());
    rec.ouputTransitionFired(outOne, new UnifiedToken(-2.4));
    rec.tickFinished(null, null);


    Map<Integer, Integer> inpNrToInpPlace = new HashMap<>();
    inpNrToInpPlace.put(0, placeIdInpOne);
    inpNrToInpPlace.put(1, placeIdInpTwo);
    Map<Integer, Integer> outNrToOutTr = new HashMap<>();
    outNrToOutTr.put(0, outOne);
    testRez = new PetriConversationResult(null, inpNrToInpPlace, outNrToOutTr, Optional.empty());

  }

  @Test
  public void simplest() {
    TestDescriptor d = new TestDescriptor(ps, 1, BinaryDescrition::new);
    d.evaluate(null);
    BinaryDescrition b = d.createDescprition(rec);
    assertEquals(String.valueOf(b.getBits()), "{0, 2}");
  }

  @Test
  public void two_bit() {
    TestDescriptor d = new TestDescriptor(ps, 2, BinaryDescrition::new);
    d.evaluate(null);
    BinaryDescrition b = d.createDescprition(rec);
    assertEquals(String.valueOf(b.getBits()), "{0, 1, 4}");
  }

  @Test
  public void three_bit() {
    TestDescriptor d = new TestDescriptor(ps, 3, BinaryDescrition::new);
    d.evaluate(null);
    BinaryDescrition b = d.createDescprition(rec);
    assertEquals(String.valueOf(b.getBits()), "{0, 1, 2, 6, 8}");
  }

  @Test
  public void multiple_tick() {
    rec.inputPuttedInPlace(placeIdInpOne, new UnifiedToken());
    rec.inputPuttedInPlace(placeIdInpTwo, new UnifiedToken(1.0));
    rec.ouputTransitionFired(outOne, new UnifiedToken(1.0));
    rec.tickFinished(null, null);

    TestDescriptor d = new TestDescriptor(ps, 1, BinaryDescrition::new);
    d.evaluate(null);
    BinaryDescrition b = d.createDescprition(rec);
    assertTrue(b.getLength() == 6);
    assertEquals(String.valueOf(b.getBits()), "{0, 2, 4, 5}");
  }

}
