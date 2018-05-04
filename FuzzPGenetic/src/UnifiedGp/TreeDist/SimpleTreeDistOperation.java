package UnifiedGp.TreeDist;

public class SimpleTreeDistOperation implements TreeDistOperation{
  private final TreeDistOperationType type;
  private final int leftIndex;
  private final int rightIndex;
  private final int cost;

  public SimpleTreeDistOperation(TreeDistOperationType type, int leftIndex, int rightIndex, int cost) {
      this.type = type;
      this.leftIndex = leftIndex;
      this.rightIndex = rightIndex;
      this.cost = cost;
  }

  @Override
  public TreeDistOperationType getType() {
      return type;
  }

  @Override
  public int getLeftIndex() {
      return leftIndex;
  }

  @Override
  public int getRightIndex() {
      return rightIndex;
  }

  @Override
  public int getCost() {
      return cost;
  }

  public static SimpleTreeDistOperation insert(int index, int cost) {
      return new SimpleTreeDistOperation(TreeDistOperationType.INSERT, TreeDistOperation.EMPTY_INDEX, index, cost);
  }

  public static SimpleTreeDistOperation delete(int index, int cost) {
      return new SimpleTreeDistOperation(TreeDistOperationType.DELETE, index, TreeDistOperation.EMPTY_INDEX, cost);
  }

  public static SimpleTreeDistOperation replace(int leftIndex, int rightIndex, int cost) {
      return new SimpleTreeDistOperation(TreeDistOperationType.REPLACE, leftIndex, rightIndex, cost);
  }

  public static SimpleTreeDistOperation keep(int leftIndex, int rightIndex, int cost) {
      return new SimpleTreeDistOperation(TreeDistOperationType.KEEP, leftIndex, rightIndex, cost);
  }

  @Override
  public String toString() {
      return String.format("%s (%d, %d)@%d", type.name(), leftIndex, rightIndex, cost);
  }

}
