package UnifiedGp.GpIndi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;

public class UnifiedGpIndiOnePointCrossover extends UnifiedGpIndiBreeder {

  @Override
  public UnifiedGpIndi[] breed(UnifiedGpIndi mother, UnifiedGpIndi father, Random rnd) {
    List<IInnerNode[]> pairs = shapePairs(mother.getRoot(), father.getRoot());
    if (pairs.size() < 2) {
      // fall back to regular cross over
      return super.breed(mother, father, rnd);
    }
    int rndIndex = rnd.nextInt(pairs.size());

    List<INode<NodeType>> w = super.makeChildren(mother, father, pairs.get(rndIndex)[0], pairs.get(rndIndex)[1]);
    return new UnifiedGpIndi[] { new UnifiedGpIndi((IInnerNode<NodeType>) w.get(0)),
        new UnifiedGpIndi((IInnerNode<NodeType>) w.get(1)) };

  }

  private static List<IInnerNode[]> shapePairs(IInnerNode<NodeType> fi, IInnerNode<NodeType> se) {
    List<IInnerNode[]> toRet = null;
    if ((!fi.getFirstChild().isLeaf()) && (!se.getFirstChild().isLeaf())) {
      toRet = shapePairs((IInnerNode<NodeType>) fi.getFirstChild(), (IInnerNode<NodeType>) se.getFirstChild());
    }

    if ((!fi.getSecondChild().isLeaf()) && (!se.getSecondChild().isLeaf())) {
      List<IInnerNode[]> rez = shapePairs((IInnerNode<NodeType>) fi.getSecondChild(),
          (IInnerNode<NodeType>) se.getSecondChild());
      if (toRet == null) {
        toRet = rez;
      } else {
        toRet.addAll(rez);
      }
    }
    if (toRet == null) {
      toRet = new ArrayList<>();
    }
    toRet.add(new IInnerNode[] { fi, se });

    return toRet;
  }

}
