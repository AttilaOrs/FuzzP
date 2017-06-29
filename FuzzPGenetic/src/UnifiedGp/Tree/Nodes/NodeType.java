package UnifiedGp.Tree.Nodes;

import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;

public enum NodeType {
  Seq, Conc, Selc, Loop, Add, Multiply, PosNegSplit, /* ops */
  Delay, Inp, Out, Block, Memory, Const, Inv, Negate; /* leafs */

  private static final String posNegSplitStr = "{[<FF,-2><FF,-1><FF,FF><1,FF>< 2,FF><FF,FF>]}";
  public static final UnifiedOneXTwoTable posNegSplitTable = UnifiedTableParser
      .parseUnifiedOneXTwoTable(posNegSplitStr);

}
