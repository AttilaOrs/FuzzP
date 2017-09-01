package UnifiedGp.GpIndi;

import java.util.Random;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.RandomNodeSelector;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import structure.operators.ICreatureBreeder;

public class UnifiedGpIndiBreeder implements ICreatureBreeder<UnifiedGpIndi> {

  private static final boolean defaultStaticSimplification = true;

  private final RandomNodeSelector<NodeType> randomNodeSelector;
  private final CopyReplace<NodeType> copyReplace;
  private final boolean staticSimplificationEnabbled;
  private final StaticSimplifierPetriBuilder simplifier;


  public UnifiedGpIndiBreeder() {
    this(defaultStaticSimplification);

  }
  public UnifiedGpIndiBreeder(boolean staticSimplificationEnabled) {
    randomNodeSelector = new RandomNodeSelector<>();
    copyReplace = new CopyReplace<>();
    this.staticSimplificationEnabbled = staticSimplificationEnabled;
    if (staticSimplificationEnabled) {
      simplifier = new StaticSimplifierPetriBuilder();
    } else {
      simplifier = null;
    }

  }

  @Override
  public UnifiedGpIndi[] breed(UnifiedGpIndi mother, UnifiedGpIndi father, Random rnd) {
    INode<NodeType> motherSelected = randomNodeSelector
        .selectRandomNode(mother.getRoot(), node -> !mother.getRoot().equals(node), rnd)
        .orElseThrow(() -> new RuntimeException(" eee " + mother.getSizes()));
    INode<NodeType> fatherSelected = randomNodeSelector
        .selectRandomNode(father.getRoot(), node -> !father.getRoot().equals(node), rnd)
        .get();

    return makeChildren(mother, father, motherSelected, fatherSelected);

  }

  protected UnifiedGpIndi[] makeChildren(UnifiedGpIndi mother, UnifiedGpIndi father, INode<NodeType> motherSelected,
      INode<NodeType> fatherSelected) {
    INode<NodeType> motherSelectedCopy = copyReplace.copyReplace(motherSelected, null, null);
    INode<NodeType> fatherSelectedCopy = copyReplace.copyReplace(fatherSelected, null, null);

    INode<NodeType> childOne = copyReplace.copyReplace(mother.getRoot(), motherSelected, fatherSelectedCopy);
    INode<NodeType> childTwo = copyReplace.copyReplace(father.getRoot(), fatherSelected, motherSelectedCopy);
    if(staticSimplificationEnabbled){
      return new UnifiedGpIndi[] { new UnifiedGpIndi((simplifier.simplifyTree((IInnerNode<NodeType>) childOne))),
          new UnifiedGpIndi(simplifier.simplifyTree((IInnerNode<NodeType>) childTwo)) };
    }

    return new UnifiedGpIndi[] { new UnifiedGpIndi((IInnerNode<NodeType>) childOne),
        new UnifiedGpIndi((IInnerNode<NodeType>) childTwo) };
  }

}
