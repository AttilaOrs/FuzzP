package FuzzyGp.Tree.Visitors;

import static FuzzyGp.Tree.Parser.parse;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Visitors.ToPetriNet;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNetChecker;

public class ToPetriNetTest {

  private ToPetriNet petriMaker;
  private FuzzyPetriNetChecker checker;

  @Before
  public void setup() {
    petriMaker = new ToPetriNet();
    checker = new FuzzyPetriNetChecker();
  }

  @Test
  public void simpleTest() {
    INode node = parse("(# (* d1 d2) (+ d3 d4) )");
    FuzzyPetriNet rez = petriMaker.convert(node);
    List<Integer> pre_t0 = rez.getPlacesNeededForTransition(0);
    assertThat(pre_t0, is(asList(0)));
    List<Integer> afterP1 = rez.getTransitionAfterPlace(1);
    assertThat(afterP1.size(), is(2));
    assertTrue(checker.checkPetriNet(rez));

  }

  @Test
  public void outHandledProperly_test() {
    FuzzyPetriNet rez = petriMaker.convert(parse("(+ o0 o0 )"));
    assertThat(rez.getNrOfTransition(), is(3));
    assertThat(rez.getOutputTransitions().size(), is(1));
    int outTtransition = rez.getOutputTransitions().get(0);
    int connectionPlace = rez.getPlacesNeededForTransition(outTtransition).get(0);
    for (int i = 0; i < 3; i++) {
      if (i != outTtransition) {
        List<Integer> ls = rez.getOutputPlacesForTransition(i);
        assertThat(ls, hasItem(connectionPlace));
      }
    }
    assertTrue(checker.checkPetriNet(rez));
  }

  @Test
  public void inputHandled_properly() {
    FuzzyPetriNet rez = petriMaker.convert(parse("(* (* i0 i1 ) i0)"));
    long inpPlaces = IntStream.range(0, rez.getNrOfPlaces()).filter(rez::isInputPlace).count();
    assertThat(inpPlaces, is(2L));
    assertTrue(checker.checkPetriNet(rez));
  }

  @Test
  public void fullLeaf_MakesCorrectNet() {
    FuzzyPetriNet rez = petriMaker.convert(parse("(# f-0-0 d1)"));
    assertTrue(checker.checkPetriNet(rez));
  }

}
