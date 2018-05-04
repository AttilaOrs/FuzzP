package UnifiedGp.TreeDist;

public interface TreeDistOperation {
  int EMPTY_INDEX = -1;

  TreeDistOperationType getType();
  int getLeftIndex();
  int getRightIndex();
  int getCost();

}
