package UnifiedGp.Tree.Visitors;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;

public class NodeUsageMerger {
  
  private NodeUsageMerger(){
    
  }
  
  
  public static UsageStats merge(INode<NodeType> oldRoot, UsageStats oldStats, INode<NodeType> newRoot, UsageStats newOne ){
    if(newRoot.isLeaf()){
      newOne.add(newRoot, oldStats.getUsage(oldRoot), 0);
    } else {
      IInnerNode<NodeType> oldRootAsInner = ((IInnerNode<NodeType>) oldRoot);
      IInnerNode<NodeType> newRootAsInner = ((IInnerNode<NodeType>) newRoot);
      merge(oldRootAsInner.getFirstChild(), oldStats, newRootAsInner.getFirstChild(), newOne);
      merge(oldRootAsInner.getSecondChild(), oldStats, newRootAsInner.getSecondChild(), newOne);
      int depthFi = newOne.getDepth(newRootAsInner.getFirstChild());
      int depthSe = newOne.getDepth(newRootAsInner.getSecondChild());
      int depth = ((depthFi > depthSe)?depthFi:depthSe)+1;
      newOne.add(newRoot, oldStats.getUsage(oldRoot), depth);
    }
    
    return newOne;
  }
  

}
