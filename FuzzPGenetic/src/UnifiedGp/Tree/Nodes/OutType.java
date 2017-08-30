package UnifiedGp.Tree.Nodes;

import static core.UnifiedPetriLogic.UnifiedTableParser.parseUnifiedOneXTwoTable;

import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;

public enum OutType {
  Copy(parseUnifiedOneXTwoTable(OutputLeaf.CopyTable), "c");

  public final UnifiedOneXTwoTable table;
  public final String symbol;

  private OutType(UnifiedOneXTwoTable table, String symbol) {
    this.table = table;
    this.symbol = symbol;
  }

}
