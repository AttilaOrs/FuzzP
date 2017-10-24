package UnifiedGp.Tree.Visitors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;

public class UsageStats {
  private final Map<INode<NodeType>, UsageAndDepth> usageMap;
  private int allTickNr;


  public UsageStats(int allTickNr) {
    usageMap = new HashMap<>();
    this.allTickNr = allTickNr;
  }
  
  public int getAllTickNr() {
    return allTickNr;
  }

  public Set<INode<NodeType>> getNodeSet(){
    return usageMap.keySet();
  }

  public void remove(INode<NodeType> toRemove) {
    usageMap.remove(toRemove);
  }

  public void add(INode<NodeType> node, int usage, int depth, int size) {
    usageMap.put(node, new UsageAndDepth(usage, depth, size));
  }

  public int getUsage(INode<NodeType> node) {
    return usageMap.getOrDefault(node, new UsageAndDepth(0, 0,1)).usage;
  }

  public int getDepth(INode<NodeType> node) {
    return usageMap.getOrDefault(node, new UsageAndDepth(0, 0, 1)).depth;
  }
  
  public int getSize(INode<NodeType> node) {
    return usageMap.getOrDefault(node, new UsageAndDepth(0, 0, 1)).size;
  }

  @Override
  public String toString() {
    return usageMap.entrySet().stream()
        .map(e -> "< " + e.getKey() + ", u:" + e.getValue().usage + " ,d:" + e.getValue().depth + " ,s:"+ e.getValue().size +">")
        .collect(Collectors.joining("\n"));
  }


   static class UsageAndDepth {

    public UsageAndDepth(int usage, int depth, int size) {
      this.usage = usage;
      this.depth = depth;
      this.size = size;
    }


    final int usage;
    final int depth;
    final int size;
  }

}
