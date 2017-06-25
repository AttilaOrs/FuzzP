package UnifiedGp.GpIndi;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;

public interface TreeBuilderConfig {

  public double getLeafOrInnerNode();

  public double getMaxDepth();

  public Map<NodeType, Double> getLeafProbabilities();
  
  public Map<NodeType, Double> getOperatorProbabilities();
  
  public Function<Random, INode<NodeType>> getLeafFactory(NodeType t);


}
