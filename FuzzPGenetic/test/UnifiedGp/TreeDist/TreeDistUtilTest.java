package UnifiedGp.TreeDist;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import UnifiedGp.TestUtils;

public class TreeDistUtilTest {

  @Test
  public void treeEditDistance1() {
      TreeDistNode<String> T1 = new SimpleTreeDistNode<>("");
      TreeDistUtil.treeFromString(T1, "c(b(a))");

      TreeDistNode<String> T2 = new SimpleTreeDistNode<>("");
      TreeDistUtil.treeFromString(T2, "c(b(a d))");

      List<TreeDistOperation> ops = new ArrayList<>();
      assertEquals(1, TreeDistUtil.treeEditDistance(T1, T2, ops, new SimpleCostFunction<>()));
  }

  @Test
  public void treeEditDistance2() {
      TreeDistNode<String> T1 = new SimpleTreeDistNode<>("");
      TreeDistUtil.treeFromString(T1, "f(d(a c(b)) e)");

      TreeDistNode<String> T2 = new SimpleTreeDistNode<>("");
      TreeDistUtil.treeFromString(T2, "f(c(d(a b)) e)");

      List<TreeDistOperation> ops = new ArrayList<>();
      assertEquals(2, TreeDistUtil.treeEditDistance(T1, T2, ops, new SimpleCostFunction<>()));
  }

  @Test
  public void treeEditDistance3() {
      TreeDistNode<String> T1 = new SimpleTreeDistNode<>("");
      TreeDistUtil.treeFromString(T1, "a(b(d(g) e(h i j k)) c(f(l(m n))))");

      TreeDistNode<String> T2 = new SimpleTreeDistNode<>("");
      TreeDistUtil.treeFromString(T2, "a(b(d(g)) c(f(l)) hello(world))");

      List<TreeDistOperation> ops = new ArrayList<>();
      assertEquals(9, TreeDistUtil.treeEditDistance(T1, T2, ops, new SimpleCostFunction<>()));
  }
  
  @Test
  public void mytest() {
    
    int rez = TreeDistUtil.treeEditDistance(TestUtils.allSimpleOps(), TestUtils.allSimpleOps());
    assertEquals(0, rez);
  }
  
  @Test
  public void mytest2() {
    
    int rez = TreeDistUtil.treeEditDistance(TestUtils.allSimpleOps(), TestUtils.allSimpleModified() );
    assertEquals(20, rez);
  }
  
  @Test
  public void mytest3() {
    
    int rez = TreeDistUtil.treeEditDistance(TestUtils.loopLoopOne(), TestUtils.concLoopOne());
    assertEquals(10, rez);
  }
  
  

}
