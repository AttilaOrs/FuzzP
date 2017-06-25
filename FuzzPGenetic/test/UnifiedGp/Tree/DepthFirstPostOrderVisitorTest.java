package UnifiedGp.Tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.Tree.TestColorTree.Color;
import UnifiedGp.Tree.TestColorTree.ColoredInnerNode;
import UnifiedGp.Tree.TestColorTree.HasNumber;

public class DepthFirstPostOrderVisitorTest {

  private ColoredInnerNode simpleFullTree;
  private StringBuilder bld;
  Function<INode<Color>, Boolean> appenderAlwwaysTrue;

  @Before
  public void setupTree() {
    simpleFullTree = BreadthFirstVisitorTest.createSimpleFullTree();
    bld = new StringBuilder();
    appenderAlwwaysTrue = l -> {
      bld.append(((HasNumber) l).getMyNr()).append(" ");
      return true;
    };
  }


  @Test
  public void testVisit() throws Exception {
    VisitorCostumizer<Color> costum = new VisitorCostumizer<>();
    costum.registerOperatorConsumer(Color.GREEN, appenderAlwwaysTrue);
    costum.registerOperatorConsumer(Color.YELOW, appenderAlwwaysTrue);
    costum.registerOperatorConsumer(Color.RED, appenderAlwwaysTrue);
    costum.registerLeafConsumer(Color.GREEN, appenderAlwwaysTrue);
    costum.registerLeafConsumer(Color.YELOW, appenderAlwwaysTrue);
    costum.registerLeafConsumer(Color.RED, appenderAlwwaysTrue);
    DepthFirstPostOrderVisitor<Color> bfv = new DepthFirstPostOrderVisitor<>(costum);
    bfv.visit(simpleFullTree);
    assertEquals("4 5 2 6 7 3 1 ", bld.toString());
  
  }


  private Deque<Integer> s;
  @Test
  public void testVisit_pred() throws Exception {
    s = new ArrayDeque<>();
    VisitorCostumizer<Color> costum = new VisitorCostumizer<>();
    costum.registerPredicatedConsumer(node -> node.isLeaf(), node -> {
      s.push(((HasNumber) node).getMyNr());
    });
    costum.registerPredicatedConsumer(node -> !node.isLeaf(), node -> {
      Integer ll = s.pop();
      Integer lr = s.pop();
      s.push(ll + lr);
    });


    DepthFirstPostOrderVisitor<Color> bfv = new DepthFirstPostOrderVisitor<>(costum);
    bfv.visit(simpleFullTree);
    assertTrue(s.pop() == 22);
    assertTrue(s.isEmpty());
    

  }
}