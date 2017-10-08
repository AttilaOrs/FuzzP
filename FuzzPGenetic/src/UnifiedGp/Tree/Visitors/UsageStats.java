package UnifiedGp.Tree.Visitors;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;

public class UsageStats {
  private final Map<INode<NodeType>, UsageAndDepth> usageMap;

  public UsageStats() {
    usageMap = new HashMap<>();
  }

  public void add(INode<NodeType> node, int usage, int depth) {
    usageMap.put(node, new UsageAndDepth(usage, depth));
  }

  public int getUsage(INode<NodeType> node) {
    return usageMap.getOrDefault(node, new UsageAndDepth(0, 0)).usage;
  }

  public int getDepth(INode<NodeType> node) {
    return usageMap.getOrDefault(node, new UsageAndDepth(0, 0)).depth;
  }

  @Override
  public String toString() {
    return usageMap.entrySet().stream()
        .map(e -> "< " + e.getKey() + ", u:" + e.getValue().usage + " ,d:" + e.getValue().depth + ">")
        .collect(Collectors.joining("\n"));
  }


  private static class UsageAndDepth {

    public UsageAndDepth(int usage, int depth) {
      this.usage = usage;
      this.depth = depth;
    }


    final int usage;
    final int depth;
  }

}
