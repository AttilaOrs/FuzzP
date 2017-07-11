package UnifiedGp.Tree.Nodes;


import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;

public enum InputType 
{
//@formatter:off
  ReaderBlocking(UnifiedTableParser.parseUnifiedTwoXOneTable(InputLeaf.readerBlockingTable)),
  ReaderNonBlocking(UnifiedTableParser.parseUnifiedTwoXOneTable(InputLeaf.readerNonBlocking)),
  EnableIfPhi(UnifiedTableParser.parseUnifiedTwoXOneTable(InputLeaf.enableIfPhiTable)),
  EnableIfNonPhi(UnifiedTableParser.parseUnifiedTwoXOneTable(InputLeaf.enableIfNonPhiTable)),
  ShiftUp(UnifiedTableParser.parseUnifiedTwoXOneTable(InputLeaf.shiftUpIfEvenTable)),
  ShiftDown(UnifiedTableParser.parseUnifiedTwoXOneTable(InputLeaf.shiftDownIfNonPhiTable));
//@formatter:on 
  
  public final UnifiedTwoXOneTable table;

  private InputType(UnifiedTwoXOneTable table) {
    this.table = table;
  }



}
