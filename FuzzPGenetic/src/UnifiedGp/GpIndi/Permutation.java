package UnifiedGp.GpIndi;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import UnifiedGp.Tree.Visitors.RandomNodeSelector;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import structure.operators.ICreatureMutator;

public class Permutation implements ICreatureMutator<UnifiedGpIndi> {

  private static final boolean defaultStaticSimplification = true;
  private final RandomNodeSelector<NodeType> randomSelector;
  private final CopyReplace<NodeType> copyReplace;
  private final boolean staticSimplificationEnabbled;
  private final StaticSimplifierPetriBuilder simplifier;
  
  public Permutation() {
    this(defaultStaticSimplification);
  }
  
  public  Permutation(boolean staticSimplify){
    randomSelector = new RandomNodeSelector<>();
    staticSimplificationEnabbled = staticSimplify;
    simplifier = new StaticSimplifierPetriBuilder();
    copyReplace = new CopyReplace<>();
  }
  
  
  @Override
  public UnifiedGpIndi mutate(UnifiedGpIndi creature, Random random) {
    Optional<INode<NodeType>> smallRoot = randomSelector.selectRandomNode(creature.getRoot(), ((Predicate<INode<NodeType>>) INode::isLeaf).negate(), random);
    if(!smallRoot.isPresent()){
      throw new RuntimeException("no operator at exoression");
    }
    
    IInnerNode<NodeType> smallRootInner = (IInnerNode<NodeType>)smallRoot.get();
    INode<NodeType> fiRandNode = randomSelector.selectRandomNode(smallRootInner.getFirstChild(), node -> true, random).get();
    INode<NodeType> seRandNode = randomSelector.selectRandomNode(smallRootInner.getSecondChild(), node -> true, random).get();
    
    INode<NodeType> fiRandNodeCopy = copyReplace.copyReplace(fiRandNode, null, null);
    INode<NodeType> seRandNodeCopy = copyReplace.copyReplace(seRandNode, null, null);
    
    INode<NodeType> firtChildCopy = copyReplace.copyReplace(smallRootInner.getFirstChild(), fiRandNode, seRandNodeCopy);
    INode<NodeType> secondChildCopy = copyReplace.copyReplace(smallRootInner.getSecondChild(), seRandNode, fiRandNodeCopy);
    
    IInnerNode<NodeType> newSmallRoot = smallRootInner.myClone(firtChildCopy, secondChildCopy);
    if(smallRootInner.equals(creature.getRoot())){
      return new UnifiedGpIndi((staticSimplificationEnabbled)? (simplifier.simplifyTree(newSmallRoot)):newSmallRoot);
    }
     IInnerNode<NodeType> newRoot = (IInnerNode<NodeType>)copyReplace.copyReplace(creature.getRoot(), smallRootInner, newSmallRoot);
    return new UnifiedGpIndi((staticSimplificationEnabbled)? (simplifier.simplifyTree(newRoot)):newRoot);
  }

}
