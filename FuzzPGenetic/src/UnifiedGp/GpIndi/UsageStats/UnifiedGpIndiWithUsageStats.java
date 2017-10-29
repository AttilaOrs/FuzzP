package UnifiedGp.GpIndi.UsageStats;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.UsageStats;
import structure.IGPGreature;

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
  
  
  @Override
  public IGPGreature myClone() {
    CopyReplace<NodeType> t = new CopyReplace<>();
    return new UnifiedGpIndiWithUsageStats((IInnerNode<NodeType>) t.copyReplace(root, null, null));
  }

}
