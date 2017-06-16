package UnifiedGp.Tree;

public interface IInnerNode<TType> extends INode<TType> {

  INode<TType> getFirstChild();

  INode<TType> getSecondChild();

  @Override
  default public boolean isLeaf() {
    return false;
  }

}
