package UnifiedGp.Tree;

public interface ILeaf<TType, TSubtype> extends INode<TType> {

  TSubtype getSubtype();

  @Override
  default public boolean isLeaf() {
    return true;
  }

}
