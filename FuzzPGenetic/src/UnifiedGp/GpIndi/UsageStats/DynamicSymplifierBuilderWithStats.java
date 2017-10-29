package UnifiedGp.GpIndi.UsageStats;

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
import UnifiedGp.Tree.Visitors.UsageStats;

public class DynamicSymplifierBuilderWithStats {

  private VisitorCostumizer<NodeType> cosumizer;
  private DepthFirstPostOrderVisitor<NodeType> visitor;
  private Deque<INode<NodeType>> newNetStack;
  private Map<INode<NodeType>, Integer> nodeTransitionMapping;
  private Set<Integer> firedTrs;
  private UsageStats oldUSageStats;
  private UsageStats newUsageStats;

  private final static List<NodeType> concLike = Arrays.asList(NodeType.Conc, NodeType.Add, NodeType.Multiply);
  private InvalidStatsDeleter cleaner;

  private static boolean concLike(INode<NodeType> node) {
    return concLike.contains(node.getType());
  }

  public DynamicSymplifierBuilderWithStats() {
    cosumizer = new VisitorCostumizer<>();
    cosumizer.registerPredicatedConsumer(node -> node.isLeaf(), this::visitLeaf);
    cosumizer.registerPredicatedConsumer(DynamicSymplifierBuilderWithStats::concLike, this::visitConcLike);
    cosumizer.registerPredicatedConsumer(
        node -> (!node.isLeaf() && !DynamicSymplifierBuilderWithStats.concLike(node)),
        this::visitOtherOps);

    visitor = new DepthFirstPostOrderVisitor<>(cosumizer);
    cleaner = new InvalidStatsDeleter();
  }

  private void visitOtherOps(INode<NodeType> node) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
      BlockLeaf e = new BlockLeaf();
      newNetStack.push(e);
      newUsageStats.add(e, 0, 0, 1);
    } else {
      IInnerNode<NodeType> myClone = ((IInnerNode<NodeType>) node).myClone(fi, se);
      newNetStack.push(myClone);
      addInnerToStats(myClone);
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
    if (se.getType().isBlock()) {
      InnerNode e = new InnerNode(NodeType.Seq, fi, se);
      newUsageStats.add(e, newUsageStats.getUsage(fi), newUsageStats.getDepth(fi) + 1, newUsageStats.getSize(fi) + 2);
      newNetStack.push(e);
    } else if (fi.getType().isBlock()) {
      InnerNode e = new InnerNode(NodeType.Seq, se, fi);
      newUsageStats.add(e, newUsageStats.getUsage(se), newUsageStats.getDepth(se) + 1, newUsageStats.getSize(se) + 2);
      newNetStack.push(e);
    } else {
      IInnerNode<NodeType> myClone = ((IInnerNode<NodeType>) node).myClone(fi, se);
      addInnerToStats(myClone);
      newNetStack.push(myClone);
    }
  }

  private void addInnerToStats(IInnerNode<NodeType> node) {
    IInnerNode<NodeType> inner = node;
    int firedFi = newUsageStats.getUsage(inner.getFirstChild());
    int firedSe = newUsageStats.getUsage(inner.getSecondChild());
    int fire = (firedFi > firedSe) ? firedFi : firedSe;

    int depthFi = newUsageStats.getDepth(inner.getFirstChild());
    int depthSe = newUsageStats.getDepth(inner.getSecondChild());
    int depth = ((depthFi > depthSe) ? depthFi : depthSe) + 1;

    int sizeFi = newUsageStats.getSize(inner.getFirstChild());
    int sizeSe = newUsageStats.getSize(inner.getSecondChild());
    int size = sizeFi + sizeSe + 1;
    newUsageStats.add(node, fire, depth, size);
  }

  private void visitLeaf(INode<NodeType> node) {
    if (nodeTransitionMapping.containsKey(node)) {
      Integer tr = nodeTransitionMapping.get(node);
      if (!firedTrs.contains(tr)) {
        BlockLeaf e = new BlockLeaf();
        newUsageStats.add(e, 0, 0, 1);
        newNetStack.push(e);
        return;
      }
    }

    ILeaf<NodeType> myClone = ((ILeaf<NodeType>) node).myClone();
    newNetStack.push(myClone);
    newUsageStats.add(myClone, oldUSageStats.getUsage(node), 0, 1);
  }

  public IInnerNode<NodeType> createSimplifiedTree(IInnerNode<NodeType> root, Set<Integer> firedTrs,
      Map<INode<NodeType>, Integer> nodeTransitinMapping, UsageStats oldUSageStat) {
    this.nodeTransitionMapping = nodeTransitinMapping;
    this.oldUSageStats = oldUSageStat;
    this.newUsageStats = new UsageStats(oldUSageStat.getAllTickNr());
    newNetStack = new ArrayDeque<>();
    this.firedTrs = firedTrs;
    visitor.visit(root);
    INode<NodeType> rez = newNetStack.pop();
    if (rez.isLeaf()) {
      BlockLeaf se = new BlockLeaf();
      InnerNode newrez = new InnerNode(NodeType.Seq, rez, se);
      newUsageStats.add(newrez, 1, 0, 2);
      newUsageStats.add(se, 0, 0, 1);
      rez = newrez;
    }
    cleaner.clean(root, newUsageStats);
    return (IInnerNode<NodeType>) rez;
  }

  public UsageStats getNewUsageStats() {
    return newUsageStats;
  }


}
