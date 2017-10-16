package UnifiedGp.GpIndi;

import java.util.Arrays;
import java.util.List;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.RandomNodeSelector;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;

public class AbstactBreeder {

  public static final boolean defaultStaticSimplification = true;
  protected final RandomNodeSelector<NodeType> randomNodeSelector;
  protected final CopyReplace<NodeType> copyReplace;
  protected final boolean staticSimplificationEnabbled;
  protected final StaticSimplifierPetriBuilder simplifier;

  public AbstactBreeder() {
    this(defaultStaticSimplification);
  }

  public AbstactBreeder(boolean staticSimplificationEnabled) {
    randomNodeSelector = new RandomNodeSelector<>();
    copyReplace = new CopyReplace<>();
    this.staticSimplificationEnabbled = staticSimplificationEnabled;
    if (staticSimplificationEnabled) {
      simplifier = new StaticSimplifierPetriBuilder();
    } else {
      simplifier = null;
    }
  }

  protected List<INode<NodeType>> makeChildren(UnifiedGpIndi mother, UnifiedGpIndi father, INode<NodeType> motherSelected, INode<NodeType> fatherSelected) {
    INode<NodeType> motherSelectedCopy = copyReplace.copyReplace(motherSelected, null, null);
    INode<NodeType> fatherSelectedCopy = copyReplace.copyReplace(fatherSelected, null, null);
  
    INode<NodeType> childOne = copyReplace.copyReplace(mother.getRoot(), motherSelected, fatherSelectedCopy);
    INode<NodeType> childTwo = copyReplace.copyReplace(father.getRoot(), fatherSelected, motherSelectedCopy);
    if(staticSimplificationEnabbled){
      return Arrays.asList((simplifier.simplifyTree((IInnerNode<NodeType>) childOne)),
          simplifier.simplifyTree((IInnerNode<NodeType>) childTwo));
    }
  
    return Arrays.asList(childOne, childTwo);
  
  }

}
