package UnifiedGp.Tree.Visitors;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.ILeaf;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.NodeType;

public class DynamicallySimplifiedPetriNetBuilder {

  private VisitorCostumizer<NodeType> cosumizer;
  private DepthFirstPostOrderVisitor<NodeType> visitor;
  private Deque<INode<NodeType>> newNetStack;
  private Map<INode<NodeType>, Integer> nodeTransitionMapping;
  private Set<Integer> firedTrs;

  private final static List<NodeType> concLike = Arrays.asList(NodeType.Conc, NodeType.Add, NodeType.Multiply,
      NodeType.PosNegSplit);

  public DynamicallySimplifiedPetriNetBuilder() {
    cosumizer = new VisitorCostumizer<>();
    cosumizer.registerPredicatedConsumer(node -> node.isLeaf(), this::visitLeaf);
    cosumizer.registerPredicatedConsumer(node -> concLike.contains(node.getType()), this::visitConcLike);
    cosumizer.registerPredicatedConsumer(node -> (!node.isLeaf() && !concLike.contains(node.getType())),
        this::visitOtherOps);
    

    visitor = new DepthFirstPostOrderVisitor<>(cosumizer);
  }

  private void visitOtherOps(INode<NodeType> node) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
      newNetStack.push(new BlockLeaf());
    } else {
      newNetStack.push(((IInnerNode<NodeType>) node).myClone(fi, se));
    }

  }


  private void visitConcLike(INode<NodeType> node) {
    if (nodeTransitionMapping.containsKey(node)) {
      Integer tr = nodeTransitionMapping.get(node);
      if (!firedTrs.contains(tr)) {
        newNetStack.pop();
        newNetStack.pop();
        newNetStack.push(new BlockLeaf());
        return;
      }
    }
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    newNetStack.push(((IInnerNode<NodeType>) node).myClone(fi, se));
  }

  private void visitLeaf(INode<NodeType> node) {
    if (nodeTransitionMapping.containsKey(node)) {
      Integer tr = nodeTransitionMapping.get(node);
      if (!firedTrs.contains(tr)) {
        newNetStack.push(new BlockLeaf());
        return;
      }
    }
    newNetStack.push(((ILeaf<NodeType>) node).myClone());
  }

  public IInnerNode<NodeType> createSimplifiedTree(IInnerNode<NodeType> root, Set<Integer> firedTrs,
      Map<INode<NodeType>, Integer> nodeTransitinMapping) {
    this.nodeTransitionMapping = nodeTransitinMapping;
    newNetStack = new ArrayDeque<>();
    this.firedTrs = firedTrs;
    visitor.visit(root);
    INode<NodeType> rez = newNetStack.pop();
    if (rez.isLeaf()) {
      rez = new InnerNode(NodeType.Seq, rez, new BlockLeaf());
    }

    return (IInnerNode<NodeType>) rez;
  }

}
