package UnifiedGp.Tree;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public interface TreeBuilderConfig<TNodeType> {

  public interface RandomInnerodeCreator<TNodeType> {
    
    public IInnerNode<TNodeType> createInnerNode(Random rnd, INode<TNodeType> fi, INode<TNodeType> se);

  }

  public double getLeafOrInnerNode();

  public int getMaxDepth();

  public Map<TNodeType, Double> getLeafProbabilities();
  
  public Map<TNodeType, Double> getOperatorProbabilities();
  
  public Function<Random, INode<TNodeType>> getLeafFactory(TNodeType t);

  public RandomInnerodeCreator<TNodeType> getInnerNodeFactory(TNodeType t);



}
