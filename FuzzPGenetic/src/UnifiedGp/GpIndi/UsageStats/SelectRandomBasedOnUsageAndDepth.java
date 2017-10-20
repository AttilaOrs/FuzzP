package UnifiedGp.GpIndi.UsageStats;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.UsageStats;

public class SelectRandomBasedOnUsageAndDepth {
  
  
  public INode<NodeType> selectNode(UsageStats usage, Random rnd){
    
		 List<INode<NodeType>> sorted = usage.getNodeSet().stream()
        .filter(e -> !e.isLeaf())
				.sorted((INode<NodeType> en1, INode<NodeType> en2) -> Double.compare(usage.getUsage(en1)/((double) usage.getSize(en1)), usage.getUsage(en2)/((double) usage.getSize(en2))))
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
  
  private static final double sp =1.5;
  
	private Double rankOf(int index, int size) {
		return 2.0 - sp + (2 * (sp - 1.0) * ((index ) / (size - 1.0)));
	}
  

}
