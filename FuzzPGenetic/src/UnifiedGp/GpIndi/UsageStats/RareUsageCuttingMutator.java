package UnifiedGp.GpIndi.UsageStats;

import java.util.Random;

import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import structure.operators.ICreatureMutator;

public class RareUsageCuttingMutator  implements ICreatureMutator<UnifiedGpIndiWithUsageStats>{
  private final SelectRandomBasedOnUsageAndDepth select;
  private final CopyReplace<NodeType> copyReplace;
  private final StaticSimplifierPetriBuilder simplifier;
  private final boolean simplify;
  
  public RareUsageCuttingMutator(){
    this(UnifiedGpIndiTreeMutator.defaultStaticSimplification);
  }
  
  public RareUsageCuttingMutator(boolean staticSimplify) {
    select = new SelectRandomBasedOnUsageAndDepth();
    copyReplace = new CopyReplace<>(); 
    simplify = staticSimplify;
    if(simplify){
    simplifier = new  StaticSimplifierPetriBuilder();
    } else {
      simplifier = null;
    }
  }

  @Override
  public UnifiedGpIndiWithUsageStats mutate(UnifiedGpIndiWithUsageStats creature, Random random) {
    INode<NodeType> selectedNode = select.selectNode(creature.getUsageStats(), random, creature.getRoot(), node -> {
      int usage = creature.getUsageStats().getUsage(node);
      int size = creature.getUsageStats().getSize(node);
      return ((double) size)/(usage +1.0);
    });
    
    IInnerNode<NodeType> newRoot = (IInnerNode<NodeType>) copyReplace.copyReplace(creature.getRoot(), selectedNode, new BlockLeaf());
    return new UnifiedGpIndiWithUsageStats((simplify)? simplifier.simplifyTree(newRoot):newRoot);
  }
  

}
