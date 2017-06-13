package FuzzyGp.Individual;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import FuzzyGp.Individual.RandomTreeBuilder.ProblemSpecification;
import FuzzyGp.Tree.Visitors.ToSlispExpression;

public class RandomTreeBuilderTest {

  @Test
  public void test() {
    RandomTreeBuilder.ProblemSpecification pp = new ProblemSpecification(2, 3, 2);

    RandomTreeBuilder ll = new RandomTreeBuilder(pp, 5, 0.5);
    String rr = new ToSlispExpression(ll.genearteRandomCreature(new Random()).getRoot()).getString();
    assertTrue(rr.indexOf("(") != -1);
    rr = new ToSlispExpression(ll.createRandom(new Random(), false, 2)).getString();

  }
}
