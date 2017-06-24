package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;

public class BlockLeaf implements ILeaf<NodeType> {

  @Override
  public NodeType getType() {
    return NodeType.Block;
  }

  private static final String tableStr = "{[<FF><FF><FF><FF><FF><FF>]}";
  public static final UnifiedOneXOneTable table = UnifiedTableParser.parseUnifiedOneXOneTable(tableStr);
  

}
