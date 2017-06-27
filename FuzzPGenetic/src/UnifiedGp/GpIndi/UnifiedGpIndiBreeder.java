package UnifiedGp.GpIndi;

import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.RandomNodeSelector;
import structure.operators.ICreatureBreeder;

public class UnifiedGpIndiBreeder implements ICreatureBreeder<UnifiedGpIndi> {

  private RandomNodeSelector<NodeType> randomNodeSelector;
  private CopyReplace<NodeType> copyReplace;

  public UnifiedGpIndiBreeder() {
    randomNodeSelector = new RandomNodeSelector<>();
    copyReplace = new CopyReplace<>();
  }

  @Override
  public UnifiedGpIndi[] breed(UnifiedGpIndi mother, UnifiedGpIndi father, Random rnd) {
    INode<NodeType> motherSelected = randomNodeSelector
        .selectRandomNode(mother.getRoot(), node -> !mother.getRoot().equals(node), rnd)
        .orElseThrow(() -> new RuntimeException(" eee " + mother.getSizes()));
    INode<NodeType> fatherSelected = randomNodeSelector
        .selectRandomNode(father.getRoot(), node -> !father.getRoot().equals(node), rnd)
        .get();

    INode<NodeType> motherSelectedCopy = copyReplace.copyReplace(motherSelected, null, null);
    INode<NodeType> fatherSelectedCopy = copyReplace.copyReplace(fatherSelected, null, null);

    INode<NodeType> childOne = copyReplace.copyReplace(mother.getRoot(), motherSelected, fatherSelectedCopy);
    INode<NodeType> childTwo = copyReplace.copyReplace(father.getRoot(), fatherSelected, motherSelectedCopy);

    return new UnifiedGpIndi[] { new UnifiedGpIndi((IInnerNode<NodeType>) childOne),
        new UnifiedGpIndi((IInnerNode<NodeType>) childTwo) };

  }

}
