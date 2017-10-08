package UnifiedGp.Tree.Nodes;

import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;

public enum NodeType {
  Seq("@"), Conc("&"), Selc("?"), Loop("#"), Add("+"), Multiply("*"), PosNegSplit("%"), /* ops */
  Delay("d"), Inp("i"), Out("o"), Block("b"), Memory("m"), Const("c"), Inv("v"), Negate("n"); /* leafs */

  private NodeType(String symbol) {
    this.symbol = symbol;
  }

  public final String symbol;

  private static final String posNegSplitStr = "{[<FF,-2><FF,-1><FF,FF><1,FF>< 2,FF><FF,FF>]}";
  public static final UnifiedOneXTwoTable posNegSplitTable = UnifiedTableParser
      .parseUnifiedOneXTwoTable(posNegSplitStr);

  public boolean isBlock() {
    return this == NodeType.Block;
  }

  public boolean isSeq() {
    return this == NodeType.Seq;
  }

  public boolean isPosNegSplit() {
    return this == NodeType.PosNegSplit;
  }

}
