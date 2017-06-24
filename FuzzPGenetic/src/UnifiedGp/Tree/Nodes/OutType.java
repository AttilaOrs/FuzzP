package UnifiedGp.Tree.Nodes;

import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;

public enum OutType {
  Copy(UnifiedTableParser.parseUnifiedOneXTwoTable(OutputLeaf.CopyTable));

  public final UnifiedOneXTwoTable table;

  private OutType(UnifiedOneXTwoTable table) {
    this.table = table;
  }

}
