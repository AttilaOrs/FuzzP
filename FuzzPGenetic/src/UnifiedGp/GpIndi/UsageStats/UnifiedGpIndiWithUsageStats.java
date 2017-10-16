package UnifiedGp.GpIndi.UsageStats;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.UsageStats;

public class UnifiedGpIndiWithUsageStats extends UnifiedGpIndi{
  
  private UsageStats usageStats;

  public UnifiedGpIndiWithUsageStats(IInnerNode<NodeType> root) {
    super(root);
  }
  
  public void setUsageStats(UsageStats stas){
    this.usageStats = stas;
  }
  
  public UsageStats getUsageStats(){
    return usageStats;
  }
  
  

}
