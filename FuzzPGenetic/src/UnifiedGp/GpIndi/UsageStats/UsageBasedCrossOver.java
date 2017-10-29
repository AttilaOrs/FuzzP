package UnifiedGp.GpIndi.UsageStats;

import java.util.List;
import java.util.Random;

import UnifiedGp.GpIndi.AbstactBreeder;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.UsageStats;
import structure.operators.ICreatureBreeder;

public class UsageBasedCrossOver extends AbstactBreeder implements ICreatureBreeder<UnifiedGpIndiWithUsageStats> {
  SelectRandomBasedOnUsageAndDepth select;

  public UsageBasedCrossOver() {
    super();
    select = new SelectRandomBasedOnUsageAndDepth();
  }


  @Override
  public UnifiedGpIndiWithUsageStats[] breed(UnifiedGpIndiWithUsageStats mother, UnifiedGpIndiWithUsageStats father,
      Random rnd) {
    UsageStats motherStat = mother.getUsageStats();
    UsageStats fatherStat = father.getUsageStats();
    INode<NodeType> motherSelected = select.selectNode(motherStat, rnd);
    INode<NodeType> fatherSelected = select.selectNode(fatherStat, rnd);
    List<INode<NodeType>> rez = super.makeChildren(mother, father, motherSelected, fatherSelected);

    return new UnifiedGpIndiWithUsageStats[] { new UnifiedGpIndiWithUsageStats((IInnerNode<NodeType>) rez.get(0)),
        new UnifiedGpIndiWithUsageStats((IInnerNode<NodeType>) rez.get(1)) };

  }

}
