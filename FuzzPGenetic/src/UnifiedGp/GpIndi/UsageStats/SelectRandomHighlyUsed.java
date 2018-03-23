package UnifiedGp.GpIndi.UsageStats;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.UsageStats;

public class SelectRandomHighlyUsed extends SelectRandomBasedOnUsageAndDepth {

  @Override
  protected double nodeFitnes(UsageStats usage, INode<NodeType> en1) {
    int i = usage.getUsage(en1);
    return (i / ((double) usage.getSize(en1)));
  }
}
