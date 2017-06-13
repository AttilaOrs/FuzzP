package FuzzyGp.Individual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import FuzzyGp.Tree.Parser;
import structure.GPIndividSize;

public class TreeIndividualTest {

  @Test
  public void test() {
    TreeIndividual indi = new TreeIndividual(Parser.parse("(# (& d0 d1) i0 )"));
    GPIndividSize res = indi.getSizes();
    assertEquals(3, res.depth);

    indi = new TreeIndividual(Parser.parse("(# (& d0 d1) (+ i1 o2) )"));
    res = indi.getSizes();
    assertEquals(3, res.depth);

    indi = new TreeIndividual(Parser.parse("(+ i0 (+ i0 (+ i0 i0)))"));
    assertTrue(indi.getSizes().depth == 4);

  }
}
