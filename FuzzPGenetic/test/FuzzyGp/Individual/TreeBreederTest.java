package FuzzyGp.Individual;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import FuzzyGp.Tree.Parser;
import FuzzyGp.Tree.Visitors.ToSlispExpression;

public class TreeBreederTest {

  @Test
  public void yes() {
    TreeIndividual mother = new TreeIndividual(Parser.parse(("(* i0 (& o0 d0) )")));

    TreeIndividual father = new TreeIndividual(Parser.parse(("(# i2 (+ o2 d2) )")));

    TreeBreeder tr = new TreeBreeder();
    TreeIndividual[] rez = tr.breed(mother, father, new Random());
    String ll = ToSlispExpression.convertToSlisp(rez[0].getRoot());
    String hh = ToSlispExpression.convertToSlisp(rez[1].getRoot());
    String all = ll + hh;
    assertContains(all, "+", "*", "&", "*", "i0", "i2", "o0", "o2", "d0", "d2");

  }

  private void assertContains(String inWhat, String... whats) {
    for (String i : whats) {
      assertTrue(inWhat.contains(i));
    }

  }

}
