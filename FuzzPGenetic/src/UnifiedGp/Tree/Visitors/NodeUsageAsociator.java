package UnifiedGp.Tree.Visitors;

import java.util.Map;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.NodeType;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FireCounterRecorder;

public class NodeUsageAsociator {

  private VisitorCostumizer<NodeType> cosutimzer;
  private DepthFirstPostOrderVisitor<NodeType> visitor;

  public NodeUsageAsociator() {
    this.cosutimzer = new VisitorCostumizer<>();
    cosutimzer.registerPredicatedConsumer(p -> p.isLeaf(), this::visitLeafs);
    cosutimzer.registerPredicatedConsumer(p -> !p.isLeaf(), this::visitInner);
    visitor = new DepthFirstPostOrderVisitor<>(cosutimzer);
  }

  private UsageStats usage;
  private Map<INode<NodeType>, Integer> nodeToTransitionMapping;
  FireCounterRecorder<UnifiedToken> counter;

  public UsageStats calcUsage(INode<NodeType> root, FireCounterRecorder<UnifiedToken> fireCounter,
      Map<INode<NodeType>, Integer> nodeToTransitionMapping) {
    usage = new UsageStats();
    counter = fireCounter;
    this.nodeToTransitionMapping = nodeToTransitionMapping;
    visitor.visit(root);
    return usage;
  }



  private void visitLeafs(INode<NodeType> leaf) {
    if (nodeToTransitionMapping.containsKey(leaf)) {
      Integer trId = nodeToTransitionMapping.get(leaf);
      Integer fired = counter.getFireCountForTransition(trId);
      usage.add(leaf, fired, 0);
    } else {
      usage.add(leaf, 0, 0);
      System.err.println("No usage info for: " + leaf);
    }

  }

  private void visitInner(INode<NodeType> node) {
    IInnerNode<NodeType> inner = (IInnerNode<NodeType>) node;
    int firedFi = usage.getUsage(inner.getFirstChild());
    int firedSe = usage.getUsage(inner.getSecondChild());
    int fire = (firedFi > firedSe) ? firedFi : firedSe;
    
    int depthFi = usage.getDepth(inner.getFirstChild());
    int depthSe = usage.getDepth(inner.getSecondChild());
    int depth = ((depthFi > depthSe) ? depthFi : depthSe) + 1;
    usage.add(node, fire, depth);
  }

}
