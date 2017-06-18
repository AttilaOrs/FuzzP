package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class InputLeaf implements ILeaf<NodeType, InputType> {


  private final InputType type;
  private final int inpNr;

  public InputLeaf(InputType type, int inpNr) {
    this.type = type;
    this.inpNr = inpNr;
  }

  @Override
  public NodeType getType() {
    return NodeType.Inp;
  }

  @Override
  public InputType getSubtype() {
    return type;
  }

  public int inpNr() {
    return this.inpNr;
  }

  public final static String readerBlockingTable = "{" +
      "[<-2><-2><-2><-2><-2><FF>]" + //
      "[<-1><-1><-1><-1><-1><FF>]" + //
      "[< 0>< 0>< 0>< 0>< 0><FF>]" + //
      "[< 1>< 1>< 1>< 1>< 1><FF>]" + //
      "[< 2>< 2>< 2>< 2>< 2><FF>]" + //
      "[<FF><FF><FF><FF><FF><FF>]" + //
      "}";

}
