package UnifiedGp.Tree.Nodes;


import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;

public enum InputType 
{
  ReaderBlocking(UnifiedTableParser.parseUnifiedTwoXOneTable(InputLeaf.readerBlockingTable));
  public final UnifiedTwoXOneTable table;

  private InputType(UnifiedTwoXOneTable table) {
    this.table = table;
  }



}
