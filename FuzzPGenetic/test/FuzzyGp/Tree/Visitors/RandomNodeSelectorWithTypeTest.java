package FuzzyGp.Tree.Visitors;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Parser;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InversionLeaf;
import FuzzyGp.Tree.Visitors.RandomNodeSelectorWithType;

public class RandomNodeSelectorWithTypeTest {

  @Test
  public void test() {
    RandomNodeSelectorWithType tt = new RandomNodeSelectorWithType();
    INode nn = Parser.parse("(# (* i0 v) (# i1 (& o1 i2) ) )");
    INode rez = tt.getRandomNodeWithtypes(nn, new Random(), InputLeaf.class, InversionLeaf.class);
    assertTrue(rez instanceof InputLeaf || rez instanceof InversionLeaf);
  }

}
