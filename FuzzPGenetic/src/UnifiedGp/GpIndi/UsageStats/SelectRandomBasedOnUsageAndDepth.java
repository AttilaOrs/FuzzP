package UnifiedGp.GpIndi.UsageStats;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.RandomNodeSelector;
import UnifiedGp.Tree.Visitors.UsageStats;

public class SelectRandomBasedOnUsageAndDepth {
  
  
  private UsageStats currentUsage;

  public INode<NodeType> selectForCrossover(UsageStats usage, Random rnd, IInnerNode<NodeType> iInnerNode){
    this.currentUsage = usage;
    return selectNode(usage, rnd, iInnerNode, this::nodeFitnesCross);
  }
  
  
  public INode<NodeType> selectNode(UsageStats usage, Random rnd, IInnerNode<NodeType> iInnerNode, Function< INode<NodeType> , Double> nodeFitnessFuncs) {
    if (usage == null) {
      RandomNodeSelector<NodeType> l = new RandomNodeSelector<>();
      Optional<INode<NodeType>> w = l.selectRandomNode(iInnerNode, node -> !iInnerNode.equals(node) && !node.isLeaf(),
          rnd);
      return w.isPresent() ? w.get() : null;
    }
    
		 List<INode<NodeType>> sorted = usage.getNodeSet().stream()
        .filter(e -> !e.isLeaf())
				.sorted((INode<NodeType> en1, INode<NodeType> en2) -> Double.compare(nodeFitnessFuncs.apply( en1), nodeFitnessFuncs.apply( en2)))
				.collect(Collectors.toList());
		HashMap<INode<NodeType>, Double> ranking = new HashMap<>();
		double sum = 0.0;
		for (int index = 0; index < sorted.size(); index++) {
			Double rank = rankOf(index, sorted.size());
			ranking.put(sorted.get(index), rank);
			sum += rank;
		}
		double i = rnd.nextDouble()*sum;
		double tempS = 0.0;
		for(INode<NodeType> muve: ranking.keySet()){
		  tempS += ranking.get(muve);
		  if(tempS> i){
		    return muve;
		  }
		}
    return null;
  }

  private Double nodeFitnesCross( INode<NodeType> en1) {
    int i = currentUsage.getUsage(en1);
    int allTickNr = currentUsage.getAllTickNr();
    if (i > allTickNr * 3) {
      if (i > allTickNr * 10) {
        return 0.000001;
      }
      i = allTickNr * 3;
    }
    return i / ((double) currentUsage.getSize(en1));
  }
  
  private static final double sp = 2.0;
  
	private Double rankOf(int index, int size) {
		return 2.0 - sp + (2 * (sp - 1.0) * ((index ) / (size - 1.0)));
	}
  

}
