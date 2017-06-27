package UnifiedGp.Tree.Visitors;

import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.Random;

import org.junit.Test;

import UnifiedGp.Tree.BreadthFirstVisitorTest;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.TestColorTree.Color;

public class RandomNodeSelectorTest {

  @Test
  public void test() {
    INode<Color> i = BreadthFirstVisitorTest.createSimpleFullTree();
    RandomNodeSelector<Color> c= new RandomNodeSelector<>();
    Optional<INode<Color>> rez = c.selectRandomNode(i, node -> !i.equals(node), new Random());
    assertTrue(rez.isPresent());
    
  }

}
