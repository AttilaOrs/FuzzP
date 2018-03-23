package UnifiedGp.GpIndi.UsageStats;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.UsageStats;

public class SelectRandomNonused extends SelectRandomBasedOnUsageAndDepth {

  @Override
  protected double nodeFitnes(UsageStats usage, INode<NodeType> en1) {
    int i = usage.getUsage(en1);
    return 1.0 / (i / ((double) usage.getSize(en1)));
  }
}
