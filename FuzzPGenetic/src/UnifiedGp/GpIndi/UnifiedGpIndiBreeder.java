package UnifiedGp.GpIndi;

import java.util.List;
import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import structure.operators.ICreatureBreeder;

public class UnifiedGpIndiBreeder extends AbstactBreeder implements ICreatureBreeder<UnifiedGpIndi> {

  public UnifiedGpIndiBreeder() {
    super();

  }
  public UnifiedGpIndiBreeder(boolean staticSimplificationEnabled) {
    super(staticSimplificationEnabled);
  }

  @Override
  public UnifiedGpIndi[] breed(UnifiedGpIndi mother, UnifiedGpIndi father, Random rnd) {
    INode<NodeType> motherSelected = randomNodeSelector
        .selectRandomNode(mother.getRoot(), node -> !mother.getRoot().equals(node), rnd)
        .orElseThrow(() -> new RuntimeException(" eee " + mother.getSizes()));
    INode<NodeType> fatherSelected = randomNodeSelector
        .selectRandomNode(father.getRoot(), node -> !father.getRoot().equals(node), rnd)
        .get();

    List<INode<NodeType>> w = makeChildren(mother, father, motherSelected, fatherSelected);
    return new UnifiedGpIndi[] { new UnifiedGpIndi((IInnerNode<NodeType>) w.get(0)),
        new UnifiedGpIndi((IInnerNode<NodeType>) w.get(1)) };

  }

}
