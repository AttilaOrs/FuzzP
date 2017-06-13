package FuzzyGp.Individual;

import java.util.Random;

import org.junit.Test;

import FuzzyGp.Tree.Parser;
import FuzzyGp.Tree.Visitors.ToSlispExpression;

public class TreeMutatorTest {

  @Test
  public void test() {
    RandomTreeBuilder builder = new RandomTreeBuilder(new RandomTreeBuilder.ProblemSpecification(3, 2, 4), 10, 0.5);
    TreeMutator underTest = new TreeMutator(builder);
    TreeIndividual ii = new TreeIndividual(Parser.parse("(# (& i0 d0 ) (* d0 o0 ))"));

    TreeIndividual rez = underTest.mutate(ii, new Random());
    String gg = (new ToSlispExpression(rez.getRoot())).getString();

  }
}
