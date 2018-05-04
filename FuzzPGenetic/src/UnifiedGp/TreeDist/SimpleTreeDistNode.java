package UnifiedGp.TreeDist;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleTreeDistNode<T> implements TreeDistNode<T> {
  private T data;
  private TreeDistNode<T> parent;
  private List<TreeDistNode<T>> children;

  public SimpleTreeDistNode(T data) {
      this(data, null);
  }

  public SimpleTreeDistNode(TreeDistNode<T> tree) {
      final SimpleTreeDistNode<T> copy = copy(tree);
      this.data = copy.data;
      this.children = copy.children;
      this.children.forEach(child -> child.setParent(this));
  }

  public SimpleTreeDistNode(T data, TreeDistNode<T> parent) {
      this.data = data;
      this.parent = parent;
      this.children = new ArrayList<>();
  }

  @Override
  public TreeDistNode<T> getParent() {
      return parent;
  }

  @Override
  public List<TreeDistNode<T>> getChildren() {
      return children;
  }

  @Override
  public T getData() {
      return data;
  }

  @Override
  public void setParent(TreeDistNode<T> treeNode) {
      this.parent = treeNode;
  }

  @Override
  public void setData(T data) {
      this.data = data;
  }

  @Override
  public String toString() {
      return data.toString();
  }

  @SuppressWarnings("unchecked")
  private <V> SimpleTreeDistNode<V> copy(TreeDistNode<V> tree) {
      final List<SimpleTreeDistNode<V>> childrenCopy =
              tree.getChildren().stream().map((Function<TreeDistNode<V>, SimpleTreeDistNode<V>>) this::copy).collect(Collectors.toList());
      final SimpleTreeDistNode rootCopy = new SimpleTreeDistNode(tree.getData(), null);
      childrenCopy.forEach(rootCopy::addChild);
      return rootCopy;
  }

}
