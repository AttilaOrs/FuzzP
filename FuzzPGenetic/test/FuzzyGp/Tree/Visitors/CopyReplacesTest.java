package FuzzyGp.Tree.Visitors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Parser;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Visitors.CopyReplaces;
import FuzzyGp.Tree.Visitors.ToSlispExpression;

public class CopyReplacesTest {

  @Test
  public void test() {
    Operator node = (Operator) Parser.parse("(* (& d0 i0)  (+ o1 d2) )");
    Operator replaceWith = (Operator) Parser.parse("(# i0 d1)");

    CopyReplaces rr = new CopyReplaces();
    INode ss = rr.replace(node, node.secondChildren, replaceWith);

    String made = new ToSlispExpression(ss).getString();
    assertEquals("(* (& d0 i0 ) (# i0 d1 ) )", made);
  }

}
