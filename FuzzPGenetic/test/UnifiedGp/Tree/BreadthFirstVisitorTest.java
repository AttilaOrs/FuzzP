package UnifiedGp.Tree;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.Tree.TestColorTree.Color;
import UnifiedGp.Tree.TestColorTree.ColoredInnerNode;
import UnifiedGp.Tree.TestColorTree.GreenLeaf;
import UnifiedGp.Tree.TestColorTree.HasNumber;
import UnifiedGp.Tree.TestColorTree.Marker;
import UnifiedGp.Tree.TestColorTree.RedLeaf;
import UnifiedGp.Tree.TestColorTree.SubColorGreen;
import UnifiedGp.Tree.TestColorTree.SubColorRed;

public class BreadthFirstVisitorTest {


  private ColoredInnerNode simpleFullTree;
  private StringBuilder bld;
  Function<INode<Color>, Boolean> appenderAlwwaysTrue;
  private Function<INode<Color>, Boolean> appenderAlwwaysFalse;


  @Before
  public void setupTree() {
    simpleFullTree = createSimpleFullTree();
    bld = new StringBuilder();
    appenderAlwwaysTrue = l -> {
      bld.append(((HasNumber) l).getMyNr()).append(" ");
      return true;
    };
    appenderAlwwaysFalse = l -> {
      bld.append(((HasNumber) l).getMyNr()).append(" ");
      return false;
    };
  }

  public static ColoredInnerNode createSimpleFullTree() {
    RedLeaf fi = new RedLeaf(SubColorRed.RED_RED, 4);
    RedLeaf se = new RedLeaf(SubColorRed.RED_GREEN, 5);
    ColoredInnerNode ii = new ColoredInnerNode(2, Color.RED, fi, se);
    GreenLeaf gfi = new GreenLeaf(SubColorGreen.GREEN_RED, 6);
    GreenLeaf gse = new GreenLeaf(SubColorGreen.GREEN_GREEN, 7);
    ColoredInnerNode iii = new ColoredInnerNode(3, Color.GREEN, gfi, gse);
    ColoredInnerNode iiii = new ColoredInnerNode(1, Color.YELOW, ii, iii);
    return iiii;
  }


  @Test
  public void simpleFulTree_visitsEverythingInOrder() {
    VisitorCostumizer<Color, Marker> costum = new VisitorCostumizer<>();
    costum.registerOperatorConsumer(Color.GREEN, appenderAlwwaysTrue);
    costum.registerOperatorConsumer(Color.YELOW, appenderAlwwaysTrue);
    costum.registerOperatorConsumer(Color.RED, appenderAlwwaysTrue);
    costum.registerLeafConsumer(Color.GREEN, appenderAlwwaysTrue);
    costum.registerLeafConsumer(Color.YELOW, appenderAlwwaysTrue);
    costum.registerLeafConsumer(Color.RED, appenderAlwwaysTrue);
    BreadthFirstVisitor<Color, Marker> bfv = new BreadthFirstVisitor<>(costum);
    bfv.visit(simpleFullTree);
    assertEquals("1 2 3 4 5 6 7 ", bld.toString());
  }

  @Test
  public void simpleFulTree_notVisitIfReturnsFalse() {
    VisitorCostumizer<Color, Marker> costum = new VisitorCostumizer<>();
    costum.registerOperatorConsumer(Color.GREEN, appenderAlwwaysTrue);
    costum.registerOperatorConsumer(Color.YELOW, appenderAlwwaysTrue);
    costum.registerOperatorConsumer(Color.RED, appenderAlwwaysFalse);
    costum.registerLeafConsumer(Color.GREEN, appenderAlwwaysTrue);
    costum.registerLeafConsumer(Color.YELOW, appenderAlwwaysTrue);
    costum.registerLeafConsumer(Color.RED, appenderAlwwaysTrue);
    BreadthFirstVisitor<Color, Marker> bfv = new BreadthFirstVisitor<>(costum);
    bfv.visit(simpleFullTree);
    assertEquals("1 2 3 6 7 ", bld.toString());
  }


  @Test
  public void simpleFulTree_findsSubtype() {
    VisitorCostumizer<Color, Marker> costum = new VisitorCostumizer<>();
    costum.registerLeafSubConsumer(Color.RED, SubColorRed.RED_RED, appenderAlwwaysTrue);
    costum.registerLeafSubConsumer(Color.GREEN, SubColorGreen.GREEN_RED, appenderAlwwaysTrue);
    BreadthFirstVisitor<Color, Marker> bfv = new BreadthFirstVisitor<>(costum);
    bfv.visit(simpleFullTree);
    assertEquals("4 6 ", bld.toString());
  }


}
