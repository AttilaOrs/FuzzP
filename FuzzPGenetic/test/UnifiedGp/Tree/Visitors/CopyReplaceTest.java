package UnifiedGp.Tree.Visitors;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.Tree.BreadthFirstVisitor;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.TestColorTree.Color;
import UnifiedGp.Tree.TestColorTree.ColoredInnerNode;
import UnifiedGp.Tree.TestColorTree.GreenLeaf;
import UnifiedGp.Tree.TestColorTree.HasNumber;
import UnifiedGp.Tree.TestColorTree.RedLeaf;
import UnifiedGp.Tree.TestColorTree.SubColorGreen;
import UnifiedGp.Tree.TestColorTree.SubColorRed;

public class CopyReplaceTest {

  private StringBuilder bld;
  private BreadthFirstVisitor<Color> visitor;

  @Before
  public void setup() {
    bld = new StringBuilder();
    VisitorCostumizer<Color> costum = new VisitorCostumizer<>();
    costum.registerPredicatedConsumer(node -> true, node -> bld.append(((HasNumber) node).getMyNr()).append(" "));
    visitor = new BreadthFirstVisitor<>(costum);

  }

  @Test
  public void leafRepalce() {
    RedLeaf fi = new RedLeaf(SubColorRed.RED_RED, 4);
    RedLeaf se = new RedLeaf(SubColorRed.RED_GREEN, 5);
    ColoredInnerNode ii = new ColoredInnerNode(2, Color.RED, fi, se);
    GreenLeaf gfi = new GreenLeaf(SubColorGreen.GREEN_RED, 6);
    GreenLeaf gse = new GreenLeaf(SubColorGreen.GREEN_GREEN, 7);
    ColoredInnerNode iii = new ColoredInnerNode(3, Color.GREEN, gfi, gse);
    ColoredInnerNode iiii = new ColoredInnerNode(1, Color.YELOW, ii, iii);
    
    RedLeaf newFi = new RedLeaf(SubColorRed.RED_GREEN, 11);
    RedLeaf newSe = new RedLeaf(SubColorRed.RED_GREEN, 12);
    ColoredInnerNode newInner = new ColoredInnerNode(10, Color.GREEN, newFi, newSe);

    CopyReplace<Color> copyReplace = new CopyReplace<>();
    INode<Color> newRoot = copyReplace.copyReplace(iiii, fi, newInner);

    visitor.visit(newRoot);
    assertEquals("1 2 3 10 5 6 7 11 12 ", bld.toString());
  }

  @Test
  public void iinerNode_replace() {
    RedLeaf fi = new RedLeaf(SubColorRed.RED_RED, 4);
    RedLeaf se = new RedLeaf(SubColorRed.RED_GREEN, 5);
    ColoredInnerNode ii = new ColoredInnerNode(2, Color.RED, fi, se);
    GreenLeaf gfi = new GreenLeaf(SubColorGreen.GREEN_RED, 6);
    GreenLeaf gse = new GreenLeaf(SubColorGreen.GREEN_GREEN, 7);
    ColoredInnerNode iii = new ColoredInnerNode(3, Color.GREEN, gfi, gse);
    ColoredInnerNode iiii = new ColoredInnerNode(1, Color.YELOW, ii, iii);

    RedLeaf newFi = new RedLeaf(SubColorRed.RED_GREEN, 11);
    RedLeaf newSe = new RedLeaf(SubColorRed.RED_GREEN, 12);
    ColoredInnerNode newInner = new ColoredInnerNode(10, Color.GREEN, newFi, newSe);

    CopyReplace<Color> copyReplace = new CopyReplace<>();
    INode<Color> newRoot = copyReplace.copyReplace(iiii, ii, newInner);

    visitor.visit(newRoot);

    assertEquals("1 10 3 11 12 6 7 ", bld.toString());
  }

}
