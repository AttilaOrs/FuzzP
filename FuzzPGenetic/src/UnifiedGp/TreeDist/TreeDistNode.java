package UnifiedGp.TreeDist;

import java.util.List;

public interface TreeDistNode<T> {
  TreeDistNode<T> getParent();

  List<TreeDistNode<T>> getChildren();

  T getData();

  void setData(T data);

  void setParent(TreeDistNode<T> treeNode);

  default void addChild(TreeDistNode<T> child) {
      getChildren().add(child);
      child.setParent(this);
  }

  default boolean isRoot() {
      return getParent() == null;
  }

  default boolean isLeaf() {
      return getChildren().size() == 0;
  } 

}
