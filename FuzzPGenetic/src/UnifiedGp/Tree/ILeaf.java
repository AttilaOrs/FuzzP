package UnifiedGp.Tree;

public interface ILeaf<TType> extends INode<TType> {


  @Override
  default public boolean isLeaf() {
    return true;
  }

}
