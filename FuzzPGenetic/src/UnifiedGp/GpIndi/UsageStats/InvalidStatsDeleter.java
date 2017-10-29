package UnifiedGp.GpIndi.UsageStats;

import java.util.HashSet;
import java.util.Set;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.UsageStats;

public class InvalidStatsDeleter {

  private DepthFirstPostOrderVisitor<NodeType> visitor;
  private Set<INode<NodeType>> saw;

  public InvalidStatsDeleter() {
    VisitorCostumizer<NodeType> costumizer = new VisitorCostumizer<>();
    costumizer.registerPredicatedConsumer(node -> true, this::visit);
    visitor = new DepthFirstPostOrderVisitor<>(costumizer);
  }

  public void clean(INode<NodeType> root, UsageStats oldStat) {
    saw = new HashSet<>();
    visitor.visit(root);
    Set<INode<NodeType>> temp = new HashSet<>(oldStat.getNodeSet());
    for (INode<NodeType> s : temp) {
      if (!saw.contains(s)) {
        oldStat.remove(s);
      }
    }
  }

  private void visit(INode<NodeType> bob) {
    saw.add(bob);
  }

}
