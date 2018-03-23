package UnifiedGp.GpIndi.UsageStats;

import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.UsageStats;

public interface UsageSelector {

  public INode<NodeType> selectNode(UsageStats usage, Random rnd, IInnerNode<NodeType> iInnerNode);

}
