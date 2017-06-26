package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class InputLeaf implements ILeaf<NodeType> {


  private final InputType type;
  private final int inpNr;
  private final static String inputTransmitterTwoXTwoStr = "{" +
  "[<-2,-2><-2,-2><-2,-2><-2,-2><-2,-2><-2,-2>]" + //
  "[<-1,-1><-1,-1><-1,-1><-1,-1><-1,-1><-1,-1>]" + //
  "[< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0>< 0, 0>]" + //
  "[< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1>< 1, 1>]" + //
  "[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>]" + //
  "[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" + //
  "}";
  private final static String inputTransmitterTwoXOneStr = "{" +
  "[<-2><-2><-2><-2><-2><-2>]" + //
  "[<-1><-1><-1><-1><-1><-1>]" + //
  "[< 0>< 0>< 0>< 0>< 0>< 0>]" + //
  "[< 1>< 1>< 1>< 1>< 1>< 1>]" + //
  "[< 2>< 2>< 2>< 2>< 2>< 2>]" + //
  "[<FF><FF><FF><FF><FF><FF>]" + //
  "}";
  public static final UnifiedTwoXOneTable inputTransmitterTwoXOne = UnifiedTableParser
      .parseUnifiedTwoXOneTable(inputTransmitterTwoXOneStr);
  public static final UnifiedTwoXTwoTable inputTransmitterTwoXTwo = UnifiedTableParser
      .parseUnifiedTwoXTwoTable(inputTransmitterTwoXTwoStr);

  public InputLeaf(InputType type, int inpNr) {
    this.type = type;
    this.inpNr = inpNr;
  }

  @Override
  public NodeType getType() {
    return NodeType.Inp;
  }

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

  @Override
  public ILeaf<NodeType> myClone() {
    return new InputLeaf(type, inpNr);
  }

}
