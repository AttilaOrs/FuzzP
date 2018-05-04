package UnifiedGp.TreeDist;

import structure.TriFunction;

public class SimpleCostFunction<T> implements TriFunction<TreeDistNode<T>, TreeDistNode<T>, TreeDistOperationType, Integer>{

  @Override
  public Integer apply(TreeDistNode<T> t, TreeDistNode<T> u, TreeDistOperationType treeOperationType) {
    return treeOperationType == TreeDistOperationType.KEEP ? 0 : 1;
  }

}
