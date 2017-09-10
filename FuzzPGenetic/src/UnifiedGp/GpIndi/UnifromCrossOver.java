package UnifiedGp.GpIndi;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.ILeaf;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import structure.operators.ICreatureBreeder;

public class UnifromCrossOver implements ICreatureBreeder<UnifiedGpIndi> {
  private final CopyReplace<NodeType> copyReplace;
  private boolean staticSimplificationEnabbled;
  private StaticSimplifierPetriBuilder simplifier;
  private double prob;

  public UnifromCrossOver(double chanse) {
    this(UnifiedGpIndiBreeder.defaultStaticSimplification, chanse);
  }

  public double getProb() {
    return prob;
  }

  public UnifromCrossOver(boolean staticSimplificationEnabled, double chanse) {
    copyReplace = new CopyReplace<>();
    this.staticSimplificationEnabbled = staticSimplificationEnabled;
    this.prob = chanse;
    if (staticSimplificationEnabled) {
      simplifier = new StaticSimplifierPetriBuilder();
    } else {
      simplifier = null;
    }
  }

  @Override
  public UnifiedGpIndi[] breed(UnifiedGpIndi mother, UnifiedGpIndi father, Random rnd) {
    Shape s = new Shape();
    createShape(mother.getRoot(), father.getRoot(), s);
    List<INode[]> nodesToSwitch = new ArrayList<>();
    for (INode[] pair : s.shapePairs) {
      if (rnd.nextDouble() < prob) {
        nodesToSwitch.add(pair);
      }
    }

    INode<NodeType> motherCh = buildUp(mother.getRoot(),
        nodesToSwitch.stream().collect(toMap(xxx -> xxx[0], xxx -> xxx[1])),
        s);
    INode<NodeType> fatherCh = buildUp(father.getRoot(),
        nodesToSwitch.stream().collect(toMap(xxx -> xxx[1], xxx -> xxx[0])),
        s);
    if (staticSimplificationEnabbled) {
      motherCh = simplifier.simplifyTree((IInnerNode<NodeType>) motherCh);
      fatherCh = simplifier.simplifyTree((IInnerNode<NodeType>) fatherCh);
    }
    return new UnifiedGpIndi[] { new UnifiedGpIndi((IInnerNode<NodeType>) motherCh),
        new UnifiedGpIndi((IInnerNode<NodeType>) fatherCh) };
  }
  
  private INode<NodeType> buildUp(INode<NodeType> basedOn, Map<INode, INode> replace, Shape sh) {
    if (basedOn.isLeaf()) {
      if (replace.containsKey(basedOn)) {
        return copyReplace.copyReplace(replace.get(basedOn), null, null);
      }
      return ((ILeaf<NodeType>) basedOn).myClone();
    }
    IInnerNode<NodeType> innerBasedOn = ((IInnerNode<NodeType>) basedOn);
    if (replace.containsKey(basedOn)) {
      if (sh.isOnFrontier(basedOn)) {
        return copyReplace.copyReplace(replace.get(basedOn), null, null);
      } else {
        INode<NodeType> fiCh = buildUp(innerBasedOn.getFirstChild(), replace, sh);
        INode<NodeType> seCh = buildUp(innerBasedOn.getSecondChild(), replace, sh);
        return ((IInnerNode<NodeType>) replace.get(basedOn)).myClone(fiCh, seCh);
      }
    }
    INode<NodeType> fiCh = buildUp(innerBasedOn.getFirstChild(), replace, sh);
    INode<NodeType> seCh = buildUp(innerBasedOn.getSecondChild(), replace, sh);

    return innerBasedOn.myClone(fiCh, seCh);
  }

  private static void createShape(INode<NodeType> nodeOne, INode<NodeType> nodeTwo, Shape acc) {
    if ((!nodeOne.isLeaf()) && (!nodeTwo.isLeaf())) {
      acc.addToShape(new INode[] { nodeOne, nodeTwo });
      INode<NodeType> fiOne = ((IInnerNode<NodeType>) nodeOne).getFirstChild();
      INode<NodeType> fiTwo = ((IInnerNode<NodeType>) nodeOne).getSecondChild();

      INode<NodeType> seOne = ((IInnerNode<NodeType>) nodeTwo).getFirstChild();
      INode<NodeType> seTwo = ((IInnerNode<NodeType>) nodeTwo).getSecondChild();
      createShape(fiOne, seOne, acc);
      createShape(fiTwo, seTwo, acc);
      return;
    }
    acc.addToFrontier(new INode[] { nodeOne, nodeTwo });
  }

  private static class Shape {
    List<INode[]> shapePairs;
    List<INode[]> frontier;

    Shape() {
      shapePairs = new ArrayList<>();
      frontier = new ArrayList<>();
    }

    public boolean isOnFrontier(INode<NodeType> basedOn) {
      return frontier.stream().filter(arr -> arr[0].equals(basedOn) || arr[1].equals(basedOn)).findAny().isPresent();
    }

    void addToShape(INode[] what) {
      shapePairs.add(what);
    }

    void addToFrontier(INode[] what) {
      addToShape(what);
      frontier.add(what);
    }
  }

}
