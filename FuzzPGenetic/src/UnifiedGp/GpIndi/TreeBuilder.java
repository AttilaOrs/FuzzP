package UnifiedGp.GpIndi;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import structure.operators.ICreatureGenerator;

public class TreeBuilder implements ICreatureGenerator<UnifiedGpIndi> {

  private TreeBuilderConfig config;

  private Deque<INode<NodeType>> nodeStack;
  private Deque<Integer> depthStack;

  private double leafSum = -1.0;

  private double inerNodeSum;

  public TreeBuilder(TreeBuilderConfig config) {
    this.config = config;
  }

  @Override
  public UnifiedGpIndi genearteRandomCreature(Random rnd) {
    nodeStack = new ArrayDeque<>();
    depthStack = new ArrayDeque<>();
    nodeStack.push(randomLeaf(rnd));
    nodeStack.push(randomLeaf(rnd));
    depthStack.push(0);
    depthStack.push(0);
    while (nodeStack.size() > 1) {
      double leafOrNode = rnd.nextDouble();
      if (leafOrNode < config.getLeafOrInnerNode()) {
        nodeStack.push(randomLeaf(rnd));
        depthStack.push(0);
      } else {
        INode<NodeType> fi = nodeStack.pop();
        INode<NodeType> se = nodeStack.pop();
        Integer depthFi = depthStack.pop();
        Integer depthSe = depthStack.pop();
        nodeStack.push(randomInnerNode(rnd, fi, se));
        Integer nowDepth = ((depthFi < depthSe) ? depthSe : depthFi) + 1;
        if (nowDepth == config.getMaxDepth()) {
          break;
        }
        depthStack.push(nowDepth);
      }
    }

    return new UnifiedGpIndi((IInnerNode<NodeType>) nodeStack.pop());
  }

  private INode<NodeType> randomLeaf(Random rnd) {
    if (leafSum != -1.0) {
      leafSum = config.getLeafProbabilities().values().stream()
          .collect(Collectors.summarizingDouble(d -> d)).getSum();
    }
    NodeType n = selectType(rnd, leafSum, config.getLeafProbabilities());
    return config.getLeafFactory(n).apply(rnd);
  }

  private INode<NodeType> randomInnerNode(Random rnd, INode<NodeType> fi, INode<NodeType> se) {
    if (inerNodeSum != -1.0) {
      inerNodeSum = config.getOperatorProbabilities().values().stream()
          .collect(Collectors.summarizingDouble(d -> d)).getSum();
    }
    NodeType n = selectType(rnd, inerNodeSum, config.getOperatorProbabilities());
    return new InnerNode(n, fi, se);
  }

  private NodeType selectType(Random rnd, Double totalSum, Map<NodeType, Double> probs) {
    double now = rnd.nextDouble() * totalSum;
    double nowSum = 0.0;
    NodeType n = null;
    for (Entry<NodeType, Double> e : probs.entrySet()) {
      nowSum += e.getValue();
      if(nowSum > now){
        n = e.getKey();
        break;
      }
    }
    return n;
  }
}
