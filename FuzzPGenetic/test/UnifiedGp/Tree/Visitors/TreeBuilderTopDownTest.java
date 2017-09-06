package UnifiedGp.Tree.Visitors;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGpProblmes.ArtificalAnt.AntFitnes;

public class TreeBuilderTopDownTest {

  public static TreeBuilderTopDown<NodeType> createTreeBuilder() {
    return new TreeBuilderTopDown<>(new TreeBuilderCongigGeneralImpl(AntFitnes.problemSpecification()));
  }
  @Test
  public void test() {
    TreeBuilderTopDown<NodeType> builder = createTreeBuilder();
    IInnerNode<NodeType> l = builder.genearteFullRandomTree(new Random(), 3);
    long braces = l.toString().chars().filter(i -> i == '(').count();
    assertTrue(braces == 7);
  }

}
