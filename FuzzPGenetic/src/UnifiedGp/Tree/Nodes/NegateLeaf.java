package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;

public class NegateLeaf implements ILeaf<NodeType> {

  private static final String tableStr = "{[< 2>< 1>< 0><-1><-2><FF>]}";
  public static final UnifiedOneXOneTable table = UnifiedTableParser.parseUnifiedOneXOneTable(tableStr);

  @Override
  public NodeType getType() {
    return NodeType.Negate;
  }


}
