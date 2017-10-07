package UETPNLisp;

import org.junit.Test;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;

public class UETPNLispTest {

  @Test
  public void test() {
    INode<NodeType> rez = UETPNLisp.fromString("(# c:-1 (+ b v ))");
    System.out.println(rez.toString());
  }

}
