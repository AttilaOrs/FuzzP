package UnifiedGp.Tree.Nodes;


import static core.UnifiedPetriLogic.UnifiedTableParser.parseUnifiedTwoXOneTable;

import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;

public enum InputType 
{
//@formatter:off
  ReaderBlocking(parseUnifiedTwoXOneTable(InputLeaf.readerBlockingTable), "br"),
  ReaderNonBlocking(parseUnifiedTwoXOneTable(InputLeaf.readerNonBlocking),"nr"),
  EnableIfPhi(parseUnifiedTwoXOneTable(InputLeaf.enableIfPhiTable),"eip"),
  EnableIfNonPhi(parseUnifiedTwoXOneTable(InputLeaf.enableIfNonPhiTable),"enp"),
  EnableIfZero(parseUnifiedTwoXOneTable(InputLeaf.enableIfZero),"eiz"),
  EnableIfNotZero(parseUnifiedTwoXOneTable(InputLeaf.enableIfNotZero),"enz"),
  ShiftUp(parseUnifiedTwoXOneTable(InputLeaf.shiftUpIfEvenTable),"su"),
  ShiftDown(parseUnifiedTwoXOneTable(InputLeaf.shiftDownIfNonPhiTable),"sd");
//@formatter:on 
  
  public final UnifiedTwoXOneTable table;
  public final String symbol;

  private InputType(UnifiedTwoXOneTable table, String symbol) {
    this.table = table;
    this.symbol = symbol;
  }



}
